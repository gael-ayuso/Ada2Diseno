package disenio.ada2.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class MenuController {
    public static final String VISTA_LOGIN = "/resources/views/login.fxml";

    static Alert defaultAlert;
    static ButtonType acceptButton = new ButtonType("Aceptar");
    public static HashMap<String, String > filePaths = new HashMap<>();

    void CloseCurrentStage(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    public void openNewStage(String fxmlFileName, String title){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MenuController.class.getResource(fxmlFileName));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            configureStageCloseEvent(stage, fxmlFileName, title);
            stage.show();
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void configureStageCloseEvent(Stage stage, String fxmlFileName, String title) {
        if (!fxmlFileName.equals(VISTA_LOGIN)) {
            stage.setOnCloseRequest(e -> {
                openNewStage(getFxmlFather(fxmlFileName),title);
            });
        }
    }

    String getFxmlFather(String fxml){
        return filePaths.get(fxml);
    }

    static public void setAlert(Alert.AlertType alertType,String argument){
        defaultAlert = new Alert(alertType);
        defaultAlert.setTitle("Informacion");
        defaultAlert.setHeaderText(null);
        defaultAlert.getButtonTypes().setAll(acceptButton);
        defaultAlert.setContentText(argument);
        defaultAlert.showAndWait();
    }

    static public void cleanCells(TextField...cells){
        for (TextField e:cells)
            e.clear();
    }
}
