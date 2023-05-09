package ru.nsu.ashikhmin.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HashCrackDto {
    private String hash;
    private Integer maxLength;
}
