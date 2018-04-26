package com.host.controller;

import com.host.core.model.CoreModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CoreModelRestController<T extends CoreModel> {
    ResponseEntity<List<T>> getAll();

    ResponseEntity<T> save(T model);

    ResponseEntity<T> update(T model);

    ResponseEntity<T> get(Long id);

    ResponseEntity<Boolean> delete(Long id);

    ResponseEntity<Boolean> isUnique(String name);
}
