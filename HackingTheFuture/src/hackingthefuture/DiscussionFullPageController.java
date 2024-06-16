/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class DiscussionFullPageController extends Controller implements Initializable {

    @FXML
    private ImageView blackLove;

    @FXML
    private VBox commentBox;

    @FXML
    private TextField commentTF;

    @FXML
    private Button discussionAuthorBtn;

    @FXML
    private Label discussionAuthorLabel;

    @FXML
    private Label discussionContentLabel;

    @FXML
    private Label discussionDateLabel;

    @FXML
    private Button discussionLikeBtn;

    @FXML
    private Label discussionLikeCountLabel;

    @FXML
    private Label discussionTitleLabel;

    @FXML
    private Button postCommentBtn;

    @FXML
    private ImageView redLove;

    private HBox countLabelParent;
    private Discussion discussion;
    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        countLabelParent = (HBox)discussionLikeCountLabel.getParent();
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
        
        // Post new comment
        postCommentBtn.setOnAction(event->{
            String commentContent = commentTF.getText();
            if(!commentContent.isBlank()){
                Comment comment = new Comment(currentUser.getID(), commentContent, discussion.getID(), LocalDateTime.now());
                DiscussionHandler.postComment(comment, currentUser);
                refresh();
                AppMainController.showSuccessAlert("Comment posted!");
            }
        });

        refresh();
    }

    // Refresh details of the discussion and comments
    public void refresh() {
        commentTF.clear();
        
        // Discussion
        discussionTitleLabel.setText(discussion.getTitle());
        discussionDateLabel.setText(discussion.getFormattedDate());
        discussionContentLabel.setText(discussion.getContent());
        discussionAuthorLabel.setText(discussion.getAuthor().getUsername());
        discussionAuthorLabel.setId(discussion.getAuthor().getRole() + "Label");
        
        if(!countLabelParent.getChildren().contains(discussionLikeCountLabel)){
            countLabelParent.getChildren().add(discussionLikeCountLabel);
        }
        
        if (discussion.getLikeCount() == 0) {
            ((HBox) discussionLikeCountLabel.getParent()).getChildren().remove(discussionLikeCountLabel);
        } else if (!((HBox) discussionLikeBtn.getParent()).getChildren().contains(discussionLikeCountLabel)) {
            ((HBox) discussionLikeBtn.getParent()).getChildren().add(discussionLikeCountLabel);
        }
        redLove.setVisible(discussion.isUserLiked(currentUser));
        blackLove.setVisible(!discussion.isUserLiked(currentUser));
        discussionLikeCountLabel.setText(String.valueOf(discussion.getLikeCount()));
        
        // Comment
        commentBox.getChildren().clear();
        for (Comment comment : DiscussionHandler.getCommentList(this.discussion)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CommentCard.fxml"));
                Node root = loader.load();
                CommentCardController commentCardController = loader.getController();
                commentCardController.setup(comment, currentUser);
                commentBox.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
