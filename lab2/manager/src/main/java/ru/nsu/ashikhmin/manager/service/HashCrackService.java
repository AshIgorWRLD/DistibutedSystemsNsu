package ru.nsu.ashikhmin.manager.service;

import ru.nsu.ashikhmin.manager.dto.HashStatusResponseDto;
import ru.nsu.ashikhmin.manager.service.model.HashRequestStatus;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

import java.util.UUID;

public interface HashCrackService {

    UUID crackHash(String hash, int maxLength);
    HashStatusResponseDto getHashStatus(UUID requestId);
    void handleWorkerCallback(CrackHashWorkerResponse crackHashWorkerResponse);
}
