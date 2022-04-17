package com.example.leo.logChoco.regex.builder;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Base class for each type of fields.
 * Extends necessary methods for each field.
 * */
abstract public class AbstractRegexBuilder {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    @Setter
    private String value;

    protected AbstractRegexBuilder(String value) {
        this.value = value;
    }

    public void addRegexOptions(Map<String, String> options) {
        options.entrySet().stream().forEach(entry -> {
            this.addRegexOption(entry.getKey(), entry.getValue());
        });
    }

    public void addRegexOption(String key, String optionValue) {
        key = key.toUpperCase();

        try{
            FieldOption option = FieldOption.valueOf(key);

            if(FieldOption.EMPTY.equals(option)) {
                this.addPossibleEmpty(Boolean.parseBoolean(optionValue));
            } else if(FieldOption.MAXLENGTH.equals(option)) {
                this.setMaxLengthIfSupported(Integer.parseInt(optionValue));
            } else if(FieldOption.MINLENGTH.equals(option)) {
                this.setMinLengthIfSupported(Integer.parseInt(optionValue));
            } else if(FieldOption.DATEFORMAT.equals(option)) {
                this.setDateFormat(optionValue);
            }
        } catch(IllegalArgumentException e) {
            logger.error("option {} is not supported.", key);
            e.printStackTrace();
        }
    }

    /**
     * Set if a field can be omitted for all Fields except BOOLEAN
     * */
    public AbstractRegexBuilder addPossibleEmpty(boolean isPossibleEmpty) {

        if(isPossibleEmpty) {
            String value = getValue();

            if (!value.endsWith("?")) {
                setValue(value + "?");
            }
        }
        return this;
    }

    /**
     * Set maximum number of digits or characters
     * */
    abstract public AbstractRegexBuilder setMaxLengthIfSupported(int length);

    /**
     * Set minimum number of digit or characters
     * */
    abstract public AbstractRegexBuilder setMinLengthIfSupported(int length);

    /**
     * Set date format
     * */
    abstract public AbstractRegexBuilder setDateFormat(String format);

}
