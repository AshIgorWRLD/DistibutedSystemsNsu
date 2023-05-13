package ru.nsu.ashikhmin.manager.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.nsu.ashikhmin.manager.service.model.HashRequest;

public interface HashRequestsRepository extends MongoRepository<HashRequest, String> {
}
