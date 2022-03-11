package com.example.leo.logChoco.reader.service.regex.builder;

import lombok.Getter;
import lombok.Setter;

/**
 * Base class for each type of fields.
 * Extends necessary methods for each field.
 * */
public class AbstractRegexBuilder {

    @Getter
    @Setter
    private String value;

    protected AbstractRegexBuilder(String value) {
        this.value = value;
    }

    /**
     * Set if a field can be omitted for all Fields except BOOLEAN
     * */
    protected  AbstractRegexBuilder addPossibleEmpty() {
        return this;
    }


    /**
     * Set maximum number of digits or characters
     * */
    protected AbstractRegexBuilder setMaxLength(int length) {
        return this;
    }

    /**
     * Set minimum number of digit or characters
     * */
    protected AbstractRegexBuilder setMinLength(int length) {
        return this;
    }

}
