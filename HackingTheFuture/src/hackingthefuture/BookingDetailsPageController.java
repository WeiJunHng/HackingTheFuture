/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class BookingDetailsPageController implements Initializable {

    private Stage stage;

    @FXML
    private VBox bookingChildrenBox;

    @FXML
    private Label bookingDateLabel;

    @FXML
    private Label bookingDestinationLabel;

    @FXML
    private Label bookingDistanceLabel;

    @FXML
    private Button bookingParentBtn, closeBtn;

    @FXML
    private Label bookingParentLabel;

    private User currentUser;
    private Booking booking;

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

    public void setup(Booking booking, User currentUser) {
        this.currentUser = currentUser;
        this.booking = booking;

        bookingParentBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                VBox profile = loader.load();
                ProfileController profileController = loader.getController();
                profileController.setupProfile(booking.getParent(), currentUser);

                close();
                AppMainController.changePage(profile, profileController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refresh();
    }

    public void refresh() {
        bookingDestinationLabel.setText(booking.getDestination().getName());
        bookingDistanceLabel.setText(booking.getDestination().distanceOf(booking.getParent()) + " km");
        bookingParentLabel.setText(booking.getParent().getUsername());
        bookingDateLabel.setText(booking.getFormattedSlot());
        for (Student child : booking.getChildrenList()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UserHyperlink.fxml"));
                Hyperlink link = loader.load();
                UserHyperlinkController userHyperlinkController = loader.getController();
                userHyperlinkController.setUser(child, currentUser);
                link.setOnAction(eh -> {
                    try {
                        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("Profile.fxml"));
                        VBox profile = loader2.load();
                        ProfileController profileController = loader2.getController();
                        profileController.setupProfile(child, currentUser);

                        AppMainController.changePage(profile, profileController);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    close();
                });
                bookingChildrenBox.getChildren().add(link);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void close() {
        stage.close();
    }

}
