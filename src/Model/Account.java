package Model;

/**
 * Represents a standard user account
 */
public class Account {
    /** A String representing the username of this account */
    protected String username;
    /** A String representing the passwrod of this account */
    protected String password;
    /** A String representing the account ID of this account */
    protected String accountID;

    /** Empty Constructor */
    Account() {
    }

    /**
     * Gets the account's username
     * 
     * @return A String representing the account's username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets the account's username
     * 
     * @param username A String representing the account's username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the account's password
     * 
     * @return A String representing the account's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the account's password
     * 
     * @param password A String representing the account's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the account's ID
     * 
     * @return A String representing the account's ID
     */
    public String getAccountID() {
        return this.accountID;
    }

    /**
     * Sets the account's ID
     * 
     * @param accountID A String representing the account's ID
     */
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
}
