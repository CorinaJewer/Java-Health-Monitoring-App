
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages medicine reminders, providing methods to add, retrieve, update, and delete reminders in the database.
 */

public class MedicineReminderManager {

    private List<MedicineReminder> reminders;
/**
     * Constructs a MedicineReminderManager object with an empty list of reminders.
     */
    public MedicineReminderManager() {
        this.reminders = new ArrayList<>();
    }
/**
     * Adds a new medicine reminder to the database.
     *
     * @param reminder The MedicineReminder object representing the reminder to be added.
     */
    public void addReminder(MedicineReminder reminder) {

        String query = "INSERT INTO public.\"medicine_reminders\" (user_id, medicine_name, dosage, schedule, start_date, end_date)" + "VALUES (?, ?, ?, ?, ?, ?)";
    
            try (Connection connection = DatabaseConnection.getCon();
                 PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
    
                preparedStatement.setInt(1, reminder.getUserId());
                preparedStatement.setString(2, reminder.getMedicineName());
                preparedStatement.setString(3, reminder.getDosage());
                preparedStatement.setString(4, reminder.getSchedule());
                preparedStatement.setDate(5, java.sql.Date.valueOf(reminder.getStartDate()));
                preparedStatement.setDate(6, java.sql.Date.valueOf(reminder.getEndDate()));
               
                   
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reminder added successfully.");
                    reminders.add(reminder);
                } else {
                    System.out.println("Opps! Something went wrong. Please try again");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
 /**
     * Retrieves all medicine reminders associated with a specific user from the database.
     *
     * @param userId The ID of the user whose reminders are to be retrieved.
     * @return A list of MedicineReminder objects representing the user's reminders.
     */
    public List<MedicineReminder> getAllReminders(int userId) {

        List<MedicineReminder> reminders = new ArrayList<>(); 

        String query = "SELECT * FROM public.\"medicine_reminders\" WHERE user_id = ?";
    
        try (Connection connection = DatabaseConnection.getCon();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("reminder_id");
                    String medicineName = rs.getString("medicine_name");
                    String dosage = rs.getString("dosage");
                    String schedule = rs.getString("schedule");
                    LocalDate startDate = rs.getDate("start_date").toLocalDate();
                    LocalDate endDate = rs.getDate("end_date").toLocalDate();
                  
                MedicineReminder reminder = new MedicineReminder(id, userId, medicineName, dosage, schedule, startDate, endDate);
                reminders.add(reminder);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return reminders;
    }
/**
     * Retrieves medicine reminders that are overdue/expired for a specific user from the database.
     *
     * @param userId The ID of the user for whom due reminders are to be retrieved.
     * @return A list of MedicineReminder objects representing the overdue/expired reminders.
     */
    public List<MedicineReminder> getDueReminders(int userId) {

        List<MedicineReminder> dueReminders = new ArrayList<>();
        LocalDate now = LocalDate.now();

        
        List<MedicineReminder> allReminders = getAllReminders(userId);

        for (MedicineReminder reminder : allReminders) {
            if (reminder.getEndDate().isBefore(now)) { 
                dueReminders.add(reminder);
            }
        }
        return dueReminders;
    }
/**
     * Deletes a medicine reminder from the database.
     *
     * @param reminderId The ID of the reminder to be deleted.
     */
    public void deleteMedicineReminder(int reminderId) {

        String query = "DELETE FROM public.\"medicine_reminders\" WHERE reminder_id = ?";
        
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reminderId);
            
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medicine Reminder with ID " + reminderId + " has been successfully deleted.");
            } else {
                System.out.println("Opps! Something went wrong. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 /**
     * Updates a medicine reminder in the database.
     *
     * @param reminder The MedicineReminder object containing the updated reminder information.
     * @return A boolean indicating whether the update was successful.
     */       
    public boolean updateMedicationReminder(MedicineReminder reminder) {

        boolean bool = false;
    
        String query = "UPDATE public.\"medicine_reminders\" SET medicine_name = ?, dosage = ?, schedule = ?, start_date = ?, end_date = ? WHERE reminder_id = ?";
        
        try (Connection connection = DatabaseConnection.getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, reminder.getMedicineName());
            preparedStatement.setString(2, reminder.getDosage());
            preparedStatement.setString(3, reminder.getSchedule());
            preparedStatement.setDate(4, java.sql.Date.valueOf(reminder.getStartDate()));
            preparedStatement.setDate(5, java.sql.Date.valueOf(reminder.getEndDate()));
            preparedStatement.setInt(6, reminder.getId());
            
            int rowsUpdated = preparedStatement.executeUpdate();
            
            if (rowsUpdated > 0) {
                bool = true;
            }
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bool;
    }
       
}

  
