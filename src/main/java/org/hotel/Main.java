package org.hotel;

import org.hotel.order.OrderItem;
import org.hotel.payment.Invoice;
import org.hotel.payment.PaymentMethod;

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
            System.out.println(" 6.  View invoice");
            System.out.println(" 7.  Make payment");
            System.out.println(" 8.  Exit");
            System.out.print("Choice : ");

            var choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showHotelInfo(hotel);
                case "2" -> showAvailableRooms(hotel);
                case "3" -> showAllRooms(hotel);
                case "4" -> makeReservation(hotel, scanner);
                case "5" -> showReservations(hotel);
                case "6" -> showInvoice(hotel, scanner);
                case "7" -> makePayment(hotel, scanner);
                case "8" -> {
                    System.out.println("\nThank you and goodbye !");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static Hotel seedHotel() {
        var hotel = new Hotel("Stinky Socks hotel", "Ivandry", "Rue Zafy Albert", "+216 38 61 792 94", 5, 20);
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
        sb.append(" — $").append(String.format("%.0f", room.getNightlyRate())).append("/night");
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
                + " → " + r.getCheckOut().format(DATE_FMT)
                + " | " + r.getNights() + " nights"
                + " | Total: $" + String.format("%.2f", r.getInvoice().getTotal()));
        }
    }

    private static void showInvoice(Hotel hotel, Scanner scanner) {
        var guest = selectGuest(hotel, scanner);
        if (guest == null) return;

        var invoice = hotel.getInvoiceFor(guest);
        if (invoice == null) {
            System.out.println("\nNo invoice found for this guest.");
            return;
        }

        System.out.println("\n── INVOICE ──");
        System.out.println("Guest       : " + guest.getName());
        System.out.println("Room charges: $" + String.format("%.2f", invoice.getRoomCharges()));
        System.out.println("Service chrg: $" + String.format("%.2f", invoice.getServiceCharges()));
        System.out.println("Total       : $" + String.format("%.2f", invoice.getTotal()));
        System.out.println("Paid        : $" + String.format("%.2f", invoice.getTotalPaid()));
        System.out.println("Outstanding : $" + String.format("%.2f", invoice.getOutstandingBalance()));
        System.out.println("Status      : " + (invoice.isFullyPaid() ? "PAID" : "UNPAID"));
    }

    private static void makePayment(Hotel hotel, Scanner scanner) {
        var guest = selectGuest(hotel, scanner);
        if (guest == null) return;

        var invoice = hotel.getInvoiceFor(guest);
        if (invoice == null) {
            System.out.println("\nNo invoice found for this guest.");
            return;
        }

        if (invoice.isFullyPaid()) {
            System.out.println("\nInvoice is already fully paid.");
            return;
        }

        System.out.println("\n── PAYMENT ──");
        System.out.println("Outstanding: $" + String.format("%.2f", invoice.getOutstandingBalance()));
        System.out.println("Methods:");
        var methods = PaymentMethod.values();
        for (var i = 0; i < methods.length; i++) {
            System.out.println("  " + (i + 1) + ". " + methods[i].name());
        }

        var methodIdx = readInt(scanner, "Choose method (1-" + methods.length + ") : ", 1, methods.length) - 1;
        var method = methods[methodIdx];

        var amount = readDouble(scanner, "Amount to pay ($) : ", 0.01, invoice.getOutstandingBalance());

        try {
            hotel.payInvoice(guest, amount, method);
            System.out.println("\n✓ Payment of $" + String.format("%.2f", amount)
                + " via " + method.name() + " successful!");
        } catch (Exception e) {
            System.out.println("\n✗ Payment failed: " + e.getMessage());
        }
    }

    private static Guest selectGuest(Hotel hotel, Scanner scanner) {
        var reservations = hotel.getReservations();
        if (reservations.isEmpty()) {
            System.out.println("\nNo guests with reservations.");
            return null;
        }
        System.out.println("\n── GUESTS ──");
        for (var i = 0; i < reservations.size(); i++) {
            var r = reservations.get(i);
            System.out.println("  " + (i + 1) + ". " + r.getGuest().getName()
                + " (" + roomType(r.getRoom()) + ")");
        }
        var idx = readInt(scanner, "Select guest (1-" + reservations.size() + ") : ", 1, reservations.size()) - 1;
        return reservations.get(idx).getGuest();
    }

    private static double readDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                var val = Double.parseDouble(scanner.nextLine().trim());
                if (val >= min && val <= max) return val;
            } catch (NumberFormatException ignored) {}
            System.out.println("Please enter a number between " + min + " and " + max + ".");
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
