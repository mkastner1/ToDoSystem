package sample;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.model.Status;
import sample.model.ToDo;

public class ToDoController {
    public TextField nameTextField;
    public TextArea descriptionTextArea;
    public ComboBox statusComboBox;
    public ComboBox priorityComboBox;
    public AnchorPane toDoPane;
    private ToDo selected = null;

    public void setToDo(ToDo item) {
        selected = item;
        displayItem();
    }

    private void displayItem() {
        // Hier sollten die Daten von "Selected" dargestellt werden.
        statusComboBox.setItems(new Status().getList());

        nameTextField.setText(selected.getName());

        int i = 0;
        for (i = 0; i < statusComboBox.getItems().size(); i++) {
            if (((Status) statusComboBox.getItems().get(i)).getId() == ((ToDo) selected).getStatusId()) {
                break;
            }

            statusComboBox.getSelectionModel().select(i);
        }
    }
}
