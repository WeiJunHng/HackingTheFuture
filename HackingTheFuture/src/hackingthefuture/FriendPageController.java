/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class FriendPageController extends Controller implements Initializable {

    @FXML
    private StackPane backgroundStackPane;

    @FXML
    private Button closeBtn;

    @FXML
    private VBox friendRequestVBox;

    @FXML
    private VBox friendPaneForCurrentUser, friendVBox, otherFriendVBox;

    @FXML
    private Button switchFriendBtn;

    @FXML
    private Button switchFriendRequestBtn;

    @FXML
    private ScrollPane friendPane;

    @FXML
    private ScrollPane friendRequestPane;
    
    @FXML
    private StackPane otherFriendPane;

    private Button currentSelectedBtn;
    private int selectedPaneIndex = 0;
    private final String[] backgroundRadiusArr = {"0 20 20 20", "20 0 20 20"};

    private Initializable controller = null;
    private User currentUser;
    private Student owner;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        currentSelectedBtn = switchFriendBtn;

        closeBtn.setOnAction(event -> close());

        // Show "Friends" page
        switchFriendBtn.setOnAction(event -> {
            switchPane(event);
        });

        // Show "Friend Request" page
        switchFriendRequestBtn.setOnAction(event -> {
            switchPane(event);
        });
    }

    // Get current login Student
    // Student viewing own friends
    public void setup(Student currentUser) {
        owner = currentUser;
        this.currentUser = currentUser;

        refresh();
    }

    // Get owner of the "Friends" page and current login User
    // Other User viewing a student's friends
    public void setup(Student owner, User currentUser) {
        this.owner = owner;
        this.currentUser = currentUser;

        refresh();
    }

    // Refresh friends and friend requests
    public void refresh() {
        otherFriendPane.setVisible(false);
        friendPaneForCurrentUser.setVisible(false);
        
        // Student viewing own "Friends" page
        if (owner.getID().equals(currentUser.getID())) {
            friendPaneForCurrentUser.setVisible(true);
            currentSelectedBtn.setDisable(true);
            currentSelectedBtn.setId("SelectedBtn");
            backgroundStackPane.setStyle("-fx-background-color:  rgba(196,196,196,0.7); -fx-background-radius: " + backgroundRadiusArr[selectedPaneIndex]);
            backgroundStackPane.getChildren().get(selectedPaneIndex).setVisible(true);

            friendVBox.getChildren().clear();
            friendRequestVBox.getChildren().clear();

            // Update friend requests
            if (friendRequestPane.isVisible()) {
                for (String requesterID : owner.getFriendRequestList().reversed()) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("FriendRequestCard.fxml"));
                        Node root = loader.load();
                        FriendRequestCardController friendRequestCardController = loader.getController();
                        friendRequestCardController.setup((Student) UserHandler.getUser(requesterID), owner, this);
                        friendRequestVBox.getChildren().add(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } 
            // Update friends
            else if (friendPane.isVisible()) {
                for (String friendID : owner.getFriendList()) {
                    Student friend = (Student) UserHandler.getUser(friendID);
                    Hyperlink link = new Hyperlink(friend.getUsername());
                    // Show friend's profile
                    link.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                            VBox profile = loader.load();
                            ProfileController profileController = loader.getController();
                            profileController.setupProfile(friend, currentUser);
                            close();
                            AppMainController.changePage(profile, profileController);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    friendVBox.getChildren().add(link);
                }
            }
        } 
        // Other people viewing a Student's "Friends" page
        else {
            otherFriendPane.setVisible(true);
            // Update friends
            otherFriendVBox.getChildren().clear();
            for (String friendID : owner.getFriendList()) {
                Student friend = (Student) UserHandler.getUser(friendID);
                Hyperlink link = new Hyperlink(friend.getUsername());
                // Show friend's profile
                link.setOnAction(event -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                        VBox profile = loader.load();
                        ProfileController profileController = loader.getController();
                        profileController.setupProfile(friend, currentUser);
                        close();
                        AppMainController.changePage(profile, profileController);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                otherFriendVBox.getChildren().add(link);
            }
        }
    }

    // Switching page between "Friends" and "Friend Request"
    // Only used when Student viewing own "Friends" page
    private void switchPane(ActionEvent event) {
        currentSelectedBtn.setDisable(false);
        currentSelectedBtn.setId("");
        backgroundStackPane.getChildren().get(selectedPaneIndex).setVisible(false);

        currentSelectedBtn = (Button) event.getSource();

        selectedPaneIndex = (selectedPaneIndex + 1) % 2;

        refresh();
    }

    // Close the pop up window
    public void close() {
        ((Stage) closeBtn.getScene().getWindow()).close();
    }

}
