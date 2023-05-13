package ru.nsu.ashikhmin.manager.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nsu.ashikhmin.manager.dto.enums.RequestStatus;
import ru.nsu.ashikhmin.manager.service.model.HashRequestStatus;

import java.util.Date;
import java.util.List;

public interface HashRequestStatusRepository extends MongoRepository<HashRequestStatus, String> {
    HashRequestStatus findByRequestId(String requestId);

    List<HashRequestStatus> findAllByUpdatedBeforeAndStatusEquals(Date timestamp, RequestStatus status);
}
