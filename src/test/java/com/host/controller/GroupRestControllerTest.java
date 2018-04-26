package com.host.controller;

import com.host.config.AppConfig;
import com.host.core.TestHelper;
import com.host.core.dao.GroupDAO;
import com.host.core.dao.UserDAO;
import com.host.core.model.Group;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class GroupRestControllerTest implements TestHelper<Group> {
    @Autowired
    private GroupRestController groupController;
    private String name1;
    private String name2;
    private Group group;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GroupDAO groupDAO;

    @Before
    public void before() {
        group = new Group();
        name1 = getForTestGroupname1();
        name2 = getForTestGroupname2();
        deleteGroupsForTests(groupDAO);
        deleteUsersForTests(userDAO);
    }

    @After
    public void after() {
        deleteGroupsForTests(groupDAO);
        deleteUsersForTests(userDAO);
    }

    @Test
    public void injectDependenciesAvailable() {
        assertThat(groupController).isNotNull();
    }

    // save ---------------------------------------

    @Test
    public void saveGroupSuccessReturnHttpStatusCreated() {
        group = createGroupForTest(name1);
        ResponseEntity<Group> result = groupController.save(group);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getName()).isEqualTo(name1);
    }

    @Test
    public void saveGroupWithNullReturnHttpStatusBadRequest() {
        ResponseEntity<Group> result = groupController.save(null);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody()).isNull();
    }

    @Test
    public void saveInvalidGroupReturnHttpStatusNotAcceptable() {
        group.setName("");
        ResponseEntity<Group> result = groupController.save(group);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
        assertThat(result.getBody()).isEqualTo(group);
    }

    // update ---------------------------------------

    @Test
    public void updateGroupSuccessReturnHttpStatusOk() {
        group = createGroupForTest(name1);
        group = groupController.save(group).getBody();
        assertThat(group.getName()).isEqualTo(name1);

        group.setName(name2);
        ResponseEntity<Group> result = groupController.update(group);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getName()).isEqualTo(name2);
    }

    @Test
    public void updateGroupWithNullReturnHttpStatusBadRequest() {
        ResponseEntity<Group> result = groupController.update(null);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody()).isNull();
    }

    @Test
    public void updateInvalidGroupReturnHttpStatusNotAcceptable() {
        group.setName("");
        ResponseEntity<Group> result = groupController.update(group);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
        assertThat(result.getBody()).isEqualTo(group);
    }

    // getAll ---------------------------------------

    @Test
    public void getAllGroupsSuccessReturnHttpStatusOk() {
        int initSize = groupController.getAll().getBody().size();

        groupController.save(createGroupForTest(name1)).getBody();

        ResponseEntity<List<Group>> result = groupController.getAll();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().size()).isEqualTo(initSize + 1);
    }

    // get ---------------------------------------

    @Test
    public void getGroupSuccessReturnHttpStatusOk() {
        group = createGroupForTest(name1);
        Long id = groupController.save(group).getBody().getId();
        assertThat(id).isNotNull();

        ResponseEntity<Group> result = groupController.get(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getName()).isEqualTo(name1);
    }

    @Test
    public void getGroupWithNullReturnHttpStatusBadRequest() {
        ResponseEntity<Group> result = groupController.get(null);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody()).isNull();
    }

    @Test
    public void getGroupNotFoundReturnHttpStatusNotFound() {
        ResponseEntity<Group> result = groupController.get(-10L);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody()).isNull();
    }

    // delete ---------------------------------------
    @Test
    public void deleteGroupSuccessReturnHttpStatusOk() {
        group = createGroupForTest(name1);
        Long id = groupController.save(group).getBody().getId();
        assertThat(id).isNotNull();

        ResponseEntity<Boolean> result = groupController.delete(id);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(Boolean.TRUE);
    }

    @Test
    public void deleteGroupWithNullReturnHttpStatusBadRequest() {
        ResponseEntity<Boolean> result = groupController.delete(null);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody()).isEqualTo(Boolean.FALSE);
    }

    @Test
    public void deleteGroupNotFoundReturnHttpStatusNotFound() {
        ResponseEntity<Boolean> result = groupController.delete(-10L);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(result.getBody()).isEqualTo(Boolean.FALSE);
    }

    // isUnique ---------------------------------------
    @Test
    public void whenGroupNameUniqueReturnHttpStatusAccepted() {
        ResponseEntity<Boolean> result = groupController.isUnique(name1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        assertThat(result.getBody()).isEqualTo(Boolean.TRUE);
    }

    @Test
    public void whenGroupNameNotUniqueReturnHttpStatusNotAcceptable() {
        groupController.save(createGroupForTest(name1));

        ResponseEntity<Boolean> result = groupController.isUnique(name1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_ACCEPTABLE);
        assertThat(result.getBody()).isEqualTo(Boolean.FALSE);
    }

    @Test
    public void whenNullIsUniqueReturnHttpStatusBadRequest() {
        ResponseEntity<Boolean> result = groupController.isUnique(null);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(result.getBody()).isEqualTo(Boolean.FALSE);
    }

}