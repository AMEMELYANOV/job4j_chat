package ru.job4j.chat.service;

import org.springframework.stereotype.Service;
import ru.job4j.chat.model.Role;
import ru.job4j.chat.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return StreamSupport.stream(
                this.roleRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    public Optional<Role> findById(int id) {
        return roleRepository.findById(id);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

    public void delete(Role role) {
        roleRepository.delete(role);
    }
}
