package ru.nsu.ashikhmin.manager.utils;

import lombok.experimental.UtilityClass;
import ru.nsu.ashikhmin.manager.dto.HashStatusResponseDto;
import ru.nsu.ashikhmin.manager.service.model.HashRequestStatus;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class Converter {

    public static HashStatusResponseDto toHashStatusResponseDto(HashRequestStatus hashRequestStatus) {
        if (hashRequestStatus == null) {
            return null;
        }

        List<String> resultData = null;

        List<String> list = hashRequestStatus.getResult();
        if (list != null) {
            resultData = new ArrayList<>(list);
        }

       return new HashStatusResponseDto(hashRequestStatus.getStatus(), resultData);
    }
}
