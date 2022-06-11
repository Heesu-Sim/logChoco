package com.example.leo.logChoco.entity.log;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InboundLog extends LogInfo{


    public InboundLog(String ip, int port, String text) {
        super(ip, port, text);
    }
}
