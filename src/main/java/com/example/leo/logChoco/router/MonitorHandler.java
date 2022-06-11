package com.example.leo.logChoco.router;

import com.example.leo.logChoco.entity.MonitorInfo;
import com.example.leo.logChoco.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class MonitorHandler {

    private final MonitorService monitorService;

    public Mono<ServerResponse> getInboundMonitor(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                .body(Flux.interval(Duration.ofSeconds(1)).map(t -> {
                    return monitorService.getInboundMonitorInfo();
                }), MonitorInfo.class);
    }

}
