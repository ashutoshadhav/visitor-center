package com.island.visitorcenter.reservation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Holds the basic beans & configs for the ReservationServer Tests to work
 *
 * @author aadhav

 */

@Configuration
@EnableJpaRepositories(basePackages = "com.island.visitorcenter.persistance")
@ComponentScan(basePackages = "com.island.visitorcenter")
//@PropertySource("application-test.properties")
@EnableTransactionManagement
public class ReservationServerTestConfig {

}
