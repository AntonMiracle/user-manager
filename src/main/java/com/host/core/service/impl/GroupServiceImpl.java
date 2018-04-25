package com.host.core.service.impl;

import com.host.core.dao.GroupDAO;
import com.host.core.model.Group;
import com.host.core.service.GroupService;
import com.host.core.service.validator.GroupValidatorService;
import javassist.NotFoundException;
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
    public Group find(Long id) throws NotFoundException, IllegalArgumentException {
        if (id == null) throw new IllegalArgumentException("Id is null");
        Group group = groupDAO.find(id);
        if (group == null) throw new NotFoundException("No Group with id=" + id.toString());
        return group;
    }

    @Override
    public Group find(String uniqueName) throws NotFoundException, IllegalArgumentException {
        if (uniqueName == null) throw new IllegalArgumentException("UniqueName is null");
        if (isExist(uniqueName)) return groupDAO.find(uniqueName);
        throw new NotFoundException("No Group with UniqueName=" + uniqueName);
    }

    @Override
    public Set<Group> find() {
        Set<Group> groups = groupDAO.find();
        if (groups == null) groups = new HashSet<>();
        return groups;
    }

    @Override
    public Group save(Group model) throws IllegalStateException, IllegalArgumentException {
        if (model == null) throw new IllegalArgumentException("Group is null");
        if (model.getUsers() == null) model.setUsers(new HashSet<>());
        if (!groupValidator.isValid(model)) throw new IllegalStateException(groupValidator.convertToString(model));
        if (isExist(model.getName())) return update(model);
        return groupDAO.save(model);
    }

    @Override
    public Group update(Group model) throws IllegalStateException, IllegalArgumentException {
        if (model == null) throw new IllegalArgumentException("Group is null");
        if (model.getUsers() == null) model.setUsers(new HashSet<>());
        if (!groupValidator.isValid(model)) throw new IllegalStateException(groupValidator.convertToString(model));
        if (isNameUnique(model.getName())) return save(model);
        return groupDAO.update(model);
    }

    @Override
    public boolean delete(Long deleteId) throws IllegalArgumentException {
        if (deleteId == null) throw new IllegalArgumentException("Id is null");
        if (groupDAO.find(deleteId) == null) return false;
        return groupDAO.delete(deleteId);
    }

    @Override
    public boolean isNameUnique(String groupName) throws IllegalArgumentException {
        if (groupName == null) throw new IllegalArgumentException("GroupName is null");
        return groupDAO.find(groupName) == null;
    }

    @Override
    public boolean isExist(String groupName) throws IllegalArgumentException {
        if (groupName == null) throw new IllegalArgumentException("GroupName is null");
        return !isNameUnique(groupName);
    }

}
