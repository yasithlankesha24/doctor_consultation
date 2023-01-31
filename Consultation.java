import java.text.SimpleDateFormat;
import java.util.Date;

public class Consultation {
    private Doctor doctor;
    private Patient patient;
    private Date date;

    private int time;
    private double cost;
    private String note;

    public Consultation(Doctor doctor, Patient patient, Date date, double cost, String note,int time) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.cost = cost;
        this.note = note;
        this.time = time;
    }

    public String getDate() {
        SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = stm.format(this.date);
        return stringDate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    public String getTime() {
        return time==0?"9.00 - 9.30 AM":(time==1?"9.30 - 10.00 AM":"10.30 - 11.00 AM");

    }
    public int getTimeByInt() {
        return time;

    }

    public void setTime(int time) {
        this.time = time;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}