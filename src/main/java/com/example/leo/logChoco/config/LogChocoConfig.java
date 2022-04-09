package com.example.leo.logChoco.config;

import com.example.leo.logChoco.config.entity.OutboundLogInfo;
import com.example.leo.logChoco.config.entity.ServerInfo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "config")
@Getter
@Setter
public class LogChocoConfig {
    private String filePath;

    @NestedConfigurationProperty
    private OutboundLogInfo outboundLogInfo;
    @NestedConfigurationProperty
    private List<ServerInfo> receiveServer;
    @NestedConfigurationProperty
    private List<ServerInfo> targetServer;

}
