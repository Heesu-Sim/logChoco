package com.example.leo.logChoco.format;


import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.log.InboundLog;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import com.example.leo.logChoco.entity.log.LogInfo;

public class LogFormatterFactory {

    public static AbstractFormatter getFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, LogInfo inboundLog) {

        OutboundLogFormat logFormat = fieldInfo.getLogFormat();
        AbstractFormatter formatter = switch(logFormat) {
            case CEF -> new CefLogFormatter(outboundLogInfo, fieldInfo, inboundLog);
            case LEEF ->  new LeefLogFormatter(outboundLogInfo, fieldInfo, inboundLog);
            case JSON ->  new JsonFormatter(outboundLogInfo, fieldInfo, inboundLog);
            default -> new LeefLogFormatter(outboundLogInfo, fieldInfo, inboundLog);
        };

        return formatter;
    }
}
