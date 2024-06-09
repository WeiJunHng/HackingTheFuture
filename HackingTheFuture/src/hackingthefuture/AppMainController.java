/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
public class AppMainController implements Initializable {

    private static VBox currentPageBox;
//    private static Node alertPage;
//    private static AlertController alertController;
    private static Controller currentController = null;
    private static Stack<Node> previousPageStack;
    private static Stack<Controller> previousControllerStack;
    private static Button staticBackBtn;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private AnchorPane navBarMover, backBtnPane;

    @FXML
    private VBox navBar, page;

    @FXML
    private Button menuBtn, navBarProfileBtn, navBarEventsBtn, navBarQuizBtn, navBarBookingBtn, navBarDiscussionBtn, navBarLeaderboardBtn;

    @FXML
    private Button topBarFriendBtn, topBarLogoutBtn, topBarHomeBtn, backBtn;
    
    @FXML
    private HBox homePage;

    @FXML
    private Label navBarUsernameLabel, navBarRoleLabel;

    @FXML
    private ImageView menuImageView, userProfileImage;

    private boolean movable = true;

    private Button currentBarBtn = null;

    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        currentController = null;
        previousPageStack = new Stack<>();
        previousControllerStack = new Stack<>();
        staticBackBtn = backBtn;
        currentPageBox = page;
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
//            alertPage = loader.load();
//            alertController = loader.getController();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        navBarMover.setClip(new Rectangle(0, 80, 350, 720));
        navBarMover.setTranslateX(-350);
        navBarMover.setVisible(true);
        menuImageView.setDisable(true);
        moveNavBar();

        backBtnPane.setClip(new Rectangle(23, 114, 68, 43.2));
        backBtn.setVisible(false);
        backBtn.setOnAction(eh -> {
            if (previousPageStack.isEmpty()) {
                return;
            }
            if (currentPageBox.getChildren().size() > 1) {
                currentPageBox.getChildren().removeLast();
            }
            Node currentNode = previousPageStack.pop();
            if (currentNode != null) {
                currentPageBox.getChildren().add(currentNode);
            }
            currentController = previousControllerStack.pop();
            if (currentController != null) {
                currentController.refresh();
            }
            backBtn.setVisible(!previousPageStack.isEmpty());
        });
        
        topBarHomeBtn.setOnAction(event->{
            if(!getCurrentPage().equals(homePage)){
                changePage(homePage, null);
                previousPageStack.clear();
                previousControllerStack.clear();
                backBtn.setVisible(false);
            }
        });

