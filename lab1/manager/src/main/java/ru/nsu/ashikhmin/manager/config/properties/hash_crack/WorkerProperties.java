package ru.nsu.ashikhmin.manager.config.properties.hash_crack;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
public class WorkerProperties {
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
