package com.example.leo.logChoco.service;

import com.example.leo.logChoco.entity.log.InboundLog;
import com.example.leo.logChoco.entity.MonitorInfo;
import com.example.leo.logChoco.entity.log.LogInfo;
import lombok.Getter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@Service
public class MonitorService {
    @Getter
    private Sinks.Many<List<LogInfo>> inboundSink;

    @Getter
    private HashMap<String, MonitorInfo> inboundMonitorCache = new HashMap<>();

    @PostConstruct
    public void init() {
        inboundSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<List<LogInfo>> inboundFlux = inboundSink.asFlux();
        inboundFlux.subscribe(consumeLog());

    }

    private Consumer<List<LogInfo>> consumeLog() {
        return list -> {
            list.stream().forEach(logInfo -> {
                if(logInfo instanceof InboundLog) {
                    addInboundInfoToCache((InboundLog) logInfo);
                } else {

                }
            });
        };
    }

    /**
     * add inboundInfo To cache
     * */
    private void addInboundInfoToCache(InboundLog inboundLog) {
        String ip = inboundLog.getIp();

        if(inboundMonitorCache.containsKey(ip)) {
            MonitorInfo info = inboundMonitorCache.get(ip);
            long count = info.getCount();
            info.setCount(++count);


        } else {
            MonitorInfo info = new MonitorInfo();

            info.setIp(ip);
            info.setPort(inboundLog.getPort());
            info.setCount(1);
            inboundMonitorCache.put(ip, info);
        }

    }

    /**
     * return inbound info that stored in cache.
     * and clear cache
     * */
    public HashMap<String, MonitorInfo> getInboundMonitorInfo() {
        HashMap<String, MonitorInfo> info = (HashMap<String, MonitorInfo>) inboundMonitorCache.clone();
        inboundMonitorCache.clear();
        return info;
    }






}
