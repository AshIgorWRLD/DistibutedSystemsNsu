package ru.nsu.ashikhmin.manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nsu.ashikhmin.manager.config.ControllerUrls;
import ru.nsu.ashikhmin.manager.dto.CallbackResponseDto;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

public interface InternalWorkerController {

    @PostMapping(value = ControllerUrls.WORKER_CALLBACK_URL)
    ResponseEntity<CallbackResponseDto> handleWorkerCallback(@RequestBody CrackHashWorkerResponse crackHashWorkerResponse);
}
