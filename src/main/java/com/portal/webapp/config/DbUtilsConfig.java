package com.portal.webapp.config;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbUtilsConfig {

    @Autowired
    private PostgresConfig postgresConfig;

    @Bean
    public DataSource postgresConnection() {
        HikariDataSource basicDataSource = new HikariDataSource();
        basicDataSource.setDriverClassName("org.postgresql.Driver");
        basicDataSource.setUsername(postgresConfig.getUserId());
        basicDataSource.setPassword(postgresConfig.getPassword());
        basicDataSource.setJdbcUrl(postgresConfig.getSrvUrl());
        return basicDataSource;
    }
}
