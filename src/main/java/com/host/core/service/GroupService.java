package com.host.core.service;

import com.host.core.model.Group;

public interface GroupService extends CoreModelService<Group> {
    boolean isNameUnique(String groupName);

    boolean isExist(String groupName);

    boolean remove(Long userId, Long groupId);

}
