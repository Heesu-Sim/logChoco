package com.example.leo.logChoco.router.handler;

import com.example.leo.logChoco.service.SettingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.Map;

@Component
@RequiredArgsConstructor
public class SettingHandler {

    private final SettingService settingService;

    public Mono<ServerResponse> getSettingFile(ServerRequest request) {

        String formatSetting = settingService.getFormatSetting();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(formatSetting), Map.class);
    }

    public Mono<ServerResponse> saveSettingFile(ServerRequest request) {

        boolean isSaved = settingService.saveFormatSetting(request.queryParam("json"));
        return ServerResponse.ok().build();
    }
}
