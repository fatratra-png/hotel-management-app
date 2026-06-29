package org.hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        var hotel = new Hotel("Grand Hôtel Palace", "Paris", "12 Rue de la Paix", "+33123456789", 5, 20, null);
        var scanner = new Scanner(System.in);

        for (int i = 0; i < 4; i++) hotel.addRoom(new StandardRoom());
        for (int i = 0; i < 3; i++) hotel.addRoom(new MidRoom());
        hotel.addRoom(new LuxuryRoom());

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║    BIENVENUE AU GRAND HÔTEL PALACE   ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (true) {
            System.out.println("\n── MENU PRINCIPAL ──");
            System.out.println("1.  Voir les chambres disponibles");
            System.out.println("2.  Réserver une chambre");
            System.out.println("3.  Quitter");
            System.out.print("Votre choix : ");

            var choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> showAvailableRooms(hotel);
                case "2" -> reserveRoomCLI(hotel, scanner);
                case "3" -> {
                    System.out.println("\nMerci et à bientôt !");
                    return;
                }
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private static void showAvailableRooms(Hotel hotel) {
        var rooms = hotel.getAvailableRooms();
        if (rooms.isEmpty()) {
            System.out.println("Aucune chambre disponible pour le moment.");
            return;
        }
        System.out.println("\n── CHAMBRES DISPONIBLES ──");
        for (var room : rooms) {
            var type = switch (room) {
                case StandardRoom r -> "Standard";
                case MidRoom r -> "Moyenne";
                case LuxuryRoom r -> "Luxe";
                default -> "Inconnue";
            };
            System.out.println("  • " + type + (room.isHasSeaView() ? " (vue mer)" : "") + (room.isHasAC() ? " [Clim]" : ""));
        }
    }

    private static void reserveRoomCLI(Hotel hotel, Scanner scanner) {
        System.out.println("\n── NOUVELLE RÉSERVATION ──");

        System.out.print("Nom du client : ");
        var guestName = scanner.nextLine().trim();
        if (guestName.isEmpty()) {
            System.out.println("Nom invalide.");
            return;
        }
        var guestId = "G" + System.currentTimeMillis();
        var guest = new Guest(guestId, guestName);
        hotel.register(guest);

        var rooms = List.copyOf(hotel.getRooms());
        if (rooms.isEmpty()) {
            System.out.println("Aucune chambre dans l'hôtel.");
            return;
        }

        System.out.println("\nChambres disponibles :");
        for (int i = 0; i < rooms.size(); i++) {
            var room = rooms.get(i);
            var type = switch (room) {
                case StandardRoom r -> "Standard";
                case MidRoom r -> "Moyenne";
                case LuxuryRoom r -> "Luxe";
                default -> "Inconnue";
            };
            System.out.println("  " + (i + 1) + ". " + type + (room.isHasSeaView() ? " (vue mer)" : ""));
        }

        int roomIndex;
        while (true) {
            System.out.print("Choisissez une chambre (1-" + rooms.size() + ") : ");
            try {
                roomIndex = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (roomIndex >= 0 && roomIndex < rooms.size()) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("Choix invalide.");
        }
        var selectedRoom = rooms.get(roomIndex);

        LocalDate checkIn;
        while (true) {
            System.out.print("Date d'arrivée (jj/mm/aaaa) : ");
            try {
                checkIn = LocalDate.parse(scanner.nextLine().trim(), DATE_FMT);
                if (!checkIn.isBefore(LocalDate.now())) break;
                System.out.println("La date doit être aujourd'hui ou dans le futur.");
            } catch (DateTimeParseException e) {
                System.out.println("Format invalide. Utilisez jj/mm/aaaa.");
            }
        }

        int nights;
        while (true) {
            System.out.print("Nombre de nuits : ");
            try {
                nights = Integer.parseInt(scanner.nextLine().trim());
                if (nights > 0) break;
            } catch (NumberFormatException ignored) {}
            System.out.println("Nombre invalide.");
        }
        var checkOut = checkIn.plusDays(nights);

        if (!hotel.isRoomAvailable(selectedRoom, checkIn, checkOut)) {
            System.out.println("\n✗ Cette chambre n'est pas disponible du " + checkIn.format(DATE_FMT)
                + " au " + checkOut.format(DATE_FMT) + ".");
            System.out.println("Veuillez choisir d'autres dates ou une autre chambre.");
            return;
        }

        System.out.println("\n── RÉCAPITULATIF ──");
        System.out.println("Client    : " + guestName);
        System.out.println("Chambre   : " + switch (selectedRoom) {
            case StandardRoom r -> "Standard";
            case MidRoom r -> "Moyenne";
            case LuxuryRoom r -> "Luxe";
            default -> "Inconnue";
        });
        System.out.println("Arrivée   : " + checkIn.format(DATE_FMT));
        System.out.println("Départ    : " + checkOut.format(DATE_FMT));
        System.out.println("Nuits     : " + nights);

        System.out.print("Confirmer la réservation ? (o/N) : ");
        var confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("o") && !confirm.equals("oui")) {
            System.out.println("Réservation annulée.");
            return;
        }

        try {
            hotel.reserveRoom(guest, selectedRoom, checkIn, checkOut);
            System.out.println("\n✓ RÉSERVATION EFFECTUÉE !");
            System.out.println("Merci " + guestName + ", votre séjour au Grand Hôtel Palace vous attend.");
        } catch (Exception e) {
            System.out.println("\n✗ Erreur lors de la réservation : " + e.getMessage());
        }
    }
}
