package Database;

import DatabaseBoundary.*;
import Model.Admin;

public class AdminDB {
    private Admin[] admins;

    public AdminDB(Admin[] admins) {
        this.admins = admins;
    }

    public Admin[] getAdmins() {
        return this.admins;
    }

    public static void main(String[] args) {
        AdminDB a = new AdminDB(DatabaseReader.readAdminDatabase());
        for (Admin admin : a.getAdmins()) {
            admin.printAdminDetails();
            System.out.println("\n");
        }
    }
}
