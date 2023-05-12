package ru.nsu.ashikhmin.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.ashikhmin.manager.dto.enums.HashRequestStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HashStatusResponseDto {
    private HashRequestStatus status;
    private List<String> data;
}
