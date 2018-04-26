package com.host.config;

import com.host.core.dao.GroupDAO;
import com.host.core.dao.UserDAO;
import com.host.core.model.Group;
import com.host.core.model.User;
import com.host.core.service.GroupService;
import com.host.core.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class Initialized implements InitializingBean {
    final static public String GROUP_NAME_USER = "user";
    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;

    @Override
    public void afterPropertiesSet() throws Exception {
        initUserGroup();
//        generationTestUsers(20);
//        generationTestGroups(10);
    }

    private void initUserGroup() {
        if (groupDAO.find(GROUP_NAME_USER) == null) {
            Group group = new Group();
            group.setName(GROUP_NAME_USER);
            group.setUsers(new HashSet<>());
            groupDAO.save(group);
        }
    }

    private void generationTestUsers(int number) {
        for (int i = 0; i < number; ++i) {
            User user = new User();
            user.setUsername("username" + String.valueOf((char) i));
            user.setBirthDay(LocalDate.now().minusDays(100 * (i + 1)));
            user.setPassword("password");
            user.setGroups(new HashSet<>());
            user.setLastName("lastName" + String.valueOf((char) i));
            user.setFirstName("FirstName" + String.valueOf((char) i));
            Set<Group> groups = new HashSet<>();
            groups.add(groupDAO.find(GROUP_NAME_USER));
            userService.save(user);
        }
    }

    private void generationTestGroups(int number) {
        for (int i = 0; i < number; ++i) {
            Group group = new Group();
            group.setName("Name" + String.valueOf((char) i));
            group.setUsers(new HashSet<>());
            groupDAO.save(group);
        }
    }
}
