package ru.nsu.ashikhmin.impl.controller.impl;

import org.springframework.http.ResponseEntity;
import ru.nsu.ashikhmin.api.controller.HashController;
import ru.nsu.ashikhmin.api.dto.HashCrackDto;
import ru.nsu.ashikhmin.api.dto.HashCrackResponseDto;

import java.util.UUID;

public class HashControllerImpl implements HashController {
    @Override
    public ResponseEntity<HashCrackResponseDto> getHashCrack(HashCrackDto hashCrackDto) {
        return null;
    }

    @Override
    public ResponseEntity<HashCrackResponseDto> getHashStatus(UUID requestId) {
        return null;
    }
}
