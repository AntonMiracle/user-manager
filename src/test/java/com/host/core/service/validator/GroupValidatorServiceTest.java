package com.host.core.service.validator;

import com.host.core.model.Group;
import com.host.core.service.validator.impl.GroupValidatorServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GroupValidatorServiceTest implements TestHelper<Group> {
    private GroupValidatorService validatorService;
    private Group group;

    @Before
    public void before() {
        validatorService = new GroupValidatorServiceImpl();
        group = new Group();
    }

    private String nameField = getField(Group.class, "name");

    @Test
    public void nameWithNullInvalid() {
        group.setName(null);
        long count = countConstraintViolation(validatorService, group, nameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nameWithLengthLessThan4Invalid() {
        group.setName("nam");
        long count = countConstraintViolation(validatorService, group, nameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nameWithLengthMoreThan15Invalid() {
        group.setName("nameenameenameeR");
        long count = countConstraintViolation(validatorService, group, nameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nameWithAtLeastOneUpperCaseSymbolInvalid() {
        group.setName("Admin");
        long count = countConstraintViolation(validatorService, group, nameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void nameWithOnlyAlphabetSymbolsValid() {
        group.setName("admin");
        long count = countConstraintViolation(validatorService, group, nameField);
        assertThat(count == 0).isTrue();
    }

    private String usersField = getField(Group.class, "users");

    @Test
    public void usersWithNullInvalid() {
        group.setUsers(null);
        long count = countConstraintViolation(validatorService, group, usersField);
        assertThat(count == 1).isTrue();
    }

    private String idField = getField(Group.class, "id");

    @Test
    public void idLessThanOneInvalid() throws NoSuchFieldException, IllegalAccessException {
        setProtectedId(Group.class, group, 0L);
        long count = countConstraintViolation(validatorService, group, idField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void withValidFieldsValid() {
        group.setName("name");
        group.setUsers(new HashSet<>());
        assertThat(validatorService.isValid(group)).isTrue();
    }

    @Test
    public void trimFields() {
        group.setName("   name  ");
        group.setUsers(new HashSet<>());
        validatorService.trimFields(group);
        assertThat(validatorService.isValid(group)).isTrue();
    }

    @Test
    public void has3Fields() {
        assertThat(countFields(group)).isEqualTo(3);
    }
}