package com.host.config;

import com.host.core.model.CoreModel;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class RepositoryConfig {
    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.dbname}")
    private String dbName;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;


    private String getUrl() {
        StringBuilder url = new StringBuilder("\"jdbc:h2:file:.\"");
        url.append(File.separator).append("src");
        url.append(File.separator).append("main");
        url.append(File.separator).append("resources");
        url.append(File.separator).append(dbName);
        return url.toString();
    }

    @Bean()
    public BasicDataSource getDataSource() {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName(driverClassName);
        bds.setUrl(getUrl());
        bds.setUsername(username);
        bds.setPassword(password);
        bds.setInitialSize(1);
        bds.setMaxTotal(5);
        return bds;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource ds) {
        LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(getDataSource());
        sfb.setHibernateProperties(getHibernateProperties());
        sfb.setPackagesToScan(new String[]{CoreModel.class.getPackage().toString()});
        return sfb;
    }

    @Bean
    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.show_sql", hibernateShowSql);
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);

        return properties;
    }

}
