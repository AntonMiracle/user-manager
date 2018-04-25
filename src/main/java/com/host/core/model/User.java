package com.host.core.model;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class User implements Serializable, CoreModel {
    @DecimalMin("1.0")
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]{4,15}$")
    @Column(name = "USERNAME", nullable = false, unique = true, length = 15)
    private String username;

    @NotNull
    @Pattern(regexp = "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16})$")
    @Column(name = "PASSWORD", nullable = false, length = 256)
    private String password;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]{0,15}$")
    @Column(name = "FIRST_NAME", nullable = false, length = 15)
    private String firstName;

    @NotNull
    @Pattern(regexp = "^[A-Za-z]{0,20}$")
    @Column(name = "LAST_NAME", nullable = false, length = 20)
    private String lastName;

    @NotNull
    @Past
    @Column(name = "BIRTHDAY", nullable = false, length = 10)
    private LocalDate birthDay;

    @NotNull
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "USER_GROUP",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    private Set<Group> groups;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public Set<Group> getGroups() {
        return groups;
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

        User user = (User) o;

        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (birthDay != null ? !birthDay.equals(user.birthDay) : user.birthDay != null) return false;
        if (groups != null ? !groups.equals(user.groups) : user.groups != null) return false;
        return id != null ? id.equals(user.id) : user.id == null;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        result = 31 * result + (groups != null ? groups.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
