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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
@Transactional
public class GroupServiceTest implements TestHelper<Group> {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GroupDAO groupDAO;
    private Group group;
    private String name1;
    private String name2;

    @Before
    public void before() {
        name1 = getForTestGroupname1();
        name2 = getForTestGroupname2();
        group = new Group();
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
    public void findGroupById() {
        group = groupService.save(createGroupForTest(name1));
        assertThat(groupService.find(group.getId())).isNotNull();
    }

    @Test
    public void findGroupByIdWithNullReturnNull() {
        assertThat(groupService.find((Long) null)).isNull();
    }

    @Test
    public void findGroupByNameWithNullReturnNull() {
        assertThat(groupService.find((String) null)).isNull();
    }

    @Test
    public void findGroupByNotExistingIdReturnNull() {
        assertThat(groupService.find(-10L)).isNull();
    }

    @Test
    public void findGroupByName() {
        groupService.save(createGroupForTest(name1));
        assertThat(groupService.find(name1)).isNotNull();
    }

    @Test
    public void findAllGroups() {
        int size = groupService.find().size();
        groupService.save(createGroupForTest(name1));
        assertThat(groupService.find().size()).isEqualTo(size + 1);
    }

    // save ---------------------------

    @Test
    public void saveGroupWithNullReturnNull() {
        assertThat(groupService.save(null)).isNull();
    }

    @Test
    public void saveGroup() {
        groupService.save(createGroupForTest(name1));
        assertThat(groupService.find(name1)).isNotNull();
    }

    @Test
    public void saveInvalidGroupReturnNull() {
        assertThat(groupService.save(new Group())).isNull();
    }

    // update ---------------------------

    @Test
    public void updateGroupWithNullReturnNull() {
        assertThat(groupService.update(null)).isNull();
    }

    @Test
    public void updateGroupName() {
        assertThat(groupService.isExist(name1)).isFalse();

        group = groupService.save(createGroupForTest(name1));
        group.setName(name2);
        groupService.update(group);

        assertThat(groupService.find(name2).getName()).isEqualTo(name2);
    }

    @Test
    public void updateInvalidGroupReturnNull() {
        assertThat(groupService.update(new Group())).isNull();
    }

    // delete ---------------------------

    @Test
    public void deleteWithNullReturnFalse() {
        assertThat(groupService.delete(null)).isFalse();
    }

    @Test
    public void deleteGroupById() {
        group = groupService.save(createGroupForTest(name1));
        assertThat(groupService.find(name1)).isNotNull();
        groupService.delete(group.getId());
        assertThat(groupService.isExist(name1));
    }

    // isExist ---------------------------

    @Test
    public void isExistWithNullReturnFalse() {
        assertThat(groupService.isExist(null)).isFalse();
    }

    @Test
    public void isExistReturnFalse() {
        groupService.save(createGroupForTest(name1));
        assertThat(groupService.isExist(name1)).isTrue();
    }

    // remove ---------------------------

    @Test
    public void removeUserFromGroup() {
        User user = userService.save(createUserForTest(getForTestUsername1()));

        group = groupService.save(createGroupForTest(name1));
        group.getUsers().add(user);
        groupService.update(group);

        assertThat(group.getUsers().size()).isEqualTo(1);
        assertThat(groupService.remove(user.getId(), group.getId())).isTrue();
        assertThat(groupService.find(name1).getUsers().size()).isEqualTo(0);
    }
}