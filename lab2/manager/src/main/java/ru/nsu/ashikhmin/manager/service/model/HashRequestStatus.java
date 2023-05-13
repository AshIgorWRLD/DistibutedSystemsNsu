package ru.nsu.ashikhmin.manager.service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.nsu.ashikhmin.manager.dto.enums.RequestStatus;

import java.util.*;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document("RequestStatus")
public class HashRequestStatus {
    @Id
    private String requestId;

    private RequestStatus status;

    private List<String> data;

    private HashSet<Integer> notAnsweredWorkers;

    private Date updated;

    public HashRequestStatus(int workersCount) {
        this.requestId = UUID.randomUUID().toString();
        this.status = RequestStatus.IN_PROGRESS;
        this.updated = new Date(System.currentTimeMillis());
        data = new ArrayList<>();
        notAnsweredWorkers = new HashSet<>();
        for (int i = 0; i < workersCount; i++) {
            notAnsweredWorkers.add(i);
        }
    }
}
