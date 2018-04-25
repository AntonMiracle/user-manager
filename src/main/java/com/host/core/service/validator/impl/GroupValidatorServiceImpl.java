package com.host.core.service.validator.impl;

import com.host.core.model.Group;
import com.host.core.service.validator.GroupValidatorService;
import org.springframework.stereotype.Service;

@Service
public class GroupValidatorServiceImpl implements GroupValidatorService {
    @Override
    public void trimFields(Group object) {
        if (object == null) return;
        if (object.getName() != null) object.setName(object.getName().trim());
    }
}
