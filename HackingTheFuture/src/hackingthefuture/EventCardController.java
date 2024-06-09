/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class EventCardController implements Initializable {

    @FXML
    private HBox eventCard;

    @FXML
    private Label eventDateLabel;

    @FXML
    private Label eventDescriptionLabel;

    @FXML
    private Label eventTimeLabel;

    @FXML
    private Label eventTitleLabel;

    @FXML
    private Label eventVenueLabel;

    @FXML
    private Button eventJoinBtn;

    private User currentUser;
    private Event event;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        eventJoinBtn.setVisible(false);
    }

    public void setupEventCard(User user, Event event) {
        currentUser = user;
        this.event = event;

        eventTitleLabel.setText(this.event.getTitle());
        eventDescriptionLabel.setText(this.event.getDescription());
        eventDateLabel.setText(this.event.getFormattedDate());
        eventTimeLabel.setText(this.event.getFormattedTime());
        eventVenueLabel.setText(this.event.getVenue());
        if (currentUser instanceof Student) {
            eventJoinBtn.setOnAction(e -> {
                Event clashedEvent = ((Student) currentUser).getClashedEvent(this.event);
                if (clashedEvent != null) {
                    AlertController.showErrorAlert("Clashed with the event:\n" + clashedEvent.getTitle(), (Stage) eventJoinBtn.getScene().getWindow());
                    return;
                }
                ((Student) currentUser).joinEvent(this.event);
                refreshCard();
                AlertController.showSuccessAlert("You have joined the event!", (Stage) eventJoinBtn.getScene().getWindow());
            });
            refreshCard();
        }

        eventCard.setOnMouseClicked(eh -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EventDetailsPage.fxml"));
                javafx.scene.Parent root = loader.load();
                EventDetailsPageController eventDetailsPageController = loader.getController();
                eventDetailsPageController.setup(event, currentUser);

                Stage stage = new Stage();
                Scene scene = new Scene(root, Color.TRANSPARENT);

                stage.setScene(scene);
                stage.setResizable(false);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner((Stage) eventCard.getScene().getWindow());

                stage.setX(270);
                stage.setY(75);

                stage.show();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        });
    }

    private void refreshCard() {
        if (((Student) currentUser).getRegisteredEventList().contains(this.event.getID())) {
            eventJoinBtn.setDisable(true);
            eventJoinBtn.setText("Joined");
        }
        eventJoinBtn.setVisible(this.event.getDate().isAfter(LocalDate.now()));
    }

}
