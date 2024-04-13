import java.time.LocalDate;

/**
 * This class represents health data of a user, such as weight, height, steps, heart rate, water intake and hours of sleepn on a given date.
 */

public class HealthData {
    private int id;
    private int userId;
    private double weight;
    private double height;
    private int steps;
    private int heartRate;
    private double waterIntake;
    private double hoursOfSleep;
    private LocalDate date;

/**
     * Constructs a HealthData object with the specified attributes.
     *
     * @param id The ID of the health data entry.
     * @param userId The ID of the user to whom the health data belongs.
     * @param weight The weight of the user.
     * @param height The height of the user.
     * @param steps The number of steps taken by the user.
     * @param heartRate The heart rate of the user.
     * @param waterIntake The water intake of the user.
     * @param hoursOfSleep The hours of sleep of the user.
     * @param date The date of the health data entry.
     */
    public HealthData(int id, int userId, double weight, double height, int steps, int heartRate, double waterIntake, double hoursOfSleep,LocalDate date ){
        this.id = id;
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.steps = steps;
        this.heartRate = heartRate;
        this.waterIntake = waterIntake;
        this.hoursOfSleep = hoursOfSleep;
        this.date = date;
    }
/**
     * Gets the ID of the health data entry.
     *
     * @return The ID of the health data entry.
     */
    public int getId(){
        return id;
    }
/**
     * Sets the ID of the health data entry.
     *
     * @param id The ID to set.
     */
    public void setId(int id){
        this.id = id;
    }
/**
     * Gets the ID of the user to whom the health data belongs.
     *
     * @return The ID of the user.
     */
    public int getUserId(){
        return userId;
    }
/**
     * Sets the ID of the user to whom the health data belongs.
     *
     * @param userId The ID of the user.
     */
    public void setUserId(int userId){
        this.userId = userId;
    }
/**
     * Gets the weight of the user.
     *
     * @return The weight of the user.
     */
    public double getWeight(){
        return weight;
    }
/**
     * Sets the weight of the user.
     *
     * @param weight The weight to set.
     */
    public void setWeight(double weight){
       this.weight = weight;
    }
/**
     * Gets the height of the user.
     *
     * @return The height of the user.
     */
    public double getHeight(){
        return height;
    }
/**
     * Sets the height of the user.
     *
     * @param height The height to set.
     */
    public void setHeight(double height){
        this.height = height;
    }
/**
     * Gets the number of steps taken by the user.
     *
     * @return The number of steps taken.
     */
    public int getSteps(){
        return steps;
    }
/**
     * Sets the number of steps taken by the user.
     *
     * @param steps The number of steps to set.
     */
    public void setSteps(int steps){
        this.steps = steps;
    }
/**
     * Gets the heart rate of the user.
     *
     * @return The heart rate of the user.
     */
    public int getHeartRate(){
        return heartRate;
    }
/**
     * Sets the heart rate of the user.
     *
     * @param heartRate The heart rate to set.
     */
    public void setHeartRate(int heartRate){
        this.heartRate = heartRate;
    }
/**
     * Gets the water intake of the user.
     *
     * @return The water intake of the user.
     */
    public double getWaterIntake(){
        return waterIntake;
    }
/**
     * Sets the water intake of the user.
     *
     * @param waterIntake The water intake to set.
     */
    public void setWaterIntake(double waterIntake){
        this.waterIntake = waterIntake;
    }
/**
     * Gets the hours of sleep of the user.
     *
     * @return The hours of sleep of the user.
     */
    public double getHoursOfSleep(){
        return hoursOfSleep;
    }
/**
     * Sets the hours of sleep of the user.
     *
     * @param hoursOfSleep The hours of sleep to set.
     */
    public void setHoursOfSleep(double hoursOfSleep){
        this.hoursOfSleep = hoursOfSleep;
    }
 /**
     * Gets the date of the health data entry.
     *
     * @return The date of the health data entry.
     */
    public LocalDate getDate(){
        return date;
    }
/**
     * Sets the date of the health data entry.
     *
     * @param date The date to set.
     */
    public void setDate(LocalDate date){
        this.date = date;
    }
/**
     * Returns a string representation of the HealthData object.
     *
     * @return A string containing the health data attributes.
     */
    @Override
    public String toString() {
        return (
               "Weight: " + weight + "\n" +
               "Height: " + height + "\n" +
               "Steps: " + steps + "\n" +
               "Heart Rate: " + heartRate + "\n" +
               "Water Intake: " + waterIntake + "\n" +
               "Hours of Sleep: " + hoursOfSleep + "\n" +
               "Date: " + date);
    }  
}
