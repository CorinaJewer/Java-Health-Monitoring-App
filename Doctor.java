/**
 * This class represents a Doctor, extending the functionality of the User class. It contains attributes specific to a doctor such as medical license number and specialization.
 */


public class Doctor extends User{
    /** The medical license number of the doctor. */
    private String medicalLicenseNumber;
    /** The specialization of the doctor. */
    private String specialization;

     /**
     * Constructs a Doctor object.
     *
     * @param id The ID of the doctor.
     * @param firstName The first name of the doctor.
     * @param lastName The last name of the doctor.
     * @param email The email address of the doctor.
     * @param password The password of the doctor's account.
     * @param isDoctor A boolean indicating whether the user is a doctor.
     * @param medicalLicenseNumber The medical license number of the doctor.
     * @param specialization The specialization of the doctor.
     */
    public Doctor(int id, String firstName, String lastName, String email, String password, boolean isDoctor, String medicalLicenseNumber, String specialization) {
        super(id, firstName, lastName, email, password, isDoctor);
        this.medicalLicenseNumber = medicalLicenseNumber;
        this.specialization = specialization;
    }

    /**
     * Gets the medical license number of the doctor.
     *
     * @return The medical license number of the doctor.
     */

    public String getMedicalLicenseNumber(){
        return medicalLicenseNumber;
    }

    /**
     * Sets the medical license number of the doctor.
     *
     * @param medicalLicenseNumber The medical license number to set.
     */
    public void setMedicalLicenseNumber(String medicalLicenseNumber){
        this. medicalLicenseNumber = medicalLicenseNumber;
    }

    /**
     * Gets the specialization of the doctor.
     *
     * @return The specialization of the doctor.
     */
    public String getSpecialization(){
        return specialization;
    }

    /**
     * Sets the specialization of the doctor.
     *
     * @param specialization The specialization to set.
     */
    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }
}

