/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class QuizCardController implements Initializable {

    @FXML
    private VBox bg;

    @FXML
    private Button quizAttemptBtn;

    @FXML
    private Label quizDescriptionLabel;

    @FXML
    private Label quizThemeLabel;

    @FXML
    private Label quizTitleLabel;

    private User currentUser;
    private Quiz quiz;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // Get current login User and quiz
    public void setupQuizCard(QuizPageController QPCtrler, User user, Quiz quiz) {
        currentUser = user;
        this.quiz = quiz;

        quizTitleLabel.setText(this.quiz.getTitle());
        quizDescriptionLabel.setText(this.quiz.getDescription());
        quizThemeLabel.setText(this.quiz.getTheme());
        quizThemeLabel.setId(this.quiz.getTheme());

        // If current login User is Student
        if (currentUser instanceof Student) {
            // Attempt quiz
            quizAttemptBtn.setOnAction(event -> {
                ((Student) currentUser).doQuiz(this.quiz);
                // Redirect to link of Quizizz of the quiz
                try {
                    Desktop.getDesktop().browse(new URI(this.quiz.getContent()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Refresh
                refreshCard();
                QPCtrler.refresh();
                AppMainController.showSuccessAlert("Congratulations!\nYou have been awarded 2 marks.");
            });
        }
        
        refreshCard();
    }

    // Refresh details of the quiz
    private void refreshCard() {
        // If current login User is Educator, change colour of quiz card to blue
        if (currentUser instanceof Educator) {
            bg.setId(User.ROLE.EDUCATOR);
            quizAttemptBtn.setVisible(false);
        } 
        // If current login User is Student
        else if (currentUser instanceof Student) {
            // If the Student have attempted the quiz, change colour of quiz card to red, disable "Attempt" button and change text of the button
            if (((Student) currentUser).getQuizDoneList().contains(this.quiz.getID())) {
                quizAttemptBtn.setText("Done");
                quizAttemptBtn.setDisable(true);
                bg.setId("StudentDone");
            } 
            // Else, change colour of quiz card to green
            else {
                bg.setId("StudentNotDone");
            }
        }
    }

}
