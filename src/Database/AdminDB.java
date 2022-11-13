package Database;

import DatabaseBoundary.*;
import Model.Admin;

/**
 * A class for interfacing Admin database
 */
public class AdminDB {
    /** An array of Admin objects */
    private Admin[] admins;

    /**
     * Constructor
     */
    public AdminDB() {
        this.admins = DatabaseReader.readAdminDatabase();
    }

    /**
     * Gets the Admin array
     * 
     * @return An array of Admin
     */
    public Admin[] getAdmins() {
        return this.admins;
    }

    /**
     * Adds a new admin account
     */
    public static void addNewAdminAccount() {
        DatabaseWriter.addNewAdminAccount();
    }

    /**
     * Removes an admin account
     */
    public static void removeAdminAccount() {
        DatabaseWriter.removeAdminAccount();
    }

    /**
     * Gets the Admin object from username and password
     * 
     * @param username A String representing the username of the Admin
     * @param password A String representing the password of the Admin
     * @return An Admin object
     */
    public static Admin getAdminFromUsername(String username, String password) {
        for (Admin admin : new AdminDB().getAdmins()) {
            if (admin.getUsername().toLowerCase().compareTo(username.toLowerCase()) == 0
                    && admin.getPassword().compareTo(password) == 0) {
                return admin;
            }
        }
        return null;
    }

    /**
     * Check whether Admin of username exist
     * 
     * @param username A String representing the username of the Admin
     * @return true if it exists
     */
    public static boolean isUsernameExist(String username) {
        for (Admin admin : new AdminDB().getAdmins()) {
            if (admin.getUsername().toLowerCase().compareTo(username.toLowerCase()) == 0) {
                return true;
            }
        }
        return false;
    }
}
