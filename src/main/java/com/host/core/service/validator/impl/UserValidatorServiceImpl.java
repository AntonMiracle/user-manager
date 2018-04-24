package com.host.core.service.validator.impl;

import com.host.core.model.User;
import com.host.core.service.validator.UserValidatorService;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorServiceImpl implements UserValidatorService {
    @Override
    public void trimFields(User object) {
        if (object == null) return;
        if (object.getFirstName() != null) object.setFirstName(object.getFirstName().trim());
        if (object.getLastName() != null) object.setLastName(object.getLastName().trim());
        if (object.getPassword() != null) object.setPassword(object.getPassword().trim());
        if (object.getUsername() != null) object.setUsername(object.getUsername().trim());
    }
}
