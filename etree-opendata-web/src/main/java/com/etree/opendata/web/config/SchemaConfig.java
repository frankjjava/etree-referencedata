package com.etree.opendata.web.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "config-params")
@Data
public class SchemaConfig {

    private Map<String, Object> entities;

}
