package com.example.leo.logChoco.service;

import com.example.leo.logChoco.config.LogChocoConfig;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that is in charge of managing setting file.
 * */
@Service
@RequiredArgsConstructor
public class SettingService {

    private final LogChocoConfig logChocoConfig;


    /**
     * Read setting file for log format and return it.
     * */
    public String getFormatSetting() {
        String filePath = logChocoConfig.getFilePath();

        Path path = Paths.get(filePath);

        try (Stream<String> lines = Files.lines(path)) {
            String json = lines.collect(Collectors.joining());

            if(json.contains("\t")) {
                json = json.replaceAll("\t", "");
            }

            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
