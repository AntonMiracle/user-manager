package com.host.controller;

import com.host.config.AppConfig;
import com.host.core.TestHelper;
import com.host.core.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserRestControllerTest implements TestHelper<User> {
    @Autowired
    private UserRestController userCotroller;

    @Test
    public void test() {
        assertThat(true).isEqualTo(true);
    }
}