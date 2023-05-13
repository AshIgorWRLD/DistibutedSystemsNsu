package ru.nsu.ashikhmin.manager.config.properties.hash_crack;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerProperties {
    private Integer expireTime;
    private QueueProperties queueProperties;
    private String alphabet;
    private Integer workersAmount;
}