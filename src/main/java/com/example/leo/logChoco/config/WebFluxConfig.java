package com.example.leo.logChoco.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.List;

@Configuration
@EnableWebFlux
@RequiredArgsConstructor
/**
 * #TODO move allowed origin to setting file.
 * */
public class WebFluxConfig implements WebFluxConfigurer {

    private final LogChocoConfig logChocoConfig;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public void addCorsMappings(CorsRegistry registry) {

        List<String> allowCorsOrigins = logChocoConfig.getAllowCorsOrigins();
        CorsRegistration registration = registry.addMapping("/**")
                .allowedMethods("*")
                .allowCredentials(true).maxAge(3600);

        allowCorsOrigins.stream().forEach(origin ->   {
            registration.allowedOrigins(origin.trim());
            logger.debug("add Allow-Cors-Origin: {}", origin);
        });
    }
}
