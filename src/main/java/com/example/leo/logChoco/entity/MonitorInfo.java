package com.example.leo.logChoco.entity;

import com.example.leo.logChoco.entity.log.LogInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonitorInfo {

    private String ip;
    private int port;
    private long count;
}
