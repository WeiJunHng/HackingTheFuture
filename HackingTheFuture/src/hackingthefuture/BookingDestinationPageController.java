/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class BookingDestinationPageController extends Controller implements Initializable {

    @FXML
    private Button bookBtn;

    @FXML
    private SplitMenuButton childrenMenu;

    @FXML
    private HBox destinationImageHBox;

    @FXML
    private Button destinationPrevBtn, destinationNextBtn;

    @FXML
    private SplitMenuButton destinationMenu;

    @FXML
    private Label distanceLabel;

    @FXML
    private SplitMenuButton slotMenu;

    private Parent currentParent;

    private int destinationIndex;
    private final List<Destination> destinationList = new ArrayList<>();

    private boolean prevLoop = false;
    private boolean nextLoop = false;

    private CheckBox selectAllBox = new CheckBox();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        destinationNextBtn.setOnAction(event -> {
            nextLoop = destinationIndex == destinationList.size() - 1;
            if (nextLoop) {
                destinationImageHBox.getChildren().add(destinationImageHBox.getChildren().removeFirst());
                destinationIndex--;
                destinationImageHBox.setTranslateX(destinationImageHBox.getTranslateX() + 452);
            }
            destinationIndex = (destinationIndex + 1) % destinationList.size();
            refresh();
        });

        destinationPrevBtn.setOnAction(event -> {
            prevLoop = destinationIndex == 0;
            if (prevLoop) {
                destinationImageHBox.getChildren().addFirst(destinationImageHBox.getChildren().removeLast());
                destinationIndex++;
                destinationImageHBox.setTranslateX(destinationImageHBox.getTranslateX() - 452);
            }
            destinationIndex = (destinationIndex - 1 + destinationList.size()) % destinationList.size();
            refresh();
        });

        selectAllBox.setSelected(true);
        selectAllBox.setDisable(true);
        selectAllBox.setStyle("-fx-opacity: 1; -fx-font-size: 17;");

        Label allLabel = new Label("All");
        allLabel.setStyle("-fx-opacity: 1; -fx-text-fill : #4e4e4e; -fx-font-size: 25; -fx-padding: 0 5 0 10;");
        selectAllBox.setGraphic(allLabel);

        MenuItem allMenuItem = new MenuItem();
        allMenuItem.setGraphic(selectAllBox);

        allMenuItem.setOnAction(event -> {
            selectAllBox.setSelected(!selectAllBox.isSelected());
            for (MenuItem item : childrenMenu.getItems()) {
                if (item.getGraphic() instanceof StudentCheckBox studentCheckBox) {
                    studentCheckBox.setSelected(selectAllBox.isSelected());
                }
            }
            refreshSlot();
        });

        childrenMenu.getItems().add(allMenuItem);
    }

    private class StudentCheckBox extends CheckBox {

        private Student user;

        public StudentCheckBox(Student user) {
            super();
            this.user = user;
        }

        public Student getUser() {
            return user;
        }
    }

    public void setup(int chosenIndex, Parent currentParent) {
        destinationIndex = chosenIndex;
        this.currentParent = currentParent;

        PriorityQueue<Destination> queue = Destination.getDestinationQueue(this.currentParent);

        destinationImageHBox.getChildren().clear();
        while (!queue.isEmpty()) {
            Destination destination = queue.poll();
            destinationList.add(destination);

            // Destination Image
            ImageView destinationImage = new ImageView(new Image(getClass().getResourceAsStream("/Resources/Images/" + destination.getName() + ".png")));
            destinationImage.setFitHeight(244);
            destinationImage.setFitWidth(352);
            destinationImageHBox.getChildren().add(destinationImage);

            // Destination Name
            MenuItem destinationNameItem = new MenuItem(destination.getName());
            destinationNameItem.setStyle("-fx-text-fill : #4e4e4e; -fx-font-size: 25;");
            destinationNameItem.setOnAction(eh -> {
                destinationIndex = destinationMenu.getItems().indexOf(destinationNameItem);
                refresh();
            });
            destinationMenu.getItems().add(destinationNameItem);
        }

        for (Student child : currentParent.getChildrenObjectList()) {

            StudentCheckBox box = new StudentCheckBox(child);
            box.setSelected(true);
            box.setDisable(true);
            box.setStyle("-fx-opacity: 1; -fx-font-size: 17;");

            Label childLabel = new Label(child.getUsername());
            childLabel.setStyle("-fx-opacity: 1; -fx-text-fill : #4e4e4e; -fx-font-size: 25; -fx-padding: 0 5 0 10;");
            box.setGraphic(childLabel);

            MenuItem childMenuItem = new MenuItem();
            childMenuItem.setGraphic(box);

            childMenuItem.setOnAction(event -> {
                box.setSelected(!box.isSelected());

                boolean allChosen = true;
                for (MenuItem item : childrenMenu.getItems()) {
                    if (item.getGraphic() instanceof StudentCheckBox studentCheckBox) {
                        allChosen = allChosen && studentCheckBox.isSelected();
                    }
                }
                selectAllBox.setSelected(allChosen);

                refreshSlot();
            });

            childrenMenu.getItems().add(childMenuItem);
        }

        bookBtn.setOnAction(event -> {
            if (childrenMenu.getText().equals("Choose at least a child")) {
                System.out.println("Choose at least a child!!!");
                return;
            }
            if (slotMenu.getItems().isEmpty()) {
                System.out.println("No slot available for the destination");
                return;
            }
            if (slotMenu.getText().equals("Choose a slot")) {
                System.out.println("Choose a slot");
                return;
            }
            Destination destination = Destination.getDestination(destinationMenu.getText());
            LocalDate slot = LocalDate.parse(slotMenu.getText(), DateTimeFormatter.ofPattern("d-M-yyyy"));

            List<Student> childrenSelected = new ArrayList<>();

            for (MenuItem item : childrenMenu.getItems()) {
                if (item.getGraphic() instanceof StudentCheckBox studentCheckBox) {
                    if (studentCheckBox.isSelected()) {
                        childrenSelected.add(studentCheckBox.getUser());
                    }
                }
            }
            this.currentParent.book(new Booking(this.currentParent, destination, slot, childrenSelected));
            refresh();
        });

        refresh();
    }

    @Override
    public void refresh() {
//        destinationImageHBox.setTranslateX(-452 * destinationIndex);
        int actualDestinationIndex = destinationIndex;
        if (nextLoop) {
            actualDestinationIndex++;
        } else if (prevLoop) {
            actualDestinationIndex--;
        }
        actualDestinationIndex = (actualDestinationIndex + destinationList.size()) % destinationList.size();
        destinationMenu.setText(destinationList.get(actualDestinationIndex).getName());
        distanceLabel.setText(String.format("%.2f km Away", destinationList.get(actualDestinationIndex).distanceOf(currentParent)));

        Timeline switchAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0.01),
                        new KeyValue(destinationPrevBtn.disableProperty(), true),
                        new KeyValue(destinationNextBtn.disableProperty(), true)
                ),
                new KeyFrame(Duration.seconds(0.5),
                        new KeyValue(destinationImageHBox.translateXProperty(), -452 * destinationIndex),
                        new KeyValue(destinationPrevBtn.disableProperty(), false),
                        new KeyValue(destinationNextBtn.disableProperty(), false)
                )
        );
        switchAnimation.setOnFinished(event -> {
            if (prevLoop) {
                destinationImageHBox.getChildren().add(destinationImageHBox.getChildren().removeFirst());
                destinationIndex = destinationList.size() - 1;
                destinationImageHBox.setTranslateX(-452 * destinationIndex);
                prevLoop = false;
            }
            if (nextLoop) {
                destinationImageHBox.getChildren().addFirst(destinationImageHBox.getChildren().removeLast());
                destinationIndex = 0;
                destinationImageHBox.setTranslateX(-452 * destinationIndex);
                nextLoop = false;
            }
        });
        switchAnimation.play();

        refreshSlot();
    }

    private void refreshSlot() {
        slotMenu.getItems().clear();
        LocalDate today = LocalDate.now();

        boolean childSelected = false;
        String childrenSelected = "";
        for (MenuItem item : childrenMenu.getItems()) {
            if (item.getGraphic() instanceof StudentCheckBox studentCheckBox) {
                childSelected = childSelected || studentCheckBox.isSelected();
                if (studentCheckBox.isSelected()) {
                    childrenSelected += studentCheckBox.getUser().getUsername() + ", ";
                }
            }
        }

        if (!childSelected) {
            childrenMenu.setText("Choose at least a child");
            slotMenu.setText("No Slot Available");
            return;
        } else if (selectAllBox.isSelected()) {
            childrenMenu.setText("All");
        } else {
            childrenMenu.setText(childrenSelected.replaceAll(", $", ""));
        }

        for (int i = 1; i <= 7; i++) {

            LocalDate slot = today.plusDays(i);

            if (currentParent.isClashed(slot)) {
                continue;
            }

            boolean notClashed = true;
            for (MenuItem item : childrenMenu.getItems()) {
                if (item.getGraphic() instanceof StudentCheckBox studentCheckBox) {
                    if (studentCheckBox.isSelected()) {
                        notClashed = notClashed && !studentCheckBox.getUser().isClashed(slot);
                    }
                }
            }

            if (notClashed) {
                MenuItem slotMenuItem = new MenuItem(slot.format(DateTimeFormatter.ofPattern("d-M-yyyy")));
                slotMenuItem.setStyle("-fx-text-fill : #4e4e4e; -fx-font-size: 25;");
                slotMenuItem.setOnAction(event -> {
                    slotMenu.setText(slotMenuItem.getText());
                });
                slotMenu.getItems().add(slotMenuItem);
            }
        }

        if (slotMenu.getItems().isEmpty()) {
            slotMenu.setText("No Slot Available");
        } else {
            slotMenu.setText("Choose a slot");
        }
    }

}