        menuBtn.setOnAction(event -> moveNavBar());
    }

    public void getUser(User user) {
        currentUser = user;

        userProfileImage.setImage(new Image("/Resources/Images/"+user.getRole()+" Profile.png"));
        navBarUsernameLabel.setText(currentUser.getUsername());
        navBarRoleLabel.setText(currentUser.getRole().toUpperCase());
        navBarRoleLabel.setId(currentUser.getRole());

        for (Node node : navBar.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setOnAction(event -> changeSelectedBtn(event));
            }
        }

        if (currentUser instanceof hackingthefuture.Parent) {
            navBar.getChildren().remove(navBarQuizBtn);
        } else {
            navBar.getChildren().remove(navBarBookingBtn);
        }

        if (currentUser instanceof Student student) {
            topBarFriendBtn.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FriendPage.fxml"));
                    Parent root = loader.load();

                    FriendPageController friendPageController = loader.getController();
                    friendPageController.setup(student);
                    Stage stage = new Stage();
                    Scene scene = new Scene(root, Color.TRANSPARENT);

                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner((Stage) topBarFriendBtn.getScene().getWindow());

                    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                    stage.setX(2 + (screenBounds.getWidth() - ((VBox) root).getPrefWidth()) / 2);
                    stage.setY((screenBounds.getHeight() - ((VBox) root).getPrefHeight()) / 2);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            ((HBox) topBarFriendBtn.getParent()).getChildren().remove(topBarFriendBtn);
        }

        topBarLogoutBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                showSuccessAlert("Successfully Logout");
                ((Stage) topBarLogoutBtn.getScene().getWindow()).setScene(scene);
                currentUser = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void moveNavBar() {
        if (!movable) {
            return;
        }

        movable = false;
        menuBtn.setDisable(true);

        Image img;
        if (navBarMover.getTranslateX() == 0) {
            img = new Image("/Resources/Images/menu.png");
        } else {
            img = new Image("/Resources/Images/menu-cross.png");
        }

        Timeline switchAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(menuImageView.rotateProperty(), 180 - menuImageView.getRotate()),
                        new KeyValue(menuImageView.imageProperty(), img)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(navBarMover.translateXProperty(), -350 - navBarMover.getTranslateX())
                //                        new KeyValue(pageDescLabel.translateXProperty(), 170 - pageDescLabel.getTranslateX())
                )
        );

        switchAnimation.setOnFinished(e -> {
            menuBtn.setDisable(false);
            movable = true;
        });

        switchAnimation.play();
    }

    private void changeSelectedBtn(ActionEvent event) {
        Button btnClicked = (Button) event.getSource();
        if (!btnClicked.equals(currentBarBtn)) {
            if (currentBarBtn != null) {
                currentBarBtn.setId("");
            }
            currentBarBtn = btnClicked;
            currentBarBtn.setId("selected");
        }
        moveNavBar();
        // Show profile
        if (btnClicked.equals(navBarProfileBtn)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                Node profile = loader.load();
                ProfileController profileController = loader.getController();
                profileController.setupProfile(currentUser, currentUser);
                changePage(profile, profileController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (btnClicked.equals(navBarEventsBtn)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EventPage.fxml"));
                Node root = loader.load();
                EventPageController eventPageController = loader.getController();
                eventPageController.setupEventPage(currentUser);
                changePage(root, eventPageController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (btnClicked.equals(navBarQuizBtn)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizPage.fxml"));
                Node root = loader.load();
                QuizPageController quizPageController = loader.getController();
                quizPageController.setupQuizPage(currentUser);
                changePage(root, quizPageController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (btnClicked.equals(navBarDiscussionBtn)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DiscussionPage.fxml"));
                Node root = loader.load();
                DiscussionPageController discussionPageController = loader.getController();
                discussionPageController.setup(currentUser);
                changePage(root, discussionPageController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (btnClicked.equals(navBarBookingBtn)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DestinationListPage.fxml"));
                Node root = loader.load();
                DestinationListPageController destinationListPageController = loader.getController();
                destinationListPageController.setup((hackingthefuture.Parent) currentUser);
                changePage(root, destinationListPageController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (btnClicked.equals(navBarLeaderboardBtn)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LeaderboardPage.fxml"));
                Node root = loader.load();
                LeaderboardPageController leaderboardPageController = loader.getController();
                leaderboardPageController.setup(currentUser);
                changePage(root, leaderboardPageController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void changePage(Node node, Controller controller) {
        previousPageStack.add(getCurrentPage());
        previousControllerStack.add(currentController);
        if (currentPageBox.getChildren().size() > 1) {
            currentPageBox.getChildren().removeLast();
        }
        currentPageBox.getChildren().add(node);
        currentController = controller;
        staticBackBtn.setVisible(!previousPageStack.isEmpty());
    }

    public static Node getCurrentPage() {
        if (currentPageBox.getChildren().size() == 1) {
            return null;
        }
        return currentPageBox.getChildren().get(1);
    }

    public static void showSuccessAlert(String message) {
        AlertController.showSuccessAlert(message, (Stage) currentPageBox.getScene().getWindow());
    }

    public static void showErrorAlert(String message) {
        AlertController.showErrorAlert(message, (Stage) currentPageBox.getScene().getWindow());
    }
    
    public static void refreshPage(){
        currentController.refresh();
    }

//    private static void showAlert() {
//        Stage stage = new Stage();
//        Scene scene = new Scene((Parent) alertPage, Color.TRANSPARENT);
//
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.initStyle(StageStyle.TRANSPARENT);
//        stage.initModality(Modality.WINDOW_MODAL);
//        stage.initOwner((Stage) currentPageBox.getScene().getWindow());
//
//        stage.setX(420);
//        stage.setY(200);
//
//        stage.show();
//    }

}
