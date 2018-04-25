package com.host.core.service;

import com.host.config.AppConfig;
import com.host.core.TestHelper;
import com.host.core.dao.GroupDAO;
import com.host.core.dao.UserDAO;
import com.host.core.model.Group;
import javassist.NotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@Transactional
public class GroupServiceTest implements TestHelper<Group> {
    @Autowired
    private GroupService groupService;
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

    @Test
    public void findGroupById() throws NotFoundException {
        group = groupService.save(createGroupForTest(name1));
        assertThat(groupService.find(group.getId())).isNotNull();
    }

    // find ---------------------------

    @Test(expected = IllegalArgumentException.class)
    public void whenFindGroupByIdWithNullThenThrowIAE() throws NotFoundException {
        groupService.find((Long) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenFindGroupByNameWithNullThenThrowIAE() throws NotFoundException {
        groupService.find((String) null);
    }

    @Test(expected = NotFoundException.class)
    public void whenFindGroupByNotExistingIdThenThrowNFE() throws NotFoundException {
        groupService.find(-10L);
    }

    @Test
    public void findGroupByName() throws NotFoundException {
        groupService.save(createGroupForTest(name1));
        assertThat(groupService.find(name1)).isNotNull();
    }

    @Test
    public void findAllGroups() {
        groupService.save(createGroupForTest(name1));
        assertThat(groupService.find().size()).isEqualTo(2);
    }

    // save ---------------------------

    @Test(expected = IllegalArgumentException.class)
    public void whenSaveGroupWithNullThenThrowIAE() {
        groupService.save(null);
    }

    @Test
    public void saveGroup() throws NotFoundException {
        groupService.save(createGroupForTest(name1));
        assertThat(groupService.find(name1)).isNotNull();
    }

    @Test(expected = IllegalStateException.class)
    public void whenSaveInvalidGroupThenThrowISE() {
        groupService.save(new Group());
    }

    // update ---------------------------

    @Test(expected = IllegalArgumentException.class)
    public void whenUpdateGroupWithNullThenThrowIAE() {
        groupService.update(null);
    }

    @Test
    public void updateGroupName() throws NotFoundException {
        assertThat(groupService.isExist(name1)).isFalse();

        group = groupService.save(createGroupForTest(name1));
        group.setName(name2);
        groupService.update(group);

        assertThat(groupService.find(name2).getName()).isEqualTo(name2);
    }

    @Test(expected = IllegalStateException.class)
    public void whenUpdateInvalidGroupThenThrowISE() {
        groupService.update(new Group());
    }

    // delete ---------------------------

    @Test(expected = IllegalArgumentException.class)
    public void whenDeleteWithNullThenThrowIAE() {
        groupService.delete(null);
    }

    @Test
    public void deleteGroupById() throws NotFoundException {
        group = groupService.save(createGroupForTest(name1));
        assertThat(groupService.find(name1)).isNotNull();
        groupService.delete(group.getId());
        assertThat(groupService.isExist(name1));
    }

    // isExist ---------------------------

    @Test(expected = IllegalArgumentException.class)
    public void whenIsExistWithNullThenThrowIAE() {
        groupService.isExist(null);
    }

    @Test
    public void isExist() {
        groupService.save(createGroupForTest(name1));
        assertThat(groupService.isExist(name1)).isTrue();
    }
}