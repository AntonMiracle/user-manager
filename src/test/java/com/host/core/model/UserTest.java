package com.host.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserTest {
    private User user;

    @Before
    public void before() {
        user = new User();
    }

    @Test
    public void setAndGetUsername() {
        user.setUsername("username");
        assertThat(user.getUsername()).isEqualTo("username");
    }

    @Test
    public void setAndGetPassword() {
        user.setPassword("pass");
        assertThat(user.getPassword()).isEqualTo("pass");
    }

    @Test
    public void setAndGetFirstName() {
        user.setFirstName("name");
        assertThat(user.getFirstName()).isEqualTo("name");
    }

    @Test
    public void setAndGetLastName() {
        user.setLastName("surname");
        assertThat(user.getLastName()).isEqualTo("surname");
    }

    @Test
    public void setAndGetBirthDate() {
        LocalDate birthDay = LocalDate.of(1986, 1, 2);
        user.setBirthDay(birthDay);
        assertThat(user.getBirthDay()).isEqualTo(birthDay);
    }

    @Test
    public void setAndGetGroups() {
        Set<Group> groups = new HashSet<>();
        user.setGroups(groups);
        assertThat(user.getGroups()).isEqualTo(groups);
    }

    @Test
    public void setAndGetId() {
        user.setId(new Long(10));
        assertThat(user.getId()).isEqualTo(10L);
    }

    @Test
    public void implementsSerializable() {
        Serializable serializable = new User();
        assertThat(serializable).isNotNull();
    }

    @Test
    public void setIdHasProtectedModifierAccess() throws NoSuchMethodException {
        Method setId = User.class.getDeclaredMethod("setId", Long.class);
        assertThat(Modifier.isProtected(setId.getModifiers())).isTrue();
    }

    @Test
    public void checkEqualsAndHashCode() {
        User user2 = new User();
        user2.setBirthDay(LocalDate.of(1986, 1, 2));
        user.setBirthDay(LocalDate.of(1986, 1, 1));

        EqualsVerifier.forClass(User.class)
                .usingGetClass()
                .withPrefabValues(User.class, user, user2)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}