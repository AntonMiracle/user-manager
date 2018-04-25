package com.host.core.dao;

import com.host.config.AppConfig;
import com.host.config.Initialized;
import com.host.core.TestHelper;
import com.host.core.model.Group;
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
public class GroupDAOTest implements TestHelper<Group> {
    @Autowired
    private GroupDAO groupDAO;

    private Group group;
    private String name1;
    private String name2;

    @Before
    public void before() {
        group = new Group();
        name1 = getForTestGroupname1();
        name2 = getForTestGroupname2();
        deleteGroupsForTests();
    }

    @Test
    public void injectDependencyAvailable() {
        assertThat(groupDAO).isNotNull();
    }

    @After
    public void after() {
        deleteGroupsForTests();
    }

    private void deleteGroupsForTests() {
        if (groupDAO.find(name1) != null) groupDAO.delete(groupDAO.find(name1).getId());
        if (groupDAO.find(name2) != null) groupDAO.delete(groupDAO.find(name2).getId());
    }

    @Test
    public void groupWithNameUserInitialized() {
        assertThat(groupDAO.find(Initialized.GROUP_NAME_USER)).isNotNull();
    }

    @Test
    public void deleteTestingGroup() {
        groupDAO.save(createGroupForTest(name1));
        groupDAO.save(createGroupForTest(name2));

        assertThat(groupDAO.find(name1)).isNotNull();
        assertThat(groupDAO.find(name2)).isNotNull();

        deleteGroupsForTests();

        assertThat(groupDAO.find(name1)).isNull();
        assertThat(groupDAO.find(name2)).isNull();
    }

    @Test
    public void saveGroupWithNoUsers() {
        group = groupDAO.save(createGroupForTest(name1));

        assertThat(group.getId()).isNotNull();
        assertThat(group.getName()).isEqualTo(name1);
    }

    @Test
    public void deleteGroup() {
        group = createGroupForTest(name1);
        groupDAO.save(group);

        assertThat(group.getId()).isNotNull();
        assertThat(groupDAO.delete(group.getId())).isTrue();
    }

    @Test
    public void findGroupById() {
        groupDAO.save(createGroupForTest(name1));
        group = groupDAO.find(name1);

        assertThat(group.getId()).isNotNull();
        assertThat(group.getName()).isEqualTo(name1);
    }

    @Test
    public void findAllGroups() {
        int size = groupDAO.find().size();

        groupDAO.save(createGroupForTest(name1));
        groupDAO.save(createGroupForTest(name2));

        assertThat(groupDAO.find().size()).isEqualTo(size + 2);
    }

    @Test
    public void findGroupByName() {
        groupDAO.save(createGroupForTest(name1));
        group = groupDAO.find(name1);

        assertThat(group).isNotNull();
        assertThat(group.getName()).isEqualTo(name1);
    }

    @Test
    public void updateGroup() {
        assertThat(groupDAO.find(name1)).isNull();
        assertThat(groupDAO.find(name2)).isNull();

        groupDAO.save(createGroupForTest(name1));

        assertThat(groupDAO.find(name1)).isNotNull();
        assertThat(groupDAO.find(name2)).isNull();

        group = groupDAO.find(name1);
        group.setName(name2);
        groupDAO.update(group);

        assertThat(groupDAO.find(name1)).isNull();
        assertThat(groupDAO.find(name2)).isNotNull();
    }

}