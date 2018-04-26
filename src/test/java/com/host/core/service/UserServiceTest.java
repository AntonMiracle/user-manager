package com.host.core.service;

import com.host.config.AppConfig;
import com.host.core.TestHelper;
import com.host.core.dao.GroupDAO;
import com.host.core.dao.UserDAO;
import com.host.core.model.Group;
import com.host.core.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
@Transactional
public class UserServiceTest implements TestHelper<User> {

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
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
        deleteGroupsForTests(groupDAO);
        deleteUsersForTests(userDAO);
    }

    @After
    public void after() {
        deleteGroupsForTests(groupDAO);
        deleteUsersForTests(userDAO);
    }

    // find ---------------------------

    @Test
    public void findUserById() {
        user = userService.save(createUserForTest(username1));
        assertThat(userService.find(user.getId())).isNotNull();
    }

    @Test
    public void findUserByIdWithNullReturnNull() {
        assertThat(userService.find((Long) null)).isNull();
    }

    @Test
    public void findUserByNameWithNullReturnNull() {
        assertThat(userService.find((String) null)).isNull();
    }

    @Test
    public void findUserByNotExistingIdReturnNull() {
        assertThat(userService.find(-10L)).isNull();
    }

    @Test
    public void findUserByName() {
        userService.save(createUserForTest(username1));
        assertThat(userService.find(username1)).isNotNull();
    }

    @Test
    public void findAllUsers() {
        userService.save(createUserForTest(username1));
        assertThat(userService.find().size()).isEqualTo(1);
    }

    // save ---------------------------

    @Test
    public void saveUserWithNullReturnNull() {
        assertThat(userService.save(null)).isNull();
    }

    @Test
    public void saveUser() {
        userService.save(createUserForTest(username1));
        assertThat(userService.find(username1)).isNotNull();
    }

    @Test
    public void whenSaveUserPasswordCrypt() {
        String noCryptPassword = "PasswOrd3";
        user = createUserForTest(username1);
        user.setPassword(noCryptPassword);
        user = userService.save(user);
        assertThat(user.getPassword()).isNotEqualTo(noCryptPassword);
    }

    @Test
    public void saveUserOnlyWithUsernamePasswordBirthday() {
        user.setUsername(username1);
        user.setPassword("ddddddsa3F");
        user.setBirthDay(LocalDate.now().minusDays(23));

        user = userService.save(user);

        assertThat(user.getGroups().size()).isEqualTo(1);
        assertThat(user.getFirstName()).isNotNull();
        assertThat(user.getLastName()).isNotNull();
    }

    @Test
    public void saveInvalidUserReturnNull() {
        assertThat(userService.save(new User())).isNull();
    }

    // update ---------------------------

    @Test
    public void updateUserWithNullReturnNull() {
        assertThat(userService.update(null)).isNull();
    }

    @Test
    public void updateUserName() {
        assertThat(userService.isExist(username1)).isFalse();

        user = userService.save(createUserForTest(username1));
        user.setUsername(username2);
        userService.update(user);

        assertThat(userService.find(username2).getUsername()).isEqualTo(username2);
    }

    @Test
    public void updateInvalidUserReturnNull() {
        assertThat(userService.update(new User())).isNull();
    }

    // delete ---------------------------

    @Test
    public void deleteWithNullReturnFalse() {
        assertThat(userService.delete(null)).isFalse();
    }

    @Test
    public void deleteUserById() {
        user = userService.save(createUserForTest(username1));
        assertThat(userService.find(username1)).isNotNull();
        assertThat(userService.delete(user.getId())).isTrue();
        assertThat(userService.isExist(username1));
    }

    // isExist ---------------------------

    @Test
    public void isExistWithNullReturnFalse() {
        assertThat(userService.isExist(null)).isFalse();
    }

    @Test
    public void isExistReturnTrue() {
        userService.save(createUserForTest(username1));
        assertThat(userService.isExist(username1)).isTrue();
    }

    // remove ---------------------------

    @Test
    public void removeUserFromGroup() {
        Group group = groupService.save(createGroupForTest(getForTestGroupname1()));

        user = userService.save(createUserForTest(username1));
        user.getGroups().add(group);
        userService.update(user);

        assertThat(user.getGroups().size()).isEqualTo(2);
        assertThat(userService.remove(group.getId(), user.getId())).isTrue();
        assertThat(userService.find(username1).getGroups().size()).isEqualTo(1);
    }
}