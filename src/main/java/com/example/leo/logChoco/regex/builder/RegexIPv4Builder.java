package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.regex.FieldsInRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegexIPv4Builder extends AbstractRegexBuilder{

    private Logger logger = LoggerFactory.getLogger(getClass());

    protected RegexIPv4Builder() {
        super(FieldsInRegex.REGEX_IPV4);
    }

    @Override
    public AbstractRegexBuilder setMaxLengthIfSupported(int length) {
        logger.warn("Can't set max length to IP adress");
        return this;
    }

    @Override
    public AbstractRegexBuilder setMinLengthIfSupported(int length) {
        logger.warn("Can't set minimum length to IP adress");
        return this;
    }

    @Override
    public AbstractRegexBuilder setDateFormat(String format) {
        logger.warn("Can't set date format to IPv4");
        return this;
    }
}
