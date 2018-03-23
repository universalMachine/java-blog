package com.wang.blog.web;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class InfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(InfoController.class);
    private static final List<String> TOMCAT_DB_CONN_POOL_PROPERTIES = Arrays.asList(
            "driverClassName", "maxActive", "maxAge", "maxIdle", "maxWait", "minEvictableIdleTimeMillis",
            "minIdle", "name", "removeAbandonedTimeout", "timeBetweenEvictionRunsMillis", "validationInterval",
            "validationQuery", "validationQueryTimeout", "removeAbandoned", "testOnBorrow", "testWhileIdle",
            "testOnConnect", "poolSweeperEnabled", "logAbandoned", "jmxEnabled", "url", "dataSourceJNDI",
            "initialSize", "suspectTimeout", "username", "useDisposableConnectionFacade");

    @Autowired
    private DataSource dataSource;

    @RequestMapping(path = "/data-source-info", method = RequestMethod.GET, produces = "application/json")
    public Object getInfo() {
        Map<String, Object> dataSourceDetails = new TreeMap<>();
        dataSourceDetails.put("data-source-class", dataSource.getClass().getName());
        addObjPropertiesUnderKey(dataSource, "data-source-config", dataSourceDetails);
        return dataSourceDetails;
    }

    static void addObjPropertiesUnderKey(Object obj, String key, Map<String, Object> map) {
        try {
            Map<String, Object> properties = new TreeMap<>();
            TOMCAT_DB_CONN_POOL_PROPERTIES.stream().forEach(property -> addPropertyIfNotNull(obj, property, properties));
            if (!properties.isEmpty()) {
                map.put(key, properties);
            }
        } catch (Exception e) {
            LOGGER.warn("Cannot fetch properties of object: {}.", obj, e);
        }
    }

    static void addPropertyIfNotNull(Object obj, String property, Map<String, Object> map) {
        try {
            String value = BeanUtils.getProperty(obj, property);
            if (value != null) {
                map.put(property, value);
            }
        } catch (Exception e) {
            LOGGER.warn("Cannot get property '{}' from  object of class {}. {}", property, obj.getClass(), e.getMessage());
        }
    }
}