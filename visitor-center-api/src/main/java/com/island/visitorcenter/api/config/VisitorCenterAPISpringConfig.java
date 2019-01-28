package com.island.visitorcenter.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 *
 *@author aadhav
 */
@Configuration
@EnableAutoConfiguration
//@Compo("com.island.visitorcenter.api.model")
public class VisitorCenterAPISpringConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorCenterAPISpringConfig.class);

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("Enabled JPA repositories for basePackages=com.island.visitorcenter.persistance.repository");
    }
}