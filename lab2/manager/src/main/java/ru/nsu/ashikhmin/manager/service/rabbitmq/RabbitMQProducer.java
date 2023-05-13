package ru.nsu.ashikhmin.manager.service.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;
import org.springframework.stereotype.Service;
import ru.nsu.ashikhmin.manager.config.properties.ConfigProperties;
import ru.nsu.ashikhmin.manager.repo.HashRequestsRepository;
import ru.nsu.ashikhmin.manager.service.model.HashRequest;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

import java.util.List;

@Service
@Slf4j
public class RabbitMQProducer implements ConnectionListener {
    private final AmqpTemplate amqpTemplate;
    private final String outputQueue;
    private final HashRequestsRepository hashRequestsRepository;

    public RabbitMQProducer(AmqpTemplate amqpTemplate, ConnectionFactory connectionFactory,
                            HashRequestsRepository hashRequestsRepository, ConfigProperties configProperties) {
        this.amqpTemplate = amqpTemplate;
        this.hashRequestsRepository = hashRequestsRepository;
        this.outputQueue = configProperties.receiveManagerProperties().getQueueProperties().getOutput();
        connectionFactory.addConnectionListener(this);
    }

    public boolean trySendMessage(CrackHashManagerRequest request) {
        try {
            amqpTemplate.convertAndSend(outputQueue, request);
            log.info("Set {} part of {} task request was sent", request.getPartNumber(), request.getRequestId());
            return true;
        } catch (AmqpException ex) {
            log.error("Failed to send request '{}', cached message", request.getRequestId());
            return false;
        }
    }

    @Override
    public void onCreate(Connection connection) {
        List<HashRequest> requests = hashRequestsRepository.findAll();
        for (var request : requests) {
            if (trySendMessage(request.getRequest())) {
                hashRequestsRepository.delete(request);
            }
        }
    }
}
