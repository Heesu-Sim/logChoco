package com.example.leo.logChoco.format;


import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.ReadFieldInfo;

public class LogFormatterFactory {

    public static AbstractFormatter getFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, String logText) {

        OutboundLogFormat logFormat = fieldInfo.getLogFormat();
        AbstractFormatter formatter = switch(logFormat) {
            case CEF -> new CefLogFormatter(outboundLogInfo, fieldInfo, logText);
            case LEEF ->  new LeefLogFormatter(outboundLogInfo, fieldInfo, logText);
            default -> new LeefLogFormatter(outboundLogInfo, fieldInfo, logText);
        };

        return formatter;
    }
}
