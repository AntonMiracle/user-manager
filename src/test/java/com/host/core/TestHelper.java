package com.host.core;

import com.host.config.Initialized;
import com.host.core.dao.GroupDAO;
import com.host.core.dao.UserDAO;
import com.host.core.model.CoreModel;
import com.host.core.model.Group;
import com.host.core.model.User;
import com.host.core.service.validator.ValidatorService;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Help testing {@link com.host.core.service.validator}
 *
 * @param <T> type of {@link com.host.core.model}
 * @author Anton Bondarenko
 * @version 0.1
 */
public interface TestHelper<T extends CoreModel> {
    /**
     * Count errors in {@param fieldName} in {@param object}
     *
     * @param validatorService which will validate {@param object}
     * @param object           which need to validate
     * @param fieldName        field of {@param object}
     * @return numbers of error in {@param fieldName}
     */
    default long countConstraintViolation(ValidatorService<T> validatorService, T object, String fieldName) {
        return validatorService.validate(object).stream()
                .filter(error -> error.getPropertyPath().toString().equals(fieldName))
                .count();
    }

    /**
     * Checking field name in {@param claz}
     *
     * @param claz where fields with name {@param name}
     * @param name of fields in {@param claz}
     * @return checking name
     */
    default String checkFieldName(Class<T> claz, String name) {
        try {
            return claz.getDeclaredField(name).getName();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Wrong field name", e);
        }
    }

    /**
     * Count fields in {@param object}
     *
     * @param object where need to calculate number of fields
     * @return number of fields in {@param object}
     */
    default long countFields(T object) {
        Field[] fields = object.getClass().getDeclaredFields();
        return Arrays.stream(fields).filter(field -> field.getName().matches("[A-Za-z]+[A-Za-z_]*")).count();
    }

    /**
     * Set protected field with name "id"
     *
     * @param clazz type of {@param where}
     * @param where of type {@param clazz}
     * @param id    of {@param where}
     * @throws NoSuchFieldException   when id is not exist
     * @throws IllegalAccessException when access false
     */
    default void setProtectedId(Class<T> clazz, T where, Long id) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField("id");
        field.setAccessible(true);
        field.set(where, id);
    }

    default String getForTestUsername1() {
        return "uf";
    }

    default String getForTestUsername2() {
        return "us";
    }

    default String getForTestGroupname1() {
        return "gf";
    }

    default String getForTestGroupname2() {
        return "gs";
    }

    default User createUserForTest(String username) {
        Set<Group> groups = new HashSet<>();
        Group group = new Group();
        group.setName(Initialized.GROUP_NAME_USER);

        User user = new User();
        user.setGroups(groups);
        user.setBirthDay(LocalDate.now().minusDays(100));
        user.setLastName("last");
        user.setFirstName("First");
        user.setUsername(username);
        user.setPassword("Pass2Wword2");
        return user;
    }

    default Group createGroupForTest(String name) {
        Group group = new Group();
        group.setName(name);
        group.setUsers(new HashSet<>());
        return group;
    }

    default void deleteGroupsForTests(GroupDAO dao) {
        if (dao.find(getForTestGroupname1()) != null) dao.delete(dao.find(getForTestGroupname1()).getId());
        if (dao.find(getForTestGroupname2()) != null) dao.delete(dao.find(getForTestGroupname2()).getId());
    }

    default void deleteUsersForTests(UserDAO dao) {
        if (dao.find(getForTestUsername1()) != null) dao.delete(dao.find(getForTestUsername1()).getId());
        if (dao.find(getForTestUsername2()) != null) dao.delete(dao.find(getForTestUsername2()).getId());
    }

}
