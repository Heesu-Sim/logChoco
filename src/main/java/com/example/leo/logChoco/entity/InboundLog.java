package com.example.leo.logChoco.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InboundLog {

    private String remoteAddr;
    private int remotePort;
    private String receivedLog;

    public InboundLog() {}

    public InboundLog(String remoteAddr, int remotePort, String receivedLog) {
        this.receivedLog = receivedLog;
        this.remoteAddr = remoteAddr;
        this.remotePort = remotePort;
    }
}
