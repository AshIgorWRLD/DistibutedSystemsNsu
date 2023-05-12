package ru.nsu.ashikhmin.worker.config.properties.task_processing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
public class ManagerProperties {
    private String host;
    private String scheme;
    private String port;
    private String path;

    public String getUrl(){
        return UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host(host)
                .port(port)
                .path(path)
                .toUriString();
    }
}
