package com.example.leo.logChoco.router.handler;


import com.example.leo.logChoco.service.MonitorService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Getter
public class MonitorHandler {
    private final MonitorService monitorService;

    public Mono<ServerResponse> getInboundMonitor(ServerRequest request) {

        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(Flux.interval(Duration.ofSeconds(1)).map(t -> {
                    return monitorService.getInboundMonitorInfo();
                }), Map.class);
    }

    public Mono<ServerResponse> getRealtimeLogs(ServerRequest request) {

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(BodyInserters.fromServerSentEvents(monitorService.getRealTimeFlux()
                        .map(s -> ServerSentEvent.<String>builder()
                                .id(String.valueOf(System.currentTimeMillis()))
                                .event("message")
                                .data(s)
                                .build())));

    }
}
