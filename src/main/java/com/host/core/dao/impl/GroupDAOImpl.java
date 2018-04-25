package com.host.core.dao.impl;

import com.host.core.dao.GroupDAO;
import com.host.core.model.Group;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class GroupDAOImpl implements GroupDAO {
    @Override
    public Group find(Long id) {
        return null;
    }

    @Override
    public Set<Group> find() {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean add(Group object) {
        return false;
    }

    @Override
    public boolean update(Group object) {
        return false;
    }

    @Override
    public Group find(String name) {
        return null;
    }
}
