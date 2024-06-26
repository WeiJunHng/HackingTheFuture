/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class EventDetailsPageController implements Initializable {
    
    private Stage stage;

    @FXML
    private Label dateLabel;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Button joinBtn, closeBtn;

    @FXML
    private Label timeLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label venueLabel;

    private Event event;
    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(() -> {
            stage = (Stage) closeBtn.getScene().getWindow();
        });

        closeBtn.setOnAction(eh -> {
            close();
        });
    }

    // Get the event and current login User
    public void setup(Event event, User currentUser) {
        this.event = event;
        this.currentUser = currentUser;
        
        refresh();
    }

    // Refresh details of the event and the "Join" button
    public void refresh() {
        titleLabel.setText(event.getTitle());
        descriptionTextArea.setText(event.getDescription());
        venueLabel.setText(event.getVenue());
        dateLabel.setText(event.getFormattedDate());
        timeLabel.setText(event.getFormattedTime());
        
        if (currentUser instanceof Student) {
            // If the User is Student and haven't joined the event yet, show the "Join" button, else remove it
            if (!((Student) currentUser).getRegisteredEventList().contains(event.getID())) {
                joinBtn.setOnAction(eh -> {
                    Event clashedEvent = ((Student) currentUser).getClashedEvent(this.event);
                    // If the event is clashed with other event
                    if (clashedEvent != null) {
                        AlertController.showErrorAlert("Clashed with the event:\n" + clashedEvent.getTitle(), stage);
                        return;
                    }
                    
                    // Update registered events of the Student
                    ((Student) currentUser).joinEvent(event);
                    refresh();
                    AppMainController.refreshPage();
                    AlertController.showSuccessAlert("You have joined the event!", stage);
                });
                return;
            }
        }
        ((VBox)joinBtn.getParent()).getChildren().remove(joinBtn);

    }
    
    // Close the pop up window
    private void close(){
        stage.close();
    }

}
