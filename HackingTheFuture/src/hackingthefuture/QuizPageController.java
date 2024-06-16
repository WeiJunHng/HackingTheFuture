/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class QuizPageController extends Controller implements Initializable {

    @FXML
    private GridPane quizGridPane;

    @FXML
    private AnchorPane filterPane;

    @FXML
    private VBox filterChoiceVBox;

    @FXML
    private Button filterBtn, createQuizBtn;

    @FXML
    private CheckBox filterAllCheckBox;

    private User currentUser;
    private boolean allCBClicked = false;
    private final ArrayList<String> themesChosen = new ArrayList<>(List.of("Science", "Technology", "Engineering", "Mathematics"));

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        createQuizBtn.setVisible(false);
        filterPane.setClip(new Rectangle(1140, 31, 287.2, 45));
        filterBtn.setOnAction(event -> filterPane.setClip(new Rectangle(1140, 31, 287.2, 274 - ((Rectangle) filterPane.getClip()).getHeight())));
        for (Node node : filterChoiceVBox.getChildren()) {
            ((CheckBox) node).setSelected(true);
        }
        ((VBox) filterPane.getParent().getChildrenUnmodifiable().get(0)).setOnMouseClicked(event -> filterPane.setClip(new Rectangle(1140, 31, 287.2, 45)));
    }

    // Get the current login User
    // Initialise the page
    public void setupQuizPage(User user) {
        currentUser = user;
        refresh();

        // If current login User is Educator
        if (currentUser instanceof Educator currentEducator) {
            // Show "Create Quiz" button
            createQuizBtn.setVisible(true);
            
            // Show "Create Quiz" page
            createQuizBtn.setOnAction(eh -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuizPage.fxml"));
                    javafx.scene.Parent root = loader.load();
                    CreateQuizPageController createQuizPageController = loader.getController();
                    createQuizPageController.setupPage(currentEducator);

                    Stage stage = new Stage();
                    Scene scene = new Scene(root, Color.TRANSPARENT);

                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner((Stage) createQuizBtn.getScene().getWindow());

                    stage.setX(295);
                    stage.setY(130);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        // Select or deselect all options in the filter
        filterAllCheckBox.setOnAction(event -> {
            allCBClicked = true;
            for (int i = 1; i < 5; i++) {
                ((CheckBox) filterChoiceVBox.getChildren().get(i)).setSelected(filterAllCheckBox.isSelected());
            }
            refresh();
            allCBClicked = false;
        });

        for (int i = 1; i < 5; i++) {
            CheckBox box = (CheckBox) filterChoiceVBox.getChildren().get(i);
            
            box.selectedProperty().addListener((observable, oldValue, newValue) -> {
                String theme = ((Label) box.getChildrenUnmodifiable().get(0)).getText();
                // When option deselected
                if (!newValue) {
                    // Remove the corresponding theme from list of themes chosen
                    themesChosen.remove(theme);
                    
                    // Deselect the "All" option
                    filterAllCheckBox.setSelected(false);
                } 
                // Option chosen and themes chosen does not contain the corresponding theme
                else if (!themesChosen.contains(theme)) {
                    // Add the corresponding theme
                    themesChosen.add(theme);
                    
                    // If the option is not changed due to "All" option chosen, determine does all themes chosen
                    // If so, set the "All" option to be selected
                    if (!allCBClicked) {
                        boolean allChosen = true;
                        for (int j = 1; j < 5; j++) {
                            CheckBox box2 = (CheckBox) filterChoiceVBox.getChildren().get(j);
                            allChosen = allChosen && box2.isSelected();
                        }
                        filterAllCheckBox.setSelected(allChosen);
                    }
                }
                
                // If the option is not changed due to "All" option chosen, determine does all themes chosen
                // Refresh all quiz shown
                if (!allCBClicked) {
                    refresh();
                }
            });
        }
    }

    // Refresh the page
    public void refresh() {
        List<Quiz> quizList = QuizHandler.getQuizList();

        quizGridPane.getChildren().clear();

        List<Quiz> doneQuizList = new ArrayList<>();

        int i = 0;
        for (Quiz quiz : quizList) {
            // If the theme of the quiz is not selected in the filter, do not show it
            if (!themesChosen.contains(quiz.getTheme())) {
                continue;
            } 
            // If the current login User is Student and has done the quiz, add the quiz into doneQuizList
            else if (currentUser instanceof Student) {
                if (((Student) currentUser).getQuizDoneList().contains(quiz.getID())) {
                    doneQuizList.add(quiz);
                    continue;
                }
            }
            
            // Add the quiz to the gridpane to show it
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizCard.fxml"));
                VBox root = loader.load();
                QuizCardController quizCardController = loader.getController();
                quizCardController.setupQuizCard(this, currentUser, quiz);
                quizGridPane.add(root, i % 3, i / 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }

        // If the current login User is not Student, skip the process below
        if (!(currentUser instanceof Student)) {
            return;
        }

        // Add all quiz done by the Student at the bottom
        for (Quiz quiz : doneQuizList) {
            if (!themesChosen.contains(quiz.getTheme())) {
                continue;
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizCard.fxml"));
                VBox root = loader.load();
                QuizCardController quizCardController = loader.getController();
                quizCardController.setupQuizCard(this, currentUser, quiz);
                quizGridPane.add(root, i % 3, i / 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            i++;
        }
    }

}
