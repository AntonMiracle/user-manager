package com.host.core.dao.impl;

import com.host.core.dao.UserDAO;
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
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User find(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where user.id = '" + id.toString() + "'");
        return (User) query.uniqueResult();
    }

    @Override
    public Set<User> find() {
        Session session = sessionFactory.getCurrentSession();
        Set<User> users = (Set<User>) session.createQuery("from User").getResultStream().collect(Collectors.toCollection(HashSet::new));
        return users;
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(find(id));
        session.flush();
        return true;
    }

    @Override
    public User save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }

    @Override
    public User update(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(user);
        session.flush();
        return user;
    }

    @Override
    public User find(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from User as user where user.username = '" + username + "'");
        return (User) query.uniqueResult();
    }
}
