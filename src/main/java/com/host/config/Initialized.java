package com.host.config;

import com.host.core.dao.GroupDAO;
import com.host.core.model.Group;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class Initialized implements InitializingBean {
    final static public String GROUP_NAME_USER = "user";
    @Autowired
    private GroupDAO groupDAO;

    @Override
    public void afterPropertiesSet() throws Exception {
        initUserGroup();
    }

    private void initUserGroup() {
        if (groupDAO.find(GROUP_NAME_USER) == null) {
            Group group = new Group();
            group.setName(GROUP_NAME_USER);
            group.setUsers(new HashSet<>());
            groupDAO.save(group);
        }
    }
}
