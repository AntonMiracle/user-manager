package com.host.core.service;

import com.host.core.model.Group;

public interface GroupService extends CoreModelService<Group> {
    boolean isNameUnique(String groupName) throws IllegalArgumentException;

    boolean isExist(String groupName) throws IllegalArgumentException;

}
