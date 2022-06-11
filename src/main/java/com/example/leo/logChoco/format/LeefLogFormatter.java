package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.LeefInfo;
import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.log.InboundLog;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import com.example.leo.logChoco.entity.log.LogInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Class that convert raw log into LEEF format.
 * */
public class LeefLogFormatter extends AbstractFormatter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final String DEFAULT_LEEF_VERSION = "2.0";
    private final String DEFAULT_LEEF_DELIMITER_FOR_LEEF = "\t";
    private String delimiter;
    private LeefInfo leefInfo;

    public LeefLogFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, LogInfo inboundLog) {
        super(outboundLogInfo, fieldInfo, inboundLog);
        leefInfo = super.outboundLogInfo.getLeefInfo();

        String configDelimiter = leefInfo.getDelimiter();
        delimiter = StringUtils.hasText(configDelimiter) ? configDelimiter : DEFAULT_LEEF_DELIMITER_FOR_LEEF;
    }

    @Override
    protected String createHeader() {
        String leefVersion = leefInfo.getLeefVersion();
        String vendor = leefInfo.getVendor();
        String productName = leefInfo.getProductName();
        String productVersion = leefInfo.getProductVersion();
        boolean includeHeader = leefInfo.isIncludeSyslogHeader();

        // check if leef version is valid.
        if(!"1.0".equals(leefVersion) && !"2.0".equals(leefVersion)) {
            leefVersion = DEFAULT_LEEF_VERSION;
            logger.warn("LEEF version {} is not valid. change it to 2.0", leefVersion);
        }

        StringBuilder sb = new StringBuilder();

        // create syslog header
        if(includeHeader) {
            String syslogHeader = getSyslogHeader();
            sb.append(syslogHeader).append(" ");
        }

        //create LEEF header
        sb.append("LEEF:").append(leefVersion).append("|").append(vendor).append("|").append(productName)
                .append("|").append(productVersion).append("|").append(super.eventId);

        if(leefVersion.equals(DEFAULT_LEEF_VERSION)) {
            sb.append("|");
            if(!delimiter.equals(DEFAULT_LEEF_DELIMITER_FOR_LEEF)) {
                sb.append(delimiter);
            }
            sb.append("|");
        }


        return sb.toString();
    }

    protected String getSyslogHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTimeStamp()).append(" ").append(super.remoteAddr).append(" ");
        return sb.toString();
    }

    protected String getTimeStamp() {
        SimpleDateFormat df = new SimpleDateFormat("MMM dd HH:mm:ss", new Locale("en", "US"));
        String timeStamp = df.format(Calendar.getInstance().getTime());
        return timeStamp;
    }

    @Override
    protected String createTail() {
        return "";
    }

    @Override
    protected String createBody() {
        String body = parseLogIntoKeyValue(delimiter, false);
        return body;
    }


}
