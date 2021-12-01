package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoleService;

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
        return new ResponseEntity<Role>(
                role.orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Role is not found. Please, check id.")
                ), HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Role> create(@RequestBody Role role) {
        if (role.getName() == null) {
            throw new NullPointerException("Name mustn't be empty");
        }
        return new ResponseEntity<Role>(
                this.roleService.save(role),
                HttpStatus.CREATED
        );
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
}
