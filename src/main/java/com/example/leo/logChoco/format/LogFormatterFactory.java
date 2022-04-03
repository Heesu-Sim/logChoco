package com.example.leo.logChoco.format;


import com.example.leo.logChoco.config.entity.LogInfo;
import com.example.leo.logChoco.entity.ReadFieldInfo;

public class LogFormatterFactory {

    public static AbstractFormatter getFormatter(LogInfo logInfo, ReadFieldInfo fieldInfo, String logText) {

        OutboundLogFormat logFormat = fieldInfo.getLogFormat();
        AbstractFormatter formatter = switch(logFormat) {
            case CEF -> new CefLogFormatter(logInfo, fieldInfo, logText);
            case LEEF ->  new LeefLogFormatter(logInfo, fieldInfo, logText);
            default -> new LeefLogFormatter(logInfo, fieldInfo, logText);
        };

        return formatter;
    }
}
