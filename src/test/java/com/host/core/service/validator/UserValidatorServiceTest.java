package com.host.core.service.validator;

import com.host.config.AppConfig;
import com.host.core.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserValidatorServiceTest implements TestHelper<User> {
    @Autowired
    private UserValidatorService validatorService;
    private User user;

    @Before
    public void before() {
        user = new User();
    }

    private String idField = checkFieldName(User.class, "id");

    @Test
    public void idLessThanOneInvalid() throws NoSuchFieldException, IllegalAccessException {
        setProtectedId(User.class, user, 0L);
        long count = countConstraintViolation(validatorService, user, idField);
        assertThat(count == 1).isTrue();
    }

    private String usernameField = checkFieldName(User.class, "username");

    @Test
    public void usernameWithNullInvalid() {
        user.setUsername(null);
        long count = countConstraintViolation(validatorService, user, usernameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void usernameWithLengthLessThan4Invalid() {
        user.setUsername("use");
        long count = countConstraintViolation(validatorService, user, usernameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void usernameWithLengthMoreThan15Invalid() {
        user.setUsername("usernameusername");
        long count = countConstraintViolation(validatorService, user, usernameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void usernameWithUpperCaseSymbolsValid() {
        user.setUsername("UsernAme");
        long count = countConstraintViolation(validatorService, user, usernameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void usernameWithOnlyAlphabetSymbolsValid() {
        user.setUsername("username");
        long count = countConstraintViolation(validatorService, user, usernameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void usernameWithWhiteSymbolFromStartInvalid() {
        user.setUsername("  username");
        long count = countConstraintViolation(validatorService, user, usernameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void usernameWithWhiteSymbolInTheEndInvalid() {
        user.setUsername("username  ");
        long count = countConstraintViolation(validatorService, user, usernameField);
        assertThat(count == 1).isTrue();
    }

    private String passwordField = checkFieldName(User.class, "password");

    @Test
    public void passwordWithNullInvalid() {
        user.setPassword(null);
        long count = countConstraintViolation(validatorService, user, passwordField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void passwordWithLengthLessThan6Invalid() {
        user.setPassword("passw");
        long count = countConstraintViolation(validatorService, user, passwordField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void passwordWithLengthMoreThan16Invalid() {
        user.setPassword("pasS4wordpasswordD");
        long count = countConstraintViolation(validatorService, user, passwordField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void passwordWithAtLeastOneUpperCaseSymbolAndNumberValid() {
        user.setPassword("PassWord4");
        long count = countConstraintViolation(validatorService, user, passwordField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void passwordWithoutAtLeastOneUpperCaseSymbolAndNumberInvalid() {
        user.setPassword("password");
        long count = countConstraintViolation(validatorService, user, passwordField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void passwordWithWhiteSymbolFromStartInvalid() {
        user.setPassword("  password");
        long count = countConstraintViolation(validatorService, user, passwordField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void passwordWithWhiteSymbolInTheEndInvalid() {
        user.setPassword("password  ");
        long count = countConstraintViolation(validatorService, user, passwordField);
        assertThat(count == 1).isTrue();
    }

    private String firstNameField = checkFieldName(User.class, "firstName");

    @Test
    public void firstNameWithNullInvalid() {
        user.setFirstName(null);
        long count = countConstraintViolation(validatorService, user, firstNameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void firstNameWithZeroLengthValid() {
        user.setFirstName("");
        long count = countConstraintViolation(validatorService, user, firstNameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void firstNameWithLengthMoreThan15Invalid() {
        user.setFirstName("firstNameNameNaM");
        long count = countConstraintViolation(validatorService, user, firstNameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void firstNameWithOnlyAlphabetSymbolsValid() {
        user.setFirstName("Anton");
        long count = countConstraintViolation(validatorService, user, firstNameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void firstNameWithWhiteSymbolFromStartInvalid() {
        user.setFirstName("  firstName");
        long count = countConstraintViolation(validatorService, user, firstNameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void firstNameWithWhiteSymbolInTheEndInvalid() {
        user.setFirstName("firstName  ");
        long count = countConstraintViolation(validatorService, user, firstNameField);
        assertThat(count == 1).isTrue();
    }

    private String lastNameField = checkFieldName(User.class, "lastName");

    @Test
    public void lastNameWithNullInvalid() {
        user.setLastName(null);
        long count = countConstraintViolation(validatorService, user, lastNameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void lastNameWithZeroLengthValid() {
        user.setLastName("");
        long count = countConstraintViolation(validatorService, user, lastNameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void lastNameWithLengthMoreThan20Invalid() {
        user.setLastName("SurnameeeeSurnameeeeE");
        long count = countConstraintViolation(validatorService, user, lastNameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void lastNameWithOnlyAlphabetSymbolsValid() {
        user.setLastName("Bond");
        long count = countConstraintViolation(validatorService, user, lastNameField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void lastNameWithWhiteSymbolFromStartInvalid() {
        user.setLastName("  surname");
        long count = countConstraintViolation(validatorService, user, lastNameField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void lastNameWithWhiteSymbolInTheEndInvalid() {
        user.setLastName("surname  ");
        long count = countConstraintViolation(validatorService, user, lastNameField);
        assertThat(count == 1).isTrue();
    }

    private String birthDayField = checkFieldName(User.class, "birthDay");

    @Test
    public void birthDayWithNullInvalid() {
        user.setBirthDay(null);
        long count = countConstraintViolation(validatorService, user, birthDayField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void birthDayWhenPastValid() {
        LocalDate paste = LocalDate.of(1986, 1, 1);
        user.setBirthDay(paste);
        long count = countConstraintViolation(validatorService, user, birthDayField);
        assertThat(count == 0).isTrue();
    }

    @Test
    public void birthDayWhenFutureInvalid() {
        user.setBirthDay(LocalDate.now().plusDays(1));
        long count = countConstraintViolation(validatorService, user, birthDayField);
        assertThat(count == 1).isTrue();
    }

    private String groupsField = checkFieldName(User.class, "groups");

    @Test
    public void groupsWithNullInvalid() {
        user.setGroups(null);
        long count = countConstraintViolation(validatorService, user, groupsField);
        assertThat(count == 1).isTrue();
    }

    @Test
    public void withValidFieldsValid() {
        user.setGroups(new HashSet<>());
        user.setBirthDay(LocalDate.now().minusDays(12));
        user.setLastName("Surname");
        user.setFirstName("Name");
        user.setPassword("Password6");
        user.setUsername("login");
        assertThat(validatorService.isValid(user)).isTrue();
    }

    @Test
    public void trimFields() {
        user.setGroups(new HashSet<>());
        user.setBirthDay(LocalDate.now().minusDays(12));
        user.setLastName("   Surname   ");
        user.setFirstName("   Name   ");
        user.setPassword("  Password6   ");
        user.setUsername(" login ");
        validatorService.trimFields(user);
        assertThat(validatorService.isValid(user)).isTrue();
    }

    @Test
    public void has7Fields() {
        assertThat(countFields(user)).isEqualTo(7);
    }
}