import java.util.*;

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
    }

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    public int getAvailableRooms(String type) {
        return availability.getOrDefault(type, 0);
    }

    // UPDATE inventory after allocation
    public void decrementRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// =============================
// CLASS - RoomAllocationService
// =============================
class RoomAllocationService {

    // To prevent duplicate room IDs
    private Set<String> allocatedRoomIds;

    // Track rooms by type
    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    // Allocate room
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String roomType = reservation.getRoomType();

        // Check availability
        if (inventory.getAvailableRooms(roomType) <= 0) {
            System.out.println("No rooms available for " + roomType);
            return;
        }

        // Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Store allocated ID
        allocatedRoomIds.add(roomId);

        assignedRoomsByType
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        // Update inventory
        inventory.decrementRoom(roomType);

        // Confirmation
        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName()
                + ", Room ID: " + roomId);
    }

    // Generate unique ID
    private String generateRoomId(String roomType) {

        int count = assignedRoomsByType
                .getOrDefault(roomType, new HashSet<>())
                .size() + 1;

        return roomType + "-" + count;
    }
}

// =============================
// MAIN CLASS
// =============================
public class UseCaseInitialization {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        // Inventory setup
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 2);
        inventory.addRoom("Double", 2);
        inventory.addRoom("Suite", 1);

        // Booking requests (FIFO simulation)
        Queue<Reservation> queue = new LinkedList<>();
        queue.offer(new Reservation("Abhi", "Single"));
        queue.offer(new Reservation("Subha", "Single"));
        queue.offer(new Reservation("Vannathi", "Suite"));

        // Allocation service
        RoomAllocationService allocator = new RoomAllocationService();

        // Process queue
        while (!queue.isEmpty()) {
            Reservation r = queue.poll();
            allocator.allocateRoom(r, inventory);
        }
    }
}