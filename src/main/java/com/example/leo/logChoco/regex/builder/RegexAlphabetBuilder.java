package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.regex.FieldsInRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexAlphabetBuilder extends AbstractRegexBuilder {
    private Logger logger = LoggerFactory.getLogger(getClass());
    protected RegexAlphabetBuilder() {
        super(FieldsInRegex.REGEX_ALPHABET);
    }

    @Override
    public AbstractRegexBuilder setExactLengthIfSupported(int length) {
        if(super.removeMinOrMaxLengthIfExist()) {
            logger.warn("Remove min or max length option for alphabet to set exact length");
        }

        super.removeExactLengthIfExist();

        String value = this.getValue();

        int end = value.indexOf(")");
        String prefix = value.substring(0, end);
        String middle = super.getExactLength(length);
        String postfix = value.substring(end);

        String newVal = prefix + middle + postfix;

        super.setValue(newVal);
        logger.debug("Alphabet regex after set exact length : {}", newVal);
        return this;
    }

    @Override
    public AbstractRegexBuilder setMaxLengthIfSupported(int length) {

        if(super.removeExactLengthIfExist()) {
            logger.warn("Remove exact length option for alphabet to set max  length");
        }

        String value = super.getValue();

        String prefix, middle, postfix;

        // when min or max number of digits is already set
        if(value.contains("{") && value.contains("}") && value.contains(",")) {
            prefix = value.substring(0, value.indexOf(",") + 1);
            postfix = value.substring(value.indexOf("}"));
            middle = String.valueOf(length);

        } else {
            int end = value.indexOf(")");
            prefix = value.substring(0, end);
            middle = "{1," + length + "}";
            postfix = value.substring(end);
        }
        String newVal = prefix + middle + postfix;
        super.setValue(newVal);
        logger.debug("Alphabet regex after set max length : {}", newVal);
        return this;
    }

    @Override
    public AbstractRegexBuilder setMinLengthIfSupported(int length) {

        if(super.removeExactLengthIfExist()) {
            logger.warn("Remove exact length option for alphabet to set min  length");
        }

        String value = super.getValue();

        String prefix, middle, postfix;

        if(value.contains("{") && value.contains("}") && value.contains(",")) {
            prefix = value.substring(0, value.indexOf("{") + 1);
            postfix = value.substring(value.indexOf(","));
            middle = String.valueOf(length);

        } else {
            int end = value.indexOf(")");
            prefix = value.substring(0, end);
            middle = "{" + length + ",}";
            postfix = value.substring(end);
        }
        String newVal = prefix + middle + postfix;
        super.setValue(newVal);
        logger.debug("Alphabet regex after set minimum length : {}", newVal);
        return this;
    }

    @Override
    public AbstractRegexBuilder setDateFormat(String format) {
        logger.warn("Can't set date format to Alphabet");
        return this;
    }

}
