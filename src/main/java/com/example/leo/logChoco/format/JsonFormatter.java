package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.log.InboundLog;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import com.example.leo.logChoco.entity.log.LogInfo;

public class JsonFormatter extends AbstractFormatter {

    private final String DEFAULT_LEEF_DELIMITER_FOR_JSON = ",";
    private String delimiter;
    public JsonFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, LogInfo inboundLog) {
        super(outboundLogInfo, fieldInfo, inboundLog);

        delimiter = DEFAULT_LEEF_DELIMITER_FOR_JSON;
    }

    @Override
    protected String createHeader() {
        return "{";
    }

    @Override
    protected String createTail() {
        return "}";
    }

    @Override
    protected String createBody() {
        return super.parseLogIntoKeyValue(delimiter, true);
    }
}
