package com.host.core.service;

import com.host.core.model.User;

public interface UserService extends CoreModelService<User> {
    boolean isUsernameUnique(String username);

    boolean isUserExist(String username);

    String cryptPassword(String passwordToCrypt);

    boolean remove(Long groupId, Long userId);

}
