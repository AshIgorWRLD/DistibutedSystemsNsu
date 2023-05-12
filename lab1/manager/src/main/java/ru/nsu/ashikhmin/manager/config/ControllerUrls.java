package ru.nsu.ashikhmin.manager.config;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerUrls {

    public static final String HASH_URL = "/api/hash";
    public static final String HASH_CRACK_URL = HASH_URL + "/crack";
    public static final String HASH_STATUS_URL = HASH_URL + "/status";
    public static final String WORKER_CALLBACK_URL = "/api/internal/manager/callback";
}
