package com.host.core.service.impl;

import com.host.config.Initialized;
import com.host.core.dao.UserDAO;
import com.host.core.model.Group;
import com.host.core.model.User;
import com.host.core.service.GroupService;
import com.host.core.service.UserService;
import com.host.core.service.validator.UserValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserValidatorService userValidator;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private GroupService groupService;

    @Override
    public User find(Long id) {
        if (id == null) return null;
        return userDAO.find(id);
    }

    @Override
    public User find(String uniqueName) {
        if (uniqueName == null) return null;
        return userDAO.find(uniqueName);
    }

    @Override
    public Set<User> find() {
        Set<User> users = userDAO.find();
        if (users == null) users = new HashSet<>();
        return users;
    }

    @Override
    public User save(User model) {
        if (model == null) return null;
        if (!validate(model)) return null;
        if (isExist(model.getUsername())) return null;
        model.setPassword(cryptPassword(model.getPassword()));
        return userDAO.save(model);
    }

    @Override
    public User update(User model) {
        if (model == null) return null;
        if (!validate(model)) return null;
        if (isUsernameUnique(model.getUsername())) return null;
        return userDAO.update(model);
    }

    @Override
    public boolean delete(Long deleteId) {
        if (deleteId == null) return false;
        if (userDAO.find(deleteId) == null) return false;
        return userDAO.delete(deleteId);
    }

    @Override
    public boolean isUsernameUnique(String username) {
        if (username == null) return false;
        return userDAO.find(username) == null;
    }

    @Override
    public boolean isExist(String username) {
        if (username == null) return false;
        return !isUsernameUnique(username);
    }

    @Override
    public String cryptPassword(String passwordToCrypt) {
        MessageDigest md5 = getMD5();
        md5.update(passwordToCrypt.getBytes(), 0, passwordToCrypt.length());
        return new BigInteger(1, md5.digest()).toString(60);
    }

    @Override
    public boolean validate(User model) {
        if (model == null) return false;

        if (model.getFirstName() == null) model.setFirstName("");
        if (model.getLastName() == null) model.setLastName("");

        model.setGroups(checkGroups(model.getGroups()));

        return userValidator.isValid(model);
    }

    @Override
    public boolean remove(Long removeId, Long fromId) {
        if (removeId == null | fromId == null) return false;

        if (find(fromId) == null) return false;
        User user = find(fromId);

        Group removeGroup = null;
        for (Group group : user.getGroups()) {
            if (group.getId().equals(removeId)) {
                removeGroup = group;
                break;
            }
        }
        if (removeGroup == null) return false;

        boolean result = user.getGroups().remove(removeGroup);
        if (result) update(user);
        return result;
    }

    private Set<Group> checkGroups(Set<Group> groups) {
        if (groups == null) groups = new HashSet<>();
        if (!isBasicGroupExist(groups)) {
            groups.add(groupService.find(Initialized.GROUP_NAME_USER));
        }
        return groups;
    }

    private boolean isBasicGroupExist(Set<Group> groups) {
        if (groups != null) {
            for (Group group : groups) {
                if (group.getName().equals(Initialized.GROUP_NAME_USER)) {
                    return true;
                }
            }
        }
        return false;
    }

    private MessageDigest getMD5() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Error when getting MD5" + e);
        }
    }
}
