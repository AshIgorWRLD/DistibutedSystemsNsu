package ru.nsu.ashikhmin.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.ashikhmin.api.dto.enums.HashStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HashStatusResponseDto {
    private HashStatus status;
    private List<String> data;
}
