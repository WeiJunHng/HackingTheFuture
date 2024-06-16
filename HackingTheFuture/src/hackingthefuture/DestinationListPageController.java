/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class DestinationListPageController extends Controller implements Initializable {

    @FXML
    private Button destinationPrevBtn, destinationNextBtn;

    @FXML
    private HBox destinationHBox;

    private Parent currentParent;

    private final List<Destination> destinationList = Destination.getDestinationList();
    private int currentDestinationIndex = 0;
    private boolean prevLoop = false;
    private boolean nextLoop = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // Get current login Parent
    // Initialise the page
    public void setup(Parent currentParent) {
        this.currentParent = currentParent;

        destinationHBox.getChildren().clear();
        
        // Use priority queue to get the destinations
        PriorityQueue<Destination> destinationQueue = Destination.getDestinationQueue(currentParent);
        for (int i=0;!destinationQueue.isEmpty();i++) {
            Destination destination = destinationQueue.poll();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DestinationCard.fxml"));
                Node root = loader.load();
                DestinationCardController destinationCardController = loader.getController();
                destinationCardController.setup(destination, i, currentParent);
                destinationHBox.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Action for "next" button
        destinationNextBtn.setOnAction(event -> {
            nextLoop = currentDestinationIndex == destinationList.size() - 1;
            if (nextLoop) {
                destinationHBox.getChildren().add(destinationHBox.getChildren().removeFirst());
                currentDestinationIndex--;
                destinationHBox.setTranslateX(destinationHBox.getTranslateX() + 540.8);
            }
            currentDestinationIndex = (currentDestinationIndex + 1) % destinationList.size();
            switchDestination();
        });

        // Action for "previous" button
        destinationPrevBtn.setOnAction(event -> {
            prevLoop = currentDestinationIndex == 0;
            if (prevLoop) {
                destinationHBox.getChildren().addFirst(destinationHBox.getChildren().removeLast());
                currentDestinationIndex++;
                destinationHBox.setTranslateX(destinationHBox.getTranslateX() - 540.8);
            }
            currentDestinationIndex = (currentDestinationIndex - 1 + destinationList.size()) % destinationList.size();
            switchDestination();
        });
    }
    
    @Override
    public void refresh(){
        
    }

    // Switching animation for changing destination
    private void switchDestination() {
//        destinationPrevBtn.setDisable(true);
//        destinationNextBtn.setDisable(true);
        Timeline switchAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.01),
                        new KeyValue(destinationPrevBtn.disableProperty(), true),
                        new KeyValue(destinationNextBtn.disableProperty(), true)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(destinationHBox.translateXProperty(), -540.8 * currentDestinationIndex),
                        new KeyValue(destinationPrevBtn.disableProperty(), false),
                        new KeyValue(destinationNextBtn.disableProperty(), false)
                )
        );
        switchAnimation.setOnFinished(event -> {
            if (prevLoop) {
                destinationHBox.getChildren().add(destinationHBox.getChildren().removeFirst());
                currentDestinationIndex = destinationList.size() - 1;
                destinationHBox.setTranslateX(-540.8 * currentDestinationIndex);
                prevLoop = false;
            }
            if (nextLoop) {
                destinationHBox.getChildren().addFirst(destinationHBox.getChildren().removeLast());
                currentDestinationIndex = 0;
                destinationHBox.setTranslateX(-540.8 * currentDestinationIndex);
                nextLoop = false;
            }
        });
        switchAnimation.play();
    }

}
