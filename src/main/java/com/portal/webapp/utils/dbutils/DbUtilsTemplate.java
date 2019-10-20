package com.portal.webapp.utils.dbutils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DbUtilsTemplate {

    private DataSource dataSource;
    private QueryRunner queryRunner;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public DbUtilsTemplate(DataSource dataSource) {
        log.info("DbUtilsTemplate");
        this.dataSource = dataSource;
        // TODO Auto-generated constructor stub
    }


    public int update(String sql) {
        return update(sql, null);
    }

    public int update(String sql, Object param) {
        return update(sql, new Object[] { param });
    }

    public int update(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        int affectedRows = 0;
        try {
            if (params == null) {
                affectedRows = queryRunner.update(sql);
            } else {
                affectedRows = queryRunner.update(sql, params);
            }
        } catch (SQLException e) {
            log.error("Error occured while attempting to update data", e);
        }
        return affectedRows;
    }

    public int[] batchUpdate(String sql, Object[][] params) {
        queryRunner = new QueryRunner(dataSource);
        int[] affectedRows = new int[0];
        try {
            affectedRows = queryRunner.batch(sql, params);
        } catch (SQLException e) {
            log.error("Error occured while attempting to batch update data", e);
        }
        return affectedRows;
    }

    public List<Map<String, Object>> find(String sql) {
        return find(sql, null);
    }

    public List<Map<String, Object>> find(String sql, Object param) {
        return find(sql, new Object[] {param});
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> find(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        try {
            if (params == null) {
                list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler());
            } else {
                list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler(), params);
            }
        } catch (SQLException e) {
            log.error("Error occured while attempting to query data", e);
        }
        return list;
    }

    public <T> List<T> find(Class<T> entityClass, String sql) {
        return find(entityClass, sql, null);
    }

    public <T> List<T> find(Class<T> entityClass, String sql, Object param) {
        return find(entityClass, sql, new Object[] { param });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> find(Class<T> entityClass, String sql, Object[] params) {
        log.info("执行查询：" + sql);
        List<T> list = new ArrayList<T>();
        try {
            queryRunner = new QueryRunner(dataSource);
            if (params == null) {
                list = (List<T>) queryRunner.query(sql, new BeanListHandler(entityClass));
            } else {
                list = (List<T>) queryRunner.query(sql, new BeanListHandler(entityClass), params);
            }
        } catch (SQLException e) {
            log.error("Error occured while attempting to query data", e);
        }
        return list;
    }

    public <T> T findFirst(Class<T> entityClass, String sql) {
        return findFirst(entityClass, sql, null);
    }

    public <T> T findFirst(Class<T> entityClass, String sql, Object param) {
        return findFirst(entityClass, sql, new Object[] { param });
    }

    @SuppressWarnings("unchecked")
    public <T> T findFirst(Class<T> entityClass, String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new BeanHandler(entityClass));
            } else {
                object = queryRunner.query(sql, new BeanHandler(entityClass), params);
            }
        } catch (SQLException e) {
            log.error("Error occured while attempting to query data", e);
        }
        return (T) object;
    }

    public Map<String, Object> findFirst(String sql) {
        return findFirst(sql, null);
    }

    public Map<String, Object> findFirst(String sql, Object param) {
        return findFirst(sql, new Object[] { param });
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> findFirst(String sql, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Map<String, Object> map = null;
        try {
            if (params == null) {
                map = (Map<String, Object>) queryRunner.query(sql, new MapHandler());
            } else {
                map = (Map<String, Object>) queryRunner.query(sql, new MapHandler(), params);
            }
        } catch (SQLException e) {
            log.error("Error occured while attempting to query data", e);
        }
        return map;
    }

    public Object findBy(String sql, String columnName) {
        return findBy(sql, columnName, null);
    }

    public Object findBy(String sql, String columnName, Object param) {
        return findBy(sql, columnName, new Object[] { param });
    }

    public Object findBy(String sql, String columnName, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler(columnName));
            } else {
                object = queryRunner.query(sql, new ScalarHandler(columnName), params);
            }
        } catch (SQLException e) {
            log.error("Error occured while attempting to query data", e);
        }
        return object;
    }

    public Object findBy(String sql, int columnIndex) {
        return findBy(sql, columnIndex, null);
    }

    public Object findBy(String sql, int columnIndex, Object param) {
        return findBy(sql, columnIndex, new Object[] { param });
    }

    public Object findBy(String sql, int columnIndex, Object[] params) {
        queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler(columnIndex));
            } else {
                object = queryRunner.query(sql, new ScalarHandler(columnIndex), params);
            }
        } catch (SQLException e) {
            log.error("Error occured while attempting to query data", e);
        }
        return object;
    }
}