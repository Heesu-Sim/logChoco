package com.example.leo.logChoco.sender.service;

import com.example.leo.logChoco.config.LogChocoConfig;
import com.example.leo.logChoco.config.entity.ServerInfo;
import com.example.leo.logChoco.entity.BufferInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboundLogService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final LogChocoConfig logChocoConfig;


    @Getter
    protected Sinks.Many<String> sink;


    @PostConstruct
    public void init() {

        List<ServerInfo> targetServers = logChocoConfig.getTargetServer();

        sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<List<String>> flux = sink.asFlux().bufferTimeout(BufferInfo.BUFFER_SIZE, BufferInfo.BUFFER_DURATION_SECOND);
        flux.doOnComplete(() -> {

        }).subscribe(log -> {
            targetServers.stream().forEach(server -> {

            });
        });
    }
}
