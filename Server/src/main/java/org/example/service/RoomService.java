package org.example.service;

import org.example.exception.ResourceNotFoundException;
import org.example.model.Room;
import org.example.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room getById(Long id) {
        return roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Аудитория не найдена"));
    }

    public void insert(Room room) {
        roomRepository.save(room);
    }

    public void update(Room room, Long id) {
        getById(id);

        room.setId(id);
        roomRepository.save(room);
    }

    public void delete(Long id) {
        getById(id);

        roomRepository.deleteById(id);
    }
}
