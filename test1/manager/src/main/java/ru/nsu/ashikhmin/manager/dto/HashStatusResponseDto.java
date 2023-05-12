package ru.nsu.ashikhmin.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.ashikhmin.manager.dto.enums.HashStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HashStatusResponseDto {
    private HashStatus status;
    private List<String> data;
}
