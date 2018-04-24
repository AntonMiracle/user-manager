package com.host.core.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

public class Group implements Serializable, CoreModel {
    @NotNull
    @Pattern(regexp = "^[a-z]{4,15}$")
    private String name;
    @NotNull
    private Set<User> users;
    @DecimalMin("1.0")
    private Long id;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsers() {
        return users;
    }

    protected void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (name != null ? !name.equals(group.name) : group.name != null) return false;
        if (users != null ? !users.equals(group.users) : group.users != null) return false;
        return id != null ? id.equals(group.id) : group.id == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
