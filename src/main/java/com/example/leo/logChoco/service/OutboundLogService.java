package com.example.leo.logChoco.service;

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
import reactor.netty.ConnectionObserver;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import reactor.netty.udp.UdpClient;
import reactor.util.retry.RetrySpec;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OutboundLogService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final LogChocoConfig logChocoConfig;

    @Getter
    protected Sinks.Many<String> sink;

    private Map<String, TcpClient> tcpClientMap = new HashMap<>();
    private Map<String, UdpClient> udpClientMap = new HashMap<>();

    private Map<String, Connection> tcpConnMap = new HashMap<>();
    private Map<String, Connection> udpConnMap = new HashMap<>();

    private List<HttpClient> httpClient;

    private long MAX_NUM_OF_RETRY = 100;
    private long RETRY_INTERVAL_MILLS = 2000;

    @PostConstruct
    public void init() {

        createClient();

        sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<List<String>> flux = sink.asFlux().bufferTimeout(BufferInfo.BUFFER_SIZE, BufferInfo.BUFFER_DURATION_SECOND);
        flux.subscribe(logs -> {

            tcpConnMap.entrySet().stream().forEach(entry -> {
                Connection conn = entry.getValue();
                if(!conn.isDisposed()) {
                    Flux<String> logFlux = Flux.fromStream(logs.stream());

                    logFlux.doOnComplete(() -> {
                    }).subscribe(log -> {
                        conn.outbound().sendString(Mono.just(log), CharsetUtil.UTF_8).then().subscribe();
                    });
                }
            });
        });
    }

    private void createClient() {
        List<ServerInfo> targetServers = logChocoConfig.getTargetServer();
        targetServers.stream()
                .filter(serverInfo -> serverInfo.getProtocol().equals(NetworkProtocol.TCP))
                .forEach(serverInfo -> addTcpConnectionToMap(serverInfo));

    }

    private void addTcpConnectionToMap(ServerInfo serverInfo) {
        String ip = serverInfo.getIp();
        int port = serverInfo.getPort();
        NetworkProtocol protocol = serverInfo.getProtocol();

        String key = getKey(ip, port);

        TcpClient tcpClient = TcpClient.create()
                .host(serverInfo.getIp())
                .port(serverInfo.getPort())
                .doOnConnect(conn -> logger.info("Trying to connect to {}:{}, via {}", ip, port, protocol))
                .doOnConnected(conn -> logger.info("Connection made to {}:{}, via {}", ip, port, protocol))
                .doOnDisconnected(conn -> {
                    logger.info("Connection is disposed. {}:{}, via {}", ip, port, protocol);
                    conn.onTerminate();
                })
                .observe(observeConnectionStateChange(ip, port, NetworkProtocol.TCP));

        tcpClientMap.put(key, tcpClient);

        tcpClient.connect().subscribe(conn -> {
            addConnectionToMap(key, conn, NetworkProtocol.TCP);
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

    private ConnectionObserver observeConnectionStateChange(String ip, int port, NetworkProtocol protocol) {

        String key = getKey(ip, port);

        return (conn, state) -> {
            if (state == ConnectionObserver.State.DISCONNECTING) {
                if(NetworkProtocol.TCP.equals(protocol)) {
                    TcpClient client = tcpClientMap.get(key);
                    client.connect()
                            .doOnError(err -> logger.error("Failed to reconnect {}:{} via {}", ip, port, protocol))
                            .retryWhen(RetrySpec.fixedDelay(MAX_NUM_OF_RETRY, Duration.ofMillis(RETRY_INTERVAL_MILLS)))
                            .subscribe(newConn -> addConnectionToMap(key, newConn, protocol));
                }
            }
        };
    }

    private void addConnectionToMap(String key, Connection conn, NetworkProtocol protocol) {
        if(NetworkProtocol.TCP.equals(protocol)) {
            tcpConnMap.put(key, conn);
        }
    }




    private String getKey(String ip, int port) {
        return ip + ":" + port;
    }

}
