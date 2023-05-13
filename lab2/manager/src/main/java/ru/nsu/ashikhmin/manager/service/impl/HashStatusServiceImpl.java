package ru.nsu.ashikhmin.manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.ashikhmin.manager.dto.enums.RequestStatus;
import ru.nsu.ashikhmin.manager.repo.HashRequestStatusRepository;
import ru.nsu.ashikhmin.manager.repo.HashRequestsRepository;
import ru.nsu.ashikhmin.manager.service.HashStatusService;
import ru.nsu.ashikhmin.manager.service.model.HashRequest;
import ru.nsu.ashikhmin.manager.service.model.HashRequestStatus;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashStatusServiceImpl implements HashStatusService {

    private final HashRequestsRepository hashRequestsRepository;
    private final HashRequestStatusRepository hashRequestStatusRepository;

    @Override
    public HashRequestStatus findByHashRequestId(String hashRequestId) {
        return hashRequestStatusRepository.findByRequestId(hashRequestId);
    }

    @Override
    public HashRequestStatus insertHashRequestStatusAndReturn(HashRequestStatus hashRequestStatus) {
        return hashRequestStatusRepository.insert(hashRequestStatus);
    }

    @Override
    public void saveHashRequestStatus(HashRequestStatus hashRequestStatus) {
        hashRequestStatusRepository.save(hashRequestStatus);
    }

    @Override
    public List<HashRequestStatus> findAllHashRequestStatusesByUpdatedBeforeAndStatusEquals(Date timestamp,
                                                                                            RequestStatus status) {
        return hashRequestStatusRepository.findAllByUpdatedBeforeAndStatusEquals(timestamp, status);
    }

    @Override
    public void insertHashRequest(CrackHashManagerRequest crackHashManagerRequest) {
        hashRequestsRepository.insert(new HashRequest(crackHashManagerRequest));
    }
}
