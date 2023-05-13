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
import ru.nsu.ashikhmin.manager.service.model.HashRequestStatusModel;
import ru.nsu.ashikhmin.manager.utils.Converter;

import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HashControllerImpl implements HashController {

    private final HashCrackService hashCrackService;

    @Override
    public ResponseEntity<HashCrackResponseDto> getHashCrack(HashCrackDto hashCrackDto) {
        log.info("Received request to crack hash: {}", hashCrackDto);

        String id = hashCrackService.crackHash(hashCrackDto.getHash(), hashCrackDto.getMaxLength());

        return new ResponseEntity<>(new HashCrackResponseDto(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HashStatusResponseDto> getHashStatus(String requestId) {
        log.info("Received request to get status of request: {}", requestId);

        HashRequestStatusModel hashStatus = hashCrackService.getHashStatus(requestId);

        return new ResponseEntity<>(Converter.toHashStatusResponseDto(hashStatus), HttpStatus.OK);
    }
}
