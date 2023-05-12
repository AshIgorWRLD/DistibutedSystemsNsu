package ru.nsu.ashikhmin.manager.service.impl;

import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.nsu.ashikhmin.manager.config.properties.ConfigProperties;
import ru.nsu.ashikhmin.manager.dto.enums.HashRequestStatus;
import ru.nsu.ashikhmin.manager.service.HashCrackService;
import ru.nsu.ashikhmin.manager.service.WorkerService;
import ru.nsu.ashikhmin.manager.service.model.HashRequestStatusModel;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class HashCrackServiceImpl implements HashCrackService {
    private final Map<UUID, HashRequestStatusModel> requests;
    private final ArrayDeque<Pair<UUID, Timestamp>> requestList;
    private final CrackHashManagerRequest.Alphabet alphabet;
    private final int managerExpireTime;
    private final WorkerService workerService;

    public HashCrackServiceImpl(ConfigProperties configProperties, WorkerService workerService) {
        this.requests = new ConcurrentHashMap<>();
        this.requestList = new ArrayDeque<>();
        this.alphabet = new CrackHashManagerRequest.Alphabet();

        this.managerExpireTime = configProperties.receiveManagerProperties().getExpireTime();

        this.workerService = workerService;
    }

    @Override
    public UUID crackHash(String hash, int maxLength) {
        UUID id = UUID.randomUUID();
        requests.put(id, new HashRequestStatusModel());
        CrackHashManagerRequest crackHashManagerRequest = new CrackHashManagerRequest();
        crackHashManagerRequest.setHash(hash);
        crackHashManagerRequest.setMaxLength(maxLength);
        crackHashManagerRequest.setRequestId(id.toString());
        crackHashManagerRequest.setPartNumber(1);
        crackHashManagerRequest.setPartCount(1);
        crackHashManagerRequest.setAlphabet(alphabet);

        boolean workerResponse = workerService.sendRequest(id, crackHashManagerRequest);

        if (!workerResponse) {
            return null;
        }

        requestList.add(Pair.of(id, new Timestamp(System.currentTimeMillis())));
        return id;
    }

    @Override
    public HashRequestStatusModel getHashStatus(UUID requestId) {
        return requests.get(requestId);
    }

    @Override
    public void handleWorkerCallback(CrackHashWorkerResponse crackHashWorkerResponse) {
        HashRequestStatusModel requestStatus = requests.get(UUID.fromString(crackHashWorkerResponse.getRequestId()));
        if (requestStatus.getStatus() == HashRequestStatus.IN_PROGRESS) {
            if (crackHashWorkerResponse.getAnswers() != null) {
                requestStatus.getData().addAll(crackHashWorkerResponse.getAnswers().getWords());
                requestStatus.setStatus(HashRequestStatus.READY);
            }
        }
    }

    @Scheduled(fixedDelay = 10000)
    private void expireRequests() {
        requestList.removeIf(pair -> {
            if (System.currentTimeMillis() - pair.getSecond().getTime() > convertMinutesToSeconds(managerExpireTime)) {
                requests.computeIfPresent(pair.getFirst(), (s, requestStatus) -> {
                    if (requestStatus.getStatus().equals(HashRequestStatus.IN_PROGRESS)) {
                        requestStatus.setStatus(HashRequestStatus.ERROR);
                    }
                    return requestStatus;
                });
                return true;
            }
            return false;
        });
    }

    private int convertMinutesToSeconds(int minuteValue) {
        return minuteValue * 60 * 1000;
    }
}
