module com.ipc1.proyecto1_202003654 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    opens models to javafx.base; 
    opens com.ipc1.proyecto1_202003654 to javafx.fxml;
    exports com.ipc1.proyecto1_202003654;
}
