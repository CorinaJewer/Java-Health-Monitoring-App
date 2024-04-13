 
 /**
 * This class represents a user with attributes such as user_id, firstName, lastName, email, password, and isDoctor.
 */

public class User {

    private int user_id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean isDoctor;
/**
     * Constructs a new User object with the specified attributes.
     *
     * @param user_id   The unique identifier for the user.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     * @param password  The password of the user.
     * @param isDoctor  Indicates whether the user is a doctor or not.
     */
    public User(int user_id, String firstName, String lastName, String email, String password, boolean isDoctor) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isDoctor = isDoctor;
    }
/**
     * Gets the user's unique identifier.
     *
     * @return The user's unique identifier.
     */
    public int getId() {
        return user_id;
    }
/**
     * Sets the user's unique identifier.
     *
     * @param user_id The user's unique identifier.
     */
    public void setId(int user_id) {
        this.user_id = user_id;
    }
/**
     * Gets the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }
/**
     * Sets the first name of the user.
     *
     * @param firstName The first name of the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
/**
     * Gets the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }
/**
     * Sets the last name of the user.
     *
     * @param lastName The last name of the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
/**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }
/**
     * Sets the email address of the user.
     *
     * @param email The email address of the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }
/**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }
/**
     * Sets the password of the user.
     *
     * @param password The password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
/**
     * Checks if the user is a doctor.
     *
     * @return True if the user is a doctor, false otherwise.
     */
    public boolean isDoctor() {
        return isDoctor;
    }
/**
     * Sets whether the user is a doctor.
     *
     * @param doctor True if the user is a doctor, false otherwise.
     */
    public void setDoctor(boolean doctor) {
        isDoctor = doctor;
    }  
    
    
}
