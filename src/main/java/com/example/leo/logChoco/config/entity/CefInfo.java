package com.example.leo.logChoco.config.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CefInfo {

    private String vendor;
    private String productName;
    private String productVersion;
    private boolean includeSyslogHeader;

    private String defaultSeverity;
    private String defaultEventName;
    private Map<String, String> defualtEventNameMapper;
}
