package Database;

import Model.Customer;
import DatabaseBoundary.*;

/**
 * A class for interfacing customer database
 */
public class CustomerDB {
    /** An array of Customer objects */
    private Customer[] customers;

    /** Constructor */
    public CustomerDB() {
        this.customers = DatabaseReader.readCustomerDatabase();
    }

    /**
     * Gets the Customer array
     * 
     * @return An array of customers
     */
    public Customer[] getCustomers() {
        return this.customers;
    }

    /**
     * Adds a new customer account
     */
    public static void addNewCustomerAccount() {
        DatabaseWriter.addNewCustomerAccount();
    }

    /**
     * Removes a customer account
     */
    public static void removeCustomerAccount() {
        DatabaseWriter.removeCustomerAccount();
    }

    /**
     * Gets the customer from their username and password
     * 
     * @param username A String representing the username of the customer
     * @param password A String representing the password of the customer
     * @return A Customer object
     */
    public static Customer getCustomerFromUsername(String username, String password) {
        for (Customer customer : new CustomerDB().getCustomers()) {
            if (customer.getUsername().toLowerCase().compareTo(username.toLowerCase()) == 0
                    && customer.getPassword().compareTo(password) == 0) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Checks whether customer with username exists
     * 
     * @param username A String representing the username of the customer
     * @return true if customer with username exists
     */
    public static boolean isUsernameExist(String username) {
        for (Customer customer : new CustomerDB().getCustomers()) {
            if (customer.getUsername().toLowerCase().compareTo(username.toLowerCase()) == 0) {
                return true;
            }
        }
        return false;
    }
}
