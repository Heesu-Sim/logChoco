package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.ReadFieldInfo;

public class LeefLogFormatter extends AbstractFormatter {


    public LeefLogFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, String logText) {
        super(outboundLogInfo, fieldInfo, logText);
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
