import java.io.Serializable;

public class Person implements Serializable {
    private String name;
    private String surname;
    private String dob;
    private String mobileNumber;

    public Person(String name, String surname, String dob, String mobileNumber) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}