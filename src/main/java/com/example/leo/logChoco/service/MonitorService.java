package com.example.leo.logChoco.service;

import com.example.leo.logChoco.entity.log.InboundLog;
import com.example.leo.logChoco.entity.MonitorInfo;
import com.example.leo.logChoco.entity.log.LogInfo;
import lombok.Getter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

@Service
public class MonitorService {

    @Getter
    private Sinks.Many<List<LogInfo>> inboundSink;


    @PostConstruct
    public void init() {
        inboundSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<List<LogInfo>> inboundFlux = inboundSink.asFlux();
        inboundFlux.subscribe(consumeLog());

    }

    private Consumer<List<LogInfo>> consumeLog() {
        return list -> {


        };
    }

    public MonitorInfo getInboundMonitorInfo() {
        MonitorInfo info = new MonitorInfo();
        return info;
    }




}
