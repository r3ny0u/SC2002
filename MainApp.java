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
