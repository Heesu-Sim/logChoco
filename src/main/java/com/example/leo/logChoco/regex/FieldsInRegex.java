package com.example.leo.logChoco.regex;


import lombok.Getter;

/**
 * Class that represents each type in regular expression
 * */
@Getter
public class FieldsInRegex {

    public final static String REGEX_FLOAT = "([+-]?([0-9]*[.])?[0-9]+)";
    public final static String REGEX_INTEGER = "((-+)?([0-9]+))";
    public final static String REGEX_BOOLEAN = "(true|false|0|1)";
    public final static String REGEX_IPV4 = "((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    public final static String REGEX_ANYTHING = "(.*)";
    public final static String REGEX_ALPHABET = "([a-zA-Z])";

    public final static String REGEX_DATE_YEAR = "(\\\\d{4})";
    public final static String REGEX_DATE_MONTH = "(0[1-9]|1[012])";
    public final static String REGEX_DATE_DATE = "(0[1-9]|[12][0-9]|3[01])";
    public final static String REGEX_DATE_HOUR_12 = "((0[1-9])|(1[0-2]))";
    public final static String REGEX_DATE_HOUR_24 = "([0-9]|0[0-9]|1[0-9]|2[0-3])";

    public final static String REGEX_DATE_MINUTE = "([0-5][0-9])";
    public final static String REGEX_DATE_SECOND = "([0-5][0-9])";
    public final static String REGEX_DATE_MILLISECOND = "[0-9]";



}
