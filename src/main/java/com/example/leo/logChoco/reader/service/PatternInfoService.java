package com.example.leo.logChoco.reader.service;

import com.example.leo.logChoco.config.LogChocoConfig;
import com.example.leo.logChoco.entity.FieldType;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import com.example.leo.logChoco.exception.InvalidLogFormatException;
import com.example.leo.logChoco.regex.builder.AbstractRegexBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// 설정파일에서 ${DATE}`${TIME_STAMP}`{NUMBER}`{BOOLEAN}
@Service
public class PatternInfoService {

    @Autowired
    private LogChocoConfig logChocoConfig;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String DEFAULT_OPTION_DELIMITER = ":";

    @Getter
    private List<ReadFieldInfo> fieldList;


    @PostConstruct
    public void init() {
       fieldList = readPatternInfoFile();

       fieldList.stream().forEach(fieldInfo -> {
           try {
               setRegexFormat(fieldInfo);
           } catch (InvalidLogFormatException e) {
               e.printStackTrace();
           }
       });
    }

    /**
     *
     * Method that  create regex format
     */
    private void setRegexFormat(ReadFieldInfo fieldInfo) throws InvalidLogFormatException{
        String delimiter = fieldInfo.getDelimiter();
        String[] formats = fieldInfo.getFormat().split(delimiter);
        String[] columns = fieldInfo.getColumns().split(delimiter);


        if(formats.length < 1 || columns.length < 1) {
            throw new InvalidLogFormatException("Check format, columns, delimiter in configuration file. The length of 'format' or 'columns' separated by delimiter is less than 1");
        }

        if(formats.length != columns.length) {
            throw new InvalidLogFormatException("The length of format in configuration file should be same with the length of colums");
        }


        IntStream.range(0, formats.length).forEach(i -> {
            try {
                String format = formats[i];

                String type = format;

                //if each format has option
                if(format.indexOf("(") > 0 && format.endsWith(")")) {
                    type = format.substring(0, format.indexOf("("));

                    //each options is separated by &

                }

                AbstractRegexBuilder builder = AbstractRegexBuilder.getRegexBuilder(FieldType.valueOf(type.toUpperCase()));

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        });



    }



    private List<ReadFieldInfo> readPatternInfoFile() {

        String filePath = logChocoConfig.getFilePath();
        logger.info("Read configuration file -> {}", filePath);
        ObjectMapper mapper = new ObjectMapper();
        Path path = Paths.get(filePath);
        StringBuilder sb = new StringBuilder();

        try (Stream<String>lines = Files.lines(path)) {
            lines.forEach(s -> sb.append(s));
            return mapper.readValue(sb.toString(), new TypeReference<List<ReadFieldInfo>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private boolean validatePattern(String input, String pattern) {
        pattern = "^" + pattern + "$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        return m.matches();
    }



}
