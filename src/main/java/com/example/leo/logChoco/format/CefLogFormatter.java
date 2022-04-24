package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.CefInfo;
import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.InboundLog;
import com.example.leo.logChoco.entity.ReadFieldInfo;

public class CefLogFormatter extends LeefLogFormatter{

    private CefInfo cefInfo;

    public CefLogFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, InboundLog inboundLog) {
        super(outboundLogInfo, fieldInfo, inboundLog);
        cefInfo = outboundLogInfo.getCefInfo();
    }

    @Override
    protected String createHeader() {
        return null;
    }

    @Override
    protected String createTail() {
        return super.createTail();
    }

    @Override
    protected String createBody() {
        return super.createBody();
    }


}
