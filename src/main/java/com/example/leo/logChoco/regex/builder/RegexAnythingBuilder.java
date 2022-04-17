package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.regex.FieldsInRegex;

public class RegexAnythingBuilder extends AbstractRegexBuilder {

    protected RegexAnythingBuilder() {
        super(FieldsInRegex.REGEX_ANYTHING);
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
        return this;
    }
}
