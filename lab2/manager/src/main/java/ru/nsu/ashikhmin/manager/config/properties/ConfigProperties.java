package ru.nsu.ashikhmin.manager.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.nsu.ashikhmin.manager.config.properties.hash_crack.ManagerProperties;

@Configuration
@PropertySource("classpath:application.properties")
public class ConfigProperties {

    @Bean
    @ConfigurationProperties(prefix = "hash-crack.manager")
    public ManagerProperties receiveManagerProperties(){
        return new ManagerProperties();
    }
}
