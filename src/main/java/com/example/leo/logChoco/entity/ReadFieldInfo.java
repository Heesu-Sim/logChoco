package com.example.leo.logChoco.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.regex.Pattern;

/**
 * Class that represents detailed information of each log from setting file
 * and also has compiled data of log format.
 * */
@Getter
@Setter
@ToString
public class ReadFieldInfo {
    private String format;
    private String columns;
    private String delimiter;
    private int idIndex;

    private String formatInRegex;
    private Pattern pattern;

    public void setFormatInRegex(String formatInRegex) {
        Pattern pattern = Pattern.compile(formatInRegex);
        this.formatInRegex = formatInRegex;
    }
}
