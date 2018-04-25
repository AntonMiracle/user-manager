package com.host.core.service.impl;

import com.host.core.dao.GroupDAO;
import com.host.core.model.Group;
import com.host.core.model.User;
import com.host.core.service.GroupService;
import com.host.core.service.validator.GroupValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private GroupValidatorService groupValidator;

    @Override
    public Group find(Long id) {
        if (id == null) return null;
        return groupDAO.find(id);
    }

    @Override
    public Group find(String uniqueName) {
        if (uniqueName == null) return null;
        return groupDAO.find(uniqueName);
    }

    @Override
    public Set<Group> find() {
        Set<Group> groups = groupDAO.find();
        if (groups == null) groups = new HashSet<>();
        return groups;
    }

    @Override
    public Group save(Group model) {
        if (model == null) return null;
        if (!validate(model)) return null;
        if (isExist(model.getName())) return update(model);
        return groupDAO.save(model);
    }

    @Override
    public Group update(Group model) {
        if (model == null) return null;
        if (!validate(model)) return null;
        if (isNameUnique(model.getName())) return save(model);
        return groupDAO.update(model);
    }

    @Override
    public boolean delete(Long deleteId) {
        if (deleteId == null) return false;
        if (groupDAO.find(deleteId) == null) return false;
        return groupDAO.delete(deleteId);
    }

    @Override
    public boolean validate(Group model) {
        if (model == null) return false;
        if (model.getUsers() == null) model.setUsers(new HashSet<>());
        return groupValidator.isValid(model);
    }

    @Override
    public boolean remove(Long removeId, Long fromId) {
        if (removeId == null || fromId == null) return false;
        if (find(fromId) == null) return false;
        Group group = find(fromId);
        User removeUser = null;
        for (User user : group.getUsers()) {
            if (user.getId().equals(removeId)) {
                removeUser = user;
                break;
            }
        }
        if (removeUser == null) return false;
        boolean result = group.getUsers().remove(removeUser);
        if (result) update(group);
        return result;
    }

    @Override
    public boolean isNameUnique(String groupName) {
        if (groupName == null) return false;
        return groupDAO.find(groupName) == null;
    }

    @Override
    public boolean isExist(String groupName) {
        if (groupName == null) return false;
        return !isNameUnique(groupName);
    }
}
