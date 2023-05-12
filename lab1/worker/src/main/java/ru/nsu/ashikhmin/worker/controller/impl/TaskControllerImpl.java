package ru.nsu.ashikhmin.worker.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.nsu.ashikhmin.worker.controller.TaskController;
import ru.nsu.ashikhmin.worker.dto.TaskResponseDto;
import ru.nsu.ashikhmin.worker.service.TaskProcessingService;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {

    private final TaskProcessingService taskProcessingService;

    @Override
    public ResponseEntity<TaskResponseDto> getTask(CrackHashManagerRequest crackHashManagerRequest) {
        log.info("getTask() : {}", crackHashManagerRequest);
        taskProcessingService.processTask(crackHashManagerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new TaskResponseDto());
    }
}
