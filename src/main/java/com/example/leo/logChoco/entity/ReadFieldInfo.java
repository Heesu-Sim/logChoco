package com.example.leo.logChoco.entity;

import com.example.leo.logChoco.format.OutboundLogFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Class that represents detailed information of each log from setting file
 * and also has compiled regex data of log format.
 * */
@Getter
@Setter
@ToString
public class ReadFieldInfo {
    // String of key 'format' that is read from setting file.
    private List<String> format;
    // String of key 'columns' that is read from setting file.
    private List<String> columns;
    // String of key 'delimiter' that is read from setting file.
    // delimiter for inbound log. Not outbound log.
    // it divide logs into separate value.
    private String delimiter;
    // String of key 'logFormat' that is read from setting file.
    private OutboundLogFormat logFormat;
    private int idIndex;

    // used for CEF format
    private int severityIndex;

    private String formatInRegex;
    private Pattern pattern;

    public void setFormatInRegex(String formatInRegex) {
        this.pattern = Pattern.compile(formatInRegex);
        this.formatInRegex = formatInRegex;
    }

    /*
     * Check if given string matches regex.
     */
    public boolean checkIfMatchLogRegex(String text) {
        return this.pattern.matcher(text).matches();
    }
}
