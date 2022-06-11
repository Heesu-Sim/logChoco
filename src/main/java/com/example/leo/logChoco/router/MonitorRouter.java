package com.example.leo.logChoco.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class MonitorRouter {

    @Bean
    public RouterFunction<ServerResponse> routerInfo(MonitorHandler monitorHandler) {
        return RouterFunctions.route()
                .GET("/monitor/inbound/log", request -> monitorHandler.getInboundMonitor(request))
                .build();
    }
}
