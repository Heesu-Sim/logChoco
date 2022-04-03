package com.example.leo.logChoco.config.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Class that has information about sender.
 * This class is for creating log header and etc.
 * */
@Getter
@Setter
public class OutboundLogInfo {

    // version of leef format
    private String leefVersion;
    // name of vendor that send logs
    private String vendor;
    // name of product that send logs
    private String productName;
    // version of product that send logs
    private String productVersion;
    // delimiter for outbound log.
    private String delimiter = "\t";


}
