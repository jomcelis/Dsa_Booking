
package com.example.reservation;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.*;
import javax.swing.JOptionPane;



public class ReservationBooking {



    private static final String filePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\customer_data.txt";


    public ReservationBooking() {
    }

    private final String[] choices ={"General check up","Tooth Extraction","ramen","X-Ray", "Consultation"};

    public String date;

    @FXML
    public Button BookBtn;

    @FXML
    public ChoiceBox<String> DropDownBtn;


    @FXML
    private Button backBtn;

    @FXML
    private RadioButton elevenAm;

    @FXML
    private RadioButton nineAm;

    @FXML
    private RadioButton onePM;

    @FXML
    private RadioButton tenAm;

    @FXML
    private RadioButton threePM;

    @FXML
    private RadioButton twoPM;


    @FXML
    private TextField inputAge;

    @FXML
    private TextField inputLastName;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputNumber;

    //Action Buttons

    @FXML
    private void initialize() throws IOException {
        // Initialize the mainQueue here or obtain it from another source.// Replace this with your actual initialization code.
        File file = new File(filePath);

        // Check if the file exists, and create it if it doesn't
        if (!file.exists()) {
            file.createNewFile();
        }
        // Add a listener to capture the selected date when a radio button is clicked.
        ToggleGroup dateToggleGroup = new ToggleGroup();
        nineAm.setToggleGroup(dateToggleGroup);
        tenAm.setToggleGroup(dateToggleGroup);
        elevenAm.setToggleGroup(dateToggleGroup);

        onePM.setToggleGroup(dateToggleGroup);
        twoPM.setToggleGroup(dateToggleGroup);
        threePM.setToggleGroup(dateToggleGroup);

        dateToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (dateToggleGroup.getSelectedToggle() != null) {
                date = dateToggleGroup.getSelectedToggle().getUserData().toString();
            }
        });

        // Set user data for radio buttons to store the date.
        nineAm.setUserData("9:00 AM");
        tenAm.setUserData("10:00 AM");
        elevenAm.setUserData("11:00 AM");

        onePM.setUserData("1:00 PM");
        twoPM.setUserData("2:00 PM");
        threePM.setUserData("3:00 PM");

        DropDownBtn.setItems(FXCollections.observableArrayList(choices));
    }


    @FXML
    void DropDownBtn_Click(MouseEvent event) {
    }

    @FXML
    void BookBtn_Click(ActionEvent event) {
        String name = inputName.getText();
        String lastName = inputLastName.getText();
        String ageText = inputAge.getText();
        String contactNumber = inputNumber.getText();
        String selectedDate = date;
        String reason = DropDownBtn.getValue();

        if (name.isEmpty() || lastName.isEmpty() || ageText.isEmpty() || reason == null) {
            JOptionPane.showMessageDialog(null, "Missing/Wrong input", "Error", JOptionPane.ERROR_MESSAGE);
            clearTextFields();
        } else {
            try {
                int age = Integer.parseInt(ageText);

                if (age < 0) {
                    JOptionPane.showMessageDialog(null, "Negative numbers not allowed.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!contactNumber.matches("\\d{11}")) {
                    JOptionPane.showMessageDialog(null, "Contact number should contain exactly 11 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (containsSpecialCharacters(name) || containsSpecialCharacters(lastName)) {
                    JOptionPane.showMessageDialog(null, "Name and Last Name should not contain special characters.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    createFolder();
                    addData(name, lastName, ageText, contactNumber, selectedDate, reason);
                    JOptionPane.showMessageDialog(null, "Successful booking!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a numeric value for age.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void clearTextFields() {
        inputName.setText("");
        inputLastName.setText("");
        inputAge.setText("");
        inputNumber.setText("");
    }

    private boolean containsSpecialCharacters(String text) {
        return !text.matches("^[a-zA-Z0-9 ]*$");
    }



//    @FXML
//    void backBtn_Click(ActionEvent event) throws IOException {
//        Main m = new Main();
//        m.changeScene("UserHomepage.fxml");
//    }


//    @FXML
//    private void createReservationBooking() {
//        // Create an instance of ReservationBooking and pass the MainQueue instance.
//        ReservationBooking reservationBooking = new ReservationBooking(mainQueue);
//        // Add it to your UI, e.g., using a layout pane or as needed.
//    }

    @FXML
    void inputAgeAction(ActionEvent event) {

    }

    @FXML
    void inputLastNameAction(ActionEvent event) {

    }

    @FXML
    void inputNameAction(ActionEvent event) {

    }

    @FXML
    void inputNumberAction(ActionEvent event) {

    }


    @FXML
    void nineAm_Action(ActionEvent event) {

    }

    @FXML
    void onePM_Action(ActionEvent event) {

    }

    @FXML
    void tenAm_Action(ActionEvent event) {

    }

    @FXML
    void threePM_Action(ActionEvent event) {

    }

    @FXML
    void twoPM_Action(ActionEvent event) {

    }

    @FXML
    void elevenAm_Action(ActionEvent event) {

    }

    void addData(String name, String lastName, String age, String contactNumber, String date, String reason) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String existingDate = parts[3].trim(); // Assuming the date is at index 3

                // Check if the date already exists
                if (existingDate.equals(date)) {
                    JOptionPane.showMessageDialog(null, "This timeslot is already booked.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method if the timeslot is already booked
                }
            }

            // Write the new booking to the file if the timeslot is available
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(name + ",");
                writer.write(lastName + ",");
                writer.write(contactNumber + ",");
                writer.write(date + ",");
                writer.write(reason + "\r\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle or log the exception properly
        }
    }


    void createFolder() {
        File fileDirectory = new File("C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\Clients.txt");
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
    }


}