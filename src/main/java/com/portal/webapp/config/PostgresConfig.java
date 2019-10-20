package com.portal.webapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("postgres")
public class PostgresConfig {

    private String srvUrl;
    private String userId;
    private String password;

    public String getSrvUrl() {
        return srvUrl;
    }

    public void setSrvUrl(String srvUrl) {
        this.srvUrl = srvUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
