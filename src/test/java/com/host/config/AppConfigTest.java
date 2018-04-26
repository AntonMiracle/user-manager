package com.host.config;

import com.host.core.dao.GroupDAO;
import com.host.core.dao.UserDAO;
import com.host.core.service.GroupService;
import com.host.core.service.UserService;
import com.host.core.service.validator.GroupValidatorService;
import com.host.core.service.validator.UserValidatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =AppConfig.class)
@WebAppConfiguration
public class AppConfigTest {
    @Autowired
    private GroupValidatorService groupVS;
    @Autowired
    private UserValidatorService userVS;
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private PropertyPlaceholderConfigurer properties ;

    @Test
    public void injectDependencyGroupValidatorService() {
        assertThat(groupVS).isNotNull();
    }

    @Test
    public void injectDependencyUserValidatorService() {
        assertThat(userVS).isNotNull();
    }

    @Test
    public void injectDependencyGroupService() {
        assertThat(groupService).isNotNull();
    }

    @Test
    public void injectDependencyUserService() {
        assertThat(userService).isNotNull();
    }
    @Test
    public void injectDependencyGroupDAO() {
        assertThat(groupDAO).isNotNull();
    }

    @Test
    public void injectDependencyUserDAO() {
        assertThat(userDAO).isNotNull();
    }

    @Test
    public void injectDependencyProperties() {
        assertThat(properties).isNotNull();
    }


}