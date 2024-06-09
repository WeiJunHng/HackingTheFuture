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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class CommentCardController implements Initializable {
    
    @FXML
    private ImageView blackLove;

    @FXML
    private Label commentContentLabel;

    @FXML
    private Button commentAuthorBtn;

    @FXML
    private Label commentAuthorLabel, commentDateLabel;

    @FXML
    private Button commentLikeBtn;

    @FXML
    private Label commentLikeCountLabel;

    @FXML
    private ImageView redLove;

    private Comment comment;
    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setup(Comment comment, User currentUser) {
        this.comment = comment;
        this.currentUser = currentUser;
        
        commentAuthorBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                VBox profile = loader.load();
                ProfileController profileController = loader.getController();
                profileController.setupProfile(comment.getAuthor(), currentUser);
                
                AppMainController.changePage(profile, profileController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        commentLikeBtn.setOnAction(event -> {
            if (this.comment.isUserLiked(this.currentUser)) {
                this.comment.disliked(this.currentUser);
            } else {
                this.comment.liked(this.currentUser);
            }
            refresh();
        });

        refresh();
    }

    public void refresh() {
        commentDateLabel.setText(comment.getFormattedDate());
        commentContentLabel.setText(comment.getContent());
        commentAuthorLabel.setText(comment.getAuthor().getUsername());
        commentAuthorLabel.setId(comment.getAuthor().getRole() + "Label");
        if (comment.getLikeCount() == 0) {
            ((HBox) commentLikeCountLabel.getParent()).getChildren().remove(commentLikeCountLabel);
        } else if (!((HBox) commentLikeBtn.getParent()).getChildren().contains(commentLikeCountLabel)) {
            ((HBox) commentLikeBtn.getParent()).getChildren().add(commentLikeCountLabel);
        }
        redLove.setVisible(comment.isUserLiked(currentUser));
        blackLove.setVisible(!comment.isUserLiked(currentUser));
        commentLikeCountLabel.setText(String.valueOf(comment.getLikeCount()));
    }
    
}
