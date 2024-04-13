import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides data access methods for interacting with the database
 * related to the Doctor's portal functionality.
 */


public class DoctorPortalDao {
    
    private UserDao userDao;
    private HealthDataDao healthDataDao;

/**
     * Constructs a DoctorPortalDao object.
     * Initializes UserDao and HealthDataDao objects.
     */
    public DoctorPortalDao() {
        userDao = new UserDao();
        healthDataDao = new HealthDataDao();
    }
/**
     * Retrieves a Doctor object by its associated user ID.
     *
     * @param userId The ID of the user associated with the doctor.
     * @return A Doctor object corresponding to the provided user ID, or null if not found.
     */
    public Doctor getDoctorById(int userId) {
        Doctor doctor = null;

        String query = "SELECT * FROM public.\"doctors\" WHERE user_id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                // Retrieve doctor-specific details from the result set
                String medicalLicenseNumber = rs.getString("medical_license_number");
                String specialization = rs.getString("specialization");
                             
                User user = userDao.getUserById(userId); 
                
                doctor = new Doctor(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.isDoctor(), medicalLicenseNumber, specialization);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctor;
    }
    /**
     * Retrieves a list of patients associated with a doctor by the doctor's ID.
     *
     * @param doctorId The ID of the doctor.
     * @return A list of User objects representing the patients of the doctor.
     */   
    public List<User> getPatientsByDoctorId(int doctorId) {

        List<User> patients = new ArrayList<>();

        String query = "SELECT * FROM public.\"doctor_patient\" WHERE doctor_id = ?";

        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement statement = con.prepareStatement(query);
            statement.setInt(1, doctorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int patientId = rs.getInt("patient_id");
                User patient = userDao.getUserById(patientId);
                if (patient != null) {
                    patients.add(patient);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
/**
     * Retrieves health data associated with a patient by the patient's ID.
     *
     * @param patientId The ID of the patient.
     * @return A list of HealthData objects representing the health data of the patient.
     */
    public List<HealthData> getHealthDataByPatientId(int patientId) {

        return healthDataDao.getHealthDataByUserId(patientId);
    }
/**
     * Adds a medicine reminder for a patient.
     *
     * @param patientId The ID of the patient.
     * @param reminder The MedicineReminder object representing the medicine reminder to be added.
     */
    public void addMedicineReminderForPatient(int patientId, MedicineReminder reminder) {

        String query = "INSERT INTO public.\"medicine_reminders\" (user_id, medicine_name, dosage, schedule, start_date, end_date) " + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getCon()) {
            try (PreparedStatement statement = con.prepareStatement(query)) {
                statement.setInt(1, patientId);
                statement.setString(2, reminder.getMedicineName());
                statement.setString(3, reminder.getDosage());
                statement.setString(4, reminder.getSchedule());
                statement.setDate(5, java.sql.Date.valueOf(reminder.getStartDate()));
                statement.setDate(6, java.sql.Date.valueOf(reminder.getEndDate()));           
    
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reminder added successfully.");
                } else {
                    System.out.println("Opps, Something went wrong.Failed to add the medicine reminder. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}    


