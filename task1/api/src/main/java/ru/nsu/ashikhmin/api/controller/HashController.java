package ru.nsu.ashikhmin.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.ashikhmin.api.configuration.ControllerUrls;
import ru.nsu.ashikhmin.api.dto.HashCrackDto;
import ru.nsu.ashikhmin.api.dto.HashCrackResponseDto;

import java.util.UUID;

public interface HashController {

    @PostMapping(value = ControllerUrls.HASH_CRACK_URL)
    ResponseEntity<HashCrackResponseDto> getHashCrack(@RequestBody HashCrackDto hashCrackDto);

    @GetMapping(value = ControllerUrls.HASH_STATUS_URL)
    ResponseEntity<HashCrackResponseDto> getHashStatus(@RequestParam(name = "requestId") UUID requestId);
}
