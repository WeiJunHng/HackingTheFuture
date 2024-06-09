/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class EventPageController extends Controller implements Initializable {

    @FXML
    private HBox ongoingEventHBox;

    @FXML
    private VBox upcomingEventVBox;

    @FXML
    private Button liveEventPrevBtn, liveEventNextBtn, createEventBtn;

    private User currentUser;

    private int currentEventIndex = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        createEventBtn.setVisible(false);
    }

    public void setupEventPage(User user) {
        currentUser = user;

        if (currentUser instanceof Educator currentEducator) {
            createEventBtn.setVisible(true);
            createEventBtn.setOnAction(eh -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEventPage.fxml"));
                    javafx.scene.Parent root = loader.load();
                    CreateEventPageController createEventPageController = loader.getController();
                    createEventPageController.setupPage(currentEducator);

                    Stage stage = new Stage();
                    Scene scene = new Scene(root, Color.TRANSPARENT);

                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner((Stage) createEventBtn.getScene().getWindow());

                    stage.setX(295);
                    stage.setY(110);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        refresh();
    }

    @Override
    public void refresh() {
        List<Event> ongoingEventList = EventHandler.getOngoingEventList();

        if (ongoingEventList.size() > 1) {
            liveEventNextBtn.setVisible(true);
            liveEventPrevBtn.setVisible(true);
        }

        liveEventNextBtn.setOnAction(event -> {
            currentEventIndex = (currentEventIndex + 1) % ongoingEventList.size();
            switchEvent();
        });

        liveEventPrevBtn.setOnAction(event -> {
            currentEventIndex = (currentEventIndex - 1 + ongoingEventList.size()) % ongoingEventList.size();
            switchEvent();
        });

        ongoingEventHBox.getChildren().clear();
        for (Event event : ongoingEventList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EventCard.fxml"));
                HBox root = loader.load();
                EventCardController eventCardController = loader.getController();
                eventCardController.setupEventCard(currentUser, event);
                ongoingEventHBox.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        upcomingEventVBox.getChildren().clear();
        for (Event event : EventHandler.getUpcomingEventList()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EventCard.fxml"));
                HBox root = loader.load();
                EventCardController eventCardController = loader.getController();
                eventCardController.setupEventCard(currentUser, event);
                upcomingEventVBox.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void switchEvent() {
        Timeline switchAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(ongoingEventHBox.translateXProperty(), -1350.4 * currentEventIndex)
                )
        );
        switchAnimation.play();
    }

}
