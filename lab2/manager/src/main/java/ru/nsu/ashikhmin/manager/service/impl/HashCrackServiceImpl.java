package ru.nsu.ashikhmin.manager.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.nsu.ashikhmin.manager.config.properties.ConfigProperties;
import ru.nsu.ashikhmin.manager.config.properties.hash_crack.ManagerProperties;
import ru.nsu.ashikhmin.manager.dto.HashStatusResponseDto;
import ru.nsu.ashikhmin.manager.dto.enums.RequestStatus;
import ru.nsu.ashikhmin.manager.service.HashCrackService;
import ru.nsu.ashikhmin.manager.service.HashStatusService;
import ru.nsu.ashikhmin.manager.service.model.HashRequestStatus;
import ru.nsu.ashikhmin.manager.service.rabbitmq.RabbitMQProducer;
import ru.nsu.ashikhmin.manager.utils.Converter;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@Service
public class HashCrackServiceImpl implements HashCrackService {

    private final CrackHashManagerRequest.Alphabet alphabet;
    private final int managerExpireTime;
    private final RabbitMQProducer rabbitMQProducer;
    private final HashStatusService hashStatusService;
    private final int workersAmount;
    private static final String EMPTY_REGEX = "";

    public HashCrackServiceImpl(ConfigProperties configProperties, RabbitMQProducer rabbitMQProducer,
                                HashStatusService hashStatusService) {
        this.alphabet = new CrackHashManagerRequest.Alphabet();

        ManagerProperties managerProperties = configProperties.receiveManagerProperties();
        String alphabetString = managerProperties.getAlphabet();
        List.of(alphabetString.split(EMPTY_REGEX)).forEach(alphabet.getSymbols()::add);

        this.workersAmount = managerProperties.getWorkersAmount();
        this.managerExpireTime = managerProperties.getExpireTime();
        this.rabbitMQProducer = rabbitMQProducer;
        this.hashStatusService = hashStatusService;
    }

    @Override
    public UUID crackHash(String hash, int maxLength) {
        HashRequestStatus hashRequestStatus = hashStatusService.insertHashRequestStatusAndReturn(new HashRequestStatus(workersAmount));
        IntStream.range(0, workersAmount).forEach(i -> {
            CrackHashManagerRequest crackHashManagerRequest = new CrackHashManagerRequest();
            crackHashManagerRequest.setHash(hash);
            crackHashManagerRequest.setMaxLength(maxLength);
            crackHashManagerRequest.setRequestId(hashRequestStatus.getRequestId());
            crackHashManagerRequest.setPartNumber(i);
            crackHashManagerRequest.setPartCount(workersAmount);
            crackHashManagerRequest.setAlphabet(alphabet);
            sendTaskToWorker(crackHashManagerRequest);
        });
        return UUID.fromString(hashRequestStatus.getRequestId());
    }

    @Override
    public HashStatusResponseDto getHashStatus(UUID requestId) {
        return Converter.toHashStatusResponseDto(hashStatusService.findByHashRequestId(requestId.toString()));
    }

    @Override
    public void handleWorkerCallback(CrackHashWorkerResponse crackHashWorkerResponse) {
        HashRequestStatus hashRequestStatus = hashStatusService.findByHashRequestId(crackHashWorkerResponse.getRequestId());
        if (hashRequestStatus.getStatus() == RequestStatus.IN_PROGRESS) {
            if (crackHashWorkerResponse.getAnswers() != null) {
                hashRequestStatus.getData().addAll(crackHashWorkerResponse.getAnswers().getWords());
                hashRequestStatus.getNotAnsweredWorkers().remove(crackHashWorkerResponse.getPartNumber());
                if (hashRequestStatus.getNotAnsweredWorkers().isEmpty()) {
                    hashRequestStatus.setStatus(RequestStatus.READY);
                }
                hashStatusService.saveHashRequestStatus(hashRequestStatus);
            }
        }
    }

    private void sendTaskToWorker(CrackHashManagerRequest crackHashManagerRequest) {
        if (!rabbitMQProducer.trySendMessage(crackHashManagerRequest)) {
            hashStatusService.insertHashRequest(crackHashManagerRequest);
        }
    }

    @Scheduled(fixedDelay = 10000)
    void expireRequests() {
        hashStatusService.findAllHashRequestStatusesByUpdatedBeforeAndStatusEquals(
                        new Date(System.currentTimeMillis() - convertMinutesToSeconds(managerExpireTime)),
                        RequestStatus.IN_PROGRESS)
                .forEach(hashRequestStatus -> {
                    hashRequestStatus.setStatus(RequestStatus.ERROR);
                    hashStatusService.saveHashRequestStatus(hashRequestStatus);
                });
    }

    private int convertMinutesToSeconds(int minuteValue) {
        return minuteValue * 60 * 1000;
    }

}
