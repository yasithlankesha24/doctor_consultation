public class Patient extends Person {
    private String id;

    public Patient(String name, String surname, String dob, String mobileNumber, String id) {
        super(name, surname, dob, mobileNumber);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}