package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.CefInfo;
import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.log.InboundLog;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import com.example.leo.logChoco.entity.log.LogInfo;

import java.util.Map;
import java.util.Optional;

public class CefLogFormatter extends LeefLogFormatter{

    private CefInfo cefInfo;
    private int severityIndex;
    private int eventNameIndex;

    private String DEFAULT_CEF_VERSION = "0";

    public CefLogFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, LogInfo inboundLog) {
        super(outboundLogInfo, fieldInfo, inboundLog);
        this.cefInfo = outboundLogInfo.getCefInfo();
        this.severityIndex = fieldInfo.getSeverityIndex();
        this.eventNameIndex = fieldInfo.getEventNameIndex();
    }

    @Override
    protected String createHeader() {

        StringBuilder sb = new StringBuilder();

        String vendor = cefInfo.getVendor();
        String productName = cefInfo.getProductName();
        String productVersion = cefInfo.getProductVersion();

        // If index of severity is not defined, get default value from yml file.
        String severity = severityIndex < 0? cefInfo.getDefaultSeverity() : logTextList.get(severityIndex);

        /* If index of event name is not defined, get it from 'defaultEventNameMapper' or 'defaultEventName' in yml file.
           Search name from 'defaultEventNameMapper' with eventId and if no name is found, set eventName with 'defaultEventName'
        * */
        String eventName;
        if(eventNameIndex >= 0) {
            eventName = logTextList.get(eventNameIndex);
        } else {
            Map<String, String> nameMapper = cefInfo.getDefualtEventNameMapper();

            eventName = Optional.ofNullable(nameMapper.get(eventId)).orElse(cefInfo.getDefaultEventName());
        }

        if(cefInfo.isIncludeSyslogHeader()) {
            String syslogHeader = super.getSyslogHeader();
            sb.append(syslogHeader).append(" ");
        }

        sb.append("CEF:").append(DEFAULT_CEF_VERSION).append("|").append(vendor).append("|")
                .append(productName).append("|").append(productVersion).append("|").append(super.eventId)
                .append("|").append(eventName).append("|").append(severity).append("|");

        return sb.toString();
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
