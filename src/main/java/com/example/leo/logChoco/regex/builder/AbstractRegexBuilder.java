package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.entity.FieldType;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for each type of fields.
 * Extends necessary methods for each field.
 * */
public abstract class AbstractRegexBuilder {

    @Getter
    @Setter
    private String value;

    protected AbstractRegexBuilder(String value) {
        this.value = value;
    }

    public static AbstractRegexBuilder getRegexBuilder(FieldType type) {

        if(FieldType.INTEGER.equals(type)) {
            return new RegexIntegerBuilder();
        } else if(FieldType.IPV4.equals(type)) {
            return new RegexIPv4Builder();
        } else if(FieldType.BOOLEAN.equals(type)) {
            return new RegexBooleanBuilder();
        } else if(FieldType.DATE.equals(type)) {
            return new RegexDateBuilder();
        }

        return new RegexAnythingBuilder();
    }

//    public AbstractRegexBuilder addOptionToField(AbstractRegexBuilder builder, FieldOption option) {
//        if(FieldOption.EMPTY.equals(option)) {
//            return builder.addPossibleEmpty();
//        } else if(FieldOption.MAXLENGTH.equals(option)) {
//            return builder.setMaxLengthIfSupported()
//        }
//    }

    /**
     * Set if a field can be omitted for all Fields except BOOLEAN
     * */
    public AbstractRegexBuilder addPossibleEmpty() {

        String value = getValue();

        if(!value.endsWith("?")) {
            setValue(value + "?");
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

}
