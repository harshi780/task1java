import java.util.*;

class User {
    private String username;
    private String password;
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}

class Reservation {
    private String trainNumber;
    private String trainName;
    private String classType;
    private String from;
    private String to;
    private Date journeyDate;
    private static int pnrCounter = 1000;
    private int pnr;

    public Reservation(String trainNumber, String trainName, String classType, String from, String to, Date journeyDate) {
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.from = from;
        this.to = to;
        this.journeyDate = journeyDate;
        this.pnr = pnrCounter++;
    }
    
    public int getPnr() {
        return pnr;
    }

    @Override
    public String toString() {
        return "PNR: " + pnr + ", Train: " + trainName + " (" + trainNumber + "), Class: " + classType +
               ", From: " + from + ", To: " + to + ", Date: " + journeyDate;
    }
}

class ReservationSystem {
    private Map<Integer, Reservation> reservations = new HashMap<>();
    
    public Reservation makeReservation(String trainNumber, String trainName, String classType, String from, String to, Date journeyDate) {
        Reservation reservation = new Reservation(trainNumber, trainName, classType, from, to, journeyDate);
        reservations.put(reservation.getPnr(), reservation);
        System.out.println("Reservation made successfully! PNR: " + reservation.getPnr());
        return reservation;
    }
    
    public void cancelReservation(int pnr) {
        if (reservations.containsKey(pnr)) {
            Reservation reservation = reservations.remove(pnr);
            System.out.println("Reservation cancelled for PNR: " + reservation.getPnr());
        } else {
            System.out.println("No reservation found with PNR: " + pnr);
        }
    }
}

public class OnlineReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User user = new User("user123", "pass123");
        ReservationSystem reservationSystem = new ReservationSystem();

        // Login Process
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (!user.login(username, password)) {
            System.out.println("Invalid login credentials!");
            return;
        }
        
        System.out.println("Login successful!");

        // Reservation Process
        System.out.print("Enter train number: ");
        String trainNumber = scanner.nextLine();
        System.out.print("Enter train name: ");
        String trainName = scanner.nextLine();
        System.out.print("Enter class type: ");
        String classType = scanner.nextLine();
        System.out.print("Enter journey from: ");
        String from = scanner.nextLine();
        System.out.print("Enter journey to: ");
        String to = scanner.nextLine();
        System.out.print("Enter journey date (dd-mm-yyyy): ");
        String dateInput = scanner.nextLine();

        // Assuming date parsing (for simplicity, not handling exceptions here)
        Date journeyDate = new GregorianCalendar(
            Integer.parseInt(dateInput.split("-")[2]),
            Integer.parseInt(dateInput.split("-")[1]) - 1,
            Integer.parseInt(dateInput.split("-")[0])
        ).getTime();

        Reservation reservation = reservationSystem.makeReservation(trainNumber, trainName, classType, from, to, journeyDate);

        // Cancellation Process
        System.out.print("Enter PNR to cancel reservation: ");
        int pnr = scanner.nextInt();
        reservationSystem.cancelReservation(pnr);
    }
}
