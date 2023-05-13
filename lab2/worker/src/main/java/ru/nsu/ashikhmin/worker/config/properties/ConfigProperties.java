package ru.nsu.ashikhmin.worker.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.nsu.ashikhmin.worker.config.properties.task_processing.WorkerProperties;

@Configuration
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    @Bean
    @ConfigurationProperties(prefix = "task-processing.worker.queue")
    public WorkerProperties receiveWorkerProperties() {
        return new WorkerProperties();
    }
}
