import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

/**
     * This class represents the main application for a Health Monitoring System.
     * It provides functionality for user registration, login, managing health data, medication reminders,
     * and access to a doctor portal for authorized users.
    */


public class HealthMonitoringApp {

/**
     * UserDao instance for handling user-related database operations.
    */
    private static UserDao userDao = new UserDao();
/**
     * HealthDataDao instance for handling health data-related database operations.
     */
    private static HealthDataDao healthDataDao = new HealthDataDao();
/**
     * DoctorPortalDao instance for handling doctor portal-related database operations.
     */
    private static DoctorPortalDao doctorPortalDao = new DoctorPortalDao();
    /**
     * RecommendationSystem instance for providing health recommendations.
     */
    private static RecommendationSystem recommendationSystem = new RecommendationSystem();
/**
     * MedicineReminderManager instance for managing medication reminders.
     */
    private static MedicineReminderManager medicineManager = new MedicineReminderManager();

    private static Scanner in = new Scanner(System.in);
/**
     * Flag indicating whether a user is logged in.
     */
    private static boolean isLoggedIn = false;
/**
     * Id of the currently logged-in user.
     */
    private static int userId;

/**
     * Main method of the application, entry point of execution.
     * @param args Command-line arguments
     */

    public static void main(String[] args) {

        int selection;

        do {
            if (isLoggedIn) {
                do {
                    displayLoggedInMenu();
                    selection = in.nextInt();
                    loggedInMenuOption(selection);
                } while (isLoggedIn && selection != 12); 
            } else {
                welcomeMessage();
                displayMenu();
                selection = in.nextInt();
                mainMenuOption(selection);
            }
        } while (selection != 3);
    }
/**
     * Handles the options available in the main menu before login.
     * @param selection The user's menu choice.
     */
    private static void mainMenuOption(int selection) {

        switch (selection) {
            case 1:
                newUser();
                break;
            case 2:
                testLoginUser();
                break;
            case 3:
                System.out.println("Have a Happy & Healthy Day!");
                break;
            default:
                System.out.println("Oops! It appears that choice is invalid. Please choose again.");
                break;
        }
    }
/**
     * Handles the options available in the menu after login.
     * @param selection The user's menu choice.
     */
    private static void loggedInMenuOption(int selection) {

        switch (selection) {
            case 1: 
                updateUserProfile();
                break;
            case 2: 
                deleteUserProfile();
                break;
            case 3:
                addHealthData();
                break;
            case 4:
                updateHealthData();
                break;
            case 5:
                deleteHealthDataRecord();
                break;
            case 6:
                retrieveHealthRecommendations();
                break;
            case 7:
                addMedicationReminder();
                break;
            case 8:
                displayMedicationReminders();
                break;
            case 9:
                displayOverdueMedicationReminders();
                break;
            case 10:
                updateMedicationReminder();
            break;
            case 11:
                if (userDao.isDoctor(userId)) {
                displayDoctorPortalMenu();
                } else {
                System.out.println("Access denied. You are not authorized to access the doctor portal.");
                }
                break;
            case 12:
                isLoggedIn = false; 
                return;
            default:
                System.out.println("Oops! It looks like that choice is invalid. Please choose again.");
                break;
        }
    }
/**
     * Handles the options available in the doctor portal menu.
     * @param selection The doctor's menu choice.
     */  
    private static void doctorMenuOption(int selection) {

        switch (selection) {
            case 1:
                viewPatientsForDoctor();
                break;
            case 2:
                viewPatientMedicineReminders();
                break;
            case 3:
                viewHealthDataForPatient();
                break;
            case 4:
                prescribeMedication();
                break;
            case 5:
                isLoggedIn = false;
                return;
            default:
                System.out.println("Opps! It appears that choice is not available. Please choose again.");
        }
    }
/**
     * Displays a welcome message to the user.
     */
    private static void welcomeMessage() {
        System.out.println();
        System.out.println("Welcome to the Smart Health Monitoring System!");
        System.out.println("   \"Your Daily Dose of Health Made Simple.\"");      
    }
/**
     * Displays the main menu options before login.
     */
    private static void displayMenu() {
        System.out.println();
        System.out.println("Let's Get Started");
        System.out.println("-----------------");
        System.out.println();
        System.out.println("1. New User Registration");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println();
        System.out.print("Please enter the number of your choice: ");
    }
/**
     * Displays the menu options available after login.
     */
    private static void displayLoggedInMenu() {
        System.out.println();
        System.out.println("Health Options");
        System.out.println("--------------");
        System.out.println();
        System.out.println("1.  Update User Profile");
        System.out.println("2.  Delete User Profile");
        System.out.println("3.  Add Health Data Record");
        System.out.println("4.  Update Health Data Record");
        System.out.println("5.  Delete Health Data Record");
        System.out.println("6.  Retrieve Health Recommendations");
        System.out.println("7.  Add Medication Reminder");
        System.out.println("8.  Retrieve Medication Reminder List");
        System.out.println("9.  Retrieve Overdue Medication Reminders");
        System.out.println("10. Update Medication Reminder");
        System.out.println("11. Access Doctor Portal");
        System.out.println("12. Logout");
        System.out.println();
        System.out.print("Please enter the number of your choice: ");
    }
/**
     * Displays the menu options available in the doctor portal.
     */
    private static void displayDoctorPortalMenu() {
        int selection;
        do {
            System.out.println();
            System.out.println("Doctor Portal Menu");
            System.out.println("------------------");
            System.out.println();
            System.out.println("1. View All Patients");
            System.out.println("2. View Patient Medication List");
            System.out.println("3. View Patient Health Data");
            System.out.println("4. Prescribe Patient Medication");
            System.out.println("5. Logout");
            System.out.println();
            System.out.print("Please enter the number of your choice: ");
       
            selection = in.nextInt();
            doctorMenuOption(selection);
        } while (selection != 5);
    }
/**
     * Registers a new user by collecting their details and storing them in the database.
     * Prompts the user for first name, last name, email, password, and doctor status.
     */   
    private static void newUser() {

        System.out.println();
        System.out.print("Please enter your first name: ");
        String firstName = in.next();
        System.out.print("Please enter your last name: ");
        String lastName = in.next();
        System.out.print("Please enter your email address: ");
        String email = in.next();
        System.out.print("Please enter your password: ");
        String password = in.next();
        System.out.print("Are you a licensed medical doctor? (Y/N) ");
        String isDoctorEntry = in.next();

        Boolean isDoctor;

        if (isDoctorEntry.equalsIgnoreCase("y")) {
            isDoctor = true;
        } else if (isDoctorEntry.equalsIgnoreCase("n")) {
            isDoctor = false;
        } else {
            System.out.println("Opps! That's an invalid entry, please enter Y or N.");
            displayMenu();
            return;
        }
           
        User user = new User(0, firstName, lastName, email, password, isDoctor);
     
        int userId = userDao.createUser(user);

        user.setId(userId);
        
    }
/**
     * Logs in a user by verifying their email and password against the database records.
     * @param email The email address of the user.
     * @param password The password of the user.
     * @return true if login is successful, false otherwise.
     */
    private static boolean loginUser(String email, String password) {
      
        User user = userDao.getUserByEmail(email);

        if (user != null) {
           
            return userDao.verifyPassword(email, password);
        } else{
            return false;
         
        }    
    }
/**
     * Allows a user to log in by prompting for their email and password.
     * Prints a welcome message upon successful login and sets the user as logged in.
     */
    private static void testLoginUser() {

        System.out.print("Please enter your email address: ");
        String userEmail = in.next();
        System.out.print("Please enter your password: ");
        String userPassword = in.next();
        
        
        boolean loginSuccess = loginUser(userEmail, userPassword);
        User loggedInUser = userDao.getUserByEmail(userEmail);

        if (loginSuccess) {
            
            System.out.println();
            System.out.println("Hello, " + loggedInUser.getFirstName() + ". Let's get Healthy!");
            isLoggedIn = true;
            userId = loggedInUser.getId();
        }
    }
/**
     * Allows a logged-in user to update their profile information such as name, email, password, and doctor status.
     * Prints the current user details and provides options for updating each field.
     */
    private static void updateUserProfile() {
   
        User user = userDao.getUserById(userId); 
        
        System.out.println();
        System.out.println("User Details");
        System.out.println("------------");
        System.out.println("First Name: " + user.getFirstName());
        System.out.println("Last Name: " + user.getLastName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Is Doctor: " + (user.isDoctor() ? "Yes" : "No"));System.out.println();


        System.out.println();
        System.out.println("Update User Profile");
        System.out.println("-------------------");
        System.out.println("1. Update First Name");
        System.out.println("2. Update Last Name");
        System.out.println("3. Update Email");
        System.out.println("4. Update Password");
        System.out.println("5. Update Doctor Status");
        System.out.println("6. Exit");
        System.out.println();
        System.out.print("Please choose the number of your choice: ");
    
        int selection = in.nextInt();
        in.nextLine(); 
        
        switch (selection) {
            case 1:
                System.out.print("Enter new first name: ");
                String newFirstName = in.nextLine().trim();
                if (!newFirstName.isEmpty()) {
                    user.setFirstName(newFirstName);
                }
                break;
            case 2:
                System.out.print("Enter new last name: ");
                String newLastName = in.nextLine().trim();
                if (!newLastName.isEmpty()) {
                    user.setLastName(newLastName);
                }
                break;
            case 3:
                System.out.print("Enter new email: ");
                String newEmail = in.nextLine().trim();
                if (!newEmail.isEmpty()) {
                    user.setEmail(newEmail);
                }
                break;
            case 4:
                System.out.print("Enter new password: ");
                String newPassword = in.nextLine().trim();
                if (!newPassword.isEmpty()) {
                    String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                    user.setPassword(hashedPassword);
                }
                break;
            case 5:
                System.out.print("Are you a licensed medical doctor? (Y/N): ");
                String isDoctorEntry = in.nextLine().trim().toUpperCase();
                boolean isDoctor = isDoctorEntry.equals("Y");
                user.setDoctor(isDoctor);
                break;
            case 6:
                System.out.println("Exit");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }
        

    boolean updateUser = userDao.updateUser(user);
    
    if (updateUser) {
        System.out.println("User information updated successfully.");
    } else {
        System.out.println("Failed to update user information. Please try again.");
    }
}
/**
     * Deletes the user's profile, including all associated data, if confirmed.
     * Prompts the user for confirmation before deletion.
     */
    private static void deleteUserProfile() {

        System.out.println();
        System.out.println("Deleting your account will permanently remove all the data associated with it.");
        System.out.println();
        System.out.print("Delete Account (Y/N): ");
        String reply = in.next();
        in.nextLine(); 
        if (reply.equalsIgnoreCase("Y")) {
            boolean isDeleted = userDao.deleteUser(userId);
            if (isDeleted) {
                isLoggedIn = false; 
                System.out.println();
                System.out.println("Your account has been successfully deleted. We're sad to see you go.");
                System.out.println();
            } else {
                System.out.println("Unable to delete your account. Please try again.");
            }
        } else {
            System.out.println("So glad you've decided to keep investing in your health.");
        }
}
/**
     * Adds a new health data record for the current user by prompting for various health parameters.
     * Creates a new HealthData object and saves it to the database.
     */
    private static void addHealthData(){

        System.out.println();
        System.out.println("Your Health Data");
        System.out.println("----------------");
        System.out.print("Date (MM-DD-YYYY): ");
        String dateString = in.next();
        LocalDate date = parseDate(dateString);
        if (date == null) {
            return;
        }

        System.out.print("Weight (kg): ");
        double weight = in.nextDouble();
        System.out.print("Height (cms): ");
        double height = in.nextDouble();
        System.out.print("Steps Count: ");
        int steps = in.nextInt();
        System.out.print("Heart Rate: ");
        int heartRate = in.nextInt();
        System.out.print("Water Intake (ozs): ");
        int waterIntake = in.nextInt();
        System.out.print("Hours of Sleep (hrs): ");
        int hoursOfSleep = in.nextInt();

              
        HealthData healthData = new HealthData(0, userId, weight, height, steps, heartRate, waterIntake, hoursOfSleep, date);

        int id = healthDataDao.createHealthData(healthData);
      
        healthData.setId(id);

        System.out.println();
        System.out.println("Your health data has been updated sucessfully.");

    }
/**
     * Allows the user to update an existing health data record by choosing from a list of their records
     * and updating the selected fields.
     */        
    private static void updateHealthData() {
    
    List<HealthData> healthDataList = healthDataDao.getHealthDataByUserId(userId);

    if (healthDataList.isEmpty()) {
        System.out.println("No health data found for the current user. Please enter a health data record.");
        return;
    }
  
    System.out.println();
    System.out.println("Health Records");
    System.out.println("--------------");
    System.out.println();
    for (int i = 0; i < healthDataList.size(); i++) {
        HealthData healthData = healthDataList.get(i);
        System.out.println((i + 1) + ". Date: " + healthData.getDate());
        System.out.println();
    }
    
    System.out.print("Please choose the number of the health data record you wish to update: ");
  
    int selectedNumber = in.nextInt();
    in.nextLine(); 
    
    if (selectedNumber < 1 || selectedNumber > healthDataList.size()) {
        System.out.println("Opps! it appears that choice is invalid. Please choose again.");
        return;
    }
    
    HealthData selectedHealthData = healthDataList.get(selectedNumber - 1);
    
    System.out.println();
    System.out.println("Current Health Data");
    System.out.println("-------------------");
    System.out.println("Weight: " + selectedHealthData.getWeight());
    System.out.println("Height: " + selectedHealthData.getHeight());
    System.out.println("Steps: " + selectedHealthData.getSteps());
    System.out.println("Heart Rate: " + selectedHealthData.getHeartRate());
    System.out.println("Water Intake: " + selectedHealthData.getWaterIntake());
    System.out.println("Hours of Sleep: " + selectedHealthData.getHoursOfSleep());
    System.out.println("Date: " + selectedHealthData.getDate());
    System.out.println();
    
  
    System.out.println("Update Health Data");
    System.out.println("------------------");
    System.out.println("1. Update Weight");
    System.out.println("2. Update Height");
    System.out.println("3. Update Steps");
    System.out.println("4. Update Heart Rate");
    System.out.println("5. Update Water Intake");
    System.out.println("6. Update Hours of Sleep");
    System.out.println("7. Exit");
    System.out.println();
    System.out.print("Please enter the number of your choice: ");
    
    int choice = in.nextInt();
    in.nextLine(); 
    
    switch (choice) {
        case 1:
            System.out.print("Enter new weight: ");
            double newWeight = in.nextDouble();
            selectedHealthData.setWeight(newWeight);
            break;
        case 2:
            System.out.print("Enter new height: ");
            double newHeight = in.nextDouble();
            selectedHealthData.setHeight(newHeight);
            break;
        case 3:
            System.out.print("Enter new steps count: ");
            int newSteps = in.nextInt();
            selectedHealthData.setSteps(newSteps);
            break;
        case 4:
            System.out.print("Enter new heart rate: ");
            int newHeartRate = in.nextInt();
            selectedHealthData.setHeartRate(newHeartRate);
            break;
        case 5:
            System.out.print("Enter new water intake amount in ounces: ");
            int newWaterIntake = in.nextInt();
            selectedHealthData.setWaterIntake(newWaterIntake);
            break;
        case 6:
            System.out.print("Enter new hours of sleep: ");
            int newHoursOfSleep = in.nextInt();
            selectedHealthData.setHoursOfSleep(newHoursOfSleep);
            break;
        case 7:
            return;
        default:
            System.out.println("Opps! It appears that option is not available. Please choose again.");
            return;
    }
  
    boolean updateSuccess = healthDataDao.updateHealthData(selectedHealthData);
    if (updateSuccess) {
        System.out.println();
        System.out.println("Your Health Data record has been updated successfully.");
    } else {
        System.out.println("Opps! Something went wrong. Please try again.");
    }
}
/**
     * Deletes a specific health data record of the current user after confirming the deletion.
     * Displays a list of the user's health records and prompts for the record to delete.
     */
    private static void deleteHealthDataRecord() {

        System.out.println();
        System.out.println("Deleting your health record will permanently remove all the data associated with it.");
        System.out.println();
    
        List<HealthData> healthDataList = healthDataDao.getHealthDataByUserId(userId);
    
        if (healthDataList.isEmpty()) {
            System.out.println("No health records found.");
            return; 
        }

        System.out.println();
        System.out.println("Health Records");
        System.out.println("--------------");
        System.out.println();
        for (int i = 0; i < healthDataList.size(); i++) {
        HealthData healthData = healthDataList.get(i);
        System.out.println((i + 1) + ". Date: " + healthData.getDate());
        }
        System.out.println();
    
          
        System.out.print("Enter the ID of the health record you want to delete: ");

        int idToDelete = in.nextInt();

        if (idToDelete < 1 || idToDelete > healthDataList.size()) {
            System.out.println("Invalid input. Please enter a valid record number.");
            return;
        }
    
        int recordToDelete = healthDataList.get(idToDelete - 1).getId();
    
        System.out.print("Delete Health Data Record (Y/N): ");
        String confirmation = in.next();
        if (confirmation.equalsIgnoreCase("Y")) {
            boolean isDeleted = healthDataDao.deleteHealthData(recordToDelete);
            if (isDeleted) {
                System.out.println();
                System.out.println("Your health record has been successfully deleted.");
                System.out.println();
    
            } else {
                System.out.println("Oops! Something went wrong. Please try again.");
            }
        } else {
            System.out.println("This health record will remain on file.");
        }
    }
/**
     * Retrieves health recommendations based on the latest health data of the current user.
     * Uses a recommendation system to generate recommendations.
     */ 
    private static void retrieveHealthRecommendations() {
       
        List<HealthData> healthDataList = healthDataDao.getHealthDataByUserId(userId);
        
        if (healthDataList.isEmpty()) {
            System.out.println("No health data found for the current user. Please add health data first.");
            return;
        }
               
        HealthData latestHealthData = healthDataList.get(healthDataList.size() - 1);
                  
        List<String> recommendations = recommendationSystem.generateRecommendations(latestHealthData);
    
        System.out.println();
        System.out.println("Health Recommendations");
        System.out.println("----------------------");
        System.out.println();
        for (String recommendation : recommendations) {
            System.out.println("* " + recommendation);
            System.out.println(); 
        }
    }
/**
     * Allows the user to add a new medication reminder by specifying details such as medication name, dosage, schedule, start date, and end date.
     * Prompts the user for input and creates a new MedicationReminder object, then saves it using the MedicineReminderManager.
     */
    private static void addMedicationReminder() {

        System.out.println();
        System.out.println("New Medication Reminder");
        System.out.println("-----------------------");
        System.out.println();
        
        System.out.print("Medicine Name: ");
        String medicineName = in.next();
        in.nextLine();
        
        System.out.print("Dosage: ");
        String dosage = in.nextLine();
        
        System.out.println();
        System.out.println(" Please Choose Your Medication Schedule");
        System.out.println();

        System.out.println("1. Every 24 hours");
        System.out.println("2. Every 12 hours");
        System.out.println("3. Every 8 hours");
        System.out.println();
        System.out.print("Please enter the number of your choice: ");
        int choice = in.nextInt();
        in.nextLine(); 
        
        String schedule;
        switch (choice) {
            case 1:
                schedule = "Every 24 hours";
                break;
            case 2:
                schedule = "Every 12 hours";
                break;
            case 3:
                schedule = "Every 8 hours";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to every 24 hours.");
                schedule = "Every 24 hours";
                break;
        }
        
        System.out.print("Start Date (MM-DD-YYYY): ");
        String startDateString = in.next();
        LocalDate startDate = parseDate(startDateString);
        if (startDate == null) {
            return;
        }
        
        System.out.print("End Date (MM-DD-YYYY): ");
        String endDateString = in.next();
        LocalDate endDate = parseDate(endDateString);
        if (endDate == null) {
            return;
        }

    
        MedicineReminder reminder = new MedicineReminder(0, userId, medicineName, dosage, schedule, startDate, endDate);
        medicineManager.addReminder(reminder);
    }
  
    private static LocalDate parseDate(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    LocalDate date = null;
    try {
        date = LocalDate.parse(dateString, formatter);
    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please enter date in MM-DD-YYYY format.");
    }
    return date;
}
/**
     * Displays the list of medication reminders for the current user.
     * Retrieves medication reminders from the MedicineReminderManager and displays them to the user.
     */
    private static void displayMedicationReminders() {

        List<MedicineReminder> reminders = medicineManager.getAllReminders(userId);
        if (reminders.isEmpty()) {
            System.out.println("No medication reminders found for the current user.");
        } else {
            System.out.println();
            System.out.println("Medication Reminders");
            System.out.println("--------------------");
            System.err.println();
            for (MedicineReminder reminder : reminders) {
                System.out.println(reminder);
                System.out.println();
            }
        }    
    }
/**
     * Displays a list of medication reminders that are overdue for the current user.
     * Retrieves overdue medication reminders from the MedicineReminderManager and displays them to the user.
     */
    private static void displayOverdueMedicationReminders(){

        List<MedicineReminder> overdueReminders = medicineManager.getDueReminders(userId);
    
        if (overdueReminders.isEmpty()) {
            System.out.println("You have no overdue medication reminders at this time.");
        } else {
            System.out.println();
            System.out.println("Overdue Medication Reminders");
            System.out.println("----------------------------");
            for (MedicineReminder reminder : overdueReminders) {
                System.out.println(reminder); 
                System.out.println();
            }

            in.nextLine();
            System.out.println("Do you want to delete any overdue reminders? (Y/N)");
            String response = in.nextLine().trim().toLowerCase();
            if (response.equals("y")) {
                deleteOverdueReminder(overdueReminders);
            }
        }
    }
 /**
     * Displays a list of patients assigned to the doctor.
     * Retrieves the list of patients associated with the current doctor, then displays their user details.
     */  
    private static void viewPatientsForDoctor() {

        int doctorId = userId;
        User doctor = userDao.getUserById(doctorId);
        List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctorId);
        if (!patients.isEmpty()) {
            System.out.println();
            System.out.println("Detailed Patient List for Doctor " + doctor.getFirstName() + " " + doctor.getLastName());
            System.out.println("----------------------------------------------");
            System.out.println();
            for (User patient : patients) {
                System.out.println("Patient ID: " + patient.getId());
                System.out.println("Name: " + patient.getFirstName() + " " + patient.getLastName());
                System.out.println("Email: " + patient.getEmail());
                System.out.println(); 
                
                System.out.println();               
            }
        } else {
            System.out.println("No patients found for Doctor " + doctor.getFirstName() + " " + doctor.getLastName());
        }
    }
/**
     * Allows the doctor to view health data records for a selected patient.
     * Prompts the doctor to select a patient and then displays the health data records for that patient.
     */       
    private static void viewHealthDataForPatient() {

        
        int doctorId = userId;
        Doctor doctor = doctorPortalDao.getDoctorById(userId);
        List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctorId);
    
