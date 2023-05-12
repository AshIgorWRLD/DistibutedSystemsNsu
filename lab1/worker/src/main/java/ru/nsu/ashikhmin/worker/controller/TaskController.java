package ru.nsu.ashikhmin.worker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.nsu.ashikhmin.worker.config.ControllerUrls;
import ru.nsu.ashikhmin.worker.dto.TaskResponseDto;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

public interface TaskController {

    @PostMapping(value = ControllerUrls.GET_TASK_URL)
    ResponseEntity<TaskResponseDto> getTask(@RequestBody CrackHashManagerRequest crackHashManagerRequest);
}
