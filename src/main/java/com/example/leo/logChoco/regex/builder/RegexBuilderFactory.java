package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.regex.FieldType;

public class RegexBuilderFactory {

    public static AbstractRegexBuilder getRegexBuilder(FieldType type) {

        AbstractRegexBuilder builder = switch(type) {
            case INTEGER ->  new RegexIntegerBuilder();
            case IPV4 -> new RegexIPv4Builder();
            case BOOLEAN -> new RegexBooleanBuilder();
            case DATE -> new RegexDateBuilder();
            case ALPHABET -> new RegexAlphabetBuilder();
            case ANYTHING -> new RegexAnythingBuilder();
            case FLOAT -> new RegexFloatBuilder();
            default -> new RegexAnythingBuilder();
        };

        return builder;
    }
}
