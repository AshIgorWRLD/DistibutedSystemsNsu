package ru.nsu.ashikhmin.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.ashikhmin.manager.dto.enums.RequestStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HashStatusResponseDto {
    private RequestStatus status;
    private List<String> data;
}
