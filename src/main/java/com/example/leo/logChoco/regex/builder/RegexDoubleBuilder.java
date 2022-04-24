package com.example.leo.logChoco.regex.builder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexDoubleBuilder extends AbstractRegexBuilder{

    private Logger logger = LoggerFactory.getLogger(getClass());

    protected RegexDoubleBuilder(String value) {
        super(value);
    }

    @Override
    public AbstractRegexBuilder setExactLengthIfSupported(int length) {
        return this;
    }

    @Override
    public AbstractRegexBuilder setMaxLengthIfSupported(int length) {
        return this;
    }

    @Override
    public AbstractRegexBuilder setMinLengthIfSupported(int length) {
        return this;
    }

    @Override
    public AbstractRegexBuilder setDateFormat(String format) {
        logger.warn("Can't set date format to Double");
        return this;
    }
}
