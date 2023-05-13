package ru.nsu.ashikhmin.worker.config.properties.task_processing;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkerProperties {
    private String inputQueue;
    private String outputQueue;
}
