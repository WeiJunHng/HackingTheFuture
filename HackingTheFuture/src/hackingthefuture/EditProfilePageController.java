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

        // Switch the pane between "Email", "Username", "Password", and/or "Parent"/"Children"
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

    // Get the current login User
    public void setup(User user) {
        currentUser = user;

        usernameSaveBtn.setOnAction(event -> {
            String username = newUsernameTF.getText();
            String password = editUsernamePwPF.getText();
            
            // There is empty field
            if (username.isBlank() || password.isBlank()) {
                showErrorAlert("All fields must be filled in!");
                return;
            }

            // New username equals to current username
            if (username.equals(currentUser.getUsername())) {
                showErrorAlert("New username cannot be same with old username");
                return;
            }

            // Password entered is incorrect
            if (!BCrypt.checkpw(password, currentUser.getPassword())) {
                showErrorAlert("Incorrect password");
                return;
            }

            // Username has been taken
            if (LoginRegisterHandler.emailOrUsernameExist(username)) {
                showErrorAlert("The username has been taken");
                return;
            }

            // Change username of the User
            LoginRegisterHandler.changeUsername(username, currentUser);
            showSuccessAlert("Username changed successfully!");
            refresh();
        });

        emailSaveBtn.setOnAction(event -> {
            String email = newEmailTF.getText();
            String password = editEmailPwPF.getText();
            // There is empty field
            if (email.isBlank() || password.isBlank()) {
                showErrorAlert("All fields must be filled in!");
                return;
            }

            // Email entered is invalid format
            if (!LoginRegisterHandler.isValidateEmail(email)) {
                showErrorAlert("Invalid email address");
                return;
            }

            // New email equals to current email
            if (email.equals(currentUser.getEmail())) {
                showErrorAlert("New email cannot be same with old email");
                return;
            }

            // Password entered is incorrect
            if (!BCrypt.checkpw(password, currentUser.getPassword())) {
                showErrorAlert("Incorrect password");
                return;
            }

            // Email has been registered
            if (LoginRegisterHandler.emailOrUsernameExist(email)) {
                showErrorAlert("This email is already registered");
                return;
            }

            // Change email od the User
            LoginRegisterHandler.changeEmail(email, currentUser);
            showSuccessAlert("Email changed successfully!");
            refresh();
        });

        pwSaveBtn.setOnAction(event -> {
            String oldPw = oldPwPF.getText();
            String newPw = newPwPF.getText();
            String confirmPw = confirmPwPF.getText();

            // There is empty field
            if (oldPw.isBlank() || newPw.isBlank() || confirmPw.isBlank()) {
                showErrorAlert("All fields must be filled in!");
                return;
            }

            // Old password entered is incorrect
            if (!BCrypt.checkpw(oldPw, currentUser.getPassword())) {
                showErrorAlert("Incorrect password");
                return;
            }

            // Confirm password invalid
            if (!newPw.equals(confirmPw)) {
                showErrorAlert("Please confirm your new password");
                return;
            }

            // New password equals to current password
            if (newPw.equals(oldPw)) {
                showErrorAlert("New password cannot be same with old password");
                return;
            }

            // Change password of the User
            LoginRegisterHandler.changePassword(newPw, currentUser);
            showSuccessAlert("Password changed successfully!");
            refresh();
        });

        // If the User is Educator, remove the "Parent"/"Children" button
        if (currentUser instanceof Educator) {
            backgroundRadiusArr = new String[]{"0 20 20 20", "20 20 20 20", "20 0 20 20"};
            ((HBox) switchKinBtn.getParent()).getChildren().remove(switchKinBtn);
            for (Node btn : switchUsernameBtn.getParent().getChildrenUnmodifiable()) {
                ((Button) btn).setPrefWidth(668.8 / 3);
            }
        }

        // If the User is Student
        if (currentUser instanceof Student currentStudent) {
            ((Label) switchKinBtn.getGraphic()).setText("Parent");
            kinLabel.setText("New Parent Email");
            kinSaveBtn.setOnAction(event -> {
                String parentEmail = kinEmailTF.getText();

                // There is empty field
                if (parentEmail.isBlank()) {
                    showErrorAlert("All fields must be filled in!");
                    return;
                }

                // Email of parent entered is invalid format
                if (!LoginRegisterHandler.isValidateEmail(parentEmail)) {
                    showErrorAlert("Invalid email address");
                    return;
                }

                // Email of the parent has not been registered
                if (!LoginRegisterHandler.emailOrUsernameExist(parentEmail)) {
                    showErrorAlert("User doesn't exist");
                    return;
                }

                // Get User based on the email of parent entered
                User parent = UserHandler.getUserByEmail(parentEmail);

                // If role of the User is not Parent
                if (!(parent instanceof Parent)) {
                    showErrorAlert("The user is not parent");
                    return;
                }

                // Student already has the Parent
                if (currentStudent.getParentList().contains(parent.getID())) {
                    showErrorAlert("Cannot add existing parent");
                    return;
                }

                // Number of parents of the Student is already 2
                if (!LoginRegisterHandler.isValidateParentCount(currentStudent.getEmail())) {
                    showErrorAlert("Already has 2 parents\nCannot add more");
                    return;
                }

                // Update parents of the Student
                UserHandler.addParent(currentStudent, (Parent) parent);
                showSuccessAlert("Parent added successfully");
                refresh();
            });
        }

        // If the User is Parent
        if (currentUser instanceof Parent currentParent) {
            ((Label) switchKinBtn.getGraphic()).setText("Children");
            kinLabel.setText("New Child Email");
            kinSaveBtn.setOnAction(event -> {
                String childEmail = kinEmailTF.getText();

                // There is empty field
                if (childEmail.isBlank()) {
                    showErrorAlert("All fields must be filled in!");
                    return;
                }

                // Email of child entered is invalid format
                if (!LoginRegisterHandler.isValidateEmail(childEmail)) {
                    showErrorAlert("Invalid email address");
                    return;
                }

                // Email of the child has not been registered
                if (!LoginRegisterHandler.emailOrUsernameExist(childEmail)) {
                    showErrorAlert("User doesn't exist");
                    return;
                }

                // Get User based on the email of child entered
                User child = UserHandler.getUserByEmail(childEmail);

                // If the role of the User is not Student
                if (!(child instanceof Student)) {
                    showErrorAlert("The user is not student");
                    return;
                }

                // Parent already has the child
                if (currentParent.getChildrenList().contains(child.getID())) {
                    showErrorAlert("Cannot add existing child");
                    return;
                }

                // Number of parents of the child is already 2
                if (!LoginRegisterHandler.isValidateParentCount(child.getEmail())) {
                    showErrorAlert("The student already has 2 parents\nCannot add more");
                    return;
                }

                // Update children of the Parent
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

    // Switch page between "Email", "Username", "Password", and/or "Parent"/"Children"
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

    // Clear all textfield and password field
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

    // Close the pop up window
    private void close() {
        stage.close();
        AppMainController.refreshPage();
    }

    // Initialise "Success" alert
    private void showSuccessAlert(String message) {
        alertController.setSuccess(message);
        showAlert();
    }

    // Initialise "Error" alert
    private void showErrorAlert(String message) {
        alertController.setError(message);
        showAlert();
    }

    // Show the alert
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