        if (!patients.isEmpty()) {
            System.out.println();
            System.out.println("Patient List for Doctor " + doctor.getFirstName() + " " + doctor.getLastName());
            System.out.println("---------------------------------");

            int index = 1;
            System.out.println();
            for (User patient : patients) {
                System.out.println(index++ + ". " +  patient.getFirstName() + " " + patient.getLastName());
            }

            System.out.println();
    
            System.out.print("Select a patient by entering the number of your choice: ");
            int selectedPatient = in.nextInt();
            in.nextLine(); 
    
            if (selectedPatient >= 1 && selectedPatient <= patients.size()) {
                int selectedPatientId = patients.get(selectedPatient - 1).getId();
    
                List<HealthData> healthDataList = doctorPortalDao.getHealthDataByPatientId(selectedPatientId);
                if (!healthDataList.isEmpty()) {
                    System.out.println();
                    for (HealthData healthData : healthDataList) {
                        User patient = patients.get(selectedPatient - 1);
                        System.out.println("Health Data for " + patient.getFirstName() + " " + patient.getLastName() + "\n" +
                       "------------------------------\n" + healthData); 
                        
                    }
                } else {
                    System.out.println();
                    User patient = patients.get(selectedPatient - 1);
                    System.out.println("No health data found for Patient " + patient.getFirstName() + " " + patient.getLastName() + ".");
                }
            } else {
                System.out.println();
                System.out.println("Opps! It appears that choice is invalid. Please choose again.");
            }
        } else {
            System.out.println();
            System.out.println("No patients found for Doctor with ID " + doctor.getFirstName() + " " +  doctor.getLastName());
        }
    }
