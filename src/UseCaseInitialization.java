import java.util.*;

// =============================
// CUSTOM EXCEPTION
// =============================
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}

// =============================
// CLASS - Reservation
// =============================
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// =============================
// CLASS - RoomInventory
// =============================
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public boolean isValidRoomType(String roomType) {
        return availability.containsKey(roomType);
    }
}

// =============================
// CLASS - BookingRequestQueue
// =============================
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }
}

// =============================
// CLASS - ReservationValidator
// =============================
class ReservationValidator {

    public void validate(
            String guestName,
            String roomType,
            RoomInventory inventory) throws InvalidBookingException {

        // Validate guest name
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty");
        }

        // Validate room type (CASE SENSITIVE)
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

// =============================
// MAIN CLASS
// =============================
public class UseCaseInitialization{

    public static void main(String[] args) {

        System.out.println("Booking Validation\n");

        Scanner scanner = new Scanner(System.in);

        // Initialize components
        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        try {
            // Input
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String roomType = scanner.nextLine();

            // Validation
            validator.validate(name, roomType, inventory);

            // If valid → add to queue
            Reservation r = new Reservation(name, roomType);
            bookingQueue.addRequest(r);

            System.out.println("Booking request accepted!");

        } catch (InvalidBookingException e) {

            // Graceful error handling
            System.out.println("Booking failed: " + e.getMessage());

        } finally {
            scanner.close();
        }
    }
}
