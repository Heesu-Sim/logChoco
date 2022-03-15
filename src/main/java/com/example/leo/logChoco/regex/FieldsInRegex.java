package com.example.leo.logChoco.regex;


import lombok.Getter;

/**
 * Class that represents each type in regular expression
 * */
@Getter
public class FieldsInRegex {

    public final static String REGEX_FLOAT = "([+-]?([0-9]*[.])?[0-9]+)";
    public final static String REGEX_INTEGER = "((-+)?([0-9]+))";
    public final static String REGEX_BOOLEAN = "(true|false)";
    public final static String REGEX_IPV4 = "((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
    public final static String REGEX_ANYTHING = "(.*)";



}
