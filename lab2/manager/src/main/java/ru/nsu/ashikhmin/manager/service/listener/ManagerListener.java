package ru.nsu.ashikhmin.manager.service.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

@Slf4j
public class ManagerListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        log.info("Received message: {}", message);
    }
}
