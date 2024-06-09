/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class LeaderboardPageController extends Controller implements Initializable {

    @FXML
    private TableView leaderboardView;

    @FXML
    private TableColumn<Student, String> rankColumn, pointColumn;

    @FXML
    private TableColumn<Student, Student> userColumn;

    private User currentUser;
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Rectangle clip = new Rectangle(1, 0, 1354, 597);
        clip.setArcWidth(50);
        clip.setArcHeight(50);
        clip.setStyle("-fx-border-radius: 25; -fx-border-color: white; -fx-border-width: 2;");
        leaderboardView.setClip(clip);
        
        Platform.runLater(() -> {
            leaderboardView.getScene().getStylesheets().add(getClass().getResource("LeaderboardStyling.css").toExternalForm());
        });
    }

    public void setup(User user) {
        currentUser = user;

        // Highlight ranking of current user
        leaderboardView.setRowFactory(tv -> new TableRow<Student>() {
            @Override
            public void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                if (student == null) {
                    setStyle("");
                } else if (student.getID().equals(currentUser.getID())) {
                    setStyle("-fx-background-color: linear-gradient(to right, #80cc85, #67dd9a); -fx-text-background-color: #32473b;");
                } else {
                    setStyle("");
                }
            }
        });

        // Auto ranking start from 1
        rankColumn.setCellValueFactory(cellData -> {
            return new ReadOnlyObjectWrapper(leaderboardView.getItems().indexOf(cellData.getValue()) + 1 + "");
        });

        // Customise "(You)" after username of current user
        userColumn.setCellValueFactory(cellData -> {
            return new ReadOnlyObjectWrapper<>(cellData.getValue());
        });

        userColumn.setCellFactory(column -> new TableCell<>() {
            private final Hyperlink link = new Hyperlink();

            @Override
            protected void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                if (empty || student == null) {
                    setGraphic(null);
                } else {
                    link.setText(student.getUsername() + (student.getID().equals(currentUser.getID()) ? " (You)" : ""));
                    link.setId("UserHyperlink");
                    link.setOnAction(event -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
                            Node profile = loader.load();
                            ProfileController profileController = loader.getController();
                            profileController.setupProfile(student, currentUser);

                            AppMainController.changePage(profile, profileController);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    setGraphic(link);
                }
            }
        });

        pointColumn.setCellValueFactory(cellData -> {
            return new ReadOnlyObjectWrapper(cellData.getValue().getPoint());
        });

        refresh();
    }

    private ObservableList<Student> getStudents() {
        ObservableList<Student> res = FXCollections.observableArrayList();

        PriorityQueue<Student> leaderboardQueue = new PriorityQueue<>((s1, s2) -> Integer.compare(s2.getPoint(), s1.getPoint()));
        leaderboardQueue.addAll(LeaderboardHandler.getStudents());

        while (!leaderboardQueue.isEmpty() && leaderboardQueue.peek().getPoint()!=0) {
            res.add(leaderboardQueue.poll());
        }
        
        PriorityQueue<Student> noPointStudentQueue = new PriorityQueue<>((s1, s2) -> s1.getID().compareTo(s2.getID()));
        noPointStudentQueue.addAll(leaderboardQueue);
        
        while (!noPointStudentQueue.isEmpty()) {
            res.add(noPointStudentQueue.poll());
        }

        return res;
    }

    public void refresh() {
        leaderboardView.setItems(getStudents());
    }

}
