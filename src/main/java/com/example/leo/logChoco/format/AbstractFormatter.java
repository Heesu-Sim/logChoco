package com.example.leo.logChoco.format;

import com.example.leo.logChoco.config.entity.LogInfo;
import com.example.leo.logChoco.entity.ReadFieldInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
abstract public class AbstractFormatter {

    private LogInfo logInfo;
    private String logText;
    private ReadFieldInfo fieldInfo;

    public AbstractFormatter(LogInfo logInfo, ReadFieldInfo fieldInfo, String logText) {
        this.logInfo = logInfo;
        this.fieldInfo = fieldInfo;
        this.logText = logText;
    }

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

        String delimiter = logInfo.getDelimiter();
        String columns = fieldInfo.getColumns();

        return "";



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
