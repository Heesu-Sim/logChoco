package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.regex.FieldsInRegex;

public class RegexDateBuilder extends AbstractRegexBuilder {
    protected RegexDateBuilder() {
        super("");
    }

    @Override
    public AbstractRegexBuilder setMaxLengthIfSupported(int length) {
        return null;
    }

    @Override
    public AbstractRegexBuilder setMinLengthIfSupported(int length) {
        return null;
    }
}
