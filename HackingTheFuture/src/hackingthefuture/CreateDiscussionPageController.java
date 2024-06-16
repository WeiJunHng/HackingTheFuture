/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class CreateDiscussionPageController implements Initializable {

    @FXML
    private Button closeBtn;

    @FXML
    private TextArea discussionContentTextArea;

    @FXML
    private Button discussionPostBtn;

    @FXML
    private TextField discusssionTitleTF;

    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        closeBtn.setOnAction(event -> {
            close();
        });
        discusssionTitleTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                discusssionTitleTF.setId("");
            }
        });
        discussionContentTextArea.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                discussionContentTextArea.setId("");
            }
        });
    }

    // Initialise the pop up window and get the current login User
    public void setup(User currentUser, DiscussionPageController controller) {
        this.currentUser = currentUser;

        // Post the discussion
        discussionPostBtn.setOnAction(event -> {
            String title = discusssionTitleTF.getText();
            String content = discussionContentTextArea.getText();

            // Stop when there is any empty field
            if (title.isBlank() || content.isBlank()) {
                discusssionTitleTF.setId(title.isBlank() ? "Empty" : "");
                discussionContentTextArea.setId(content.isBlank() ? "Empty" : "");
                AlertController.showErrorAlert("All field must be filled in!", (Stage) closeBtn.getScene().getWindow());
                return;
            }

            // Save the discussion
            Discussion discussion = new Discussion(title, currentUser.getID(), content, LocalDateTime.now());
            DiscussionHandler.postDiscussion(discussion, currentUser);

            // Refresh the discussion page, close pop up window and display alert of "Success"
            AppMainController.refreshPage();
            close();
            AppMainController.showSuccessAlert("Discussion Posted");
        });
    }

    // Close the pop up window
    private void close() {
        ((Stage) closeBtn.getScene().getWindow()).close();
    }

}
