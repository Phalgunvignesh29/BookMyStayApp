import java.util.*;

// =============================
// DOMAIN MODEL - Room
// =============================
class Room {

    private int beds;
    private int size;
    private double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + price);
    }
}

// =============================
// INVENTORY (STATE HOLDER)
// =============================
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void addRoom(String type, int count) {
        availability.put(type, count);
    }

    // READ-ONLY ACCESS
    public Map<String, Integer> getRoomAvailability() {
        return availability;
    }
}

// =============================
// SEARCH SERVICE (READ-ONLY)
// =============================
class RoomSearchService {

    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        System.out.println("Room Search\n");

        // Get availability (READ-ONLY)
        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Single Room
        if (availability.get("Single") > 0) {
            System.out.println("Single Room:");
            singleRoom.displayDetails();
            System.out.println("Available: " + availability.get("Single"));
            System.out.println();
        }

        // Double Room
        if (availability.get("Double") > 0) {
            System.out.println("Double Room:");
            doubleRoom.displayDetails();
            System.out.println("Available: " + availability.get("Double"));
            System.out.println();
        }

        // Suite Room
        if (availability.get("Suite") > 0) {
            System.out.println("Suite Room:");
            suiteRoom.displayDetails();
            System.out.println("Available: " + availability.get("Suite"));
        }
    }
}

// =============================
// MAIN CLASS
// =============================
public class UseCaseInitialization {

    public static void main(String[] args) {

        // Create Rooms
        Room singleRoom = new Room(1, 250, 1500.0);
        Room doubleRoom = new Room(2, 400, 2500.0);
        Room suiteRoom = new Room(3, 750, 5000.0);

        // Create Inventory
        RoomInventory inventory = new RoomInventory();
        inventory.addRoom("Single", 5);
        inventory.addRoom("Double", 3);
        inventory.addRoom("Suite", 2);

        // Search Service
        RoomSearchService searchService = new RoomSearchService();

        // Perform Search
        searchService.searchAvailableRooms(
                inventory,
                singleRoom,
                doubleRoom,
                suiteRoom
        );
    }
}