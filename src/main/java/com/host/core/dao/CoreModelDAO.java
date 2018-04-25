package com.host.core.dao;

import com.host.core.model.CoreModel;

import java.util.Set;

public interface CoreModelDAO<T extends CoreModel> {
    T find(Long id);

    Set<T> find();

    boolean delete(Long id);

    T save(T object);

    T update(T object);
}
