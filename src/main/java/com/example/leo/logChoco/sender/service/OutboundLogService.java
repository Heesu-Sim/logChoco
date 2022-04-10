package com.example.leo.logChoco.sender.service;

import com.example.leo.logChoco.config.LogChocoConfig;
import com.example.leo.logChoco.config.entity.ServerInfo;
import com.example.leo.logChoco.entity.BufferInfo;
import com.example.leo.logChoco.entity.NetworkProtocol;
import io.netty.util.CharsetUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.netty.Connection;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import reactor.netty.udp.UdpClient;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OutboundLogService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final LogChocoConfig logChocoConfig;

    @Getter
    protected Sinks.Many<String> sink;


    private List<TcpClient> tcpClientList;
    private List<UdpClient> udpClientList;
    private List<HttpClient> httpClient;
    
    @PostConstruct
    public void init() {

        createClient();

        sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<List<String>> flux = sink.asFlux().bufferTimeout(BufferInfo.BUFFER_SIZE, BufferInfo.BUFFER_DURATION_SECOND);
        flux.subscribe(logs -> {
            List<Mono<Connection>> connectionMonoList = createConnection();

            connectionMonoList.stream().forEach(connectionMono -> {
                connectionMono.subscribe(connection -> {

                    Flux<String> logFlux = Flux.fromStream(logs.stream());

                    logFlux.doOnComplete(() -> {
                        disposeConnection(connectionMonoList);
                    }).subscribe(log -> {
                        connection.outbound().sendString(Mono.just(log), CharsetUtil.UTF_8).then().subscribe();
                    });
                });
            });
        });
    }

    private void createClient() {
        List<ServerInfo> targetServers = logChocoConfig.getTargetServer();
        tcpClientList = targetServers.stream()
                .filter(serverInfo -> serverInfo.getProtocol().equals(NetworkProtocol.TCP))
                .map(serverInfo -> setTcpClients(serverInfo))
                .collect(Collectors.toList());

        udpClientList = targetServers.stream()
                .filter(serverInfo -> serverInfo.getProtocol().equals(NetworkProtocol.UDP))
                .map(serverInfo -> setUdpClient(serverInfo))
                .collect(Collectors.toList());
    }

    private List<Mono<Connection>> createConnection() {
        List<Mono<Connection>> tcpConnections = tcpClientList.stream()
                .map(client -> (Mono<Connection>) client.connect())
                .collect(Collectors.toList());
        List<Mono<Connection>> udpConnections = udpClientList.stream()
                .map(client -> (Mono<Connection>) client.connect())
                .collect(Collectors.toList());

        return Stream.concat(tcpConnections.stream(), udpConnections.stream()).collect(Collectors.toList());
    }

    private void disposeConnection(List<Mono<Connection>> connectionMonoList) {
        connectionMonoList.stream().forEach(connectionMono -> {
            connectionMono.subscribe(connection -> connection.dispose());
        });
    }

    private TcpClient setTcpClients(ServerInfo serverInfo) {
        return TcpClient.create()
                .host(serverInfo.getIp())
                .port(serverInfo.getPort())
                .doOnConnected(conn -> {
                    logger.info("Connection made to {}:{}, via {}", serverInfo.getIp(), serverInfo.getPort(), serverInfo.getProtocol());
                })
                .doOnDisconnected(conn -> {
                    logger.info("Connection is disposed. {}:{}, via {}", serverInfo.getIp(), serverInfo.getPort(), serverInfo.getProtocol());
                });
    }

    private UdpClient setUdpClient(ServerInfo serverInfo) {
        return UdpClient.create()
                .host(serverInfo.getIp())
                .port(serverInfo.getPort())
                .doOnConnect(conn -> {
                    logger.info("Connection made to {}:{}, via {}", serverInfo.getIp(), serverInfo.getPort(), serverInfo.getProtocol());
                })
                .doOnDisconnected(conn -> {
                    logger.info("Connection is disposed. {}:{}, via {}", serverInfo.getIp(), serverInfo.getPort(), serverInfo.getProtocol());
                });
    }

}
