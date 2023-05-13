package ru.nsu.ashikhmin.manager.service;

import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.util.UUID;

public interface WorkerService {
    boolean sendRequest(String id, CrackHashManagerRequest crackHashManagerRequest);
}
