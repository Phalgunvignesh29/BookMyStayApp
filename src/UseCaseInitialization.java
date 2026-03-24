import java.util.*;

// =============================
// CLASS - RoomInventory
// =============================
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 0); // as per output expectation
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }

    public void incrementRoom(String type) {
        availability.put(type, availability.get(type) + 1);
    }

    public int getAvailableRooms(String type) {
        return availability.getOrDefault(type, 0);
    }
}

// =============================
// CLASS - CancellationService
// =============================
class CancellationService {

    // Stack to track released room IDs (LIFO)
    private Stack<String> releasedRoomIds;

    // Map: Reservation ID → Room Type
    private Map<String, String> reservationRoomTypeMap;

    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }

    // Register confirmed booking
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }

    // Cancel booking
    public void cancelBooking(String reservationId, RoomInventory inventory) {

        // Validate reservation
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Invalid cancellation request.");
            return;
        }

        String roomType = reservationRoomTypeMap.get(reservationId);

        // Push to stack (rollback tracking)
        releasedRoomIds.push(reservationId);

        // Restore inventory
        inventory.incrementRoom(roomType);

        // Remove booking
        reservationRoomTypeMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    // Display rollback history
    public void showRollbackHistory() {

        System.out.println("\nRollback History (Most Recent First):");

        while (!releasedRoomIds.isEmpty()) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.pop());
        }
    }
}

// =============================
// MAIN CLASS
// =============================
public class UseCaseInitialization {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation\n");

        // Initialize
        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();

        // Register a booking (simulate previous allocation)
        service.registerBooking("Single-1", "Single");

        // Cancel booking
        service.cancelBooking("Single-1", inventory);

        // Show rollback history
        service.showRollbackHistory();

        // Show updated inventory
        System.out.println("\nUpdated Single Room Availability: "
                + inventory.getAvailableRooms("Single"));
    }
}
