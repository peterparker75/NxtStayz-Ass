
package com.example.nxtstayz.controller;

import com.example.nxtstayz.model.Room;
import com.example.nxtstayz.model.Hotel;
import com.example.nxtstayz.service.RoomJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class RoomController {

    @Autowired
    private RoomJpaService roomJpaService;

    // @GetMapping("/hotels/{hotelId}/rooms")
    // public List<Room> getRoomsByHotelId(@PathVariable int hotelId) {
    // return roomJpaService.getRoomsByHotelId(hotelId);
    // }

    @GetMapping("/hotels/rooms")
    public ArrayList<Room> getRooms() {
        return roomJpaService.getRooms();
    }

    @GetMapping("/hotels/rooms/{roomId}")
    public Room getRoomById(@PathVariable("roomId") int roomId) {
        return roomJpaService.getRoomById(roomId);
    }

    @PostMapping("/hotels/rooms")
    public Room addRoom(@RequestBody Room room) {
        return roomJpaService.addRoom(room);
    }

    @PutMapping("/hotels/rooms/{roomId}")
    public Room updateRoom(@PathVariable("roomId") int roomId, @RequestBody Room room) {
        return roomJpaService.updateRoom(roomId, room);
    }

    @DeleteMapping("/hotels/rooms/{roomId}")
    public void deleteRoom(@PathVariable int roomId) {
        roomJpaService.deleteRoom(roomId);
    }

    @GetMapping("/hotels/{roomId}/hotel")
    public Hotel getHotelByRoomId(@PathVariable int roomId) {
        return roomJpaService.getRoomHotel(roomId);
    }

}