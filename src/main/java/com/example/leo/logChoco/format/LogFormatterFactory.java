package com.example.leo.logChoco.format;


import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.InboundLog;
import com.example.leo.logChoco.entity.ReadFieldInfo;

public class LogFormatterFactory {

    public static AbstractFormatter getFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, InboundLog inboundLog) {

        OutboundLogFormat logFormat = fieldInfo.getLogFormat();
        AbstractFormatter formatter = switch(logFormat) {
            case CEF -> new CefLogFormatter(outboundLogInfo, fieldInfo, inboundLog);
            case LEEF ->  new LeefLogFormatter(outboundLogInfo, fieldInfo, inboundLog);
            default -> new LeefLogFormatter(outboundLogInfo, fieldInfo, inboundLog);
        };

        return formatter;
    }
}
