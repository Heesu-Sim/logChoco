package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.entity.FieldType;

public class RegexBuilderFactory {

    public static AbstractRegexBuilder getRegexBuilder(FieldType type) {

        if(FieldType.INTEGER.equals(type)) {
            return new RegexIntegerBuilder();
        } else if(FieldType.IPV4.equals(type)) {
            return new RegexIPv4Builder();
        } else if(FieldType.BOOLEAN.equals(type)) {
            return new RegexBooleanBuilder();
        } else if(FieldType.DATE.equals(type)) {
            return new RegexDateBuilder();
        }

        return new RegexAnythingBuilder();
    }
}
