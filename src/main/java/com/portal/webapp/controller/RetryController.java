package com.portal.webapp.controller;

import com.portal.webapp.service.RetryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class RetryController {

    private static final Logger logger = LoggerFactory.getLogger(RetryController.class);

    @Autowired
    RetryService retryService;

    @GetMapping("/sqlretry")
    @ExceptionHandler({ Exception.class })
    public String sqlRetry() throws Exception {

        logger.info("===============================");
        logger.info("Inside RestController mathod..");
        retryService.retryService("select * from articoli");

        return "Success";
    }
}
