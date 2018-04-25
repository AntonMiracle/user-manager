package com.host.core.dao.impl;

import com.host.core.dao.GroupDAO;
import com.host.core.model.Group;
import com.host.core.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class GroupDAOImpl implements GroupDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Group find(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Groups as groups where groups.id = '" + id.toString() + "'");
        return (Group) query.uniqueResult();
    }

    @Override
    public Set<Group> find() {
        Session session = sessionFactory.getCurrentSession();
        Set<Group> groups = (Set<Group>) session.createQuery("from Groups").getResultStream().collect(Collectors.toCollection(HashSet::new));
        return groups;
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(find(id));
        session.flush();
        return true;
    }

    @Override
    public Group add(Group group) {
        Session session = sessionFactory.getCurrentSession();
        session.save(group);
        return group;
    }

    @Override
    public Group update(Group group) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(group);
        session.flush();
        return group;
    }

    @Override
    public Group find(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Groups as groups where groups.name = '" + name + "'");
        return (Group) query.uniqueResult();
    }
}
