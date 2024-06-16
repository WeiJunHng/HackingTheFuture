/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
public class ProfileController extends Controller implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private HBox profileBg, kinBox;

    @FXML
    private Label profileEmailLabel;

    @FXML
    private Label profileRoleLabel;

    @FXML
    private Label profileUsernameLabel;

    @FXML
    private Label kinRoleLabel;

    @FXML
    private Label profileLocationLabel, profileStudentPointLabel;

    @FXML
    private Label profileEducatorEventCreatedLabel, profileEducatorQuizCreatedLabel;

    @FXML
    private VBox kinInfoBox;

    @FXML
    private Button profileEditBtn, profileAddFriendBtn, profileEducatorCreateEventBtn, profileEducatorCreateQuizBtn;

    @FXML
    private VBox profileStudentRightBox, profileParentRightBox, profileEducatorRightBox;

    @FXML
    private VBox registeredEventBox, studentPastBookingBox, parentPastBookingBox;

    @FXML
    private HBox studentFriendBox;

    @FXML
    private Label studentFriendCountLabel;

    @FXML
    private Button studentViewFriendBtn;

    private User user, currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    // Get owner of the profile and current login User
    public void setupProfile(User user, User currentUser) {
        if (user.getID().equals(currentUser.getID())) {
            this.user = currentUser;
        } else {
            this.user = user;
        }
        this.currentUser = currentUser;

        refresh();
    }

    // Refresh the profile
    public void refresh() {
        profileStudentRightBox.setVisible(false);
        profileParentRightBox.setVisible(false);
        profileEducatorRightBox.setVisible(false);
        studentFriendBox.setVisible(false);

        kinInfoBox.getChildren().clear();
        parentPastBookingBox.getChildren().clear();
        registeredEventBox.getChildren().clear();
        studentPastBookingBox.getChildren().clear();

        profileBg.setId(user.getRole() + "Bg");
        profileRoleLabel.setText(user.getRole().toUpperCase());
        profileRoleLabel.setId(user.getRole() + "Label");
        profileUsernameLabel.setText(user.getUsername());
        profileEmailLabel.setText(user.getEmail());
        profileLocationLabel.setText(user.getLocation().replace(",", " , ").replaceAll("\\(|\\)", ""));

        // If User viewing own profile
        if (user.getID().equals(currentUser.getID())) {
            // Show "Edit" page
            profileEditBtn.setOnAction(event->{
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("EditProfilePage.fxml"));
                    Parent root = loader.load();

                    EditProfilePageController editProfilePageController = loader.getController();
                    editProfilePageController.setup(currentUser);
                    Stage stage = new Stage();
                    Scene scene = new Scene(root, Color.TRANSPARENT);

                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner((Stage) studentViewFriendBtn.getScene().getWindow());

                    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                    stage.setX((screenBounds.getWidth() - ((VBox) root).getPrefWidth()) / 2);
                    stage.setY((screenBounds.getHeight() - ((VBox) root).getPrefHeight()) / 2);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        // If User viewing own profile, show "Edit Profile" button, else hide it
        profileEditBtn.setVisible(user.getID().equals(currentUser.getID()));

        profileAddFriendBtn.setVisible(false);
        if (user instanceof Student profileOwner && currentUser instanceof Student currentStudent) {
            // If other viewing a Student's profile, 
            // not Student viewing own profile
            // and owner is not friend of current login Student, show "Add Freind" button, else hide it
            profileAddFriendBtn.setVisible(!profileOwner.isFriend(currentStudent) && !user.getID().equals(currentUser.getID()));

            if (profileOwner.isFriend(currentStudent)) {

            } 
            // If friend request sent to profile owner, disable "Add Friend" button and change its text
            else if (currentStudent.isFriendRequestSent(profileOwner)) {
                profileAddFriendBtn.setText("Request Sent");
                profileAddFriendBtn.setDisable(true);
            } else {
                // Send friend request
                profileAddFriendBtn.setOnAction(event -> {
                    AppMainController.showSuccessAlert(currentStudent.sendFriendRequest(profileOwner));
                    refresh();
                });
            }
        }

        // For student
        if (user instanceof Student student) {
            studentFriendBox.setVisible(true);
            studentFriendCountLabel.setText(student.getFriendList().size() + "");
            
            // Show "Friends" page
            studentViewFriendBtn.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FriendPage.fxml"));
                    Parent root = loader.load();

                    FriendPageController friendPageController = loader.getController();
                    friendPageController.setup(student, currentUser);
                    Stage stage = new Stage();
                    Scene scene = new Scene(root, Color.TRANSPARENT);

                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner((Stage) studentViewFriendBtn.getScene().getWindow());

                    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                    stage.setX(2 + (screenBounds.getWidth() - ((VBox) root).getPrefWidth()) / 2);
                    stage.setY((screenBounds.getHeight() - ((VBox) root).getPrefHeight()) / 2);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // List of parents
            kinRoleLabel.setText("Parent");
            for (String childrenID : student.getParentList()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserHyperlink.fxml"));
                    Hyperlink link = loader.load();
                    UserHyperlinkController userHyperlinkController = loader.getController();
                    userHyperlinkController.setUser(UserHandler.getUser(childrenID), currentUser);
                    kinInfoBox.getChildren().add(link);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // List of registered events
            List<Event> registeredEventList = student.getRegisteredEventObjectList();
            registeredEventList.sort((e1, e2) -> e1.getDate().compareTo(e2.getDate()));
            for (Event event : registeredEventList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Link.fxml"));
                    Hyperlink link = loader.load();
                    link.setText(event.getTitle());
                    link.setOnAction(e -> {
                        try {
                            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("EventDetailsPage.fxml"));
                            Parent root = loader2.load();
                            EventDetailsPageController eventDetailsPageController = loader2.getController();
                            eventDetailsPageController.setup(event, currentUser);

                            Stage stage = new Stage();
                            Scene scene = new Scene(root, Color.TRANSPARENT);

                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.initOwner((Stage) profileBg.getScene().getWindow());

                            stage.setX(270);
                            stage.setY(75);

                            stage.show();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    });
                    registeredEventBox.getChildren().add(link);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // List of past bookings
            List<Booking> pastBookingList = student.getRegisteredBookingList();
            pastBookingList.sort((e1, e2) -> e1.getSlot().compareTo(e2.getSlot()));
            for (Booking booking : pastBookingList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Link.fxml"));
                    Hyperlink link = loader.load();
                    link.setText(booking.getDestination().getName());
                    link.setOnAction(e -> {
                        try {
                            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("BookingDetailsPage.fxml"));
                            Parent root = loader2.load();
                            BookingDetailsPageController bookingDetailsPageController = loader2.getController();
                            bookingDetailsPageController.setup(booking, currentUser);

                            Stage stage = new Stage();
                            Scene scene = new Scene(root, Color.TRANSPARENT);

                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.initOwner((Stage) profileBg.getScene().getWindow());

                            stage.setX(295);
                            stage.setY(100);

                            stage.show();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    });
                    studentPastBookingBox.getChildren().add(link);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            profileStudentRightBox.setVisible(true);
            // Show points
            profileStudentPointLabel.setText(student.getPoint() + "");

        } 
        // For  parent
        else if (user instanceof hackingthefuture.Parent parent) {
            // List of children
            kinRoleLabel.setText("Children");
            for (String childrenID : parent.getChildrenList()) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UserHyperlink.fxml"));
                    Hyperlink link = loader.load();
                    UserHyperlinkController userHyperlinkController = loader.getController();
                    userHyperlinkController.setUser(UserHandler.getUser(childrenID), currentUser);
                    kinInfoBox.getChildren().add(link);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // List of past bookings
            List<Booking> pastBookingList = parent.getPastBookingList();
            pastBookingList.sort((e1, e2) -> e1.getSlot().compareTo(e2.getSlot()));
            for (Booking booking : pastBookingList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Link.fxml"));
                    Hyperlink link = loader.load();
                    link.setText(booking.getDestination().getName());
                    link.setOnAction(e -> {
                        try {
                            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("BookingDetailsPage.fxml"));
                            Parent root = loader2.load();
                            BookingDetailsPageController bookingDetailsPageController = loader2.getController();
                            bookingDetailsPageController.setup(booking, currentUser);

                            Stage stage = new Stage();
                            Scene scene = new Scene(root, Color.TRANSPARENT);

                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.initModality(Modality.WINDOW_MODAL);
                            stage.initOwner((Stage) profileBg.getScene().getWindow());

                            stage.setX(295);
                            stage.setY(100);

                            stage.show();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    });
                    parentPastBookingBox.getChildren().add(link);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            profileParentRightBox.setVisible(true);

        } 
        // For educator
        else if (user instanceof Educator educator) {
            kinBox.setVisible(false);

            profileEducatorRightBox.setVisible(true);
            // Hide "Create" button for event and quiz if not Educator viewing own profile
            if (!user.getID().equals(currentUser.getID())) {
                profileEducatorCreateEventBtn.setVisible(false);
                profileEducatorCreateQuizBtn.setVisible(false);
            }

            // Show "Create Event" page
            profileEducatorCreateEventBtn.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateEventPage.fxml"));
                    Parent root = loader.load();
                    CreateEventPageController createEventPageController = loader.getController();
                    createEventPageController.setupPage(educator);

                    Stage stage = new Stage();
                    Scene scene = new Scene(root, Color.TRANSPARENT);

                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner((Stage) profileBg.getScene().getWindow());

                    stage.setX(295);
                    stage.setY(110);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Show "Create Quiz" page
            profileEducatorCreateQuizBtn.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateQuizPage.fxml"));
                    Parent root = loader.load();
                    CreateQuizPageController createQuizPageController = loader.getController();
                    createQuizPageController.setupPage(educator);

                    Stage stage = new Stage();
                    Scene scene = new Scene(root, Color.TRANSPARENT);

                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner((Stage) profileBg.getScene().getWindow());

                    stage.setX(295);
                    stage.setY(130);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            // Show number of event created and quiz created
            profileEducatorEventCreatedLabel.setText(educator.getEventCreatedList().size() + "");
            profileEducatorQuizCreatedLabel.setText(educator.getQuizCreatedList().size() + "");
        }
    }

}
