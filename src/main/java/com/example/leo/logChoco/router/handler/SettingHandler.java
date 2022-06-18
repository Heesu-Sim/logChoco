package com.example.leo.logChoco.router.handler;

import com.example.leo.logChoco.config.LogChocoConfig;
import com.example.leo.logChoco.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.channels.AsynchronousChannel;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SettingHandler {

    private final SettingService settingService;

    public Mono<ServerResponse> getFileList(ServerRequest request) {

        String formatSetting = settingService.getFormatSetting();

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(formatSetting), Map.class);
    }
}
