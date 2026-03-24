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
// CLASS - BookingRequestQueue
// =============================
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
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
        availability.put("Double", 2);
        availability.put("Suite", 1);
    }

    public int getAvailableRooms(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrementRoom(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public Map<String, Integer> getAll() {
        return availability;
    }
}

// =============================
// CLASS - RoomAllocationService
// =============================
class RoomAllocationService {

    private Map<String, Integer> countMap = new HashMap<>();

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {

        String type = reservation.getRoomType();

        if (inventory.getAvailableRooms(type) <= 0) {
            return;
        }

        int count = countMap.getOrDefault(type, 0) + 1;
        countMap.put(type, count);

        String roomId = type + "-" + count;

        inventory.decrementRoom(type);

        System.out.println("Booking confirmed for Guest: "
                + reservation.getGuestName()
                + ", Room ID: " + roomId);
    }
}

// =============================
// CLASS - Concurrent Processor
// =============================
class ConcurrentBookingProcessor implements Runnable {

    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService) {

        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }

    @Override
    public void run() {

        while (true) {

            Reservation reservation;

            // Synchronize queue access
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) {
                    break;
                }
                reservation = bookingQueue.getNextRequest();
            }

            // Synchronize inventory allocation
            synchronized (inventory) {
                allocationService.allocateRoom(reservation, inventory);
            }
        }
    }
}

// =============================
// MAIN CLASS
// =============================
public class UseCaseInitialization {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        // Shared resources
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Add requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vannathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kunal", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        // Threads
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService));

        // Start threads
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        // Final inventory
        System.out.println("\nRemaining Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

