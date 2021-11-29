package ru.job4j.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Integer> {

    @EntityGraph(attributePaths = {"person", "room"})
    List<Message> findAll();

    @EntityGraph(attributePaths = {"person", "room"})
    Optional<Message> findById(int id);
}
