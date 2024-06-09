/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class LoginPageController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ToggleGroup roleToggleGroup;

    @FXML
    private VBox registerPage, kinRegisterPage;

    @FXML
    private HBox loginPage, imageAndRegisterFormContainer, registerCombinedPage;

    @FXML
    private ImageView moving_image, roleImageView, kinRoleImageView;

    @FXML
    private AnchorPane moving_pane;

    @FXML
    private Hyperlink switch_register_link, switch_login_link, switch_kin_link,
            kin_switch_register_link, kin_switch_login_link;

    @FXML
    private VBox registerForm, kinInformationBox, kinRegisterKinInformationBox;

    @FXML
    private TextField loginEmailTF, loginPasswordTF,
            registerEmailTF, registerUsernameTF, registerPasswordTF, registerConfirmPasswordTF, registerKinEmailTF;

    @FXML
    private TextField kinRegisterEmailTF, kinRegisterUsernameTF, kinRegisterPasswordTF, kinRegisterConfirmPasswordTF, kinRegisterKinInfoTF;

    @FXML
    private PasswordField loginPasswordPF,
            registerPasswordPF, registerConfirmPasswordPF,
            kinRegisterPasswordPF, kinRegisterConfirmPasswordPF;

    @FXML
    private CheckBox loginShowPwCB, registerShowPwCB, kinRegisterShowPwCB;

    @FXML
    private Button loginBtn, registerBtn, kinRegisterBtn;

    @FXML
    private Label kinRegisterPromptLabel;

    private Pane separatePane;

    private TextField[] loginTextFields;

    private TextField[] registerTextFields;

    private TextField[] kinRegisterTextFields;

    private TextField[] necessaryTextFields;

    private Image[] roleImages = {new Image("/Resources/Images/student_img.png"), new Image("/Resources/Images/parent_img.png"), new Image("/Resources/Images/educator_img.png")};

    private Node alertPage;
    private AlertController alertController;
    private Scene alertScene;
    
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
            alertScene = new Scene((Parent) alertPage, Color.TRANSPARENT);
            alertController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        loginTextFields = new TextField[]{loginEmailTF, loginPasswordTF, loginPasswordPF};

        registerTextFields = new TextField[]{registerEmailTF, registerUsernameTF, registerPasswordTF, registerConfirmPasswordTF,
            registerPasswordPF, registerConfirmPasswordPF, registerKinEmailTF};

        kinRegisterTextFields = new TextField[]{kinRegisterEmailTF, kinRegisterUsernameTF, kinRegisterPasswordTF,
            kinRegisterConfirmPasswordTF};

        necessaryTextFields = new TextField[]{loginEmailTF, loginPasswordTF, loginPasswordPF, registerEmailTF, registerUsernameTF,
            registerPasswordTF, registerConfirmPasswordTF, registerPasswordPF, registerConfirmPasswordPF, registerKinEmailTF,
            kinRegisterEmailTF, kinRegisterUsernameTF, kinRegisterPasswordTF, kinRegisterConfirmPasswordTF,
            kinRegisterPasswordPF, kinRegisterConfirmPasswordPF};

        moving_pane.setClip(new Rectangle(700, 800));
        moving_pane.setVisible(true);
        loginPage.setVisible(true);
        registerPage.setVisible(false);
        kinRegisterPage.setVisible(false);
        registerCombinedPage.setVisible(true);

        ((StackPane) registerPage.getParent()).getChildren().removeAll(registerPage, kinRegisterPage);
        registerCombinedPage.getChildren().addAll(registerPage, kinRegisterPage);

        separatePane = (Pane) registerForm.getChildren().get(registerForm.getChildren().size() - 2);

        resetLoginPage();
        resetRegisterPage();
        registerForm.getChildren().remove(kinInformationBox);

        // Bind text property for PasswordField and TextField
        loginPasswordPF.textProperty().bindBidirectional(loginPasswordTF.textProperty());
        registerPasswordPF.textProperty().bindBidirectional(registerPasswordTF.textProperty());
        registerConfirmPasswordPF.textProperty().bindBidirectional(registerConfirmPasswordTF.textProperty());
        kinRegisterPasswordPF.textProperty().bindBidirectional(kinRegisterPasswordTF.textProperty());
        kinRegisterConfirmPasswordPF.textProperty().bindBidirectional(kinRegisterConfirmPasswordTF.textProperty());

        kinRegisterKinInfoTF.textProperty().bind(registerEmailTF.textProperty());

        // Bind ID property for PasswordField and TextField
        loginPasswordPF.idProperty().bindBidirectional(loginPasswordTF.idProperty());
        registerPasswordPF.idProperty().bindBidirectional(registerPasswordTF.idProperty());
        registerConfirmPasswordPF.idProperty().bindBidirectional(registerConfirmPasswordTF.idProperty());
        kinRegisterPasswordPF.idProperty().bindBidirectional(kinRegisterPasswordTF.idProperty());
        kinRegisterConfirmPasswordPF.idProperty().bindBidirectional(kinRegisterConfirmPasswordTF.idProperty());

        // When focused or entered, red line is removed
        for (TextField tf : necessaryTextFields) {
            tf.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    tf.setId("");
                }
            });
            tf.textProperty().addListener((observable, oldValue, newValue) -> {
                tf.setId("");
            });
        }

        // Show password
        loginShowPwCB.selectedProperty().addListener((observable, oldValue, newValue) -> {
            loginPasswordPF.setVisible(!newValue);
            loginPasswordTF.setVisible(newValue);
        });

        registerShowPwCB.selectedProperty().addListener((observable, oldValue, newValue) -> {
            registerPasswordPF.setVisible(!newValue);
            registerPasswordTF.setVisible(newValue);
            registerConfirmPasswordPF.setVisible(!newValue);
            registerConfirmPasswordTF.setVisible(newValue);
        });

        kinRegisterShowPwCB.selectedProperty().addListener((observable, oldValue, newValue) -> {
            kinRegisterPasswordPF.setVisible(!newValue);
            kinRegisterPasswordTF.setVisible(newValue);
            kinRegisterConfirmPasswordPF.setVisible(!newValue);
            kinRegisterConfirmPasswordTF.setVisible(newValue);
        });

        // Role chosen
        roleToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                registerForm.getChildren().remove(kinInformationBox);
                return;
            }
            RadioButton selectedRadioButton = (RadioButton) newValue;
            ObservableList<Node> list = ((HBox) selectedRadioButton.getParent()).getChildren();
            int choiceIndex = list.indexOf(selectedRadioButton);
            roleImageView.setImage(roleImages[choiceIndex]);

            // Determine to show kin's information box or not
            if (choiceIndex < 2) {
                ((Label) kinInformationBox.getChildren().get(1)).setText("Email Address of Your " + (choiceIndex == 0 ? "Parent" : "Child"));
                ((Label) kinRegisterKinInformationBox.getChildren().get(1)).setText("Email Address of " + (choiceIndex == 1 ? "Parent" : "Child"));
                kinRegisterPromptLabel.setText("Register " + (choiceIndex == 0 ? "Parent" : "Child") + " to Proceed");
                kinRoleImageView.setImage(roleImages[(choiceIndex + 1) % 2]);
                if (oldValue == null || list.indexOf(oldValue) == 2) {
                    separatePane.setPrefHeight(0);
                    registerForm.getChildren().add(registerForm.getChildren().size() - 1, kinInformationBox);
                }
                clearKinRegisterPage();
            } else {
                separatePane.setPrefHeight(35);
                registerKinEmailTF.clear();
                registerForm.getChildren().remove(kinInformationBox);
            }

            // Show role image with animation
            if (oldValue == null) {
                roleImageView.setVisible(true);
                Timeline switchAnimation = new Timeline(
                        new KeyFrame(Duration.seconds(0.5),
                                new KeyValue(imageAndRegisterFormContainer.translateXProperty(), 0),
                                new KeyValue(roleImageView.translateXProperty(), 0)
                        )
                );
                switchAnimation.play();
            }
        });

        // Switch to Register Page
        switch_register_link.setOnAction(event -> switchRegisterAnimation());

        // Switch to Login Page
        switch_login_link.setOnAction(event -> switchLoginAnimation());

        // Switch from Register to Kin
        switch_kin_link.setOnAction(event -> switchKinRegisterAnimation(true));

        // Switch from Kin to Register
        kin_switch_register_link.setOnAction(event -> switchKinRegisterAnimation(false));

        // Switch from Kin to Login
        kin_switch_login_link.setOnAction(event -> kinSwitchLoginAnimation());

        loginBtn.setOnAction(event -> login(event));
        registerBtn.setOnAction(event -> register());
        kinRegisterBtn.setOnAction(event -> kinRegister());
    }

    private void switchRegisterAnimation() {
        switch_register_link.setDisable(true);

        moving_image.setImage(new Image("/Resources/Images/login_bg.png"));

        Timeline switchAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(loginPage.visibleProperty(), false)
                ),
                new KeyFrame(Duration.seconds(0.4),
                        new KeyValue(registerPage.visibleProperty(), true)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(moving_image.imageProperty(), new Image("/Resources/Images/register_bg.png"))
                ),
                new KeyFrame(Duration.seconds(0.6),
                        new KeyValue(loginPage.translateXProperty(), -400)
                ),
                new KeyFrame(Duration.seconds(0.8),
                        new KeyValue(registerPage.translateXProperty(), 0)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(moving_pane.translateXProperty(), 1030 - moving_pane.getTranslateX()),
                        new KeyValue(moving_image.translateXProperty(), -1030 - moving_image.getTranslateX())
                )
        );

        switchAnimation.setOnFinished(e -> {
            switch_register_link.setDisable(false);
            resetLoginPage();
        });

        switchAnimation.play();
    }

    private void switchLoginAnimation() {
        switch_login_link.setDisable(true);

        moving_image.setImage(new Image("/Resources/Images/register_bg.png"));

        Timeline switchAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.47),
                        new KeyValue(loginPage.visibleProperty(), true)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(moving_image.imageProperty(), new Image("/Resources/Images/login_bg.png")),
                        new KeyValue(registerPage.visibleProperty(), false)
                ),
                new KeyFrame(Duration.seconds(0.55),
                        new KeyValue(registerPage.translateXProperty(), 400)
                ),
                new KeyFrame(Duration.seconds(0.8),
                        new KeyValue(loginPage.translateXProperty(), 0)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(moving_pane.translateXProperty(), 1030 - moving_pane.getTranslateX()),
                        new KeyValue(moving_image.translateXProperty(), -1030 - moving_image.getTranslateX())
                )
        );

        switchAnimation.setOnFinished(e -> {
            switch_login_link.setDisable(false);
            resetRegisterPage();
        });

        switchAnimation.play();
    }

    private void switchKinRegisterAnimation(boolean left) {
        switch_login_link.setDisable(true);
        switch_kin_link.setDisable(true);
        kin_switch_register_link.setDisable(true);
        kin_switch_login_link.setDisable(true);

        kinRegisterPage.setVisible(left);
        registerPage.setVisible(!left);

        Timeline switchAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.9),
                        new KeyValue(registerCombinedPage.translateXProperty(), -registerPage.getWidth() - registerCombinedPage.getTranslateX())
                )
        );

        switchAnimation.setOnFinished(e -> {
            switch_login_link.setDisable(false);
            switch_kin_link.setDisable(false);
            kin_switch_register_link.setDisable(false);
            kin_switch_login_link.setDisable(false);
        });

        switchAnimation.play();

    }

    private void kinSwitchLoginAnimation() {
        kin_switch_login_link.setDisable(true);

        moving_image.setImage(new Image("/Resources/Images/register_bg.png"));

        Timeline switchAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.47),
                        new KeyValue(loginPage.visibleProperty(), true)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(moving_image.imageProperty(), new Image("/Resources/Images/login_bg.png")),
                        new KeyValue(kinRegisterPage.visibleProperty(), false)
                ),
                new KeyFrame(Duration.seconds(0.55),
                        new KeyValue(kinRegisterPage.translateXProperty(), 400)
                ),
                new KeyFrame(Duration.seconds(0.8),
                        new KeyValue(loginPage.translateXProperty(), 0)
                ),
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(moving_pane.translateXProperty(), 1030 - moving_pane.getTranslateX()),
                        new KeyValue(moving_image.translateXProperty(), -1030 - moving_image.getTranslateX())
                )
        );

        switchAnimation.setOnFinished(e -> {
            kin_switch_login_link.setDisable(false);
            resetRegisterPage();
        });

        switchAnimation.play();
    }

    private boolean hasEmptyField(TextField[] textFields) {
        boolean res = false;
        for (TextField tf : textFields) {
            if (tf.getText().isBlank()) {
                tf.setId("Empty");
                res = true;
            }
        }
        return res;
    }

    private void register() {
        String role;
        try {
            role = ((Label) ((RadioButton) roleToggleGroup.getSelectedToggle()).getChildrenUnmodifiable().get(0)).getText();
        } catch (NullPointerException e) {
            showErrorAlert("role must be chosen");
            return;
        }

        boolean hasEmpty = false;
        for (TextField tf : registerTextFields) {
            if (tf.getText().isBlank() && !(role.equals(User.ROLE.EDUCATOR) && tf.equals(registerKinEmailTF))) {
                tf.setId("Empty");
                hasEmpty = true;
            }
        }

        if (hasEmpty) {
            showErrorAlert("All field must be filled in");
            return;
        }

        String email = registerEmailTF.getText();
        String username = registerUsernameTF.getText();
        String password = registerPasswordTF.getText();
        String confirmPassword = registerConfirmPasswordTF.getText();
        String kinEmail = registerKinEmailTF.getText();

        // Email invalid
        if (!LoginRegisterHandler.isValidateEmail(email)) { 
            // Error message
            showErrorAlert("Invalid email address");
            return;
        }

        // Email exist
        if (LoginRegisterHandler.emailOrUsernameExist(email)) {
            showErrorAlert("This email is already registered");
            return;
        }

        // Username taken
        if (LoginRegisterHandler.emailOrUsernameExist(username)) {
            showErrorAlert("Username has been taken");
            return;
        }

        // Password not same
        if (!password.equals(confirmPassword)) {
            showErrorAlert("Confirm your password!");
            return;
        }

        // Check kin email correct or not
        if (!role.equals(User.ROLE.EDUCATOR)) {

            // kinEmail invalid
            if (!LoginRegisterHandler.isValidateEmail(kinEmail)) {
                // Error message
                showErrorAlert("Invalid email for kin");
                return;
            }

            // kinEmail does not exist
            if (!LoginRegisterHandler.emailOrUsernameExist(kinEmail)) {
                // Error message
                // switchKinRegisterAnimation(true);
                showErrorAlert("Kin does not exist, register for your kin to proceed");
                return;
            }

            // Check role of kinEmail correct or not
            if (!LoginRegisterHandler.isValidateKinRole(role, kinEmail)) {
                // Error message
                showErrorAlert("Role of kin incorrect");
                return;
            }

            // Check num of parent of child equals 1 or 2
            if (role.equals(User.ROLE.PARENT)) {
                if (!LoginRegisterHandler.isValidateParentCount(kinEmail)) {
                    // Error message
                    showErrorAlert("The student already has 2 parents");
                    return;
                }
            }
        }

        if (LoginRegisterHandler.isValidateRegistration(email, username, password, confirmPassword)) {
            User.register(role, email, username, password, kinEmail);
            showSuccessAlert("Registration success!");
            switchLoginAnimation();
        }
    }

    private void kinRegister() {
        String role = ((Label) ((RadioButton) roleToggleGroup.getSelectedToggle()).getChildrenUnmodifiable().get(0)).getText();
        String kinRole = null;
        if (role.equals(User.ROLE.STUDENT)) {
            kinRole = User.ROLE.PARENT;
        } else if (role.equals(User.ROLE.PARENT)) {
            kinRole = User.ROLE.STUDENT;
        }

        if (hasEmptyField(kinRegisterTextFields)) {
            showErrorAlert("All field must be filled in");
            return;
        }
        
        String email = registerEmailTF.getText();
        String username = registerUsernameTF.getText();
        String password = registerPasswordTF.getText();
        String confirmPassword = registerConfirmPasswordTF.getText();

        String kinEmail = kinRegisterEmailTF.getText();
        String kinUsername = kinRegisterUsernameTF.getText();
        String kinPassword = kinRegisterPasswordTF.getText();
        String kinConfirmPassword = kinRegisterConfirmPasswordTF.getText();

        // Email invalid
        if (!LoginRegisterHandler.isValidateEmail(kinEmail)) {
            // Error message
            showErrorAlert("Invalid email address");
            return;
        }

        // Email exist
        if (LoginRegisterHandler.emailOrUsernameExist(kinEmail)) {
            showErrorAlert("This email is already registered");
            return;
        }

        // Username taken
        if (LoginRegisterHandler.emailOrUsernameExist(kinUsername)) {
            showErrorAlert("Username has been taken");
            return;
        }

        // Password not same
        if (!kinPassword.equals(kinConfirmPassword)) {
            showErrorAlert("Confirm your password!");
            return;
        }

        if (LoginRegisterHandler.isValidateRegistration(email, username, password, confirmPassword)) {
            User.registerBoth(role, email, username, password, kinRole, kinEmail, kinUsername, kinPassword);
            showSuccessAlert("Registration success!");
            kinSwitchLoginAnimation();
        }
    }

    private void login(ActionEvent event) {
        String emailUsername = loginEmailTF.getText();
        String password = loginPasswordTF.getText();
        
        boolean hasEmpty = false;
        for (TextField tf : loginTextFields) {
            if (tf.getText().isBlank()) {
                tf.setId("Empty");
                hasEmpty = true;
            }
        }

        if (hasEmpty) {
            showErrorAlert("All field must be filled in");
            return;
        }

        if (!LoginRegisterHandler.emailOrUsernameExist(emailUsername)) {
            loginEmailTF.setId("Empty");
            showErrorAlert("Email or Username doesn't exist.\nCheck again or register an account");
            return;
        }
        if (LoginRegisterHandler.isValidateLogin(emailUsername, password)) {
            currentUser = User.login(emailUsername);
            if (currentUser.getRole().equals(User.ROLE.STUDENT)) {
                currentUser = new Student(currentUser);
            } else if (currentUser.getRole().equals(User.ROLE.PARENT)) {
                currentUser = new hackingthefuture.Parent(currentUser);
            } else if (currentUser.getRole().equals(User.ROLE.EDUCATOR)) {
                currentUser = new Educator(currentUser);
            }
            if (currentUser == null) {
                showErrorAlert("Error");
                return;
            }
            showSuccessAlert("Successfully Login");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AppMain.fxml"));
                root = loader.load();

                AppMainController AMController = loader.getController();
                AMController.getUser(currentUser);

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Main Page");
                stage.show();
                
                return;
            } catch (IOException e) {
                e.printStackTrace();
//                System.out.println("error");
            }
        }
        loginPasswordPF.setId("Empty");
        showErrorAlert("Incorrect password");
    }

    private void resetLoginPage() {
        loginEmailTF.clear();
        loginPasswordPF.clear();
        loginShowPwCB.setSelected(false);
    }

    private void resetRegisterPage() {
        registerEmailTF.clear();
        registerUsernameTF.clear();
        registerPasswordPF.clear();
        registerConfirmPasswordPF.clear();
        registerKinEmailTF.clear();
        registerShowPwCB.setSelected(false);
        roleToggleGroup.selectToggle(null);

        clearKinRegisterPage();

        separatePane.setPrefHeight(35);
        registerPage.setTranslateX(400);
        kinRegisterPage.setTranslateX(0);
        imageAndRegisterFormContainer.setTranslateX(-350);
        registerCombinedPage.setTranslateX(0);
        roleImageView.setTranslateX(-40);
        roleImageView.setVisible(false);
    }

    private void clearKinRegisterPage() {
        kinRegisterEmailTF.clear();
        kinRegisterUsernameTF.clear();
        kinRegisterPasswordPF.clear();
        kinRegisterConfirmPasswordPF.clear();
        kinRegisterShowPwCB.setSelected(false);
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
        stage.initOwner((Stage) moving_pane.getScene().getWindow());

        stage.setX(420);
        stage.setY(200);

        stage.show();
    }
}
