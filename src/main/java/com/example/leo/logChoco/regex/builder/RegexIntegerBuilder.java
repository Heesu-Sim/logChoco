package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.regex.FieldsInRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builder for number regex
 * */
public class RegexIntegerBuilder extends AbstractRegexBuilder {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private String integerFormat = FieldsInRegex.REGEX_INTEGER;
    private String baseFormat;

    public RegexIntegerBuilder() {
        super(FieldsInRegex.REGEX_INTEGER);
        baseFormat = integerFormat.substring(integerFormat.indexOf("["), integerFormat.indexOf("]") + 1);
    }


    @Override
    public AbstractRegexBuilder setMaxLengthIfSupported(int length) {

        String value = super.getValue();

        String prefix, middle, postfix;

        // when min or max number of digits is already set
        if(value.contains("{") && value.contains("}") && value.contains(",")) {
            prefix = value.substring(0, value.indexOf(",") + 1);
            postfix = value.substring(value.indexOf("}"));
            middle = String.valueOf(length);

        } else {
            int rmIndex = value.lastIndexOf('+');
            value = value.substring(0, rmIndex) + value.substring(rmIndex + 1);
            prefix = value.substring(0, value.indexOf("]") + 1);
            postfix = value.substring(value.indexOf("]") + 1);
            middle = "{1," + length + "}";
        }
        String newVal = prefix + middle + postfix;
        super.setValue(newVal);
        logger.debug("regex after set max length : {}", newVal);
        return this;
    }

    @Override
    public AbstractRegexBuilder setMinLengthIfSupported(int length) {

        String value = super.getValue();

        String prefix, middle, postfix;

        if(value.contains("{") && value.contains("}") && value.contains(",")) {
            prefix = value.substring(0, value.indexOf("{") + 1);
            postfix = value.substring(value.indexOf(","));
            middle = String.valueOf(length);

        } else {
            int rmIndex = value.lastIndexOf('+');
            value = value.substring(0, rmIndex) + value.substring(rmIndex + 1);
            prefix = value.substring(0, value.indexOf("]") + 1);
            postfix = value.substring(value.indexOf("]") + 1);
            middle = "{" + length + ",}";
        }
        String newVal = prefix + middle + postfix;
        super.setValue(newVal);
        logger.debug("regex after set minimum length : {}", newVal);
        return this;
    }

}
