import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides data access methods for interacting with health data in the database.
 */

public class HealthDataDao {

/**
     * Creates a new health data entry in the database.
     *
     * @param healthData The HealthData object containing the health data to be created.
     * @return The ID of the newly created health data entry.
     */   
   
    public int createHealthData(HealthData healthData) {

        int generatedId = 0;
 
        String query = "INSERT INTO public.\"health_data\" (user_id, weight, height, steps, heart_rate, water_intake, hours_of_sleep, date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, healthData.getUserId());
            statement.setDouble(2, healthData.getWeight());
            statement.setDouble(3, healthData.getHeight());
            statement.setInt(4, healthData.getSteps());
            statement.setInt(5, healthData.getHeartRate());
            statement.setDouble(6, healthData.getWaterIntake());
            statement.setDouble(7, healthData.getHoursOfSleep());
            statement.setDate(8, java.sql.Date.valueOf(healthData.getDate()));
            statement.executeUpdate();

            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1); 
                } else {
                    throw new SQLException("Oops! An error has occured adding your health data, no ID was obtained.");
                }
            }
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generatedId;
    }
/**
     * Retrieves health data from the database by its ID.
     *
     * @param id The ID of the health data entry to retrieve.
     * @return The HealthData object corresponding to the provided ID.
     */
    public HealthData getHealthDataById(int id) { 

        int health_data_id = 0;
        int user_id = 0;
        Double weight = null;
        Double height = null;
        int steps = 0;
        int heartRate = 0;
        Double water_intake = null;
        Double hours_of_sleep = null;
        LocalDate date = null;
    
        String query = "SELECT * FROM public.\"health_data\" WHERE health_data_id = ?";
    
        try (Connection con = DatabaseConnection.getCon();
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    health_data_id = rs.getInt("health_data_id");
                    user_id = rs.getInt("user_id");
                    weight = rs.getDouble("weight");
                    height = rs.getDouble("height");
                    steps = rs.getInt("steps");
                    heartRate = rs.getInt("heart_rate");
                    water_intake = rs.getDouble("water_intake");
                    hours_of_sleep = rs.getDouble("hours_of_sleep");
                    Date sqlDate = rs.getDate("date");
                    if (sqlDate != null) {
                        date = sqlDate.toLocalDate();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new HealthData(health_data_id, user_id, weight, height, steps, heartRate, water_intake, hours_of_sleep, date);
    }
/**
     * Retrieves health data from the database associated with a specific user.
     *
     * @param userId The ID of the user whose health data is to be retrieved.
     * @return A list of HealthData objects representing the health data of the specified user.
     */ 
    public List<HealthData> getHealthDataByUserId(int userId) {

    List<HealthData> healthDataList = new ArrayList<>();
    
    String query = "SELECT * FROM public.\"health_data\" WHERE user_id = ?";
    
    try (Connection con = DatabaseConnection.getCon();
         PreparedStatement statement = con.prepareStatement(query)) {
        statement.setInt(1, userId);
        try (ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int health_data_id = rs.getInt("health_data_id");
                Double weight = rs.getDouble("weight");
                Double height = rs.getDouble("height");
                int steps = rs.getInt("steps");
                int heartRate = rs.getInt("heart_rate");
                Double water_intake = rs.getDouble("water_intake");
                Double hours_of_sleep = rs.getDouble("hours_of_sleep");
                Date sqlDate = rs.getDate("date");
                LocalDate date = null;
                if (sqlDate != null) {
                    date = sqlDate.toLocalDate();
                }
                
                HealthData healthData = new HealthData(health_data_id, userId, weight, height, steps, heartRate, water_intake, hours_of_sleep, date);
                healthDataList.add(healthData);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }    
    return healthDataList;
    }
/**
     * Updates an existing health data entry in the database.
     *
     * @param healthData The HealthData object containing the updated health data.
     * @return A boolean indicating whether the update was successful.
     */    
    public boolean updateHealthData(HealthData healthData) {

        boolean bool = false;
  
        String query = "UPDATE public.\"health_data\" " +
                "SET user_id = ?, weight = ?, height = ?, steps = ?, heart_rate = ?, water_intake = ?, hours_of_sleep = ?, date = ? " +
                "WHERE health_data_id = ?";
       
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, healthData.getUserId());
            statement.setDouble(2, healthData.getWeight());
            statement.setDouble(3, healthData.getHeight());
            statement.setInt(4, healthData.getSteps());
            statement.setInt(5, healthData.getHeartRate());
            statement.setDouble(6, healthData.getWaterIntake());
            statement.setDouble(7, healthData.getHoursOfSleep());
            statement.setDate(8, java.sql.Date.valueOf(healthData.getDate()));
            statement.setInt(9, healthData.getId());
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
     * Deletes a health data entry from the database by its ID.
     *
     * @param id The ID of the health data entry to delete.
     * @return A boolean indicating whether the deletion was successful.
     */
    public boolean deleteHealthData(int id) { 
        
        boolean bool = false;
      
        String query = "DELETE FROM public.\"health_data\" WHERE health_data_id = ?";
    
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

}
