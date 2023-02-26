module com.example.endassignment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.endassignment to javafx.fxml;
    exports com.example.endassignment;
    exports com.example.endassignment.Controller;
    opens com.example.endassignment.Controller to javafx.fxml;
    opens com.example.endassignment.Model to javafx.base;
}