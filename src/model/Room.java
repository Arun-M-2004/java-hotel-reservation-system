package model;

import java.util.Objects;

public class Room implements IRoom {
    private final String roomNumber;
    private final Double price;
    private final RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() { return roomNumber; }

    @Override
    public Double getRoomPrice() { return price; }

    @Override
    public RoomType getRoomType() { return roomType; }

    @Override
    public boolean isFree() { return price == 0; }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                " | Type: " + roomType +
                " | Price: " + (price == 0 ? "Free" : "$" + price + " per night");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Room room = (Room) obj;
        return Objects.equals(roomNumber, room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
