module com.example.pcos_journey {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pcos_journey to javafx.fxml;
    exports com.example.pcos_journey;
}