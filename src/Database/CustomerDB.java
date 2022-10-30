package Database;

import Model.Customer;
import DatabaseBoundary.*;

public class CustomerDB {
    private Customer[] customers;

    public CustomerDB(Customer[] customers) {
        this.customers = customers;
    }

    public Customer[] getCustomers() {
        return this.customers;
    }

    public static void main(String[] args) {
        CustomerDB a = new CustomerDB(DatabaseReader.readCustomerDatabase());
        for (Customer Customer : a.getCustomers()) {
            Customer.printCustomerDetails();
            System.out.println("\n");
        }
    }
}
