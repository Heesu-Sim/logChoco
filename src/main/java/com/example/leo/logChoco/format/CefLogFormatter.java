package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.ReadFieldInfo;

public class CefLogFormatter extends AbstractFormatter{


    public CefLogFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, String logText) {
        super(outboundLogInfo, fieldInfo, logText);
    }

    @Override
    protected String createHeader() {
        return null;
    }

    @Override
    protected String createTail() {
        return null;
    }

    @Override
    protected String createBody() {
        return null;
    }


}
