package com.ozapp.foreignexchanger.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = true)
@PropertySource("classpath:application.yaml")
public class AppProperty {
    private String fixerUrl;

    private String fixerAccessKey;
}
