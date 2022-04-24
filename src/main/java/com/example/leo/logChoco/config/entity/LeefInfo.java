package com.example.leo.logChoco.config.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeefInfo {
    // version of leef format
    private String leefVersion;
    // name of vendor that send logs
    private String vendor;
    // name of product that send logs
    private String productName;
    // version of product that send logs
    private String productVersion;
    // delimiter for outbound log.
    private String delimiter;

    // include syslog header or not
    private boolean includeSyslogHeader;
}
