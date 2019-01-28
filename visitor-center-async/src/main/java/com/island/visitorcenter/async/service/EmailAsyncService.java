package com.island.visitorcenter.async.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailAsyncService {

    void sendAsyncEmail(String emailContent);
}
