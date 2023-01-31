import java.io.Serializable;

public class Doctor extends Person  implements Serializable {
    private int medicalLicenceNumber;
    private String specialization;

    public Doctor(String name, String surname, String dob, String mobileNumber, int medicalLicenceNumber, String specialization) {
        super(name, surname, dob, mobileNumber);
        this.medicalLicenceNumber = medicalLicenceNumber;
        this.specialization = specialization;
    }

    public int getMedicalLicenceNumber() {
        return medicalLicenceNumber;
    }

    public void setMedicalLicenceNumber(int medicalLicenceNumber) {
        this.medicalLicenceNumber = medicalLicenceNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    public boolean equals(Doctor doctor){
        if(this.getMedicalLicenceNumber()==doctor.getMedicalLicenceNumber()){
            return true;
        }return false;
    }

    @Override
    public String toString() {
        return super.getSurname()+"\t"+super.getName()+"\t\t\t\t|\t"
                +medicalLicenceNumber+"\t\t\t\t\t|\t"
                +super.getDob()+"\t\t|\t"
                +super.getMobileNumber()+"\t\t|\t"
                +specialization+"\t\t\t|";
    }
}