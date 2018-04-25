package com.host.core.service;

import com.host.core.model.User;

public interface UserService extends CoreModelService<User> {
    boolean isUsernameUnique(String username) throws IllegalArgumentException;

    boolean isExist(String username) throws IllegalArgumentException;

    String cryptPassword(String passwordToCrypt);

}
