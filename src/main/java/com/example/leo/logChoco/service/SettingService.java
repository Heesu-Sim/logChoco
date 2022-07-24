package com.example.leo.logChoco.service;

import com.example.leo.logChoco.config.LogChocoConfig;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that is in charge of managing setting file.
 * */
@Service
@RequiredArgsConstructor
public class SettingService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private final LogChocoConfig logChocoConfig;

    @Getter
    private Path inboundSettingPath;
    private String defaultString = "{}";

    @PostConstruct
    public void init() {
        inboundSettingPath = Paths.get(logChocoConfig.getFilePath());
    }


    /**
     * Read setting file for log format and return it.
     * */
    public String getFormatSetting() {
        Path path = inboundSettingPath;

        if(!Files.exists(path) || Files.isDirectory(path)) {
            createDefaultFile(path);
        }

        try (Stream<String> lines = Files.lines(path)) {
            String json = lines.collect(Collectors.joining());

            if(json.contains("\\")) {
                json = json.replaceAll("\\\\", "");
            }

            if(json.contains("\t")) {
                json = json.replaceAll("\t", "");
            }

            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    // save setting file with given json text
    public boolean saveFormatSetting(Optional<String> optionalText) {
        Path path = inboundSettingPath;

        String jsonText = optionalText.orElse(defaultString);

        String tmp_postfix = "_back.json";

        if(!Files.exists(path) || Files.isDirectory(path)) {
            createDefaultFile(path);
        }

        File file =new File(path.toString());

        File tmpFile = new File(path + tmp_postfix);
        file.renameTo(tmpFile);

        try {
            writeTextToSettingFile(path, jsonText);
            tmpFile.delete();
            logger.debug("save setting text to {}", path.toString());
            logger.debug("text : {}", jsonText);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("failed to save setting text to file. {}", e.getMessage());
            // if failed to write json to file, change tmp file name back to origin
            tmpFile.renameTo(file);
            return false;
        }

        return true;
    }

    private void createDefaultFile(Path path) {
        try {
            Path fPath = Files.createFile(path);
            writeTextToSettingFile(fPath, defaultString);
        } catch (IOException e) {
            logger.error("failed to create file and write text to file. {}", e.getMessage());
        }
    }

    private void writeTextToSettingFile(Path path, String text) throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));){
            writer.write(text);
        }
    }
}
