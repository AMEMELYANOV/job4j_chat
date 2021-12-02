package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Message;
import ru.job4j.chat.service.MessageService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public List<Message> findAll() {
        return new ArrayList<>(this.messageService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> findById(@PathVariable int id) {
        var message = this.messageService.findById(id);
        return new ResponseEntity<Message>(
                message.orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Message is not found. Please, check id.")
                ), HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message) {
        if (message.getContent() == null || message.getPerson() == null
        || message.getPerson().getId() == 0 || message.getRoom() == null
        || message.getRoom().getId() == 0) {
            throw new NullPointerException("Content, Person, Person.id, "
                    + "Room and Room.id mustn't be empty");
        }
        return new ResponseEntity<Message>(
                this.messageService.save(message),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        if (message.getContent() == null || message.getPerson().getId() == 0
                || message.getRoom().getId() == 0) {
            throw new NullPointerException("Content, Person ID and Room ID mustn't be empty");
        }
        this.messageService.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Message message = new Message();
        message.setId(id);
        this.messageService.delete(message);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Message> patch(@RequestBody Message message)
            throws InvocationTargetException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(messageService.patchModel(message).
                        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Message is not found or invalid properties mapping")));
    }
}
