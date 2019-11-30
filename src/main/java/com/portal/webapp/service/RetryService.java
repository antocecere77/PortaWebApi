package com.portal.webapp.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

public interface RetryService {

    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 10000))
    void retryService(String sql) throws Exception;

    @Recover
    void recover(Exception e, String sql);
}
