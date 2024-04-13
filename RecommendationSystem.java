

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a recommendation system based on the analysis of health data.
 */

public class RecommendationSystem {

    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_STEPS = 10000;
    private static final double MIN_WATER_INTAKE = 64.0;
    private static final double MIN_HOURS_SLEEP = 7.0;
/**
     * Generates recommendations based on the provided health data.
     *
     * @param healthData The HealthData object containing the user's health data.
     * @return A list of recommendations.
     */
    public List<String> generateRecommendations(HealthData healthData) {
        List<String> recommendations = new ArrayList<>();

       // Analyze Heart Rate
        int heartRate = healthData.getHeartRate();
        if (heartRate < MIN_HEART_RATE) {
            recommendations.add("Your heart rate is lower than the recommended range. " +
                    "Consider increasing your physical activity to improve your cardiovascular health.");
        } else if (heartRate > MAX_HEART_RATE){
                recommendations.add("Your heart rate is higher than the recommended range. " +
                "Consider reducing stress and incorporating relaxation techniques.");
        }
      
       // Analyze Steps
        int steps = healthData.getSteps();
        if (steps < MIN_STEPS) {
            recommendations.add("You're not reaching the recommended daily step count of 10,000 steps. " +
                    "Try to incorporate more walking or other physical activities into your daily routine.");
        }

        // Analyze Water intake
        double waterIntake = healthData.getWaterIntake();
        if (waterIntake < MIN_WATER_INTAKE) {
            recommendations.add("You haven't reached the recommended daily water intake of 64 ounces. " +
            "Consider increasing your water intake for better hydration.");
        }

        // Analyse Sleep
        double sleepHours = healthData.getHoursOfSleep();
            if (sleepHours < MIN_HOURS_SLEEP) {
                recommendations.add("You're not getting enough sleep. Aim for at least " +
                        MIN_HOURS_SLEEP + " hours of sleep per night for better performance.");
            }
        
        if (recommendations.isEmpty()) {
            recommendations.add("No recommendations at this time. You're doing great!");
        }    
                
        userRecommendations(healthData.getUserId(), recommendations, healthData.getDate());   

        return recommendations;
    }
/**
     * Stores user recommendations in the database.
     *
     * @param userId The ID of the user.
     * @param recommendations The list of recommendations to be stored.
     * @param recommendationDate The date of the recommendations.
     */
    private void userRecommendations(int userId, List<String> recommendations, LocalDate recommendationDate) {
      
        String recommendationText = String.join(", ", recommendations);
    
        String query = "INSERT INTO public.\"recommendations\" (user_id, recommendation_text, date) VALUES (?, ?, ?)";
    
        try (Connection connection = DatabaseConnection.getCon()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, recommendationText);
            preparedStatement.setDate(3, java.sql.Date.valueOf(recommendationDate));
            
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }   
}



