package ui;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static {
        dateFormat.setLenient(false);
    }

    public static void displayMenu() {
        System.out.println("\n========= Hotel Reservation System =========");
        System.out.println("1. Find and Reserve a Room");
        System.out.println("2. See My Reservations");
        System.out.println("3. Create an Account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("=============================================");
        System.out.print("Enter choice: ");
    }

    public static void mainMenu() {
        boolean running = true;
        while (running) {
            displayMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.adminMenu();
                    break;
                case "5":
                    System.out.println("Thank you! Goodbye.");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-5.");
            }
        }
    }

    // ── Find & Reserve ──────────────────────────────────────────
    private static void findAndReserveRoom() {
        try {
            System.out.print("Enter Check-In date (MM/dd/yyyy): ");
            Date checkIn = dateFormat.parse(scanner.nextLine().trim());

            System.out.print("Enter Check-Out date (MM/dd/yyyy): ");
            Date checkOut = dateFormat.parse(scanner.nextLine().trim());

            // Validate dates
            Date today = new Date();
            if (!checkIn.after(today) && !isSameDay(checkIn, today)) {
                System.out.println("Check-in date must be today or in the future.");
                return;
            }
            if (!checkOut.after(checkIn)) {
                System.out.println("Check-out date must be after check-in date.");
                return;
            }

            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

            if (availableRooms.isEmpty()) {
                // Recommend rooms 7 days later
                System.out.println("No rooms available for those dates.");
                Calendar cal = Calendar.getInstance();

                cal.setTime(checkIn);
                cal.add(Calendar.DAY_OF_MONTH, 7);
                Date newCheckIn = cal.getTime();

                cal.setTime(checkOut);
                cal.add(Calendar.DAY_OF_MONTH, 7);
                Date newCheckOut = cal.getTime();

                Collection<IRoom> recommended = hotelResource.findARoom(newCheckIn, newCheckOut);
                if (recommended.isEmpty()) {
                    System.out.println("No recommended rooms available either. Please try different dates.");
                    return;
                }
                System.out.println("\nRecommended rooms for " +
                        dateFormat.format(newCheckIn) + " to " + dateFormat.format(newCheckOut) + ":");
                for (IRoom r : recommended) System.out.println(r);

                System.out.print("\nWould you like to book one of these? (yes/no): ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) return;

                bookRoom(recommended, newCheckIn, newCheckOut);
            } else {
                System.out.println("\nAvailable rooms:");
                for (IRoom r : availableRooms) System.out.println(r);
                bookRoom(availableRooms, checkIn, checkOut);
            }

        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use MM/dd/yyyy.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void bookRoom(Collection<IRoom> rooms, Date checkIn, Date checkOut) {
        System.out.print("\nDo you have an account? (yes/no): ");
        String hasAccount = scanner.nextLine().trim();

        if (hasAccount.equalsIgnoreCase("no")) {
            System.out.println("Please create an account first (option 3 from main menu).");
            return;
        }

        System.out.print("Enter your email: ");
        String email = scanner.nextLine().trim();

        if (hotelResource.getCustomer(email) == null) {
            System.out.println("No account found with that email.");
            return;
        }

        System.out.print("Enter room number to book: ");
        String roomNumber = scanner.nextLine().trim();

        IRoom selectedRoom = hotelResource.getRoom(roomNumber);
        if (selectedRoom == null) {
            System.out.println("Room not found.");
            return;
        }

        // Check it's in available list
        boolean found = false;
        for (IRoom r : rooms) {
            if (r.getRoomNumber().equals(roomNumber)) { found = true; break; }
        }
        if (!found) {
            System.out.println("That room is not available for the selected dates.");
            return;
        }

        Reservation reservation = hotelResource.bookARoom(email, selectedRoom, checkIn, checkOut);
        System.out.println("\n Booking confirmed!\n" + reservation);
    }

    // ── See Reservations ────────────────────────────────────────
    private static void seeMyReservations() {
        try {
            System.out.print("Enter your email: ");
            String email = scanner.nextLine().trim();

            Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
            if (reservations.isEmpty()) {
                System.out.println("No reservations found.");
            } else {
                for (Reservation r : reservations) {
                    System.out.println(r);
                    System.out.println("----------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ── Create Account ──────────────────────────────────────────
    private static void createAccount() {
        try {
            System.out.print("Enter email (e.g. name@domain.com): ");
            String email = scanner.nextLine().trim();

            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine().trim();

            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine().trim();

            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    private static boolean isSameDay(Date d1, Date d2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(d1).equals(fmt.format(d2));
    }
}
