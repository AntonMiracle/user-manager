package com.host.core.dao;

import com.host.config.AppConfig;
import com.host.core.model.Group;
import org.assertj.core.api.Java6Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class GroupDAOTest {
    @Autowired
    private GroupDAO groupDAO;
    private Group group;
    private String name1 = "n1";
    private String name2 = "n2";

    @Before
    public void before() {
        group = new Group();
        deleteGroupsForTests();
    }

    @Test
    public void injectDependencyUserDAO() {
        Java6Assertions.assertThat(groupDAO).isNotNull();
    }

    @After
    public void after() {
        deleteGroupsForTests();
    }

    @Transactional
    private void deleteGroupsForTests() {
        if (groupDAO.find(name1) != null) groupDAO.delete(groupDAO.find(name1).getId());
        if (groupDAO.find(name2) != null) groupDAO.delete(groupDAO.find(name2).getId());
    }

    @Test
    @Transactional
    public void deleteTestingGroup() {
        groupDAO.save(createGroup(name1));
        groupDAO.save(createGroup(name2));

        assertThat(groupDAO.find(name1)).isNotNull();
        assertThat(groupDAO.find(name1)).isNotNull();

        deleteGroupsForTests();

        assertThat(groupDAO.find(name1)).isNull();
        assertThat(groupDAO.find(name1)).isNull();
    }

    @Test
    @Transactional
    public void saveGroup() {
        group = groupDAO.save(createGroup(name1));

        assertThat(group.getId()).isNotNull();
        assertThat(group.getName()).isEqualTo(name1);
    }

    @Test
    @Transactional
    public void deleteGroup() {
        group = createGroup(name1);
        groupDAO.save(group);

        assertThat(group.getId()).isNotNull();
        assertThat(groupDAO.delete(group.getId())).isTrue();
    }

    @Test
    @Transactional
    public void findGroupById() {
        groupDAO.save(createGroup(name1));
        group = groupDAO.find(name1);

        assertThat(group.getId()).isNotNull();
        assertThat(group.getName()).isEqualTo(name1);
    }

    @Test
    @Transactional
    public void findAllGroups() {
        int size = groupDAO.find().size();

        groupDAO.save(createGroup(name1));
        groupDAO.save(createGroup(name2));

        assertThat(groupDAO.find().size()).isEqualTo(size + 2);
    }

    @Test
    @Transactional
    public void findGroupByName() {
        groupDAO.save(createGroup(name1));
        group = groupDAO.find(name1);

        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo(name1);
    }

    @Test
    @Transactional
    public void updateGroup() {
        assertThat(groupDAO.find(name1)).isNull();
        assertThat(groupDAO.find(name2)).isNull();

        groupDAO.save(createGroup(name1));

        assertThat(groupDAO.find(name1)).isNotNull();
        assertThat(groupDAO.find(name2)).isNull();

        group = groupDAO.find(name1);
        group.setName(name2);
        groupDAO.update(group);

        assertThat(groupDAO.find(name1)).isNull();
        assertThat(groupDAO.find(name2)).isNotNull();
    }

    private Group createGroup(String name) {
        Group group = new Group();
        group.setName(name);
        group.setUsers(new HashSet<>());
        return group;
    }
}