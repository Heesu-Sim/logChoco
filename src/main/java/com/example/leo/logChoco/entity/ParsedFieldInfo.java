package com.example.leo.logChoco.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Class that has inbound log text and its log id.
 * */

@Getter
@Setter
@ToString
public class ParsedFieldInfo {
    private String logText;
    private String logId;
}
