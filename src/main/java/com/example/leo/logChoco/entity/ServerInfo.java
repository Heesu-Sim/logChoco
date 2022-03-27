package com.example.leo.logChoco.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that has information of servers for inbound and outbound logs
 * */
@Getter
@Setter
public class ServerInfo {
    private String ip;
    private int port;
    private NetworkProtocol protocol;
}
