
package com.example.reservation;
import javafx.animation.ScaleTransition;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import javax.swing.*;
import java.io.*;
import javax.swing.JOptionPane;



public class ReservationBooking {



    private static final String filePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\customer_data.txt";


    public ReservationBooking() {
        scaleTransition = new ScaleTransition(Duration.millis(100), BookBtn);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
    }

    private final String[] choices ={"General check up","Tooth Extraction","Teeth whitening","X-Ray", "Consultation", "Braces"};

    public String date;

    @FXML
    public Button BookBtn;

    @FXML
    public ChoiceBox<String> DropDownBtn;

    @FXML
    public ComboBox<String> ComboBox;

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

        ComboBox.setItems(FXCollections.observableArrayList(choices));
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
        String reason = ComboBox.getValue();
addDataToReservations(name,lastName,ageText,contactNumber,reason);
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

    void addDataToReservations(String name, String lastName, String contactNumber, String date, String reason) {
        String reservationFilePath = "C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\Reservations.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(reservationFilePath, true))) {
            writer.write("Name: " + name + ",");
            writer.write(" Last Name: " + lastName + ",");
            writer.write(" Contact Number: " + contactNumber + ",");
            writer.write(" Date: " + date + ",");
            writer.write(" Reason: " + reason + System.getProperty("line.separator"));
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle or log the exception properly
        }
    }


    void addData(String name, String lastName, String age, String contactNumber, String date, String reason) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                // Check if there are enough parts and the date matches
                if (parts.length > 3 && parts[3].trim().equals(date)) {
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


    private void clearTextFields() {
        inputName.setText("");
        inputLastName.setText("");
        inputAge.setText("");
        inputNumber.setText("");
    }
    void createFolder() {
        File fileDirectory = new File("C:\\Users\\Lenovo\\Desktop\\BST\\Dsa_final\\src\\Customers\\Clients.txt");
        if (!fileDirectory.exists()) {
            fileDirectory.mkdirs();
        }
    }
    @FXML
    void BookBtn_MouseEntered(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), BookBtn);
        scaleTransition.setToX(1.1); // Scale X by 10%
        scaleTransition.setToY(1.1); // Scale Y by 10%
        scaleTransition.play();
    }


    @FXML
    void BookBtn_MouseExited(MouseEvent event) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), BookBtn);
        scaleTransition.setToX(1.0); // Restore X to its original size
        scaleTransition.setToY(1.0); // Restore Y to its original size
        scaleTransition.play();
    }

    private boolean containsSpecialCharacters(String text) {
        return !text.matches("^[a-zA-Z0-9 ]*$");
    }

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


    private ScaleTransition scaleTransition;
}