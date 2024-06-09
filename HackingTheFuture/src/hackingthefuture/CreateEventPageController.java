/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package hackingthefuture;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class CreateEventPageController implements Initializable {

    private Stage stage;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField titleTF;

    @FXML
    private TextField venueTF;

    @FXML
    private Spinner<Integer> hourSpinner, minuteSpinner;

    @FXML
    private Spinner<String> AmPmSpinner;

    @FXML
    private Button createEventBtn, closeBtn;

    private Educator currentEducator;

    /**
     * Initializes the controller class.
     */
    private class MinutesStringConverter extends StringConverter<Integer> {

        @Override
        public String toString(Integer object) {
            return String.format("%02d", object);
        }

        @Override
        public Integer fromString(String string) {
            return Integer.parseInt(string);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Platform.runLater(()->{
            stage = (Stage) closeBtn.getScene().getWindow();
        });

        closeBtn.setOnAction(eh -> {
            close();
        });

//        datePicker.setDayCellFactory((DatePicker param) -> new DateCell() {
//            @Override
//            public void updateItem(LocalDate item, boolean empty) {
//                super.updateItem(item, empty);
//                LocalDate minDate = LocalDate.now().plusDays(3);
//                LocalDate maxDate = minDate.plusYears(2);
//                if (item.isBefore(minDate) || item.isAfter(maxDate)) {
//                    setDisable(true);
//                }
//            }
//        });
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 12) {

            @Override
            public void decrement(int steps) {
                int newValue = getValue() - steps;
                if (newValue < getMin()) {
                    setValue(getMax());
                } else {
                    setValue(newValue);
                }
            }

            @Override
            public void increment(int steps) {
                int newValue = getValue() + steps;
                if (newValue > getMax()) {
                    setValue(getMin());
                } else {
                    setValue(newValue);
                }
            }

        });

        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0) {

            @Override
            public void decrement(int steps) {
                int newValue = getValue() - steps;
                if (newValue < getMin()) {
                    setValue(getMax());
                } else {
                    setValue(newValue);
                }
            }

            @Override
            public void increment(int steps) {
                int newValue = getValue() + steps;
                if (newValue > getMax()) {
                    setValue(getMin());
                } else {
                    setValue(newValue);
                }
            }

        });
        minuteSpinner.getValueFactory().setConverter(new MinutesStringConverter());

        AmPmSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<>(FXCollections.observableArrayList("AM", "PM")) {
            @Override
            public void decrement(int steps) {
                int newIndex = (getItems().indexOf(getValue()) - steps) % getItems().size();
                if (newIndex < 0) {
                    newIndex += getItems().size();
                }
                setValue(getItems().get(newIndex));
            }

            @Override
            public void increment(int steps) {
                int newIndex = (getItems().indexOf(getValue()) + steps) % getItems().size();
                setValue(getItems().get(newIndex));
            }
        });

        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (Pattern.matches("\\d*", change.getControlNewText())) {
                return change;
            }
            return null;
        };
        hourSpinner.getEditor().setTextFormatter(new TextFormatter<>(hourSpinner.getValueFactory().getConverter(), hourSpinner.getValueFactory().getValue(), filter));
        minuteSpinner.getEditor().setTextFormatter(new TextFormatter<>(minuteSpinner.getValueFactory().getConverter(), minuteSpinner.getValueFactory().getValue(), filter));

    }

    public void setupPage(Educator educator) {
        currentEducator = educator;
        createEventBtn.setOnAction(event -> createEvent(currentEducator));
    }

    public void refresh() {
        titleTF.clear();
        descriptionTextArea.clear();
        venueTF.clear();
        datePicker.setValue(null);
        hourSpinner.getValueFactory().setValue(12);
        minuteSpinner.getValueFactory().setValue(0);
        AmPmSpinner.getValueFactory().setValue("AM");
    }

    private void createEvent(Educator creator) {
        // !!!Check validation first!!!
        String title = titleTF.getText();
        String description = descriptionTextArea.getText();
        String venue = venueTF.getText();
        LocalDate date = datePicker.getValue();
        if (title.isBlank() || description.isBlank() || venue.isBlank() || date == null) {
            AlertController.showErrorAlert("All field must be filled in!", stage);
            return;
        }
        LocalTime time = LocalTime.of((hourSpinner.getValue() % 12) + (AmPmSpinner.getValue().equals("PM") ? 12 : 0), minuteSpinner.getValue());
        Event event = new Event(title, description, venue, date, time, creator.getID());
        EventHandler.createEvent(event, creator);
        refresh();
        close();
        AppMainController.refreshPage();
        AppMainController.showSuccessAlert("Event Created");
    }

    private void close() {
        stage.close();
    }

}
