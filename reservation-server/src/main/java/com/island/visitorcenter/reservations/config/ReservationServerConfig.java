package com.island.visitorcenter.reservations.config;

import com.island.visitorcenter.api.config.VisitorCenterAPISpringConfig;
import com.island.visitorcenter.persistance.config.VisitorCenterPersistanceSpringConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Holds the basic beans & configs for the ReservationServer to work
 *
 * @author aadhav

 */

@Configuration
@EnableCaching(mode = AdviceMode.ASPECTJ)
@EnableAutoConfiguration
@Import({VisitorCenterPersistanceSpringConfig.class, VisitorCenterAPISpringConfig.class})
public class ReservationServerConfig {

}
