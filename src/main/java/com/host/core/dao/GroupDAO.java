package com.host.core.dao;

import com.host.core.model.Group;

public interface GroupDAO extends CoreModelDAO<Group> {
    Group find(String name);
}
