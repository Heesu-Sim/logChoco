package com.example.leo.logChoco.reader.service;

import com.example.leo.logChoco.config.LogChocoConfig;
import com.example.leo.logChoco.entity.BufferInfo;
import com.example.leo.logChoco.entity.FieldType;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import com.example.leo.logChoco.exception.InvalidLogFormatException;
import com.example.leo.logChoco.format.LogFormatterFactory;
import com.example.leo.logChoco.regex.builder.AbstractRegexBuilder;
import com.example.leo.logChoco.regex.builder.RegexBuilderFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// 설정파일에서 ${DATE}`${TIME_STAMP}`{NUMBER}`{BOOLEAN}
@Service
@RequiredArgsConstructor
public class PatternInfoService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final LogChocoConfig logChocoConfig;

    @Getter
    protected Sinks.Many<String> sink;


    // 모든 로그 포맷 정보 담고있는 리스트.
    @Getter
    private List<ReadFieldInfo> fieldInfoList;

    // Separater that divides key and value for each option.
    private final String DEFAULT_OPTION_KEY_VALUE_SEPERATOR = ":";
    // Separator that divides each option.
    private final String DEFAULT_OPTION_SEPARATOR = "&";

    @PostConstruct
    public void init() {
        initRegexSetting();

        sink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<List<String>> flux = sink.asFlux().bufferTimeout(BufferInfo.BUFFER_SIZE, BufferInfo.BUFFER_DURATION_SECOND);
        flux.subscribe(consumeLogs());
    }

    /**
     * Consumer for inbound logs from inboundService.java
     * */
    private Consumer<List<String>> consumeLogs() {
        return logs -> {
            logs.stream().forEach(log -> {
                getFormattedLogText(log);
            });
        };
    }

    /**
     * Receive log text as a parameter.
     * Iterate fieldInfoList to check if the log matches any log pattern.
     * If it matches, return formatted log.
     * */
    private String getFormattedLogText(String log) {

        Optional<ReadFieldInfo> optional = fieldInfoList.stream()
                                            .filter(info -> info.checkIfMatchLogRegex(log))
                                            .findFirst();

        if(optional.isPresent()) {
            ReadFieldInfo fieldInfo = optional.get();
//            String formattedLog = LogFormatterFactory.getFormatter(fieldInfo, log).getFormattedLog();

        }
        return "";
    }


    /**
     * Read setting file and set regex cache
     * when process starts
     * */
    private void initRegexSetting() {
        fieldInfoList = readPatternInfoFile();

        fieldInfoList.stream().forEach(fieldInfo -> {
            try {
                setRegexFormat(fieldInfo);
            } catch (InvalidLogFormatException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method that  create regex format
     */
    private void setRegexFormat(ReadFieldInfo fieldInfo) throws InvalidLogFormatException {

        String separatorForValue = DEFAULT_OPTION_KEY_VALUE_SEPERATOR;
        String separatorForOption = DEFAULT_OPTION_SEPARATOR;

        String delimiter = fieldInfo.getDelimiter();
        String[] formats = fieldInfo.getFormat().split(delimiter);
        String[] columns = fieldInfo.getColumns().split(delimiter);

        if(formats.length < 1 || columns.length < 1) {
            throw new InvalidLogFormatException("Check format, columns, delimiter in configuration file. The length of 'format' or 'columns' separated by delimiter is less than 1");
        }

        if(formats.length != columns.length) {
            throw new InvalidLogFormatException("The length of format in configuration file should be same with the length of colums");
        }

        StringBuilder regexSb = new StringBuilder();
        IntStream.range(0, formats.length).forEach(i -> {
            String format = formats[i];
            try {
                String type = format;
                Map<String, String> optionMap = new HashMap<>();

                // Save each option for each columns into map.
                if(format.indexOf("(") > 0 && format.endsWith(")")) {
                    type = format.substring(0, format.indexOf("("));
                    String[] options = format.substring(format.indexOf("(") + 1, format.indexOf(")")).split(separatorForOption);

                    Arrays.stream(options).forEach(option -> {
                        String[] kv = option.split(separatorForValue,2 );

                        if(kv.length != 2) {
                            logger.warn("Wrong option for {}. each option must have key and value separated by {}", format, separatorForValue);
                            return;
                        }
                        optionMap.put(kv[0], kv[1]);
                    });
                }

                // Get regex builder according to field type. and add option to it.
                AbstractRegexBuilder builder = RegexBuilderFactory.getRegexBuilder(FieldType.valueOf(type));
                builder.addRegexOptions(optionMap);

                regexSb.append(builder.getValue()).append(delimiter);

            } catch (IllegalArgumentException e) {
                logger.error("Field type {} is not supported.  ", format);
                e.printStackTrace();
            }
        });

        regexSb.deleteCharAt(regexSb.lastIndexOf(delimiter));
        fieldInfo.setFormatInRegex(regexSb.toString());
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
