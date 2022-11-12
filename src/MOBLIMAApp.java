import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Base64;
import java.util.Base64.Encoder;

import Model.*;
import Database.*;
import Misc.Password;

public class MOBLIMAApp {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        // =========================== START OF PROGRAM ============================
        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("==================== Welcome to MOBLIMA ====================");
            System.out.println("======================== Login Menu ========================");
            System.out.println("Enter login details");
            System.out.println(" - (type 'exit' to quit MOBLIMA login page)");
            System.out.println(" - (type 'new' to create new customer account)");

            // ============================== LOGIN MENU ===============================
            // Login Menu for both Admin and Customer
            String username = "INeedADefaultValueOtherwiseGotCompilationError",
                    password = "INeedADefaultValueOtherwiseGotCompilationError",
                    encodedPassword = "";
            boolean isAdmin = false;
            boolean isLoggedIn = false;
            boolean isCreateNewAccount = false;
            

            while (true) {
                System.out.print("Username : ");
                username = scanner.nextLine();

                // Check user exit program
                if (username.toLowerCase().compareTo("exit") == 0) {
                    System.out.print("\033[2K");
                    System.out.println("Bye bye... °^°");
                    break;
                }

                // Check user create new customer account
                if (username.toLowerCase().compareTo("new") == 0) {
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    CustomerDB.addNewCustomerAccount();
                    isCreateNewAccount = true;
                    break;
                }

                password = Password.readPassword("Password : ");

                Encoder encoder = Base64.getEncoder();
                encodedPassword = encoder.encodeToString(password.getBytes());
                
                if (Admin.checkPassword(username, encodedPassword)) {
                    isAdmin = true;
                    isLoggedIn = true;
                    break;
                } else if (Customer.checkPassword(username, encodedPassword)) {
                    isAdmin = false;
                    isLoggedIn = true;
                    break;
                } else {
                    System.out.print("Invalid username, or password. Please try again >_<\n");
                    System.out.print(String.format("\033[2A")); // Move up 2
                    System.out.print("\033[2K"); // Erase line content
                    System.out.print(String.format("\033[1A")); // Move up 1
                    System.out.print("\033[2K"); // Erase line content
                }
            }

            // Print welcome message in CYAN, sleep for 2 second(s), and then clear screen
            // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
            // Not necessary need colours i just think they're neat
            if (isLoggedIn)
                System.out.println("\n\u001B[36mWelcome ~~ " + username.toUpperCase() + " ~~ :D\n\u001B[0m");

            try {
                TimeUnit.MILLISECONDS.sleep(1250);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.print("\033[H\033[2J");
            System.out.flush();

            // =======================================================================

            // =========================== USER MENU =================================
            // if isAdmin, do admin stuff, e.g. print edmin menu and stuff,
            // else, do customer stuff, e.g. check movie listing thing

            if (isAdmin && isLoggedIn) {
                Admin admin = AdminDB.getAdminFromUsername(username, encodedPassword);

                admin.adminMenu();

            } else if (!isAdmin && isLoggedIn) {
                Customer customer = CustomerDB.getCustomerFromUsername(username, encodedPassword);

                customer.customerMenu();

            } else if (isCreateNewAccount) {
                continue;

            } else if (!isLoggedIn) {
                // Break program if user is not logged in anymore
                break;
            }
            // ========================================================================

        } while (true);
        // END OF PROGRAM
    }
}

// Customer username and password:
// jason
// Jason1!
// Admin username and password
// admin
// password123
// For more usernames and password, check out CustomerDB.txt and AdminDB.txt
