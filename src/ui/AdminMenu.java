package ui;

import api.AdminResource;
import model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    public static void displayAdminMenu() {
        System.out.println("\n========== Admin Menu ==========");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.println("=================================");
        System.out.print("Enter choice: ");
    }

    public static void adminMenu() {
        boolean running = true;
        while (running) {
            displayAdminMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
                case "5":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-5.");
            }
        }
    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    private static void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    private static void addRoom() {
        try {
            System.out.print("Enter room number: ");
            String roomNumber = scanner.nextLine().trim();
            if (roomNumber.isEmpty()) {
                System.out.println("Room number cannot be empty.");
                return;
            }

            System.out.print("Enter price per night: ");
            double price = Double.parseDouble(scanner.nextLine().trim());
            if (price < 0) {
                System.out.println("Price cannot be negative.");
                return;
            }

            System.out.print("Enter room type (1 = SINGLE, 2 = DOUBLE): ");
            String typeInput = scanner.nextLine().trim();
            RoomType roomType;
            if (typeInput.equals("1")) {
                roomType = RoomType.SINGLE;
            } else if (typeInput.equals("2")) {
                roomType = RoomType.DOUBLE;
            } else {
                System.out.println("Invalid room type. Enter 1 or 2.");
                return;
            }

            IRoom room = (price == 0) ? new FreeRoom(roomNumber, roomType)
                    : new Room(roomNumber, price, roomType);

            List<IRoom> roomList = new ArrayList<>();
            roomList.add(room);
            adminResource.addRoom(roomList);
            System.out.println("Room added successfully!");

            System.out.print("Add another room? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                addRoom();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Please enter a numeric value.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
