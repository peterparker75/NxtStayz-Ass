
package com.example.nxtstayz.service;

import com.example.nxtstayz.model.Room;
import com.example.nxtstayz.model.Hotel;
import com.example.nxtstayz.repository.RoomJpaRepository;
import com.example.nxtstayz.repository.HotelJpaRepository;
import com.example.nxtstayz.repository.RoomRepository;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomJpaService implements RoomRepository {

    @Autowired
    private RoomJpaRepository roomJpaRepository;

    @Autowired
    private HotelJpaRepository hotelJpaRepository;

    public ArrayList<Room> getRooms() {
        List<Room> roomsList = roomJpaRepository.findAll();
        return new ArrayList<>(roomsList);
    }

    public Room addRoom(Room room) {
        Hotel hotel = room.getHotel();
        int hotelId = hotel.getHotelId();
        try {
            hotel = hotelJpaRepository.findById(room.getHotel().getHotelId()).get();
            room.setHotel(hotel);
            roomJpaRepository.save(room);
            return room;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Room updateRoom(int roomId, Room room) {
        try {
            Room existingRoom = roomJpaRepository.findById(roomId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            if (room.getRoomNumber() != null) {
                existingRoom.setRoomNumber(room.getRoomNumber());
            }
            if (room.getRoomType() != null) {
                existingRoom.setRoomType(room.getRoomType());
            }
            if (room.getPrice() != 0) {
                existingRoom.setPrice(room.getPrice());
            }
            if (room.getHotel() != null && room.getHotel().getHotelId() != 0) {
                Hotel hotel = hotelJpaRepository.findById(room.getHotel().getHotelId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                existingRoom.setHotel(hotel);
            }

            return roomJpaRepository.save(existingRoom);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Room getRoomById(int roomId) {
        try {
            return roomJpaRepository.findById(roomId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteRoom(int roomId) {
        try {
            // if (!roomJpaRepository.existsById(roomId)) {
            // throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            // }
            roomJpaRepository.deleteById(roomId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    public Hotel getRoomHotel(int roomId) {
        try {
            Room room = roomJpaRepository.findById(roomId).get();
            Hotel hotel = room.getHotel();
            return hotel;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    // @Override
    // public List<Room> findByHotel_HotelId(int hotelId) {
    // return roomJpaRepository.findByHotel_HotelId(hotelId);
    // }

    // public List<Room> getRoomsByHotelId(int hotelId) {
    // List<Room> rooms = roomJpaRepository.findByHotel_HotelId(hotelId);
    // if (rooms.isEmpty()) {
    // throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    // }
    // return rooms;
    // }

}