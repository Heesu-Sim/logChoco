package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
abstract public class AbstractFormatter {

    private OutboundLogInfo outboundLogInfo;
    private List<String> logTextList;
    private ReadFieldInfo fieldInfo;

    public AbstractFormatter(OutboundLogInfo outboundLogInfo, ReadFieldInfo fieldInfo, String logText) {
        logTextList = changeLogTextIntoList(fieldInfo, logText);
        this.outboundLogInfo = outboundLogInfo;
        this.fieldInfo = fieldInfo;


    }

    /**
     * split raw log and save it list
     * @param fieldInfo
     * @param  logText
     * */
    private List<String> changeLogTextIntoList(ReadFieldInfo fieldInfo, String logText) {
        String delimiter = fieldInfo.getDelimiter();
        String[] logArry = logText.split(delimiter, -1);
        return Arrays.asList(logArry);
    }

    /**
     * Make and return formatted log from raw log.
     * */
    public String getFormattedLog() {
        String header = createHeader();
        String body = createBody();
        String tail = createTail();

        StringBuilder sb = new StringBuilder();
        sb.append(header).append(body).append(tail).append("\n");
        return sb.toString();
    }

    /**
     * parse inbound log into key and value.
     * each key value is delimited by delimiter in yml file.
     * */
    protected String parseLogIntoKeyValue() {

        String delimiter = outboundLogInfo.getDelimiter();
        List<String> columnList = fieldInfo.getColumns();

        String result = (String) IntStream.range(0, columnList.size())
                .mapToObj(i -> {
                    StringBuilder sb = new StringBuilder();
                    String key = columnList.get(i);
                    String value = logTextList.get(i);

                    return sb.append(key).append("=").append(value).toString();
                })
                .collect(Collectors.joining(delimiter));


        return result;



    }


    /**
     * If necessary, create header of log
     * */
    abstract protected String createHeader();
    /**
     * If necessary, create tail of log
     * */
    abstract protected String createTail();
    /**
     * Create log body.
     * */
    abstract protected String createBody();

}
