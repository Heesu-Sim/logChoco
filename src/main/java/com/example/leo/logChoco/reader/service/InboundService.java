package com.example.leo.logChoco.reader.service;

import com.example.leo.logChoco.config.LogChocoConfig;
import com.example.leo.logChoco.entity.ServerInfo;
import com.example.leo.logChoco.reader.InboundHandler;
import io.netty.channel.ChannelOption;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.List;

/**
 * Class that has business logic for receiving log
 * */

@Service
@RequiredArgsConstructor
public class InboundService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final LogChocoConfig logChocoConfig;
    private List<ServerInfo> servers;

    @PostConstruct
    public void init() {
        servers = logChocoConfig.getReceiveServer();
        runServers();
    }

    private void runServers() {
        servers.stream()
                .filter(server -> server.getPort() > 0 && StringUtils.hasText(server.getProtocol().name()))
                .forEach(server -> {
                    switch(server.getProtocol()) {
                        case TCP -> runTcpServer(server);
                        case UDP -> runUdpServer(server);
                        case TLS -> runTlsServer(server);
                        default -> logger.error("Failed to run servers for {}. Protocol is not supported");
                    }
                    
                });
    }

    private void runTcpServer(ServerInfo server) {
        DisposableServer tcpServer = TcpServer.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .host("localhost")
                .port(server.getPort())
                .doOnChannelInit((observer, channel, remoteAddress) -> {
                })
                .bindNow();


    }

    private void runUdpServer(ServerInfo server) {

    }

    private void runTlsServer(ServerInfo server) {

    }


}
