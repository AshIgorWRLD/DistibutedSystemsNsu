package ru.nsu.ashikhmin.manager.service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Document(collection = "requests")
public class HashRequest {
    @Id
    private String id;
    private CrackHashManagerRequest request;

    public HashRequest(CrackHashManagerRequest request) {
        this.id = request.getRequestId();
        this.request = request;
    }
}
