package ru.nsu.ashikhmin.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HashCrackDto {
    private String hash;
    private int maxLength;
}
