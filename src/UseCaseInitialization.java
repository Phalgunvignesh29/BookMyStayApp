import java.util.*;

// =============================
// CLASS - Service (Add-On)
// =============================
class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }
}

// =============================
// CLASS - AddOnServiceManager
// =============================
class AddOnServiceManager {

    // Map: Reservation ID → List of Services
    private Map<String, List<Service>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    // Add service to a reservation
    public void addService(String reservationId, Service service) {
        serviceMap
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
    }

    // Calculate total cost of services
    public double calculateTotalServiceCost(String reservationId) {

        List<Service> services = serviceMap.get(reservationId);

        double total = 0;

        if (services != null) {
            for (Service s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

// =============================
// MAIN CLASS
// =============================
public class UseCaseInitialization {

    public static void main(String[] args) {

        System.out.println("Add-On Service Selection\n");

        // Example reservation ID (from previous use case)
        String reservationId = "Single-1";

        // Create services
        Service breakfast = new Service("Breakfast", 500);
        Service spa = new Service("Spa", 1000);

        // Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Add services
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, spa);

        // Display result
        System.out.println("Reservation ID: " + reservationId);

        double totalCost = manager.calculateTotalServiceCost(reservationId);

        System.out.println("Total Add-On Cost: " + totalCost);
    }
}
