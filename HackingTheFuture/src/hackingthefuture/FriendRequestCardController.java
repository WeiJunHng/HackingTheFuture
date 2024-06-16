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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class FriendRequestCardController implements Initializable {

    @FXML
    private Hyperlink link;

    @FXML
    private Button requestConfirmBtn;

    @FXML
    private Button requestDeleteBtn;

    private Student student, currentStudent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // Get Student who sent friend request, current login User and controller of the "Friends" page
    public void setup(Student student, Student currentStudent, FriendPageController friendPageController) {
        this.student = student;
        this.currentStudent = currentStudent;

        link.setText(student.getUsername());
        // Show profile of the Student who sent friend request
        link.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                VBox profile = loader.load();
                ProfileController profileController = loader.getController();
                profileController.setupProfile(this.student, this.currentStudent);

                friendPageController.close();
                AppMainController.changePage(profile, profileController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Accept friend request
        requestConfirmBtn.setOnAction(event -> {
            currentStudent.addFriend(student);
            friendPageController.refresh();
        });
        
        // Delete friend request
        requestDeleteBtn.setOnAction(event -> {
            currentStudent.deleteFriendRequest(student);
            friendPageController.refresh();
        });
    }

}
