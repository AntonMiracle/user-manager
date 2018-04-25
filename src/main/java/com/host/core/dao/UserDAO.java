package com.host.core.dao;

import com.host.core.model.User;

public interface UserDAO extends CoreModelDAO<User> {
    User find(String username);
}
