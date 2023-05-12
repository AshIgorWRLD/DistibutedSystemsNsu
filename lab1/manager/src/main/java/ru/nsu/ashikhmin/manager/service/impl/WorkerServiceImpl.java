package ru.nsu.ashikhmin.manager.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.ashikhmin.manager.config.properties.ConfigProperties;
import ru.nsu.ashikhmin.manager.dto.WorkerResponseDto;
import ru.nsu.ashikhmin.manager.service.WorkerService;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final RestTemplate restTemplate;
    private final ConfigProperties configProperties;

    @Override
    public boolean sendRequest(UUID id, CrackHashManagerRequest crackHashManagerRequest) {
        WorkerResponseDto response;
        try {
            log.info("Sending request with id : {}, to worker: {}", id, crackHashManagerRequest);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));

            ResponseEntity<WorkerResponseDto> exchange = restTemplate.exchange(
                    configProperties.receiveWorkerProperties().getUrl(),
                    HttpMethod.POST,
                    new HttpEntity<>(crackHashManagerRequest, headers),
                    WorkerResponseDto.class);
            response = exchange.getBody();

        } catch (Exception e) {
            log.error("Error while sending request to worker", e);
            return false;
        }
        if (response == null) {
            return false;
        }
        return response.isStatus();
    }
}
