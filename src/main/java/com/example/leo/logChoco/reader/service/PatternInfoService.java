package com.example.leo.logChoco.reader.service;

import com.example.leo.logChoco.config.LogChocoConfig;
import com.example.leo.logChoco.reader.service.regex.RegexFields;
import com.example.leo.logChoco.reader.service.regex.builder.RegexNumberBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 설정파일에서 ${DATE}`${TIME_STAMP}`{NUMBER}`{BOOLEAN}
@Service
public class PatternInfoService {

    @Autowired
    private LogChocoConfig logChocoConfig;

    public Map<String, String> patternMap = new HashMap<>();




    @PostConstruct
    public void init() {


    }


    private boolean validatePattern(String input, String pattern) {
        pattern = "^" + pattern + "$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        return m.matches();
    }



}
