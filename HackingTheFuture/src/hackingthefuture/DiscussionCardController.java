/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class DiscussionCardController implements Initializable {

    @FXML
    private ImageView blackLove;

    @FXML
    private VBox discussionCard;

    @FXML
    private Label discussionContentLabel;

    @FXML
    private Button discussionAuthorBtn;

    @FXML
    private Label discussionAuthorLabel;

    @FXML
    private Button discussionLikeBtn;

    @FXML
    private Label discussionLikeCountLabel;

    @FXML
    private StackPane discussionLikeIconSP;

    @FXML
    private Label discussionTitleLabel;

    @FXML
    private ImageView redLove;

    private VBox countLabelParent;
    private Discussion discussion;
    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        countLabelParent = (VBox) discussionLikeCountLabel.getParent();
    }

    // Get the discussion and current login User
    public void setup(Discussion discussion, User currentUser) {
        this.discussion = discussion;
        this.currentUser = currentUser;

        // View author's profile
        discussionAuthorBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                VBox profile = loader.load();
                ProfileController profileController = loader.getController();
                profileController.setupProfile(discussion.getAuthor(), currentUser);

                AppMainController.changePage(profile, profileController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Like or dislike the discussion
        discussionLikeBtn.setOnAction(event -> {
            if (this.discussion.isUserLiked(this.currentUser)) {
                this.discussion.disliked(this.currentUser);
            } else {
                this.discussion.liked(this.currentUser);
            }
            refresh();
        });

        // View the full discussion
        discussionCard.setOnMouseClicked(eh -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DiscussionFullPage.fxml"));
                Node root = loader.load();
                DiscussionFullPageController discussionFullPageController = loader.getController();
                discussionFullPageController.setup(discussion, currentUser);

                AppMainController.changePage(root, discussionFullPageController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refresh();
    }

    // Refresh details about the discussion
    // Determine colour of the love (red for liked, black for not liked)
    public void refresh() {
        discussionTitleLabel.setText(discussion.getTitle());
        discussionContentLabel.setText(discussion.getContent());
        discussionAuthorLabel.setText(discussion.getAuthor().getUsername());
        discussionAuthorLabel.setId(discussion.getAuthor().getRole() + "Label");
        
        if(!countLabelParent.getChildren().contains(discussionLikeCountLabel)){
            countLabelParent.getChildren().add(discussionLikeCountLabel);
        }
        
        if (discussion.getLikeCount() == 0) {
            ((VBox) discussionLikeCountLabel.getParent()).getChildren().remove(discussionLikeCountLabel);
        } else if (!((VBox) discussionLikeBtn.getParent()).getChildren().contains(discussionLikeCountLabel)) {
            ((VBox) discussionLikeBtn.getParent()).getChildren().add(discussionLikeCountLabel);
        }
        redLove.setVisible(discussion.isUserLiked(currentUser));
        blackLove.setVisible(!discussion.isUserLiked(currentUser));
        discussionLikeCountLabel.setText(String.valueOf(discussion.getLikeCount()));
    }
}
