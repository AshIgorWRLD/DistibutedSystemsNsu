package ru.nsu.ashikhmin.manager.service;

import ru.nsu.ashikhmin.manager.service.model.HashRequestStatusModel;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

public interface HashCrackService {

    String crackHash(String hash, int maxLength);

    HashRequestStatusModel getHashStatus(String requestId);

    void handleWorkerCallback(CrackHashWorkerResponse crackHashWorkerResponse);
}
