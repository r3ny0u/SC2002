package Database;

import Model.Customer;
import DatabaseBoundary.*;

public class CustomerDB {
    private Customer[] customers;

    public CustomerDB() {
        this.customers = DatabaseReader.readCustomerDatabase();
    }

    public Customer[] getCustomers() {
        return this.customers;
    }

    public static void addNewCustomerAccount() {
        DatabaseWriter.addNewCustomerAccount();
    }

    public static void removeCustomerAccount() {
        DatabaseWriter.removeCustomerAccount();
    }

    public static Customer getCustomerFromUsername(String username, String password) {
        for (Customer customer : new CustomerDB().getCustomers()) {
            if (customer.getUsername().toLowerCase().compareTo(username.toLowerCase()) == 0 && customer.getPassword().compareTo(password) == 0) {
                return customer;
            }
        }
        return null;
    }

    public static void printCustomerDBDetails() {
        for (Customer Customer : new CustomerDB().getCustomers()) {
            Customer.printCustomerDetails();
            System.out.println("\n");
        }
    }
}
