package ru.nsu.ashikhmin.manager.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.ashikhmin.manager.dto.enums.HashRequestStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HashRequestStatusModel {
    private HashRequestStatus status;
    private List<String> data;
}
