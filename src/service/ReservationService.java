package service;

import model.*;
import java.util.*;

public class ReservationService {

    private static ReservationService instance = null;

    // HashMap ensures unique room numbers
    private final Map<String, IRoom> rooms = new HashMap<>();
    private final List<Reservation>  reservations = new ArrayList<>();

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    // Add room – reject duplicates
    public void addRoom(IRoom room) {
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number " + room.getRoomNumber() + " already exists.");
        }
        rooms.put(room.getRoomNumber(), room);
        System.out.println("Room added: " + room);
    }

    public IRoom getARoom(String roomId) {
        return rooms.get(roomId);
    }

    public Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    // Find rooms NOT booked in the given date range
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>(rooms.values());

        for (Reservation reservation : reservations) {
            if (datesOverlap(reservation.getCheckInDate(), reservation.getCheckOutDate(),
                    checkInDate, checkOutDate)) {
                availableRooms.remove(reservation.getRoom());
            }
        }
        return availableRooms;
    }

    // Book a room – prevent double-booking
    public Reservation reserveARoom(Customer customer, IRoom room,
                                    Date checkInDate, Date checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room) &&
                    datesOverlap(reservation.getCheckInDate(), reservation.getCheckOutDate(),
                            checkInDate, checkOutDate)) {
                throw new IllegalArgumentException("Room " + room.getRoomNumber() +
                        " is already booked for those dates.");
            }
        }
        Reservation newRes = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newRes);
        return newRes;
    }

    // Get reservations for a specific customer
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.getCustomer().equals(customer)) {
                result.add(r);
            }
        }
        return result;
    }

    public void printAllReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        for (Reservation r : reservations) {
            System.out.println(r);
            System.out.println("----------------------------");
        }
    }

    // Helper: do two date ranges overlap?
    private boolean datesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return start1.before(end2) && end1.after(start2);
    }
}
