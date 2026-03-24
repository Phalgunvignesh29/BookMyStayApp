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
// CLASS - BookingHistory
// =============================
class BookingHistory {

    // List to store confirmed reservations
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    // Add reservation to history
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    // Get all reservations
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

// =============================
// CLASS - BookingReportService
// =============================
class BookingReportService {

    // Generate report
    public void generateReport(BookingHistory history) {

        System.out.println("Booking History Report\n");

        for (Reservation r : history.getConfirmedReservations()) {
            System.out.println(
                    "Guest: " + r.getGuestName() +
                            ", Room Type: " + r.getRoomType()
            );
        }
    }
}

// =============================
// MAIN CLASS
// =============================
public class UseCaseInitialization {

    public static void main(String[] args) {

        System.out.println("Booking History and Reporting\n");

        // Create booking history
        BookingHistory history = new BookingHistory();

        // Add confirmed reservations
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vannathi", "Suite"));

        // Generate report
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}
