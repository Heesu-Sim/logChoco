package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.InboundLog;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import org.springframework.util.StringUtils;

public class JsonFormatter extends AbstractFormatter {

    private String delimiter;
    public JsonFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, InboundLog inboundLog) {
        super(outboundLogInfo, fieldInfo, inboundLog);

//        String configDelimiter = super.outboundLogInfo.getDelimiter();
//        delimiter = StringUtils.hasText(configDelimiter) ? configDelimiter : DEFAULT_LEEF_DELIMITER;
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
        return super.parseLogIntoKeyValue(delimiter);
    }
}
