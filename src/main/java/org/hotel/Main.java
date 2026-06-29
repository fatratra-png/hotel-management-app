package org.hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        var hotel = seedHotel();
        var scanner = new Scanner(System.in);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║        STINKY SOCKS HOTEL                ║");
        System.out.println("║        Faaaaaaaaaaaaaaaah                ║");
        System.out.println("╚══════════════════════════════════════════╝");

        while (true) {
            System.out.println("\n── MENU ──");
            System.out.println(" 1.  Hotel information");
            System.out.println(" 2.  List available rooms");
            System.out.println(" 3.  List all rooms");
            System.out.println(" 4.  Make a reservation");
            System.out.println(" 5.  View reservations");
            System.out.println(" 6.  Exit");
            System.out.print("Choice : ");

            var choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showHotelInfo(hotel);
                case "2" -> showAvailableRooms(hotel);
                case "3" -> showAllRooms(hotel);
                case "4" -> makeReservation(hotel, scanner);
                case "5" -> showReservations(hotel);
                case "6" -> {
                    System.out.println("\nThank you and goodbye !");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static Hotel seedHotel() {
        var hotel = new Hotel("Grand Hotel Palace", "Paris", "12 Rue de la Paix", "+33 1 23 45 67 89", 5, 20, null);
        for (var i = 0; i < 4; i++) hotel.addRoom(new StandardRoom());
        for (var i = 0; i < 3; i++) hotel.addRoom(new MidRoom());
        hotel.addRoom(new LuxuryRoom());
        return hotel;
    }

    private static String roomType(Room room) {
        return switch (room) {
            case StandardRoom r -> "Standard";
            case MidRoom r     -> "Mid-range";
            case LuxuryRoom r  -> "Luxury";
            default            -> "Unknown";
        };
    }

    private static String roomFeatures(Room room) {
        var sb = new StringBuilder(roomType(room));
        if (room.isHasSeaView()) sb.append(" [Sea view]");
        if (room.isHasAC()) sb.append(" [AC]");
        return sb.toString();
    }

    private static void showHotelInfo(Hotel hotel) {
        System.out.println("\n── HOTEL INFORMATION ──");
        System.out.println("Name     : " + hotel.getName());
        System.out.println("Location : " + hotel.getLocation());
        System.out.println("Stars    : " + "*".repeat(hotel.getStarCount()));
        System.out.println("Rooms    : " + hotel.getRooms().size());
    }

    private static void showAvailableRooms(Hotel hotel) {
        var rooms = hotel.getAvailableRooms();
        if (rooms.isEmpty()) {
            System.out.println("\nNo rooms available.");
            return;
        }
        System.out.println("\n── AVAILABLE ROOMS ──");
        for (var room : rooms) {
            System.out.println("  • " + roomFeatures(room));
        }
    }

    private static void showAllRooms(Hotel hotel) {
        var rooms = hotel.getRooms();
        if (rooms.isEmpty()) {
            System.out.println("\nNo rooms in the hotel.");
            return;
        }
        System.out.println("\n── ALL ROOMS ──");
        for (var room : rooms) {
            System.out.println("  • " + roomFeatures(room)
                + (room.isOccupied() ? " [OCCUPIED]" : " [FREE]"));
        }
    }

    private static void makeReservation(Hotel hotel, Scanner scanner) {
        System.out.println("\n── NEW RESERVATION ──");

        System.out.print("Guest name : ");
        var guestName = scanner.nextLine().trim();
        if (guestName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        var guest = new Guest("G" + System.currentTimeMillis(), guestName);
        hotel.register(guest);

        var rooms = List.copyOf(hotel.getRooms());
        if (rooms.isEmpty()) {
            System.out.println("No rooms in the hotel.");
            return;
        }

        System.out.println("\nRooms :");
        for (var i = 0; i < rooms.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + roomFeatures(rooms.get(i)));
        }

        var roomIndex = readInt(scanner, "\nChoose a room (1-" + rooms.size() + ") : ", 1, rooms.size()) - 1;
        var selectedRoom = rooms.get(roomIndex);

        var checkIn = readDate(scanner, "Check-in date (dd/MM/yyyy) : ");
        if (checkIn == null) return;

        var nights = readInt(scanner, "Number of nights : ", 1, 365);
        var checkOut = checkIn.plusDays(nights);

        if (!hotel.isRoomAvailable(selectedRoom, checkIn, checkOut)) {
            System.out.println("\n✗ This room is not available from "
                + checkIn.format(DATE_FMT) + " to " + checkOut.format(DATE_FMT) + ".");
            return;
        }

        System.out.println("\n── SUMMARY ──");
        System.out.println("Guest     : " + guestName);
        System.out.println("Room      : " + roomFeatures(selectedRoom));
        System.out.println("Check-in  : " + checkIn.format(DATE_FMT));
        System.out.println("Check-out : " + checkOut.format(DATE_FMT));
        System.out.println("Nights    : " + nights);

        if (!confirm(scanner, "Confirm reservation ? (y/N) : ")) {
            System.out.println("Reservation cancelled.");
            return;
        }

        hotel.reserveRoom(guest, selectedRoom, checkIn, checkOut);
        System.out.println("\n✓ RESERVATION CONFIRMED !");
        System.out.println("Welcome " + guestName + ", enjoy your stay at " + hotel.getName() + ".");
    }

    private static void showReservations(Hotel hotel) {
        var reservations = hotel.getReservations();
        if (reservations.isEmpty()) {
            System.out.println("\nNo reservations.");
            return;
        }
        System.out.println("\n── RESERVATIONS ──");
        for (var r : reservations) {
            System.out.println("  • " + r.getGuest().getName()
                + " → " + roomType(r.getRoom())
                + " | " + r.getCheckIn().format(DATE_FMT)
                + " → " + r.getCheckOut().format(DATE_FMT));
        }
    }

    private static int readInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                var val = Integer.parseInt(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
            } catch (NumberFormatException ignored) {}
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    private static LocalDate readDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                var date = LocalDate.parse(scanner.nextLine().trim(), DATE_FMT);
                if (!date.isBefore(LocalDate.now())) return date;
                System.out.println("Date must be today or in the future.");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Use dd/MM/yyyy.");
            }
        }
    }

    private static boolean confirm(Scanner scanner, String prompt) {
        System.out.print(prompt);
        var input = scanner.nextLine().trim().toLowerCase();
        return input.equals("y") || input.equals("yes");
    }
}
