import java.util.*;

class Room {
    int num;
    String type;
    boolean booked;

    Room(int n, String t) {
        num = n;
        type = t;
        booked = false;
    }
}

class Booking {
    String name;
    int room;
    String type;
    double price;

    Booking(String name, int room, String type, double price) {
        this.name = name;
        this.room = room;
        this.type = type;
        this.price = price;
    }
}

public class HotelSystem {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));

        int ch = 0;

        while(ch != 5) {
            System.out.println("\n--- HOTEL MENU ---");
            System.out.println("1. Show available rooms");
            System.out.println("2. Book room");
            System.out.println("3. Cancel booking");
            System.out.println("4. Show all bookings");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            ch = sc.nextInt();
            sc.nextLine();

            if(ch == 1) {
                showRooms();
            }
            else if(ch == 2) {
                System.out.print("Enter your name: ");
                String name = sc.nextLine();
                System.out.print("Room type (Standard/Deluxe/Suite): ");
                String t = sc.nextLine();
                bookRoom(name, t);
            }
            else if(ch == 3) {
                System.out.print("Enter your name to cancel: ");
                String name = sc.nextLine();
                cancelBooking(name);
            }
            else if(ch == 4) {
                showAllBookings();
            }
            else if(ch == 5) {
                System.out.println("Thanks for using system");
            }
            else {
                System.out.println("Invalid option");
            }
        }
    }

    static void showRooms() {
        System.out.println("\nAvailable Rooms:");
        boolean found = false;

        for(Room r : rooms) {
            if(!r.booked) {
                System.out.println("Room " + r.num + " (" + r.type + ")");
                found = true;
            }
        }

        if(!found) {
            System.out.println("No rooms free right now");
        }
    }

    static double getPrice(String t) {
        if(t.equalsIgnoreCase("Standard")) return 1500;
        if(t.equalsIgnoreCase("Deluxe")) return 2500;
        if(t.equalsIgnoreCase("Suite")) return 5000;
        return 0;
    }

    static void bookRoom(String name, String t) {
        Room free = null;

        for(Room r : rooms) {
            if(!r.booked && r.type.equalsIgnoreCase(t)) {
                free = r;
                break;
            }
        }

        if(free == null) {
            System.out.println("No empty rooms of this type");
            return;
        }

        double price = getPrice(t);
        System.out.println("Room found: " + free.num);
        System.out.println("Pay: " + price);
        System.out.println("Payment Done.");

        free.booked = true;
        bookList.add(new Booking(name, free.num, free.type, price));

        System.out.println("Room booked successfully.");
    }

    static void cancelBooking(String name) {
        Booking b = null;

        for(Booking x : bookList) {
            if(x.name.equalsIgnoreCase(name)) {
                b = x;
                break;
            }
        }

        if(b == null) {
            System.out.println("Booking not found.");
            return;
        }

        for(Room r : rooms) {
            if(r.num == b.room) {
                r.booked = false;
                break;
            }
        }

        bookList.remove(b);
        System.out.println("Booking cancelled.");
    }

    static void showAllBookings() {
        if(bookList.size() == 0) {
            System.out.println("No bookings yet.");
            return;
        }

        System.out.println("\nAll Bookings:");
        for(Booking b : bookList) {
            System.out.println(b.name + " -> Room " + b.room + " (" + b.type + ") Paid: " + b.price);
        }
    }
}
