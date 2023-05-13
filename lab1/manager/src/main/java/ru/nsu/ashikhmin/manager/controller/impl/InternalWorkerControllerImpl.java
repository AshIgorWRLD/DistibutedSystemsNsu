package ru.nsu.ashikhmin.manager.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.nsu.ashikhmin.manager.controller.InternalWorkerController;
import ru.nsu.ashikhmin.manager.dto.CallbackResponseDto;
import ru.nsu.ashikhmin.manager.service.HashCrackService;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

@Controller
@Slf4j
@RequiredArgsConstructor
public class InternalWorkerControllerImpl implements InternalWorkerController {

    private final HashCrackService hashCrackService;

    @Override
    public ResponseEntity<CallbackResponseDto> handleWorkerCallback(CrackHashWorkerResponse crackHashWorkerResponse) {
        log.info("Received response from worker: {}, {}, {}", crackHashWorkerResponse.getRequestId(), crackHashWorkerResponse.getAnswers(), crackHashWorkerResponse.getPartNumber());
        hashCrackService.handleWorkerCallback(crackHashWorkerResponse);
        return new ResponseEntity<>(new CallbackResponseDto(), HttpStatus.OK);
    }
}
