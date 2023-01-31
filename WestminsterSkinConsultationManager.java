import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{

    public static ArrayList<Doctor> doctorList = new ArrayList<>();
    public WestminsterSkinConsultationManager(){
        Scanner input = new Scanner(System.in);
        doctorList = loadDoctors();

        System.out.println("--- Westminster Skin Consultation Manager ---");
        while (true) {
            switch (menu()) {
                case 1:
                    System.out.println(addNewDoctor());
                    break;
                case 2:
                    System.out.println(deleteDoctor());
                    break;
                case 3:
                    viewAllDoctors();
                    break;
                case 4:
                    System.out.println(saveFile());
                    break;
                case 5:
                    new GUI();
                    break;
            }

            System.out.println(" ");
            System.out.print(" If you want to end this program, plz enter Q or press any key to continue this : ");
            String userInput= input.next();
            System.out.println(" ");
            if(userInput.equalsIgnoreCase("q")){
                System.out.println("Thank you!");
                break;
            }
        }
    }
    public static ArrayList<Doctor> getDoctors() {
        return doctorList;
    }

    private  int menu() {
        Scanner input = new Scanner(System.in);
        String option = "-1";
        int num;

        System.out.println("---------- MENU ----------");
        System.out.println("  [1] Add a new doctor");
        System.out.println("  [2] Delete a doctor");
        System.out.println("  [3] View all doctors");
        System.out.println("  [4] Save a File");
        System.out.println("  [5] GUI");
        System.out.println("--------------------------");

        while(true) {
            System.out.print("Enter your option (1-5) : ");
            option = input.next();
            try {
                num = Integer.parseInt(option);
                if (0 < num && num < 6) {
                    return num;
                }
            } catch (Exception e){
                System.out.println("You should enter correct option");
            }
            System.out.println(option);
        }
    }

    @Override
    public  String addNewDoctor() {
        if(doctorList.size()<=10) {
            doctorList.add(getDoctor(doctorList));
            return "This doctor was successfully added !";

        }return "Check Doctors List only 10 doctors can add";
    }

    private Doctor getDoctor(ArrayList<Doctor> doctors) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nYou must enter correct details\n");

        while(true) {
            System.out.print("First Name                : ");
            String name = input.next();
            System.out.print("Surname                   : ");
            String surname = input.next();

            try {
                System.out.print("Medical Licence Number    : ");
                String num = input.next();
                int medicalLicenceNumber = Integer.parseInt(num);
                /* assume surname is mandatory one and doctor medical license number have at least 5 numbers
                and id is also used medical license number    */

                if ((!(surname==null || medicalLicenceNumber < 10000)) && !isExists(medicalLicenceNumber)) {
                    System.out.print("Date of Birth(DD-MM-YYYY) : ");
                    String dob = input.next();
                    System.out.print("Mobile Number             : ");
                    String mobileNumber = input.next();
                    System.out.print("Specialization Area       : ");
                    String specialization = input.next();
                    return new Doctor(name, surname, dob, mobileNumber, medicalLicenceNumber, specialization);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("\nYOU SHOULD ENTER SURNAME AND LICENCE NUMBER (with at least 5 digits ) CORRECTLY\n");
        }
    }

    private boolean isExists(int num) {
        for (Doctor d : doctorList) {
            if (d.getMedicalLicenceNumber() == num) {
                System.out.println("You can't enter the same medical license number, plz try again !");
                return true;
            }
        }
        return false;
    }

    @Override
    public String deleteDoctor(){

        int validMedicalLicenceNumber = getValidMedicalLicenceNumber();
        for (Doctor doctor: doctorList){
            if( validMedicalLicenceNumber== doctor.getMedicalLicenceNumber()){
                if(confirmationMessage()) {
                    doctorList.remove(doctor);
                    return "This doctor was successfully deleted !";
                }else{
                    return "Canceled!";
                }
            }
        }
        return "Plz enter the correct medical licence number";
    }

    private int getValidMedicalLicenceNumber() {
        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Medical Licence Number    : ");
                String medicalLicenceNumber = input.next();
                int num = Integer.parseInt(medicalLicenceNumber);
                if (num > 10000) {
                    return num;
                }
                System.out.println("---Enter correct Medical number(it must be a 5 digit number)---");

            } catch (Exception e) {
                System.out.println("---Enter correct Medical number(it should be a Integer value)---");
            }
        }
    }

    private boolean confirmationMessage(){
        Scanner input = new Scanner(System.in);
        System.out.print("Are you sure (Y/N) : ");
        while (true){
            String option = input.next();
            if (option.equalsIgnoreCase("y") || option.equalsIgnoreCase("n")){
                return option.equalsIgnoreCase("y");
            } else {
                System.out.println("Enter Y or N");
            }
        }
    }

    @Override
    public void viewAllDoctors(){
        System.out.println("--------------------------------+---------------------------+-------------------+-------------------+-------------------+");
        System.out.println("Full Name \t\t\t\t\t\t| Medical Licence Number \t| D O B \t\t\t| Mobile Number \t| Specialize Area\t|");
        System.out.println("--------------------------------+---------------------------+-------------------+-------------------+-------------------+");
        for (Doctor doctor: getAllDoctors()){
            System.out.println(doctor);
        }
        System.out.println("--------------------------------+---------------------------+-------------------+-------------------+-------------------+");
    }

    @Override
    public String saveFile() {

        try {
            //save data
            FileWriter writer = new FileWriter("src/Doctors.txt");
            //save objects
            FileOutputStream outputStream = new FileOutputStream("src/Doctors.ser");
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);
            oos.writeObject(doctorList);
            writer.write("Full Name\t\tDate of Birth\tMobile Number\tM L Number\tSpecialization\n");
            writer.write("------------------------------------------------------------------------------------------\n");
            for (Doctor d:doctorList) {
                writer.write(stringDoctor(d));
            }
            writer.flush();
            return "All doctors are saved";
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }

    public static ArrayList<Doctor> getAllDoctors(){
        ArrayList<Doctor>  doctorArrayList=doctorList;
        return sortStrings(doctorArrayList);
    }

    private static ArrayList<Doctor> sortStrings (ArrayList<Doctor> doctors) {

        Doctor temp;
        for (int k = 0; k < doctors.size(); k++) {
            for (int j = k + 1; j < doctors.size(); j++) {
                if (doctors.get(k).getSurname().toLowerCase().compareTo(doctors.get(j).getSurname().toLowerCase()) > 0){
                    temp = doctors.get(k);
                    doctors.set(k,doctors.get(j));
                    doctors.set(j,temp);
                }
            }
        }
        return doctors;
    }

    public String stringDoctor (Doctor d){
        return d.getName()+"\t"+d.getSurname()+"\t|"+d.getDob()+"\t|"+d.getMobileNumber()+ "\t|"+d.getMedicalLicenceNumber()+"\t\t|"+d.getSpecialization()+" \n";
    }

    public ArrayList<Doctor> loadDoctors(){
        try {
            FileInputStream fileInputStream = new FileInputStream("src/Doctors.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Doctor> temp = (ArrayList<Doctor>) objectInputStream.readObject();
            return temp;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}