package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.service.RoleService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/")
    public List<Role> findAll() {
        return new ArrayList<>(this.roleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        var role = this.roleService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(role.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Role is not found. Please, check id.")));
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        if (role.getName() == null) {
            throw new NullPointerException("Name mustn't be empty");
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.roleService.save(role));
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Role role) {
        if (role.getName() == null) {
            throw new NullPointerException("Name mustn't be empty");
        }
        this.roleService.save(role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Role role = new Role();
        role.setId(id);
        this.roleService.delete(role);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Role> patch(@RequestBody Role role)
            throws InvocationTargetException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roleService.patchModel(role).
                        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Role is not found or invalid properties mapping")));
    }
}
