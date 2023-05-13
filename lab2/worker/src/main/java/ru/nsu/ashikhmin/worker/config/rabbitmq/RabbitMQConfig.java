package ru.nsu.ashikhmin.worker.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nsu.ashikhmin.worker.config.properties.ConfigProperties;
import ru.nsu.ashikhmin.worker.config.properties.task_processing.WorkerProperties;

@Configuration
public class RabbitMQConfig {

    private final String inputQueue;
    private final String outputQueue;
    private final CachingConnectionFactory connectionFactory;

    public RabbitMQConfig(CachingConnectionFactory connectionFactory,
                          ConfigProperties configProperties) {
        WorkerProperties workerProperties = configProperties.receiveWorkerProperties();
        this.inputQueue = workerProperties.getInputQueue();
        this.outputQueue = workerProperties.getOutputQueue();
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(xmlMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue workersToManagerQueue() {
        return new Queue(inputQueue, true);
    }

    @Bean
    public Queue managerToWorkersQueue() {
        return new Queue(outputQueue, true);
    }

    @Bean
    public Jackson2XmlMessageConverter xmlMessageConverter() {
        return new Jackson2XmlMessageConverter();
    }
}
