package ru.nsu.ashikhmin.worker.service;

import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

public interface TaskProcessingService {

    CrackHashWorkerResponse processTask(CrackHashManagerRequest request);
}
