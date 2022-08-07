package com.example.leo.logChoco.router;

import com.example.leo.logChoco.router.handler.MonitorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MonitorRouter {

    @Bean
    public RouterFunction<ServerResponse> monitorRouterInfo(MonitorHandler monitorHandler) {
        return RouterFunctions.route()
                .GET("/monitor/inbound/log", request -> monitorHandler.getInboundMonitor(request))
                .GET("/monitor/realtime/logs", request -> monitorHandler.getRealtimeLogs(request))
                .build();
    }
}
