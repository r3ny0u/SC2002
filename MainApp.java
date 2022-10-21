import java.util.Scanner;

public class MainApp {
    private int day;

    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to MOBLIMA.");
        System.out.println("Enter 1 for Staff and 2 for Customer");
        System.out.print("Enter choice: ");
        int choice = s.nextInt();
        switch (choice) {
            case 1: // Staff
                System.out.print("\nEnter AdminID: ");
                String adminID = s.next();
                System.out.print("\nEnter password: ");
                String pw = s.next();

                Admin admin = new Admin(adminID, pw);
                if (admin.checkPW() == 0)
                    System.out.println("Incorrect userID or password!");

                break;
            case 2: // Customer
                break;
            default:
                do {
                    System.out.println("Invalid. Try again.");
                    System.out.print("Enter choice: ");
                    choice = s.nextInt();
                } while (choice < 1 || choice > 2);
    
        }
    }
}
