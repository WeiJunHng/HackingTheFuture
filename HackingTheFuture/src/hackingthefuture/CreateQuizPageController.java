/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class CreateQuizPageController implements Initializable {

    private Stage stage;

    @FXML
    private TextField contentTF;

    @FXML
    private Button createQuizBtn, closeBtn;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private SplitMenuButton themeMenu;

    @FXML
    private TextField titleTF;

    private Educator currentEducator;

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
        
        themeMenu.getItems().addAll(new MenuItem("Science"), new MenuItem("Technology"),
                new MenuItem("Engineering"), new MenuItem("Mathematics"));
        for (MenuItem item : themeMenu.getItems()) {
            item.setStyle("-fx-text-fill : black");
            item.setOnAction(eh -> themeMenu.setText(item.getText()));
        }
    }

    public void setupPage(Educator educator) {
        currentEducator = educator;
        createQuizBtn.setOnAction(event -> createQuiz(currentEducator));
    }

    public void refresh() {
        titleTF.clear();
        descriptionTextArea.clear();
        themeMenu.setText("Science");
        contentTF.clear();
    }

    private void createQuiz(Educator creator) {
        // !!!Check validation first!!!
        String title = titleTF.getText();
        String description = descriptionTextArea.getText();
        String theme = themeMenu.getText();
        String content = contentTF.getText();
        if(title.isBlank()||description.isBlank()||theme.isBlank()||content.isBlank()){
            AlertController.showErrorAlert("All the fields must be filled in!", stage);
            return;
        }
        Quiz quiz = new Quiz(title, description, theme, content, creator.getID());
        QuizHandler.createQuiz(quiz, creator);
        refresh();
        close();
        AppMainController.refreshPage();
        AppMainController.showSuccessAlert("Quiz Created");
    }

    private void close() {
        stage.close();
    }

}
