package com.example.leo.logChoco.entity.log;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInfo {

    private String ip;
    private int port;
    private String log;

    public LogInfo(String ip, int port, String log) {
        this.ip = ip;
        this.port = port;
        this.log = log;
    }
}
