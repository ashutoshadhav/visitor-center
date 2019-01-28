package com.island.visitorcenter.async.service.impl;

import com.island.visitorcenter.async.service.EmailAsyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;


@Service
public class EmailAsyncServiceImpl implements EmailAsyncService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExecutorService executorService;
    @Override
    public void sendAsyncEmail(String emailContent) {

        executorService.submit(() -> {
                try {
                    logger.info("Async Email sent for content={}",emailContent);

                } catch (Exception e) {
                    logger.error("Error when calling async for email", e);
                } finally {
                    logger.info("Clean up resources for email");

            }
        });
    }
}
