package com.host.controller.impl;

import com.host.controller.GroupRestController;
import com.host.core.model.Group;
import com.host.core.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/groups")
public class GroupRestControllerImpl implements GroupRestController {
    @Autowired
    private GroupService groupService;

    @Override
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Group>> getAll() {
        Set<Group> result = groupService.find();
        if (result == null) return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        List<Group> list = result.stream().collect(Collectors.toCollection(ArrayList::new));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Group[]> test() {
        Group[] result = groupService.find().stream().toArray(Group[]::new);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Override
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Group> save(@RequestBody Group model) {
        if (model == null) return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
        Group update = groupService.save(model);
        if (update != null) return new ResponseEntity(update, HttpStatus.CREATED);
        return new ResponseEntity(model, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Group> update(@RequestBody Group model) {
        if (model == null) return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
        Group update = groupService.update(model);
        if (update != null) return new ResponseEntity(update, HttpStatus.OK);
        return new ResponseEntity(model, HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Group> get(@PathVariable Long id) {
        if (id == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Group result = groupService.find(id);
        if (result != null) return new ResponseEntity(result, HttpStatus.OK);
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(Long id) {
        if (id == null) return new ResponseEntity(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        if (groupService.delete(id)) return new ResponseEntity(Boolean.TRUE, HttpStatus.OK);
        return new ResponseEntity(Boolean.FALSE, HttpStatus.NOT_FOUND);
    }

    @Override
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isUnique(String name) {
        if (name == null) return new ResponseEntity(Boolean.FALSE, HttpStatus.BAD_REQUEST);
        if (groupService.isNameUnique(name)) return new ResponseEntity(Boolean.TRUE, HttpStatus.ACCEPTED);
        return new ResponseEntity(Boolean.FALSE, HttpStatus.NOT_ACCEPTABLE);
    }

}
