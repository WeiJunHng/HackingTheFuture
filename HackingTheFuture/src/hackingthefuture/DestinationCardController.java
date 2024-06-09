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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class DestinationCardController implements Initializable {

    @FXML
    private Button destinationBookBtn;

    @FXML
    private Label destinationDistanceLabel;

    @FXML
    private ImageView destinationImage;

    @FXML
    private Label destinationNameLabel;

    private Parent currentParent;
    private int index;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setup(Destination destination, int index, Parent currentParent) {
        this.currentParent = currentParent;
        this.index = index;

        destinationImage.setImage(new Image(getClass().getResourceAsStream("/Resources/Images/" + destination.getName() + ".png")));
        destinationNameLabel.setText(destination.getName());
        destinationDistanceLabel.setText(destination.distanceOf(currentParent) + " km");
        
        destinationBookBtn.setOnAction(eh->{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("BookingDestinationPage.fxml"));
                Node root = loader.load();
                BookingDestinationPageController bookingDestinationPageController = loader.getController();
                bookingDestinationPageController.setup(this.index, currentParent);
                AppMainController.changePage(root, bookingDestinationPageController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
