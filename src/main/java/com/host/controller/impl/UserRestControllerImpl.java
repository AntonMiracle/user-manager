package com.host.controller.impl;

import com.host.controller.UserRestController;
import com.host.core.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UserRestControllerImpl implements UserRestController {


    @Override
    public ResponseEntity<List<User>> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<User> save(User model) {
        return null;
    }

    @Override
    public ResponseEntity<User> update(User model) {
        return null;
    }

    @Override
    public ResponseEntity<User> get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> delete(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> isUnique(String name) {
        return null;
    }
}
