package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {
    private final Customer customer;
    private final IRoom room;
    private final Date checkInDate;
    private final Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer    = customer;
        this.room        = room;
        this.checkInDate  = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer()    { return customer;    }
    public IRoom    getRoom()        { return room;        }
    public Date     getCheckInDate() { return checkInDate; }
    public Date     getCheckOutDate(){ return checkOutDate;}

    @Override
    public String toString() {
        return "Customer: " + customer.getFirstName() + " " + customer.getLastName() +
                "\nRoom: " + room +
                "\nCheck-In: " + checkInDate +
                "\nCheck-Out: " + checkOutDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reservation r = (Reservation) obj;
        return Objects.equals(customer, r.customer) &&
                Objects.equals(room, r.room) &&
                Objects.equals(checkInDate, r.checkInDate) &&
                Objects.equals(checkOutDate, r.checkOutDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, room, checkInDate, checkOutDate);
    }
}
