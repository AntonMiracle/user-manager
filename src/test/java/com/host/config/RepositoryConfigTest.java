package com.host.config;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class RepositoryConfigTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void injectDependencySessionFactory() {
        assertThat(sessionFactory).isNotNull();
    }

    @Test
    public void injectDependencyGroupValidatorService() {
        assertThat(dataSource).isNotNull();
    }

}