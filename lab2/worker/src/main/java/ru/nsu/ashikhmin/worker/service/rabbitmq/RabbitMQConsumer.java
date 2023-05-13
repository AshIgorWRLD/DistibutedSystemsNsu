package ru.nsu.ashikhmin.worker.service.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.nsu.ashikhmin.worker.service.TaskProcessingService;
import ru.nsu.ccfit.schema.crack_hash_request.CrackHashManagerRequest;

@Service
@Slf4j
@EnableRabbit
@RequiredArgsConstructor
public class RabbitMQConsumer {
    private final TaskProcessingService taskProcessingService;

    private final RabbitMQProducer rabbitMQProducer;

    @RabbitListener(queues = "${task-processing.worker.queue.input}")
    public void receiveMessage(CrackHashManagerRequest message) {
        log.info("Received message: {}", message);
        var response = taskProcessingService.processTask(message);
        rabbitMQProducer.produce(response);
    }
}
