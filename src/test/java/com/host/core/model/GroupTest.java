package com.host.core.model;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GroupTest {
    private Group group;

    @Before
    public void before() {
        group = new Group();
    }

    @Test
    public void setAndGetName() {
        group.setName("name");
        assertThat(group.getName()).isEqualTo("name");
    }

    @Test
    public void setAndGetUsers() {
        Set<User> users = new HashSet<>();
        group.setUsers(users);
        assertThat(group.getUsers()).isEqualTo(users);
    }

    @Test
    public void setAndGetId() {
        group.setId(new Long(10));
        assertThat(group.getId()).isEqualTo(10L);
    }

    @Test
    public void implementsSerializable() {
        Serializable serializable = new Group();
        assertThat(serializable).isNotNull();
    }

    @Test
    public void setIdHasProtectedModifierAccess() throws NoSuchMethodException {
        Method setId = Group.class.getDeclaredMethod("setId", Long.class);
        assertThat(Modifier.isProtected(setId.getModifiers())).isTrue();
    }

    @Test
    public void checkEqualsAndHashCode() {
        Group group2 = new Group();
        group2.setName("name");
        group.setName("name2");

        EqualsVerifier.forClass(Group.class)
                .usingGetClass()
                .withPrefabValues(Group.class, group, group2)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

}