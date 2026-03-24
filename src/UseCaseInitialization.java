import java.io.*;
import java.util.*;

// =============================
// CLASS - RoomInventory
// =============================
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
    }

    public void setRoom(String type, int count) {
        availability.put(type, count);
    }

    public Map<String, Integer> getAll() {
        return availability;
    }
}

// =============================
// CLASS - FilePersistenceService
// =============================
class FilePersistenceService {

    // Save inventory to file
    public void saveInventory(RoomInventory inventory, String filePath) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue());
                writer.newLine();
            }

            System.out.println("Inventory saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    // Load inventory from file
    public void loadInventory(RoomInventory inventory, String filePath) {

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("No valid inventory data found. Starting fresh.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    inventory.setRoom(parts[0], Integer.parseInt(parts[1]));
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading inventory.");
        }
    }
}

// =============================
// MAIN CLASS
// =============================
public class UseCaseInitialization {

    public static void main(String[] args) {

        System.out.println("System Recovery\n");

        String filePath = "inventory.txt";

        RoomInventory inventory = new RoomInventory();
        FilePersistenceService service = new FilePersistenceService();

        // Load previous state
        service.loadInventory(inventory, filePath);

        // If empty → initialize default
        if (inventory.getAll().isEmpty()) {
            inventory.setRoom("Single", 5);
            inventory.setRoom("Double", 3);
            inventory.setRoom("Suite", 2);
        }

        // Display inventory
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.getAll().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Save state
        service.saveInventory(inventory, filePath);
    }
}
