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

    public RegexIntegerBuilder() {
        super(FieldsInRegex.REGEX_INTEGER);
    }

    @Override
    public AbstractRegexBuilder setExactLengthIfSupported(int length) {

        super.removeExactLengthIfExist();
        String value = super.getValue();

        if(super.removeMinOrMaxLengthIfExist()) {
            value = integerFormat;
            logger.warn("Remove min or max length option for integer to set exact length");
        }

        int rmIndex = value.lastIndexOf('+');
        value = value.substring(0, rmIndex) + value.substring(rmIndex + 1);
        String prefix = value.substring(0, value.indexOf("]") + 1);
        String postfix = value.substring(value.indexOf("]") + 1);
        String middle = super.getExactLength(length);

        String newVal = prefix + middle + postfix;
        super.setValue(newVal);

        return this;
    }

    @Override
    public AbstractRegexBuilder setMaxLengthIfSupported(int length) {

        String value = super.getValue();

        if(super.removeExactLengthIfExist()) {
            value = integerFormat;
            logger.warn("Remove exact length option for integer to set max  length");
        }

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
        logger.debug("Integer regex after set max length : {}", newVal);
        return this;
    }

    @Override
    public AbstractRegexBuilder setMinLengthIfSupported(int length) {

        String value = super.getValue();

        if(super.removeExactLengthIfExist()) {
            value = integerFormat;
            logger.warn("Remove exact length option for integer to set min  length");
        }
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
        logger.debug("Integer regex after set minimum length : {}", newVal);
        return this;
    }

    @Override
    public AbstractRegexBuilder setDateFormat(String format) {
        logger.warn("Can't set date format to Integer");
        return this;
    }


}
