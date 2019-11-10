package com.portal.webapp.controller;

import com.portal.webapp.mapping.Articolo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="http://localhost:4200")
public class DummyRestController {

    Logger logger = LoggerFactory.getLogger(DummyRestController.class);

    @Autowired
    private DataSource postgresDataSource;

    @GetMapping(value = "/test")
    public String getGreetings() {
        logger.debug("Saluti, sono il tuo primo webservice controller PortalBackend");
        return "Saluti, sono il tuo primo web services controller PortalBackend";
    }

    @GetMapping(value = "/saluti/{nome}")
    public String getSaluti(@PathVariable("nome") String nome)
    {
        if(nome.equals("test")) {
            throw new RuntimeException("Utente disabilitato");
        }
        String message = String.format("\"Saluti, %s hai usato il tuo primo web service\"", nome);
        return message;
    }

    @GetMapping(value = "/longOperation")
    public String longOperation() throws InterruptedException {
        logger.debug("Long operation start");
        Thread.sleep(30000);
        logger.debug("Long operation finished");
        String message = String.format("Long operation terminata");
        return message;
    }

    @GetMapping(value = "/articolo")
    public Articolo getArticolo() throws SQLException {

        QueryRunner queryRunner = new QueryRunner(postgresDataSource);
        ResultSetHandler<Articolo> resultHandler = new BeanHandler<Articolo>(Articolo.class);

        Articolo articolo = queryRunner.query("select * from articoli where codart = '000001502'",
                resultHandler);

        return articolo;
    }

    @Cacheable("articoli")
    @GetMapping(value="/articoli")
    public List<Articolo> getArticoli() throws SQLException {
        BeanListHandler<Articolo> beanListHandler
                = new BeanListHandler<>(Articolo.class);

        QueryRunner runner = new QueryRunner();
        List<Articolo> articoliList
                = runner.query(postgresDataSource.getConnection(), "SELECT * FROM articoli", beanListHandler);

        return articoliList;
    }

    @CacheEvict(value="articoli", allEntries=true)
    @GetMapping(value="/evictCache")
    public void evictCacheArticoli() {

    }

}