/**
     * Allows the doctor to prescribe medication for a selected patient.
     * Prompts the doctor to select a patient and then enters details of the medication reminder.
     */
    public static void prescribeMedication() {

        int doctorId = userId;
        Doctor doctor = doctorPortalDao.getDoctorById(userId);
        List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctorId);
    
        if (!patients.isEmpty()) {
            System.out.println();
            System.out.println("Patient List for Doctor " + doctor.getFirstName() + " " + doctor.getLastName());
            System.out.println("-----------------------------------");
    
            int index = 1;
            System.out.println();
            for (User patient : patients) {
                System.out.println(index++ + ". " +  patient.getFirstName() + " " + patient.getLastName());
            }
            System.out.println();
    
            System.out.print("Select a patient by entering the number of your choice: ");
            int selectedPatientIndex = in.nextInt();
            in.nextLine(); 
    
            if (selectedPatientIndex >= 1 && selectedPatientIndex <= patients.size()) {
                User selectedPatient = patients.get(selectedPatientIndex - 1);
    
                System.out.println();
                System.out.println("New Medication Reminder");
                System.out.println("-----------------------");
                System.out.println();
    
                System.out.print("Medicine Name: ");
                String medicineName = in.nextLine();
    
                System.out.print("Dosage: ");
                String dosage = in.nextLine();
    
                System.out.println();
                System.out.println("Please Choose Your Medication Schedule");
                System.out.println();
    
                System.out.println("1. Every 24 hours");
                System.out.println("2. Every 12 hours");
                System.out.println("3. Every 8 hours");
                System.out.println();
                System.out.print("Please enter the number of your choice: ");
                int choice = in.nextInt();
                in.nextLine(); 
    
                String schedule;
                switch (choice) {
                    case 1:
                        schedule = "Every 24 hours";
                        break;
                    case 2:
                        schedule = "Every 12 hours";
                        break;
                    case 3:
                        schedule = "Every 8 hours";
                        break;
                    default:
                        System.out.println("Invalid choice. Defaulting to every 24 hours.");
                        schedule = "Every 24 hours";
                        break;
                }
    
                System.out.print("Start Date (MM-DD-YYYY): ");
                String startDateString = in.next();
                LocalDate startDate = parseDate(startDateString);
                if (startDate == null) {
                    return;
                }
    
                System.out.print("End Date (MM-DD-YYYY): ");
                String endDateString = in.next();
                LocalDate endDate = parseDate(endDateString);
                if (endDate == null) {
                    return;
                }
    
                MedicineReminder reminder = new MedicineReminder(0, userId, medicineName, dosage, schedule, startDate, endDate);
    
                doctorPortalDao.addMedicineReminderForPatient(selectedPatient.getId(), reminder);
            } else {
                System.out.println("Invalid choice. Please select a valid patient.");
            }
        } else {
            System.out.println("No patients found for this doctor.");
        }
    }
 /**
     * Displays the list of medication reminders for a selected patient.
     * Prompts the doctor to select a patient and then displays their medication reminders.
     */   
    private static void viewPatientMedicineReminders() {

        int doctorId = userId;
        Doctor doctor = doctorPortalDao.getDoctorById(userId);

        List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctorId);
    
        if (!patients.isEmpty()) {
            System.out.println();
            System.out.println("Patient List for Doctor " + doctor.getFirstName() + " " + doctor.getLastName());
            System.out.println("-----------------------------------");
    
            int index = 1;
            System.out.println();
            for (User patient : patients) {
                System.out.println(index++ + ". " +  patient.getFirstName() + " " + patient.getLastName());
            }
            System.out.println();

            System.out.println("Select a patient to view their medicine reminders:");
           
            int choice = in.nextInt();
    
            if (choice < 1 || choice > patients.size()) {
                System.out.println("Invalid choice. Please select a valid patient.");
                return;
            }
    
            User selectedPatient = patients.get(choice - 1);
    
            List<MedicineReminder> reminders = medicineManager.getAllReminders(selectedPatient.getId());
    
            if (reminders.isEmpty()) {
                System.out.println("No medicine reminders found for " + selectedPatient.getFirstName() + " " + selectedPatient.getLastName());
            } else {
                System.out.println();
                System.out.println("Medicine reminders for " + selectedPatient.getFirstName() + " " + selectedPatient.getLastName());
                System.out.println("------------------------------------");
                for (MedicineReminder reminder : reminders) {
                    System.out.println(reminder);
                    System.out.println();
                }
            }
        } else {
            System.out.println("No patients found for this doctor.");
        }
    }
