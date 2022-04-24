package com.example.leo.logChoco.config.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Class that has information about sender.
 * This class is for creating log header and etc.
 * */
@Getter
@Setter
public class OutboundLogInfo {

    @NestedConfigurationProperty
    private LeefInfo leefInfo;
    @NestedConfigurationProperty
    private CefInfo cefInfo;

}
