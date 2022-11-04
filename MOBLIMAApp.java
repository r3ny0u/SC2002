import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Model.*;
import Database.*;
import DatabaseBoundary.*;

public class MOBLIMAApp {
    private int day;

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        // =========================== START OF PROGRAM ============================
        do {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            System.out.println("==================== Welcome to MOBLIMA ====================");
            System.out.println("======================== Login Menu ========================");
            System.out.println("Enter login details - (type exit to quit MOBLIMA login page)");

            // ============================== LOGIN MENU ===============================
            // Login Menu for both Admin and Customer
            String username = "INeedADefaultValueOtherwiseGotCompilationError",
                    password = "INeedADefaultValueOtherwiseGotCompilationError";
            boolean isAdmin = false;
            boolean isLoggedIn = false;

            while (true) {
                System.out.print("Username: ");
                username = scanner.nextLine();

                if (username.toLowerCase().compareTo("exit") == 0) {
                    System.out.print("\033[2K"); // Erase line content
                    System.out.println("Bye bye... °^°");
                    break;
                }

                System.out.print("Password: ");
                password = scanner.nextLine();

                if (Admin.checkPassword(username, password)) {
                    isAdmin = true;
                    isLoggedIn = true;
                    break;
                } else if (Customer.checkPassword(username, password)) {
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
                System.out.println("\n\u001B[36mWelcome ~~ " + username + " ~~ :D\n\u001B[0m");

            try {
                TimeUnit.MILLISECONDS.sleep(1500);
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
                Admin admin = AdminDB.getAdminFromUsername(username, password);

                admin.adminMenu();
                // Other Admin stuff

            } else if (!isAdmin && isLoggedIn) {
                Customer customer = CustomerDB.getCustomerFromUsername(username, password);
                // Other Customer stuff
                System.out.println("Customer Menu:");

            } else if (!isLoggedIn) {
                // Breka program is user is not logged in anymore
                break;
            }
            // ========================================================================

        } while (true);
        // END OF PROGRAM
    }
}

// Customer username and password:
// snoopdogg
// smokeweedeveryday1234
// Admin username and password
// bobatea
// boba
// For more usernames and password, check out CustomerDB.txt and AdminDB.txt
