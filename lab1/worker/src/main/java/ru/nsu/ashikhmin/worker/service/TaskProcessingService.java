package ru.nsu.ashikhmin.worker.service;

import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

public interface TaskProcessingService {

    void processTask(CrackHashManagerRequest request);
}
