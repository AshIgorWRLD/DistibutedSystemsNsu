package ru.nsu.ashikhmin.worker.service.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nsu.ashikhmin.worker.config.properties.ConfigProperties;
import ru.nsu.ccfit.schema.crack_hash_response.CrackHashWorkerResponse;

@Service
@Slf4j
public class RabbitMQProducer {

    private final String outputQueue;
    private final AmqpTemplate amqpTemplate;

    public RabbitMQProducer(AmqpTemplate amqpTemplate,
                            ConfigProperties configProperties) {
        outputQueue = configProperties.receiveWorkerProperties().getOutputQueue();
        this.amqpTemplate = amqpTemplate;
    }

    public void produce(CrackHashWorkerResponse response) {
        try {
            amqpTemplate.convertAndSend(outputQueue, response);
            log.info("Set {} part of {} task request was sent", response.getPartNumber(), response.getRequestId());
        } catch (AmqpException ex) {
            log.error("Failed to send request '{}', cached message", response.getRequestId());
        }
    }
}
