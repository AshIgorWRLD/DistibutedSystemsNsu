package ru.nsu.ashikhmin.manager.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.nsu.ashikhmin.manager.controller.HashController;
import ru.nsu.ashikhmin.manager.dto.HashCrackDto;
import ru.nsu.ashikhmin.manager.dto.HashCrackResponseDto;
import ru.nsu.ashikhmin.manager.dto.HashStatusResponseDto;
import ru.nsu.ashikhmin.manager.service.HashCrackService;

import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HashControllerImpl implements HashController {

    private final HashCrackService hashCrackService;

    @Override
    public ResponseEntity<HashCrackResponseDto> getHashCrack(HashCrackDto hashCrackDto) {
        log.info("Received request to crack hash: {}", hashCrackDto);

        UUID uuid = hashCrackService.crackHash(hashCrackDto.getHash(), hashCrackDto.getMaxLength());

        return new ResponseEntity<>(new HashCrackResponseDto(uuid), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HashStatusResponseDto> getHashStatus(UUID requestId) {
        log.info("Received request to get status of request: {}", requestId);

        return new ResponseEntity<>(hashCrackService.getHashStatus(requestId), HttpStatus.OK);
    }
}
