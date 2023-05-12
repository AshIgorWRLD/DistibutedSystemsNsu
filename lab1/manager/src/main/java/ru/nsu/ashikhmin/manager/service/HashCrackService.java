package ru.nsu.ashikhmin.manager.service;

import ru.nsu.ashikhmin.manager.service.model.HashRequestStatusModel;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

import java.util.UUID;

public interface HashCrackService {

    UUID crackHash(String hash, int maxLength);
    HashRequestStatusModel getHashStatus(UUID requestId);
    void handleWorkerCallback(CrackHashWorkerResponse crackHashWorkerResponse);
}
