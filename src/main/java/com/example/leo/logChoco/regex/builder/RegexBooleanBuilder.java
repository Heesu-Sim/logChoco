package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.regex.FieldsInRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexBooleanBuilder extends AbstractRegexBuilder{
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected RegexBooleanBuilder() {
        super(FieldsInRegex.REGEX_BOOLEAN);
    }

    @Override
    public AbstractRegexBuilder setMaxLengthIfSupported(int length) {
        logger.warn("Can't set max length to Boolean");
        return this;
    }

    @Override
    public AbstractRegexBuilder setMinLengthIfSupported(int length) {
        logger.warn("Can't set minimum length to Boolean");
        return this;
    }

    @Override
    public AbstractRegexBuilder setDateFormat(String format) {
        logger.warn("Can't set date format to Boolean");
        return this;
    }
}
