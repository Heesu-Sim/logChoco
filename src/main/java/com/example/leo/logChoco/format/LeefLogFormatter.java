package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.InboundLog;
import com.example.leo.logChoco.entity.ReadFieldInfo;
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
    private final String DEFAULT_LEEF_DELIMITER = "\t";
    private String delimiter;

    public LeefLogFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, InboundLog inboundLog) {
        super(outboundLogInfo, fieldInfo, inboundLog);

        String configDelimiter = super.outboundLogInfo.getDelimiter();
        delimiter = StringUtils.hasText(configDelimiter) ? configDelimiter : DEFAULT_LEEF_DELIMITER;
    }

    @Override
    protected String createHeader() {
        String leefVersion = super.outboundLogInfo.getLeefVersion();
        String vendor = super.outboundLogInfo.getVendor();
        String productName = super.outboundLogInfo.getProductName();
        String productVersion = super.outboundLogInfo.getProductVersion();
        boolean includeHeader = super.outboundLogInfo.isIncludeSyslogHeader();



        // check if leef version is valid.
        if(!"1.0".equals(leefVersion) && !"2.0".equals(leefVersion)) {
            leefVersion = DEFAULT_LEEF_VERSION;
            logger.warn("LEEF version {} is not valid. change it to 2.0", leefVersion);
        }

        StringBuilder sb = new StringBuilder();

        // create syslog header
        if(includeHeader) {
            SimpleDateFormat df = new SimpleDateFormat("MMM dd HH:mm:ss", new Locale("en", "US"));
            String timeStamp = df.format(Calendar.getInstance().getTime());

            sb.append(timeStamp).append(" ").append(super.remoteAddr).append(" ");
        }

        //create LEEF header
        sb.append("LEEF:").append(leefVersion).append("|").append(vendor).append("|").append(productName)
                .append("|").append(productVersion).append("|").append(super.eventId);

        if(leefVersion.equals(DEFAULT_LEEF_VERSION)) {
            sb.append("|");
            if(!delimiter.equals(DEFAULT_LEEF_DELIMITER)) {
                sb.append(delimiter);
            }
            sb.append("|");
        }


        return sb.toString();
    }


    @Override
    protected String createTail() {
        return "";
    }

    @Override
    protected String createBody() {
        String body = parseLogIntoKeyValue(delimiter);
        return body;
    }


}
