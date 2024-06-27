import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Room class
class Room {
    private int roomNumber;
    private String type;
    private double price;
    private boolean isAvailable;

    // Constructor
    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.isAvailable = true; // Room is initially available
    }

    // Getters
    public int getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    // Method to check availability
    public boolean isAvailable() {
        return isAvailable;
    }

    // Method to book the room
    public void bookRoom() {
        if (isAvailable) {
            isAvailable = false;
            System.out.println("Room " + roomNumber + " booked successfully.");
        } else {
            System.out.println("Room " + roomNumber + " is not available.");
        }
    }
}

// Hotel class
class Hotel {
    private List<Room> rooms;

    // Constructor
    public Hotel() {
        this.rooms = new ArrayList<>();
        initializeRooms(); // Initialize rooms for the hotel
    }

    // Method to initialize rooms (dummy data for demonstration)
    private void initializeRooms() {
        rooms.add(new Room(101, "Single", 100.0));
        rooms.add(new Room(102, "Double", 150.0));
        rooms.add(new Room(103, "Suite", 250.0));
        // Add more rooms as needed
    }

    // Method to search for available rooms by type
    public List<Room> searchAvailableRooms(String type) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getType().equalsIgnoreCase(type)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // Method to book a room
    public void bookRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                room.bookRoom();
                return;
            }
        }
        System.out.println("Room " + roomNumber + " not found or already booked.");
    }
}

// Reservation class
class Reservation {
    private int reservationId;
    private String guestName;
    private Room bookedRoom;
    private boolean isPaid;

    // Constructor
    public Reservation(int reservationId, String guestName, Room bookedRoom) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.bookedRoom = bookedRoom;
        this.isPaid = false; // Reservation is initially unpaid
    }

    // Method to mark reservation as paid
    public void markAsPaid() {
        isPaid = true;
        System.out.println("Reservation " + reservationId + " has been paid.");
    }

    // Method to display reservation details
    public void displayDetails() {
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Guest Name: " + guestName);
        System.out.println("Room Number: " + bookedRoom.getRoomNumber());
        System.out.println("Room Type: " + bookedRoom.getType());
        System.out.println("Total Price: $" + bookedRoom.getPrice());
        System.out.println("Payment Status: " + (isPaid ? "Paid" : "Pending"));
    }
}

// Main class
public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Pay for Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    searchAvailableRooms(hotel, scanner);
                    break;
                case 2:
                    bookRoom(hotel, scanner);
                    break;
                case 3:
                    payForReservation(scanner);
                    break;
                case 4:
                    viewBookingDetails(scanner);
                    break;
                case 5:
                    System.out.println("Exiting the program...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    private static void searchAvailableRooms(Hotel hotel, Scanner scanner) {
        System.out.print("Enter room type (Single/Double/Suite): ");
        String roomType = scanner.nextLine();

        List<Room> availableRooms = hotel.searchAvailableRooms(roomType);
        if (availableRooms.isEmpty()) {
            System.out.println("No available rooms of type " + roomType + " found.");
        } else {
            System.out.println("Available " + roomType + " Rooms:");
            for (Room room : availableRooms) {
                System.out.println("- Room " + room.getRoomNumber() + ", $" + room.getPrice());
            }
        }
    }

    private static void bookRoom(Hotel hotel, Scanner scanner) {
        System.out.print("Enter room number to book: ");
        int roomNumber = scanner.nextInt();
        hotel.bookRoom(roomNumber);
    }

    private static void payForReservation(Scanner scanner) {
        // Implementation can be added based on Reservation class
        System.out.println("Payment feature is under development.");
    }

    private static void viewBookingDetails(Scanner scanner) {
        // Implementation can be added based on Reservation class
        System.out.println("View booking details feature is under development.");
    }
}
