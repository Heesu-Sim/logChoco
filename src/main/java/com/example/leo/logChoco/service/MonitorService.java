package com.example.leo.logChoco.service;

import com.example.leo.logChoco.entity.BufferInfo;
import com.example.leo.logChoco.entity.InboundLog;
import com.example.leo.logChoco.entity.MonitorInfo;
import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Consumer;

@Service
public class MonitorService {

    @Getter
    private Sinks.Many<List<InboundLog>> inboundSink;


    @PostConstruct
    public void init() {
        inboundSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<List<InboundLog>> inboundFlux = inboundSink.asFlux();
        inboundFlux.subscribe(consumeInboundLog());

    }

    private Consumer<List<InboundLog>> consumeInboundLog() {
        return list -> {
//          inboundMonitorInfo += list.size();
        };
    }

    public MonitorInfo getInboundMonitorInfo() {
        MonitorInfo info = new MonitorInfo();
        return info;
    }




}
