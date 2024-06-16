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

    // Custom converter to display hour and minute in 2 digits with leading of "0" if necessary
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

        // Customise the date picker to limit date can be chosen
        // Starting from 3 days after from today to 2 years from today
        datePicker.setDayCellFactory((DatePicker param) -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                LocalDate minDate = LocalDate.now().plusDays(3);
                LocalDate maxDate = minDate.plusYears(2);
                if (item.isBefore(minDate) || item.isAfter(maxDate)) {
                    setDisable(true);
                }
            }
        });
        
        // Set value for spinner of hour
        hourSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, 12) {

            // Customise the value of spinner as a loop
            // Changed to maximum value when value smaller the minimum value (change from 1 to 12)
            @Override
            public void decrement(int steps) {
                int newValue = getValue() - steps;
                if (newValue < getMin()) {
                    setValue(getMax());
                } else {
                    setValue(newValue);
                }
            }

            // Changed to minimum value when value exceed the maximum value (change from 12 to 1)
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

        // Set value for spinner of minute
        minuteSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0) {

            // Customise the value of spinner as a loop
            // Changed to maximum value when value smaller the minimum value (change from 0 to 59)
            @Override
            public void decrement(int steps) {
                int newValue = getValue() - steps;
                if (newValue < getMin()) {
                    setValue(getMax());
                } else {
                    setValue(newValue);
                }
            }

            // Changed to minimum value when value exceed the maximum value (change from 59 to 0)
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
        // Set converter for minute spinner to format the minute displayed
        minuteSpinner.getValueFactory().setConverter(new MinutesStringConverter());

        // Set the spinner for am/pm as a loop of "AM" and "PM"
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

        // Setup spinner for hour and minute for display value purpose
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (Pattern.matches("\\d*", change.getControlNewText())) {
                return change;
            }
            return null;
        };
        hourSpinner.getEditor().setTextFormatter(new TextFormatter<>(hourSpinner.getValueFactory().getConverter(), hourSpinner.getValueFactory().getValue(), filter));
        minuteSpinner.getEditor().setTextFormatter(new TextFormatter<>(minuteSpinner.getValueFactory().getConverter(), minuteSpinner.getValueFactory().getValue(), filter));

    }

    // Initialise the page and get the current login Educator
    public void setupPage(Educator educator) {
        currentEducator = educator;
        createEventBtn.setOnAction(event -> createEvent(currentEducator));
    }

    // Reset the page to its initial state
    public void refresh() {
        titleTF.clear();
        descriptionTextArea.clear();
        venueTF.clear();
        datePicker.setValue(null);
        hourSpinner.getValueFactory().setValue(12);
        minuteSpinner.getValueFactory().setValue(0);
        AmPmSpinner.getValueFactory().setValue("AM");
    }

    // Create event
    private void createEvent(Educator creator) {
        // !!!Check validation first!!!
        String title = titleTF.getText();
        String description = descriptionTextArea.getText();
        String venue = venueTF.getText();
        LocalDate date = datePicker.getValue();
        // Check if every fields are filled in
        if (title.isBlank() || description.isBlank() || venue.isBlank() || date == null) {
            AlertController.showErrorAlert("All field must be filled in!", stage);
            return;
        }
        LocalTime time = LocalTime.of((hourSpinner.getValue() % 12) + (AmPmSpinner.getValue().equals("PM") ? 12 : 0), minuteSpinner.getValue());
        
        // Save the event
        Event event = new Event(title, description, venue, date, time, creator.getID());
        EventHandler.createEvent(event, creator);
        refresh();
        
        // Close the pop up window, refresh the current page and show alert of "Success"
        close();
        AppMainController.refreshPage();
        AppMainController.showSuccessAlert("Event Created");
    }

    // Close the pop up window
    private void close() {
        stage.close();
    }

}
