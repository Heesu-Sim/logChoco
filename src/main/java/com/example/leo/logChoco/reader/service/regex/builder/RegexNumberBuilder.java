package com.example.leo.logChoco.reader.service.regex.builder;

import com.example.leo.logChoco.reader.service.regex.RegexFields;
import com.example.leo.logChoco.reader.service.regex.builder.AbstractRegexBuilder;
import org.springframework.util.StringUtils;

/**
 * Builder for number regex
 * */
public class RegexNumberBuilder extends AbstractRegexBuilder {

    private String integerFormat = RegexFields.REGEX_INTEGER;
    private String baseFormat;

    public RegexNumberBuilder() {
        super(RegexFields.REGEX_INTEGER);
        baseFormat = integerFormat.substring(integerFormat.indexOf("["), integerFormat.indexOf("]") + 1);
    }

    @Override
    public RegexNumberBuilder addPossibleEmpty() {
        AbstractRegexBuilder builder = super.addPossibleEmpty();
        String value = builder.getValue();

        if(!value.endsWith("?")) {
            builder.setValue(value + "?");
        }
        return this;
    }

    @Override
    public RegexNumberBuilder setMaxLength(int length) {
        AbstractRegexBuilder builder = super.addPossibleEmpty();
        String value = builder.getValue();

        String prefix, middle, postfix;

        // when min or max number of digits is already set
        if(value.contains("{") && value.contains("}") && value.contains(",")) {
            prefix = value.substring(0, value.indexOf(",") + 1);
//            String middle = value.substring(value.indexOf(",") + 1, value.indexOf("}"));
            postfix = value.substring(value.indexOf("}"));
            middle = String.valueOf(length);

        } else {

            value = value.replace("+", "");
            prefix = value.substring(0, value.indexOf("]") + 1);
            postfix = value.substring(value.indexOf("]") + 1);
            middle = "{," + length + "}";
        }

        builder.setValue(prefix + middle + postfix);
        return this;
    }




}
