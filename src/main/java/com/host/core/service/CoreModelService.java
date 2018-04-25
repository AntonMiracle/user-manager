package com.host.core.service;

import javassist.NotFoundException;

import java.util.Set;

public interface CoreModelService<T> {
    T find(Long id) throws NotFoundException, IllegalArgumentException;

    T find(String uniqueName) throws NotFoundException, IllegalArgumentException;

    Set<T> find();

    T save(T model) throws IllegalStateException, IllegalArgumentException;

    T update(T model) throws IllegalStateException, IllegalArgumentException;

    boolean delete(Long deleteId) throws IllegalArgumentException;
}
