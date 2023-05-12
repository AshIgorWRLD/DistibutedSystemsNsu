package ru.nsu.ashikhmin.worker.service;

import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

import java.util.List;

public interface ManagerService {

    void sendResponse(String requestId, int partNumber, List<String> answers);
}
