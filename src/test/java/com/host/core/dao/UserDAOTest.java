package com.host.core.dao;

import com.host.config.AppConfig;
import com.host.config.Initialized;
import com.host.core.TestHelper;
import com.host.core.model.Group;
import com.host.core.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class UserDAOTest implements TestHelper<User> {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GroupDAO groupDAO;

    private User user;
    private String username1;
    private String username2;

    @Before
    public void before() {
        user = new User();
        username1 = getForTestUsername1();
        username2 = getForTestUsername2();
        deleteUsersForTests();
    }

    @After
    public void after() {
        deleteUsersForTests();
    }

    @Test
    public void injectDependencyAvailable() {
        assertThat(userDAO).isNotNull();
        assertThat(groupDAO).isNotNull();
    }

    @Test
    public void findAllUsers() {
        int size = userDAO.find().size();
        userDAO.save(createUser(username2));
        userDAO.save(createUser(username1));
        assertThat(userDAO.find().size()).isEqualTo(size + 2);
    }

    @Test
    public void findUserById() {
        user = userDAO.save(createUser(username1));
        assertThat(user.getId()).isNotNull();
        assertThat(userDAO.find(user.getId()).getId()).isEqualTo(user.getId());
    }

    @Test
    public void findUserByUsername() {
        userDAO.save(createUser(username1));
        assertThat(userDAO.find(username1).getUsername()).isEqualTo(username1);
    }

    @Test
    public void saveUser() {
        userDAO.save(createUser(username1));
        assertThat(userDAO.find(username1)).isNotNull();
    }

    @Test
    public void updateUser() {
        user = userDAO.save(createUser(username1));
        user.setUsername(username2);
        userDAO.update(user);
        assertThat(userDAO.find(username1)).isNull();
        assertThat(userDAO.find(username2)).isNotNull();
    }

    @Test
    public void deleteUser() {
        user = userDAO.save(createUser(username1));
        assertThat(user).isNotNull();
        userDAO.delete(user.getId());
        assertThat(userDAO.find(username1)).isNull();
    }

    @Test
    public void deleteTestingUsers() {
        assertThat(userDAO.find(username1)).isNull();
        assertThat(userDAO.find(username2)).isNull();

        userDAO.save(createUser(username1));
        userDAO.save(createUser(username2));

        assertThat(userDAO.find(username1)).isNotNull();
        assertThat(userDAO.find(username2)).isNotNull();

        deleteUsersForTests();

        assertThat(userDAO.find(username1)).isNull();
        assertThat(userDAO.find(username2)).isNull();
    }

    private void deleteUsersForTests() {
        if (userDAO.find(username1) != null) userDAO.delete(userDAO.find(username1).getId());
        if (userDAO.find(username2) != null) userDAO.delete(userDAO.find(username2).getId());
    }

    private User createUser(String username) {
        Set<Group> groups = new HashSet<>();
        groups.add(groupDAO.find(Initialized.GROUP_NAME_USER));

        User user = new User();
        user.setGroups(groups);
        user.setBirthDay(LocalDate.now().minusDays(100));
        user.setLastName("last");
        user.setFirstName("First");
        user.setUsername(username);
        user.setPassword("Password2");
        return user;
    }
}