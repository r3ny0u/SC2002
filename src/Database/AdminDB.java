package Database;

import DatabaseBoundary.*;
import Model.Admin;

public class AdminDB {
    private Admin[] admins;

    public AdminDB() {
        this.admins = DatabaseReader.readAdminDatabase();
    }

    public Admin[] getAdmins() {
        return this.admins;
    }

    public static void addNewAdminAccount() {
        DatabaseWriter.addNewAdminAccount();
    }

    public static void removeAdminAccount() {
        DatabaseWriter.removeAdminAccount();
    }

    public static Admin getAdminFromUsername(String username, String password) {
        for (Admin admin : new AdminDB().getAdmins()) {
            if (admin.getUsername().toLowerCase().compareTo(username.toLowerCase()) == 0
                    && admin.getPassword().compareTo(password) == 0) {
                return admin;
            }
        }
        return null;
    }

    public static boolean isUsernameExist(String username) {
        for (Admin admin : new AdminDB().getAdmins()) {
            if (admin.getUsername().toLowerCase().compareTo(username.toLowerCase()) == 0) {
                return true;
            }
        }
        return false;
    }

    public static void printAdminDBDetails() {
        for (Admin admin : new AdminDB().getAdmins()) {
            admin.printAdminDetails();
            System.out.println("\n");
        }
    }
}
