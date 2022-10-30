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

    public static Admin getAdminFromUsername(String username, String password) {
        for (Admin admin : new AdminDB().getAdmins()) {
            if (admin.getUsername().toLowerCase().compareTo(username.toLowerCase()) == 0 && admin.getPassword().compareTo(password) == 0) {
                return admin;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        AdminDB a = new AdminDB();
        for (Admin admin : a.getAdmins()) {
            admin.printAdminDetails();
            System.out.println("\n");
        }
    }
}
