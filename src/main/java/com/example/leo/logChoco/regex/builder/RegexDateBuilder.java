package com.example.leo.logChoco.regex.builder;

import com.example.leo.logChoco.regex.FieldsInRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class RegexDateBuilder extends AbstractRegexBuilder {

    // 주어진 스트링을 string builder을 하나하나 누하면서 map에 키값이 있는지 확인적. 있다면 그 키값을 map의 value로 치환
    private Map<String, String> regexMap;

    private Logger logger = LoggerFactory.getLogger(getClass());
    protected RegexDateBuilder() {
        super("");

        regexMap = new HashMap<>();
        regexMap.put("yyyy", FieldsInRegex.REGEX_DATE_YEAR);
        regexMap.put("MM", FieldsInRegex.REGEX_DATE_MONTH);
        regexMap.put("dd", FieldsInRegex.REGEX_DATE_DATE);
        regexMap.put("hh", FieldsInRegex.REGEX_DATE_HOUR_12);
        regexMap.put("HH", FieldsInRegex.REGEX_DATE_HOUR_24);
        regexMap.put("mm", FieldsInRegex.REGEX_DATE_MINUTE);
        regexMap.put("ss", FieldsInRegex.REGEX_DATE_SECOND);
        regexMap.put("S", FieldsInRegex.REGEX_DATE_MILLISECOND);
    }

    @Override
    public AbstractRegexBuilder setMaxLengthIfSupported(int length) {
        logger.warn("Can't set max length to Date");
        return this;
    }

    @Override
    public AbstractRegexBuilder setMinLengthIfSupported(int length) {
        logger.warn("Can't set max length to Date");
        return this;
    }

    @Override
    public AbstractRegexBuilder setDateFormat(String format) {

        Set<String> keySet = regexMap.keySet();

        for(String key : keySet) {
            format = format.replaceAll(key, regexMap.get(key));
        }

        super.setValue(format);
        return this;
    }


}
