import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;



/**
 * A Data Access Object (DAO) class for performing database operations related to users.
 */

public class UserDao {
/**
     * Creates a new user in the database.
     *
     * @param user The User object containing user information.
     * @return The generated ID of the newly created user.
     */   
    public int createUser(User user) {

        int generatedId = 0;

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        String query = "INSERT INTO public.\"users\" (first_name, last_name, email, password, is_doctor) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, hashedPassword);
            statement.setBoolean(5, user.isDoctor());
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1); 
                    System.out.println();
                    System.out.println("Registration Complete. Congratulations on taking the first step towards a healtier you!");
                } else {
                    throw new SQLException("Oops! An error has occured adding the user, no ID was obtained.");
                }
            }
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }

/**
     * Retrieves user information based on the provided user ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The User object containing user information.
     */
    public User getUserById(int id) { 

        int user_id = 0;
        String firstName = null;
        String lastName = null;
        String email = null;
        String password = null;
        boolean is_doctor = false;
       
        String query = "SELECT * FROM public.\"users\" WHERE user_id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("user_id");
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                email = rs.getString("email");
                password = rs.getString("password");
                is_doctor = rs.getBoolean("is_doctor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new User(user_id, firstName, lastName, email, password, is_doctor);
    }

/**
     * Retrieves user information based on the provided email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The User object containing user information.
     */
    public User getUserByEmail(String email) { 

        int user_id = 0;
        String firstName = null;
        String lastName = null;
        String user_email = null;
        String password = null;
        boolean is_doctor = false;

  
        String query = "SELECT * FROM public.\"users\" WHERE email = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                user_id = rs.getInt("user_id");
                firstName = rs.getString("first_name");
                lastName = rs.getString("last_name");
                user_email = rs.getString("email");
                password = rs.getString("password");
                is_doctor = rs.getBoolean("is_doctor");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return new User(user_id, firstName, lastName, user_email, password, is_doctor);
    }
/**
     * Verifies the password for a given email address.
     *
     * @param email    The email address of the user.
     * @param password The password to verify.
     * @return True if the password matches the hashed password stored in the database, false otherwise.
     */
    public boolean verifyPassword(String email, String password) {

        String query = "SELECT password FROM public.\"users\" WHERE email = ?";
        try (Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString("password");
                    if (BCrypt.checkpw(password, hashedPassword)) {
                        return true; // Password is correct
                    } else {
                        // Handle case where password is invalid
                        System.out.println("Oops! The password entered is incorrect. Please try again.");
                        return false;                    
                    }
                } else {
                    // Handle case where email is not found
                    System.out.println("Oops! A user with the provided email does not exist. Please enter a valid email address or register today.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
/**
     * Updates user information in the database.
     *
     * @param user The User object containing updated user information.
     * @return True if the user information was successfully updated, false otherwise.
     */
    public boolean updateUser(User user) {

        boolean bool = false;
    
        String query = "UPDATE public.\"users\" " +
                "SET first_name = ?, last_name = ?, email = ?, password = ?, is_doctor = ? " +
                "WHERE user_id = ?";
       
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, user.isDoctor());
            statement.setInt(6, user.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }
/**
     * Deletes a user from the database based on the provided user ID.
     *
     * @param id The ID of the user to delete.
     * @return True if the user was successfully deleted, false otherwise.
     */
    public boolean deleteUser(int id) { 
        
        boolean bool = false;

        String query = "DELETE FROM public.\"users\" WHERE user_id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated != 0){
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }
/**
     * Checks if a user is a doctor based on the provided user ID.
     *
     * @param userId The ID of the user to check.
     * @return True if the user is a doctor, false otherwise.
     */
    public boolean isDoctor(int userId) {

        boolean isDoctor = false;
        
        String query = "SELECT is_doctor FROM public.\"users\" WHERE user_id = ?";

        try (Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isDoctor = resultSet.getBoolean("is_doctor");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isDoctor;
    }
}
