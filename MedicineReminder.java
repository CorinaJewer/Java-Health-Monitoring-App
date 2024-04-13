import java.time.LocalDate;

/**
 * This class represents a medicine reminder for a user.
 */

public class MedicineReminder {

    private int id;
    private int userId;
    private String medicineName;
    private String dosage;
    private String schedule;
    private LocalDate startDate;
    private LocalDate endDate;
  
/**
     * Constructs a MedicineReminder object with the specified attributes.
     *
     * @param id The ID of the medicine reminder.
     * @param userId The ID of the user for whom the reminder is set.
     * @param medicineName The name of the medicine.
     * @param dosage The dosage of the medicine.
     * @param schedule The schedule of the medicine.
     * @param startDate The start date of the reminder.
     * @param endDate The end date of the reminder.
     */
    public MedicineReminder(int id, int userId, String medicineName, String dosage, String schedule, LocalDate startDate, LocalDate endDate){

        this.id = id;
        this.userId = userId;
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.schedule =schedule;
        this.startDate = startDate;
        this.endDate = endDate;
        
    }
/**
     * Gets the ID of the medicine reminder.
     *
     * @return The ID of the medicine reminder.
     */
    public int getId(){
        return id;
    }
/**
     * Sets the ID of the medicine reminder.
     *
     * @param id The ID to set.
     */
    public void setId(int id){
        this.id = id;
    }
/**
     * Gets the ID of the user for whom the reminder is set.
     *
     * @return The ID of the user.
     */
    public int getUserId(){
        return userId;
    }
/**
     * Sets the ID of the user for whom the reminder is set.
     *
     * @param userId The ID of the user.
     */
    public void setUserId(int userId){
        this.userId = userId;
    }
/**
     * Gets the name of the medicine.
     *
     * @return The name of the medicine.
     */
    public String getMedicineName(){
        return medicineName;
    }
/**
     * Sets the name of the medicine.
     *
     * @param medicineName The name of the medicine to set.
     */
    public void setMedicineName(String medicineName){
        this.medicineName = medicineName;
    }
/**
     * Gets the dosage of the medicine.
     *
     * @return The dosage of the medicine.
     */
    public String getDosage(){
        return dosage;
    }
/**
     * Sets the dosage of the medicine.
     *
     * @param dosage The dosage of the medicine to set.
     */
    public void setDosage(String dosage){
        this.dosage = dosage;
    }
/**
     * Gets the schedule of the medicine reminder.
     *
     * @return The schedule of the medicine reminder.
     */
    public String getSchedule(){
        return schedule;
    }
/**
     * Sets the schedule of the medicine reminder.
     *
     * @param schedule The schedule of the medicine reminder to set.
     */
    public void setSchedule(String schedule){
        this.schedule = schedule;
    }
/**
     * Gets the start date of the reminder.
     *
     * @return The start date of the reminder.
     */
    public LocalDate getStartDate(){
        return startDate;
    }
/**
     * Sets the start date of the reminder.
     *
     * @param startDate The start date of the reminder to set.
     */
    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }
 /**
     * Gets the end date of the reminder.
     *
     * @return The end date of the reminder.
     */
    public LocalDate getEndDate(){
        return endDate;
    }
/**
     * Sets the end date of the reminder.
     *
     * @param endDate The end date of the reminder to set.
     */
    public void setEndDate(LocalDate endDate){
        this.endDate = endDate;
    }
/**
     * Returns a string representation of the MedicineReminder object.
     *
     * @return A string containing the medicine reminder attributes.
     */
    @Override
    public String toString() {
        return ("Reminder ID: " + id + "\n" + "Medicine Name: " + medicineName + "\n" + "Dosage: " + dosage + "\n" + "Schedule: " + schedule + "\n" + "Start Date: " + startDate + "\n" + "End Date: " + endDate);
    }   
}