/**
     * Deletes an overdue medication reminder.
     * Prompts the doctor to select a medication reminder from the list of overdue reminders and deletes it.
     */  
    private static void deleteOverdueReminder(List<MedicineReminder> overdueReminders) {

        System.out.println("Enter the Reminder ID of the medication reminder you wish to delete:");
        int reminderToDelete = in.nextInt();
        in.nextLine(); 
        
        boolean reminderFound = false;
        for (MedicineReminder reminder : overdueReminders) {
            if (reminder.getId() == reminderToDelete) {
                medicineManager.deleteMedicineReminder(reminderToDelete);
                reminderFound = true;
                break;
            }
        }
        
        if (!reminderFound) {
            System.out.println("No medication reminder found with the ID " + reminderToDelete + ". Please choose again.");
        }
    }
 /**
     * Allows the user to update an existing medication reminder.
     * Retrieves a list of the user's medication reminders, prompts for the one to update,
     * then allows the user to modify its details such as medication name, dosage, schedue, start date, and end date.
     */   
    private static void updateMedicationReminder() {
        
        List<MedicineReminder> remindersList = medicineManager.getAllReminders(userId);

        if (remindersList.isEmpty()) {
            System.out.println("No medicine reminders found for the current user. Please enter a medicine reminder.");
            return;
        }
        
        System.out.println();
        System.out.println("Medicine Reminders");
        System.out.println("------------------");
        System.out.println();
        for (int i = 0; i < remindersList.size(); i++) {
            MedicineReminder reminder = remindersList.get(i);
            System.out.println((i + 1) + ". Medicine Name: " + reminder.getMedicineName());
        }
        System.out.println();
        
        System.out.print("Please choose the number of the medicine reminder you wish to update: ");
     
        int selectedNumber = in.nextInt();
        in.nextLine(); 
        
        if (selectedNumber < 1 || selectedNumber > remindersList.size()) {
            System.out.println("Opps! it appears that choice is invalid. Please choose again.");
            return;
        }
        
        MedicineReminder selectedReminder = remindersList.get(selectedNumber - 1);
        
        System.out.println();
        System.out.println("Current Medicine Reminder");
        System.out.println("-------------------------");
        System.out.println("Medicine: " + selectedReminder.getMedicineName());
        System.out.println("Dosage: " + selectedReminder.getDosage());
        System.out.println("Schedule: " + selectedReminder.getSchedule());
        System.out.println("Start Date: " + selectedReminder.getStartDate());
        System.out.println("End Date: " + selectedReminder.getEndDate());
        System.out.println();
        
     
        System.out.println("Update Medicine Reminder");
        System.out.println("------------------------");
        System.out.println("1. Update Medicine Name");
        System.out.println("2. Update Dosage");
        System.out.println("3. Update Schedule");
        System.out.println("4. Update Start Date");
        System.out.println("5. Update End Date");
        System.out.println("6. Exit");
        System.out.println();
        System.out.print("Please enter the number of your choice: ");
        
        int choice = in.nextInt();
        in.nextLine(); 
        
        switch (choice) {
            case 1:
                System.out.print("Enter new medicine name: ");
                String newMedicineName = in.nextLine();
                selectedReminder.setMedicineName(newMedicineName);
                break;
            case 2:
                System.out.print("Enter new dosage: ");
                String newDosage = in.nextLine();
                selectedReminder.setDosage(newDosage);
                break;
            case 3:
            System.out.println("Select the schedule option:");
            System.out.println("1. Every 8 hours");
            System.out.println("2. Every 12 hours");
            System.out.println("3. Every 24 hours");
            int scheduleOption = Integer.parseInt(in.nextLine());
        
            String newSchedule = "";
            switch (scheduleOption) {
                case 1:
                    newSchedule = "every 8 hours";
                    break;
                case 2:
                    newSchedule = "every 12 hours";
                    break;
                case 3:
                    newSchedule = "every 24 hours";
                    break;
                default:
                    System.out.println("Invalid schedule option. Using default 'every 24 hours'.");
                    newSchedule = "every 24 hours";
            }
            selectedReminder.setSchedule(newSchedule);
            break;
            case 4:
                System.out.print("Enter new start date (YYYY-MM-DD): ");
                LocalDate newStartDate = LocalDate.parse(in.nextLine());
                selectedReminder.setStartDate(newStartDate);
                break;
            case 5:
                System.out.print("Enter new end date (YYYY-MM-DD): ");
                LocalDate newEndDate = LocalDate.parse(in.nextLine());
                selectedReminder.setEndDate(newEndDate);
                break;
            case 6:
                return;
            default:
                System.out.println("Opps! It appears that option is not available. Please choose again.");
                return;
        }

        boolean updateSuccess = medicineManager.updateMedicationReminder(selectedReminder);
        if (updateSuccess) {
            System.out.println();
            System.out.println("Medication Reminder updated successfully.");
        } else {
            System.out.println("Opps! Something went wrong. Please try again.");
        }
    }
    

}










