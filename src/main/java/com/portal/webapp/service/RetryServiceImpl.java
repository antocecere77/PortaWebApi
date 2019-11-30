package com.portal.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RetryServiceImpl implements RetryService {

    private static final Logger logger = LoggerFactory.getLogger(RetryServiceImpl.class);

    @Override
    public void retryService(String sql) throws Exception {
        logger.info(String.valueOf(new Date()));
        logger.info("Eseguo script... " + sql);

        throw new Exception("Ciao");
    }

    @Override
    public void recover(Exception e, String sql) {
        logger.info(String.valueOf(new Date()));
        logger.info("Recupero con la stringa " + sql);
    }
}
