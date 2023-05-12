package ru.nsu.ashikhmin.worker.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.nsu.ashikhmin.worker.config.properties.ConfigProperties;
import ru.nsu.ashikhmin.worker.dto.TaskResponseDto;
import ru.nsu.ashikhmin.worker.service.ManagerService;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

    private final RestTemplate restTemplate;
    private final ConfigProperties configProperties;

    @Override
    public void sendResponse(String requestId, int partNumber, List<String> answers) {
        CrackHashWorkerResponse response = buildResponse(requestId, partNumber, answers);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<CrackHashWorkerResponse> entity = new HttpEntity<>(response, headers);
        restTemplate.postForObject(configProperties.receiveManagerProperties().getUrl(), entity, TaskResponseDto.class);
    }

    private CrackHashWorkerResponse buildResponse(String requestId, int partNumber, List<String> answers) {
        CrackHashWorkerResponse.Answers answer = new CrackHashWorkerResponse.Answers();
        answer.getWords().addAll(answers);
        CrackHashWorkerResponse response = new CrackHashWorkerResponse();
        response.setRequestId(requestId);
        response.setPartNumber(partNumber);
        response.setAnswers(answer);
        return response;
    }
}
