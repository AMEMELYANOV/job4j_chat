package ru.job4j.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.chat.model.Room;
import ru.job4j.chat.service.RoomService;

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
                room.orElse(new Room()),
                room.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return new ResponseEntity<Room>(
                this.roomService.save(room),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Room room) {
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
}
