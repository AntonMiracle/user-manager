package com.host.core.service;

import java.util.Set;

public interface CoreModelService<T> {
    T find(Long id);

    T find(String uniqueName);

    Set<T> find();

    T save(T model);

    T update(T model);

    boolean delete(Long deleteId);

    boolean validate(T model);

    boolean remove(Long removeId, Long fromId);
}
