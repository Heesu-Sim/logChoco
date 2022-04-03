package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.LogInfo;
import com.example.leo.logChoco.entity.ReadFieldInfo;

public class LeefLogFormatter extends AbstractFormatter {


    public LeefLogFormatter(LogInfo logInfo, ReadFieldInfo fieldInfo, String logText) {
        super(logInfo, fieldInfo, logText);
    }

    @Override
    protected String createHeader() {
        return "";
    }

    @Override
    protected String createTail() {
        return "";
    }

    @Override
    protected String createBody() {
        String body = parseLogIntoKeyValue();
        return body;
    }


}
