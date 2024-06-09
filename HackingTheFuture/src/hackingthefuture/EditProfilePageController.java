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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.mindrot.jbcrypt.BCrypt;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class EditProfilePageController implements Initializable {

    private Stage stage;

    @FXML
    private StackPane backgroundStackPane;

    @FXML
    private Button closeBtn;

    @FXML
    private PasswordField confirmPwPF;

    @FXML
    private TextField confirmPwTF;

    @FXML
    private VBox editEmailBox;

    @FXML
    private PasswordField editEmailPwPF;

    @FXML
    private TextField editEmailPwTF;

    @FXML
    private VBox editKinBox;

    @FXML
    private VBox editPwBox;

    @FXML
    private VBox editUsernameBox;

    @FXML
    private PasswordField editUsernamePwPF;

    @FXML
    private TextField editUsernamePwTF;

    @FXML
    private Button emailSaveBtn;

    @FXML
    private VBox friendPaneForCurrentUser;

    @FXML
    private TextField kinEmailTF;

    @FXML
    private Button kinSaveBtn;

    @FXML
    private TextField newEmailTF;

    @FXML
    private PasswordField newPwPF;

    @FXML
    private TextField newPwTF;

    @FXML
    private TextField newUsernameTF;

    @FXML
    private PasswordField oldPwPF;

    @FXML
    private TextField oldPwTF;

    @FXML
    private Button pwSaveBtn;

    @FXML
    private Button switchEmailBtn;

    @FXML
    private Button switchKinBtn;

    @FXML
    private Button switchPwBtn;

    @FXML
    private Button switchUsernameBtn;

    @FXML
    private Button usernameSaveBtn;

    @FXML
    private Label kinLabel;

    private Node alertPage;
    private AlertController alertController;
    private Scene alertScene;

    private Button currentSelectedBtn;
    private int selectedPaneIndex = 0;
    private String[] backgroundRadiusArr = {"0 20 20 20", "20 20 20 20", "20 20 20 20", "20 0 20 20"};

    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            alertPage = loader.load();
            alertScene = new Scene((javafx.scene.Parent) alertPage, Color.TRANSPARENT);
            alertController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            stage = (Stage) closeBtn.getScene().getWindow();
        });

        closeBtn.setOnAction(event -> close());

        currentSelectedBtn = switchUsernameBtn;
        for (Node btn : switchUsernameBtn.getParent().getChildrenUnmodifiable()) {
            ((Button) btn).setOnAction(event -> switchPane((Button) btn));
        }

        editUsernamePwTF.textProperty().bindBidirectional(editUsernamePwPF.textProperty());
        editEmailPwTF.textProperty().bindBidirectional(editEmailPwPF.textProperty());
        oldPwTF.textProperty().bindBidirectional(oldPwPF.textProperty());
        newPwTF.textProperty().bindBidirectional(newPwPF.textProperty());
        confirmPwTF.textProperty().bindBidirectional(confirmPwPF.textProperty());
    }

    public void setup(User user) {
        currentUser = user;

        usernameSaveBtn.setOnAction(event -> {
            String username = newUsernameTF.getText();
            String password = editUsernamePwPF.getText();
            if (username.isBlank() || password.isBlank()) {
                showErrorAlert("All fields must be filled in!");
                return;
            }

            if (username.equals(currentUser.getUsername())) {
                showErrorAlert("New username cannot be same with old username");
                return;
            }

            if (!BCrypt.checkpw(password, currentUser.getPassword())) {
                showErrorAlert("Incorrect password");
                return;
            }

            if (LoginRegisterHandler.emailOrUsernameExist(username)) {
                showErrorAlert("The username has been taken");
                return;
            }

            LoginRegisterHandler.changeUsername(username, currentUser);
            showSuccessAlert("Username changed successfully!");
            refresh();
        });

        emailSaveBtn.setOnAction(event -> {
            String email = newEmailTF.getText();
            String password = editEmailPwPF.getText();
            if (email.isBlank() || password.isBlank()) {
                showErrorAlert("All fields must be filled in!");
                return;
            }

            if (!LoginRegisterHandler.isValidateEmail(email)) {
                showErrorAlert("Invalid email address");
                return;
            }

            if (email.equals(currentUser.getEmail())) {
                showErrorAlert("New email cannot be same with old email");
                return;
            }

            if (!BCrypt.checkpw(password, currentUser.getPassword())) {
                showErrorAlert("Incorrect password");
                return;
            }

            if (LoginRegisterHandler.emailOrUsernameExist(email)) {
                showErrorAlert("This email is already registered");
                return;
            }

            LoginRegisterHandler.changeEmail(email, currentUser);
            showSuccessAlert("Email changed successfully!");
            refresh();
        });

        pwSaveBtn.setOnAction(event -> {
            String oldPw = oldPwPF.getText();
            String newPw = newPwPF.getText();
            String confirmPw = confirmPwPF.getText();

            if (oldPw.isBlank() || newPw.isBlank() || confirmPw.isBlank()) {
                showErrorAlert("All fields must be filled in!");
                return;
            }

            if (!BCrypt.checkpw(oldPw, currentUser.getPassword())) {
                showErrorAlert("Incorrect password");
                return;
            }

            if (!newPw.equals(confirmPw)) {
                showErrorAlert("Please confirm your new password");
                return;
            }

            if (newPw.equals(oldPw)) {
                showErrorAlert("New password cannot be same with old password");
                return;
            }

            LoginRegisterHandler.changePassword(newPw, currentUser);
            showSuccessAlert("Password changed successfully!");
            refresh();
        });

        if (currentUser instanceof Educator) {
            backgroundRadiusArr = new String[]{"0 20 20 20", "20 20 20 20", "20 0 20 20"};
            ((HBox) switchKinBtn.getParent()).getChildren().remove(switchKinBtn);
            for (Node btn : switchUsernameBtn.getParent().getChildrenUnmodifiable()) {
                ((Button) btn).setPrefWidth(668.8 / 3);
            }
        }

        if (currentUser instanceof Student currentStudent) {
            ((Label) switchKinBtn.getGraphic()).setText("Parent");
            kinLabel.setText("New Parent Email");
            kinSaveBtn.setOnAction(event -> {
                String parentEmail = kinEmailTF.getText();

                if (parentEmail.isBlank()) {
                    showErrorAlert("All fields must be filled in!");
                    return;
                }

                if (!LoginRegisterHandler.isValidateEmail(parentEmail)) {
                    showErrorAlert("Invalid email address");
                    return;
                }

                if (!LoginRegisterHandler.emailOrUsernameExist(parentEmail)) {
                    showErrorAlert("User doesn't exist");
                    return;
                }

                User parent = UserHandler.getUserByEmail(parentEmail);

                if (!(parent instanceof Parent)) {
                    showErrorAlert("The user is not parent");
                    return;
                }

                if (currentStudent.getParentList().contains(parent.getID())) {
                    showErrorAlert("Cannot add existing parent");
                    return;
                }

                if (!LoginRegisterHandler.isValidateParentCount(currentStudent.getEmail())) {
                    showErrorAlert("Already has 2 parents\nCannot add more");
                    return;
                }

                UserHandler.addParent(currentStudent, (Parent) parent);
                showSuccessAlert("Parent added successfully");
                refresh();
            });
        }

        if (currentUser instanceof Parent currentParent) {
            ((Label) switchKinBtn.getGraphic()).setText("Children");
            kinLabel.setText("New Child Email");
            kinSaveBtn.setOnAction(event -> {
                String childEmail = kinEmailTF.getText();

                if (childEmail.isBlank()) {
                    showErrorAlert("All fields must be filled in!");
                    return;
                }

                if (!LoginRegisterHandler.isValidateEmail(childEmail)) {
                    showErrorAlert("Invalid email address");
                    return;
                }

                if (!LoginRegisterHandler.emailOrUsernameExist(childEmail)) {
                    showErrorAlert("User doesn't exist");
                    return;
                }

                User child = UserHandler.getUserByEmail(childEmail);

                if (!(child instanceof Student)) {
                    showErrorAlert("The user is not student");
                    return;
                }

                if (currentParent.getChildrenList().contains(child.getID())) {
                    showErrorAlert("Cannot add existing child");
                    return;
                }

                if (!LoginRegisterHandler.isValidateParentCount(child.getEmail())) {
                    showErrorAlert("The student already has 2 parents\nCannot add more");
                    return;
                }

                UserHandler.addChild(currentParent, (Student) child);
                showSuccessAlert("Child added successfully");
                refresh();
            });
        }

        refresh();
    }

    public void refresh() {
        hideAll();
        clear();
        backgroundStackPane.getChildren().get(selectedPaneIndex).setVisible(true);
        currentSelectedBtn.setId("SelectedBtn");
        backgroundStackPane.setStyle("-fx-background-color:  rgba(196,196,196,0.7); -fx-background-radius: " + backgroundRadiusArr[selectedPaneIndex]);
    }

    private void switchPane(Button btn) {
        currentSelectedBtn.setDisable(false);
        currentSelectedBtn.setId("");

        currentSelectedBtn = btn;
        currentSelectedBtn.setDisable(true);
        selectedPaneIndex = currentSelectedBtn.getParent().getChildrenUnmodifiable().indexOf(currentSelectedBtn);

        refresh();
    }

    private void hideAll() {
        for (Node box : backgroundStackPane.getChildren()) {
            ((VBox) box).setVisible(false);
        }
    }

    private void clear() {
        newUsernameTF.setText(currentUser.getUsername());
        editUsernamePwPF.clear();
        newEmailTF.setText(currentUser.getEmail());
        editEmailPwPF.clear();
        oldPwPF.clear();
        newPwPF.clear();
        confirmPwPF.clear();
        kinEmailTF.clear();
    }

    private void close() {
        stage.close();
        AppMainController.refreshPage();
    }

    private void showSuccessAlert(String message) {
        alertController.setSuccess(message);
        showAlert();
    }

    private void showErrorAlert(String message) {
        alertController.setError(message);
        showAlert();
    }

    private void showAlert() {
        Stage stage = new Stage();

        stage.setScene(alertScene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.stage);

        stage.setX(420);
        stage.setY(200);

        stage.show();
    }

}
