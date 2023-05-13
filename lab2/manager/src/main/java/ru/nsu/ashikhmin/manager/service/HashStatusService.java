package ru.nsu.ashikhmin.manager.service;

import ru.nsu.ashikhmin.manager.dto.enums.RequestStatus;
import ru.nsu.ashikhmin.manager.service.model.HashRequestStatus;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.util.Date;
import java.util.List;

public interface HashStatusService {
    HashRequestStatus findByHashRequestId(String hashRequestId);
    HashRequestStatus insertHashRequestStatusAndReturn(HashRequestStatus hashRequestStatus);

    void saveHashRequestStatus(HashRequestStatus hashRequestStatus);

    List<HashRequestStatus> findAllHashRequestStatusesByUpdatedBeforeAndStatusEquals(Date timestamp, RequestStatus status);

    void insertHashRequest(CrackHashManagerRequest crackHashManagerRequest);
}
