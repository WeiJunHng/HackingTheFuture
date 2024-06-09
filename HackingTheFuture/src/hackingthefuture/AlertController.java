/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class AlertController implements Initializable {

    @FXML
    private ImageView errorImg;

    @FXML
    private Label messageLabel;

    @FXML
    private Button okBtn;

    @FXML
    private ImageView successImg;

    @FXML
    private Label typeLabel;

    @FXML
    private VBox mainBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        errorImg.setVisible(false);
        successImg.setVisible(false);

        okBtn.setOnAction(event -> {
            ((Stage) okBtn.getScene().getWindow()).close();
        });
    }

    public void setSuccess(String message) {
        errorImg.setVisible(false);
        successImg.setVisible(false);

        mainBox.setId("Success");
        okBtn.setId("SuccessBtn");
        successImg.setVisible(true);
        typeLabel.setText("SUCCESS");
        messageLabel.setText(message);
    }

    public void setError(String message) {
        errorImg.setVisible(false);
        successImg.setVisible(false);

        mainBox.setId("Error");
        okBtn.setId("ErrorBtn");
        errorImg.setVisible(true);
        typeLabel.setText("ERROR");
        messageLabel.setText(message);
    }

    public static void showSuccessAlert(String message, Stage ownerStage) {
        try {
            AlertController temp = new AlertController();
            FXMLLoader loader = temp.getAlert();
            javafx.scene.Parent alertPage = loader.load();
            AlertController alertController = loader.getController();
            alertController.setSuccess(message);

            Stage stage = new Stage();
            Scene scene = new Scene((javafx.scene.Parent) alertPage, Color.TRANSPARENT);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(ownerStage);

            stage.setX(420);
            stage.setY(200);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void showErrorAlert(String message, Stage ownerStage) {
        try {
            AlertController temp = new AlertController();
            FXMLLoader loader = temp.getAlert();
            javafx.scene.Parent alertPage = loader.load();
            AlertController alertController = loader.getController();
            alertController.setError(message);

            Stage stage = new Stage();
            Scene scene = new Scene((javafx.scene.Parent) alertPage, Color.TRANSPARENT);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(ownerStage);

            stage.setX(420);
            stage.setY(200);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FXMLLoader getAlert() {
        return new FXMLLoader(getClass().getResource("Alert.fxml"));
    }

}
