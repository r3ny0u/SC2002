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

    public static Customer getCustomerFromUsername(String username, String password) {
        for (Customer customer : new CustomerDB().getCustomers()) {
            if (customer.getUsername().toLowerCase().compareTo(username.toLowerCase()) == 0 && customer.getPassword().compareTo(password) == 0) {
                return customer;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        CustomerDB a = new CustomerDB();
        for (Customer Customer : a.getCustomers()) {
            Customer.printCustomerDetails();
            System.out.println("\n");
        }
    }
}
