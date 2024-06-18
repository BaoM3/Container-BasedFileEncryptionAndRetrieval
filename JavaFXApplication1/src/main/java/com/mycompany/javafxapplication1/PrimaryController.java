package com.mycompany.javafxapplication1;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class PrimaryController {

    @FXML
    private Button registerBtn;

    @FXML
    private TextField userTextField;

    @FXML
    private PasswordField passPasswordField;

    @FXML
    private void registerBtnHandler(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) registerBtn.getScene().getWindow();
        DB myObj = new DB();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("register.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 640, 480);
            secondaryStage.setScene(scene);
            secondaryStage.setTitle("Register a new User");
            secondaryStage.show();
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void dialogue(String headerMsg, String contentMsg) {
        Stage secondaryStage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 300, 300, Color.DARKGRAY);

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText(headerMsg);
        alert.setContentText(contentMsg);

        Optional<ButtonType> result = alert.showAndWait();
    }

    @FXML
    private void switchToSecondary(ActionEvent event) {
        Stage secondaryStage = new Stage();
        Stage primaryStage = (Stage) registerBtn.getScene().getWindow();
        try {
            DB myObj = new DB();
            String[] credentials = {userTextField.getText(), passPasswordField.getText()};
            if(myObj.validateUser(userTextField.getText(), passPasswordField.getText())){
                performActionAndLog("Login to the account", "N/A", "N/A");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("secondary.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 640, 480);
                secondaryStage.setScene(scene);
                SecondaryController controller = loader.getController();
                controller.setUserText(userTextField.getText());
                controller.initialise();
                secondaryStage.setTitle("User Profile");
                secondaryStage.show();
                primaryStage.close();
            }
            else{
                dialogue("Invalid User Name / Password","Please try again!");
                performActionAndLog("Tried to login to the account", "N/A", "N/A");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void performActionAndLog(String action, String fileName, String modifyingUser) throws ClassNotFoundException {
        String username = userTextField.getText();
       
        AuditTrail.logAccess(username,fileName, action, modifyingUser);
    }
}
