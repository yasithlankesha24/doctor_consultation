import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GUI extends JFrame {

    private ArrayList<Doctor> doctorList = WestminsterSkinConsultationManager.getDoctors();
    private String[] patientNames = getAllPatientNames();
    private JTable doctorTable;
    private DefaultTableModel dtmDoctor;
    private JButton btnRefreshTblDoctors;
    private JButton btnRefreshTblConsultations;
    private JButton btnSort;
    private JLabel lblDoctorFullName;
    private JLabel lblDoctorML;
    private JLabel lblAvailable;
    private JDateChooser dateChooser;
    private JComboBox cmbTime;
    private JLabel lblDoctorSpec;
    private JComboBox<String> cmbPatient;
    private JTextField txtPatientId;
    private JTextField txtPatientName;
    private JTextField txtPatientSurName;
    private JTextField txtPatientDob;
    private JTextField txtPatientMNumber;
    private JLabel lblCost;
    private JTextArea txtNote;
    private JButton btnConsultant;
    private JTable channelTable;
    private DefaultTableModel dtmChannel;
    private JButton btnClear;
    private JButton btnCheckAvailable;
    private JButton btnDeleteConsultation;
    private JTextField txtDateConsultation;

    public GUI() {
        setSize(1250,850);
        setTitle("Doctor Consultation");
        setBackground(Color.WHITE);
        JLabel title = new JLabel("Doctor Consultation");
        title.setFont(new Font("",3,25));

        JPanel northPane = new JPanel();
        northPane.setBackground(Color.WHITE);
        northPane.add(title);
        add("North",northPane);
        JPanel container = new JPanel();
        container.setLayout(new GridLayout(0,1));

        //doctors table
        JPanel p1 = new JPanel(new GridLayout(0,1));
        p1.add(doctorDetailsTable());
        container.add(p1);

        //Doctor, patient and consultation details
        JPanel p2 = new JPanel();
        p2.setBackground(Color.WHITE);
        p2.add(doctorDetailsPane());
        p2.add(new JLabel("        "));
        p2.add(patientDetails());
        p2.add(new JLabel("        "));
        p2.add(consultationDetails());
        container.add(p2);

        //Consultation table
        JPanel p3 = new JPanel(new GridLayout(0,1));
        p3.add(channelDetailsTable());
        container.add(p3);

        add("Center",container);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    //Patient Details
    private String[] getAllPatientNames(){
        String[] patientDetails = new String[Database.patientArrayList.size()];
        int count=0;
        for (Patient p:Database.patientArrayList) {
            patientDetails[count++]=p.getId()+" "+p.getName()+" "+p.getSurname();
        }
        return patientDetails;
    }

    public JPanel doctorDetailsTable(){

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add("West",new JLabel("             "));
        panel.add("East",new JLabel("             "));
        panel.setBackground(Color.WHITE);
        String[] columns = {"SurName","First Name","DOB","Mobile Number","Medical Licence Number","Specialization"};
        dtmDoctor = new DefaultTableModel(columns,5);
        doctorTable = new JTable(dtmDoctor);
        // doctorTable.setFont(new Font("", Font.PLAIN,15));
        doctorTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowId = doctorTable.getSelectedRow();
                lblDoctorFullName.setText(doctorList.get(rowId).getSurname()+" "+doctorList.get(rowId).getName());
                lblDoctorML.setText(String.valueOf(doctorList.get(rowId).getMedicalLicenceNumber()));
                lblDoctorSpec.setText(doctorList.get(rowId).getSpecialization());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        JScrollPane tablePane = new JScrollPane(doctorTable);
        panel.add("Center",tablePane);
        btnRefreshTblDoctors = new JButton("Refresh");
        btnRefreshTblDoctors.setBackground(Color.WHITE);
        btnRefreshTblDoctors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                dtmDoctor.setRowCount(0);
                for (Doctor doctor:doctorList) {
                    Object[] rowData = {doctor.getSurname(),
                            doctor.getName(),
                            doctor.getDob(),
                            doctor.getMobileNumber(),
                            doctor.getMedicalLicenceNumber(),
                            doctor.getSpecialization()
                    };
                    dtmDoctor.addRow(rowData);
                }
            }
        });
        btnSort = new JButton("Sorting a Doctors list");
        btnSort.setBackground(Color.WHITE);
        btnSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doctorList = WestminsterSkinConsultationManager.getAllDoctors();
                btnRefreshTblDoctors.doClick();
            }
        });

        btnRefreshTblDoctors.doClick();
        JPanel btnPane = new JPanel();
        btnPane.add(btnRefreshTblDoctors);
        btnPane.add(btnSort);
        btnPane.setBackground(Color.WHITE);
        panel.add("South",btnPane);
        return panel;
    }

    public JPanel doctorDetailsPane(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);

        panel.setLayout(new GridLayout(0,2,5,5));
        panel.add(new JLabel("(Select a  Doctor "));
        panel.add(new JLabel("from the Doctor's table) "));
        panel.add(new JLabel("Full Name                        : "));
        panel.add(lblDoctorFullName = new JLabel("-"));
        panel.add(new JLabel("Licence Number            : "));
        panel.add(lblDoctorML = new JLabel("-"));
        panel.add(new JLabel("Specialization                : "));
        panel.add(lblDoctorSpec = new JLabel("-"));
        panel.add(new JLabel("Consultation Date         : "));
        //panel.add(txtDateConsultation);
        panel.add(dateChooser=new JDateChooser());
        panel.add(new JLabel("Select time slot"));
        String[] times = {"9.00 - 9.30 AM    ","9.30 - 10.00 AM","10.30 - 11.00 AM"};
        cmbTime = new JComboBox<>(times);
        panel.add(cmbTime);
        btnCheckAvailable = new JButton("    Check Availability    ");

        btnCheckAvailable.addActionListener(checkDoctorAvailability());
        btnCheckAvailable.setBackground(Color.WHITE);
        panel.add(new JLabel(""));
        panel.add(btnCheckAvailable);

        panel.add(lblAvailable=new JLabel("Availability                       : "));

        panel.add(lblAvailable = new JLabel("-"));
        panel.add(new JLabel(" "));

        return panel;
    }

    private  ActionListener checkDoctorAvailability(){
        ActionListener listener;
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Date date = dateChooser.getDate();

                    int mlNumber = Integer.parseInt(lblDoctorML.getText());

                    boolean isAvailable = isDoctorAvailable(date, mlNumber,cmbTime.getSelectedIndex());
                    if (isAvailable) {
                        lblAvailable.setText("Yes");
                    } else {
                        lblAvailable.setText("No");
                    }
                }catch (Exception exception){
                    showMessage(" please select a Doctor and Date");
                }
            }
        };
        return listener;
    }

    public JPanel patientDetails(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2,10,10));

        panel.add(new JLabel("Select Patient      "));
        cmbPatient = new JComboBox<>(getAllPatientNames());
        panel.add(cmbPatient);

        panel.add(new JLabel("Patient Id : "));
        panel.add(txtPatientId = new JTextField(20));
        panel.add(new JLabel("Patient Name : "));
        panel.add(txtPatientName = new JTextField(20));
        panel.add(new JLabel("Patient SurName : "));
        panel.add(txtPatientSurName = new JTextField(20));
        panel.add(new JLabel("Patient DoB : "));
        panel.add(txtPatientDob = new JTextField(20));
        panel.add(new JLabel("Patient Mobile Number : "));
        panel.add(txtPatientMNumber = new JTextField(20));
        panel.add(new JLabel(" "));
        // if patient is a past patient, In here All the past Patients Are available
        cmbPatient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = (String)cmbPatient.getSelectedItem();
                String id = (text.split(" ", 2))[0];
                for (Patient p : Database.patientArrayList) {
                    if (id.equalsIgnoreCase(p.getId())) {
                        txtPatientId.setText(p.getId());
                        txtPatientName.setText(p.getName());
                        txtPatientSurName.setText(p.getSurname());
                        txtPatientDob.setText(p.getDob());
                        txtPatientMNumber.setText(p.getMobileNumber());
                    }
                }
            }
        });
        panel.setBackground(Color.WHITE);
        return panel;
    }

    public JPanel consultationDetails(){
        JPanel panel = new JPanel();

        panel.setLayout(new BorderLayout());
        JPanel p1 = new JPanel();
        p1.add(new JLabel("Cost (Euro per Hour) : "));

        p1.add(lblCost = new JLabel("       _"));
        JPanel p2 = new JPanel(new BorderLayout());
        JPanel p3 = new JPanel(new BorderLayout());
        p2.setBackground(Color.WHITE);
        p3.setBackground(Color.WHITE);
        p2.setAlignmentX(Component.CENTER_ALIGNMENT);
        p3.add("North",new JLabel("Notes : "));
        p3.add("Center",txtNote = new JTextArea(5,20));
        p2.add("North",p3);
        JPanel p4 = new JPanel(new BorderLayout());


        p2.add("Center",p4);

        panel.add("North",p1);
        panel.add("Center",p2);
        JPanel p5 = new JPanel();
        btnConsultant = new JButton("Add a Appointment");
        btnConsultant.setFont(new Font("", Font.BOLD ,20));
        btnConsultant.setBackground(Color.WHITE);
        p5.add(btnConsultant);
        p5.setBackground(Color.WHITE);
        btnConsultant.addActionListener(addConsultation());
        panel.add("South", p5);

        return panel;
    }

    private ActionListener addConsultation(){
        ActionListener listener=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Patient patient = new Patient(txtPatientName.getText(),
                        txtPatientSurName.getText(),
                        txtPatientDob.getText(),
                        txtPatientMNumber.getText(),
                        txtPatientId.getText());
                if(patient.getId().equals("") || patient.getName().equals("")){
                    showMessage("Please enter Patient Id and First Name ");
                    return;
                }

                int mlNumber;
                try{
                    mlNumber = Integer.parseInt(lblDoctorML.getText());
                }catch (Exception exception){
                    showMessage("Please Select Doctor");
                    return;
                }
                Date date = dateChooser.getDate();
                Doctor doctor=null;

                if(date==null){
                    showMessage("Please Select Date");
                    return ;
                }
                /*given specific doctor is  available in selected date he will allocate to the consultation
                 or if selected doctor is not available in that date automatically doctor will assign
                 */
                if(isDoctorAvailable(date,mlNumber,cmbTime.getSelectedIndex())){
                    for (Doctor d : doctorList) {
                        if (d.getMedicalLicenceNumber() == mlNumber) {
                            doctor = d;
                            break;
                        }
                    }
                }else{
                    doctor =getARandomDoctor(date);
                    if(doctor==null){
                        showMessage("All doctors are Busy Select another date");
                        return;
                    }
                    showMessage("Selected Doctor is Busy!   " +
                            "Doctor : "+doctor.getSurname()+" "+doctor.getName()+"  -  " +doctor.getMedicalLicenceNumber()
                            +" Added Successfully!");
                }
                // add patient to the database
                int isDuplicate =0;
                for (Patient p :Database.patientArrayList) {
                    if( p.getId().equals(patient.getId())){
                        isDuplicate=1;
                        break;
                    }
                }
                // if user entered new Patient details, he adds to the database
                double cost ;
                String message;
                if(isDuplicate!=1){
                    Database.patientArrayList.add(patient);
                    cost =15;
                    lblCost.setText(String.valueOf(cost));
                    message = "New Patient Cost is 15 and";

                }else{
                    cost=25;
                    lblCost.setText(String.valueOf(cost));
                    message = "OLD Patient Cost is 25 and";
                }
                Database.consultationArrayList.add(new Consultation(doctor,patient,date,cost,txtNote.getText(),cmbTime.getSelectedIndex()));
                showMessage(message+ " Placed Consultation successfully");
                btnRefreshTblConsultations.doClick();
            }
        };
        return listener;
    }

    public JPanel channelDetailsTable(){

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add("West",new JLabel("             "));
        panel.add("East",new JLabel("             "));

        String[] columns = {"Doctor name","Doctor Medical Licence Number","Specialization","Patient ID","Patient Name","Date (dd-MM-yyyy)","Time Slot"
                ,"Cost","Notes"};
        dtmChannel = new DefaultTableModel(columns,6);
        channelTable = new JTable(dtmChannel);
        JScrollPane tablePane = new JScrollPane(channelTable);
        panel.add("Center",tablePane);
        channelTable.setFont(new Font("", Font.PLAIN,15));
        btnRefreshTblConsultations = new JButton("Refresh");
        btnRefreshTblConsultations.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                // if user entered new Patient details, he added to the combo box
                patientNames = getAllPatientNames();
                if(cmbPatient.getItemCount()!=patientNames.length){
                    cmbPatient.addItem(patientNames[patientNames.length-1]);
                }
                dtmChannel.setRowCount(0);
                for (Consultation c:Database.consultationArrayList) {
                    Object[] rowData = {c.getDoctor().getName()+" "+c.getDoctor().getSurname(),
                            c.getDoctor().getMedicalLicenceNumber(),
                            c.getDoctor().getSpecialization(),
                            c.getPatient().getId(),
                            c.getPatient().getName()+" "+c.getPatient().getSurname(),
                            c.getDate(),
                            c.getTime(),
                            c.getCost(),
                            c.getNote(),

                    };
                    dtmChannel.addRow(rowData);
                }
            }
        });
        JPanel btnPane = new JPanel();

        btnClear = new JButton("Clear Fields");
        btnClear.setBackground(Color.WHITE);
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        btnPane.add(btnRefreshTblConsultations);
        btnRefreshTblConsultations.setBackground(Color.WHITE);
        btnRefreshTblConsultations.doClick();
        btnDeleteConsultation = new JButton("Delete Consultation");
        btnDeleteConsultation.setBackground(Color.WHITE);
        btnDeleteConsultation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowId = channelTable.getSelectedRow();
                try {
                    int reply = JOptionPane.showConfirmDialog(null, "Are you Sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        Database.consultationArrayList.remove(rowId);
                        JOptionPane.showMessageDialog(null, "Deleted");
                        btnRefreshTblConsultations.doClick();
                    } else {
                        JOptionPane.showMessageDialog(null, "Canceled");

                    }
                }catch (Exception ex){
                    showMessage("Please select Consultation");
                }
            }
        });

        btnPane.add(btnDeleteConsultation);


        btnPane.add(btnClear);
        panel.setBackground(Color.WHITE);
        btnPane.setBackground(Color.WHITE);
        panel.add("South",btnPane);
        return panel;
    }

    private void showMessage(String message){
        JOptionPane optionPane = new JOptionPane(message,JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog("Message");
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    private void clear(){
        txtPatientId.setText("");
        txtPatientName.setText("");
        txtNote.setText("");
        txtPatientDob.setText("");
        txtPatientSurName.setText("");
        txtPatientMNumber.setText("");
        dateChooser.setDate(null);
        cmbTime.setSelectedIndex(0);
        lblDoctorFullName.setText("-");
        lblDoctorML.setText("-");
        lblDoctorSpec.setText("-");
        lblAvailable.setText("-");
        lblCost.setText("       _");
    }

    private boolean isDoctorAvailable(Date date,int MLNumber,int time){
        SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = stm.format(date);

        for (Consultation c:Database.consultationArrayList) {
            if(c.getDate().equalsIgnoreCase(stringDate)){
                if(c.getDoctor().getMedicalLicenceNumber()==MLNumber){
                    if(c.getTimeByInt()==time){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Doctor getARandomDoctor(Date date){
        Random r = new Random();
        SimpleDateFormat stm = new SimpleDateFormat("dd-MM-yyyy");
        String stringDate = stm.format(date);
        ArrayList<Doctor> temp=new ArrayList<>();
        temp.addAll(doctorList);
        for (Consultation c:Database.consultationArrayList) {
            if(c.getDate().equalsIgnoreCase(stringDate)){
                temp.remove(c.getDoctor());
            }
        }
        if(temp.size()==0){
            return null;
        }
        return temp.get(r.nextInt(temp.size()));
    }
}