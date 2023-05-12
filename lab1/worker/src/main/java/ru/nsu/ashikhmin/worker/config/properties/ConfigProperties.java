package ru.nsu.ashikhmin.worker.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.nsu.ashikhmin.worker.config.properties.task_processing.ManagerProperties;

@Configuration
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    @Bean
    @ConfigurationProperties(prefix = "task-processing.manager")
    public ManagerProperties receiveManagerProperties(){
        return new ManagerProperties();
    }
}
