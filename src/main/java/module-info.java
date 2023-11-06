module com.example.reservation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.reservation to javafx.fxml;
    exports com.example.reservation;
}