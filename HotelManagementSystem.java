import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Room {
    int roomNo;
    String type;
    boolean booked;

    Room(int roomNo, String type) {
        this.roomNo = roomNo;
        this.type = type;
        this.booked = false;
    }
}

class Booking {
    String customerName;
    int roomNo;
    String type;
    double amount;

    Booking(String customerName, int roomNo, String type, double amount) {
        this.customerName = customerName;
        this.roomNo = roomNo;
        this.type = type;
        this.amount = amount;
    }
}

class Hotel {
    ArrayList<Room> rooms = new ArrayList<>();
    ArrayList<Booking> bookings = new ArrayList<>();

    Hotel() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
        rooms.add(new Room(302, "Suite"));
    }

    void showAvailable() {
        System.out.println("\nAvailable Rooms:");
        boolean any = false;
        for (Room r : rooms) {
            if (!r.booked) {
                System.out.println("Room " + r.roomNo + " - " + r.type);
                any = true;
            }
        }
        if (!any) {
            System.out.println("No rooms free.");
        }
    }

    double getPrice(String type) {
        if (type.equalsIgnoreCase("Standard")) return 1000;
        if (type.equalsIgnoreCase("Deluxe")) return 2000;
        if (type.equalsIgnoreCase("Suite")) return 4000;
        return 0;
    }

    void bookRoom(String name, String type) {
        Room freeRoom = null;
        for (Room r : rooms) {
            if (!r.booked && r.type.equalsIgnoreCase(type)) {
                freeRoom = r;
                break;
            }
        }
        if (freeRoom == null) {
            System.out.println("No " + type + " room free.");
            return;
        }

        double price = getPrice(type);
        if (price == 0) {
            System.out.println("Wrong room type.");
            return;
        }

        System.out.println("Room found: " + freeRoom.roomNo + " (" + freeRoom.type + ")");
        System.out.println("Amount to pay: " + price);
        System.out.println("Processing payment...");
        System.out.println("Payment successful.");

        freeRoom.booked = true;
        Booking b = new Booking(name, freeRoom.roomNo, freeRoom.type, price);
        bookings.add(b);
        System.out.println("Booking done for " + name + " in room " + freeRoom.roomNo);
        saveToFile();
    }

    void cancelBooking(String name) {
        Booking found = null;
        for (Booking b : bookings) {
            if (b.customerName.equalsIgnoreCase(name)) {
                found = b;
                break;
            }
        }
        if (found == null) {
            System.out.println("No booking found for this name.");
            return;
        }
        for (Room r : rooms) {
            if (r.roomNo == found.roomNo) {
                r.booked = false;
                break;
            }
        }
        bookings.remove(found);
        System.out.println("Booking cancelled for " + name);
        saveToFile();
    }

    void showBookings() {
        System.out.println("\nAll Bookings:");
        if (bookings.size() == 0) {
            System.out.println("No bookings yet.");
            return;
        }
        for (Booking b : bookings) {
            System.out.println(b.customerName + " -> Room " + b.roomNo + " (" + b.type + ") Paid: " + b.amount);
        }
    }

    void saveToFile() {
        try {
            FileWriter fw = new FileWriter("bookings.txt");
            for (Booking b : bookings) {
                fw.write(b.customerName + "," + b.roomNo + "," + b.type + "," + b.amount + "\n");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }
}

public class HotelManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel h = new Hotel();
        int ch;

        do {
            System.out.println("\n--- Hotel Menu ---");
            System.out.println("1. Search Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            ch = sc.nextInt();
            sc.nextLine();

            if (ch == 1) {
                h.showAvailable();
            } else if (ch == 2) {
                System.out.print("Enter your name: ");
                String name = sc.nextLine();
                System.out.print("Enter room type (Standard/Deluxe/Suite): ");
                String type = sc.nextLine();
                h.bookRoom(name, type);
            } else if (ch == 3) {
                System.out.print("Enter your name: ");
                String name = sc.nextLine();
                h.cancelBooking(name);
            } else if (ch == 4) {
                h.showBookings();
            } else if (ch == 5) {
                System.out.println("Goodbye.");
            } else {
                System.out.println("Wrong choice.");
            }

        } while (ch != 5);

        sc.close();
    }
}

