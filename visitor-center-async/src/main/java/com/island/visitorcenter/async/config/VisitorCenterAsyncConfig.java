package com.island.visitorcenter.async.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author aadhav
 */
@Configuration
public class VisitorCenterAsyncConfig {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private ExecutorService executorService;

    @Bean
    public ExecutorService executorService(@Value("10") Integer threadPoolSize) {
        return Executors.newFixedThreadPool(threadPoolSize);
    }

    @PreDestroy
    public void shutdown() {
        logger.info("Shutting down executor service...");
        if (executorService != null) {
            executorService.shutdown(); // Disable new tasks from being submitted

            try {
                // Wait a while for existing tasks to terminate
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow(); // Cancel currently executing
                    // tasks
                    // Wait a while for tasks to respond to being cancelled
                    if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                        logger.error("Pool did not terminate");
                    }
                }
            } catch (InterruptedException ie) {
                // (Re-)Cancel if current thread also interrupted
                executorService.shutdownNow();
                // Preserve interrupt status
                Thread.currentThread().interrupt();
            }
        }
        
        logger.info("Executor service was shut down.");
    }

}
