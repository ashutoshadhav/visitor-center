package com.island.visitorcenter.persistance.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;

/**
 * Repository beans do not need to be specified as they are scanned by
 * annotation. Non-JTA transaction manager.
 *
 *@author aadhav
 */
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = {"com.island.visitorcenter.persistance.repository"})
@EntityScan("com.island.visitorcenter.persistance.model")
public class VisitorCenterPersistanceSpringConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorCenterPersistanceSpringConfig.class);

   /* @Bean
    public InventoryRepositoryImpl createInventoryRepositoryImpl () {
        return new InventoryRepositoryImpl();
    }*/

    @PostConstruct
    public void postConstruct() {
        LOGGER.info("Enabled JPA repositories for basePackages=com.island.visitorcenter.persistance.repository");
    }
}