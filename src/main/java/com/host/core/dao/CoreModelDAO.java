package com.host.core.dao;

import java.util.Set;

public interface CoreModelDAO<T> {
    T find(Long id);

    Set<T> find();

    boolean delete(Long id);

    boolean add(T object);

    boolean update(T object);
}
