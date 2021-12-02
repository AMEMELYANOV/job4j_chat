package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/")
    public List<Room> findAll() {
        return new ArrayList<>(this.roomService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        var room = this.roomService.findById(id);
        return new ResponseEntity<Room>(
                room.orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Room is not found. Please, check id.")
                ), HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("Name mustn't be empty");
        }
        return new ResponseEntity<Room>(
                this.roomService.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
        if (room.getName() == null) {
            throw new NullPointerException("Name mustn't be empty");
        }

        this.roomService.save(room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        Room room = new Room();
        room.setId(id);
        this.roomService.delete(room);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/")
    public ResponseEntity<Room> patch(@RequestBody Room room)
            throws InvocationTargetException, IllegalAccessException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(roomService.patchModel(room).
                        orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Room is not found or invalid properties mapping")));
    }
}
