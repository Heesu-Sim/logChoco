package com.example.leo.logChoco.router;

import com.example.leo.logChoco.router.handler.MonitorHandler;
import com.example.leo.logChoco.router.handler.SettingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class SettingRouter {

    @Bean
    public RouterFunction<ServerResponse> settingRouterInfo(SettingHandler settingHandler) {
        return RouterFunctions.route()
                .GET("/setting/log/file", request -> settingHandler.getSettingFile(request))
                .POST("/setting/log/file", request -> settingHandler.saveSettingFile(request))
                .build();
    }
}
