/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import static hackingthefuture.AppMainController.changePage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class DiscussionPageController extends Controller implements Initializable {

    @FXML
    private Button createDiscussionBtn;

    @FXML
    private VBox discussionsBox;

    private User currentUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setup(User currentUser) {
        this.currentUser = currentUser;

        createDiscussionBtn.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CreateDiscussionPage.fxml"));
                Parent root = loader.load();

                CreateDiscussionPageController createDiscussionPageController = loader.getController();
                createDiscussionPageController.setup(currentUser, this);
                Stage stage = new Stage();
                Scene scene = new Scene(root, Color.TRANSPARENT);

                stage.setScene(scene);
                stage.setResizable(false);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner((Stage) createDiscussionBtn.getScene().getWindow());

                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                stage.setX(2 + (screenBounds.getWidth() - ((VBox) root).getPrefWidth()) / 2);
                stage.setY(80);

                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refresh();
    }

    @Override
    public void refresh() {
        discussionsBox.getChildren().clear();
        for (Discussion discussion : DiscussionHandler.getDiscussionList()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("DiscussionCard.fxml"));
                Node root = loader.load();
                DiscussionCardController discussionCardController = loader.getController();
                discussionCardController.setup(discussion, currentUser);
                discussionsBox.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
