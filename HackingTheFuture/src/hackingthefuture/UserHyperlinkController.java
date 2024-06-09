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
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class UserHyperlinkController implements Initializable {

    @FXML
    private Hyperlink link;

    private User user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setUser(User user, User currentUser) {
        this.user = user;
        link.setText(this.user.getUsername());
        
        // Switch to user's profile when its hyperlink is clicked
        link.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                VBox profile = loader.load();
                ProfileController profileController = loader.getController();
                profileController.setupProfile(user, currentUser);
                
                AppMainController.changePage(profile, profileController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
