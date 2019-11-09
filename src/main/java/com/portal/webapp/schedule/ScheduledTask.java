package com.portal.webapp.schedule;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.portal.webapp.mapping.Articolo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class ScheduledTask {

    @Autowired
    private DataSource postgresDataSource;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    @Scheduled(cron = "0 * * ? * *")
    public void everyMinuteWithCronExp() throws SQLException {
        log.info("CRON EXP {}", dateFormat.format(new Date()));

        QueryRunner queryRunner = new QueryRunner(postgresDataSource);
        ResultSetHandler<Articolo> resultHandler = new BeanHandler<Articolo>(Articolo.class);

        Articolo articolo = queryRunner.query("select * from articoli where codart = '000001502'",
                resultHandler);

        log.info("CRON EXP ARTICLE CODE {}", articolo.getCodart());
    }
}
