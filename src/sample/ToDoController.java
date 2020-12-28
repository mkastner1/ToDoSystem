package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import sample.model.Priority;
import sample.model.Status;
import sample.model.ToDo;

public class ToDoController {
    public TextField nameTextField;
    public TextArea descriptionTextArea;
    public ComboBox statusComboBox;
    public ComboBox priorityComboBox;
    public AnchorPane toDoPane;
    private ToDo selected = null;
    private ObservableList<ToDo> todoList;

    public void initialize() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                nameTextField.requestFocus();
            }
        });
    }

    public void setToDo(ToDo item) {
        selected = item;
        displayItem();
    }

    private void displayItem() {
        // Hier sollten die Daten von "Selected" dargestellt werden.
        int i = 0;

        statusComboBox.setItems(new Status().getList());
        priorityComboBox.setItems(new Priority().getList());

        if (selected != null) {
            nameTextField.setText(selected.getName());
            descriptionTextArea.setText(selected.getDescription());

            for (i = 0; i < statusComboBox.getItems().size(); i++) {
                if (((Status) statusComboBox.getItems().get(i)).getId() == selected.getStatusId()) {
                    break;
                }

                statusComboBox.getSelectionModel().select(i);
            }
            for (i = 0; i < priorityComboBox.getItems().size(); i++) {
                if (((Priority) priorityComboBox.getItems().get(i)).getId() == selected.getPriorityId()) {
                    break;
                }

                priorityComboBox.getSelectionModel().select(i);
            }
        }
    }

    public void setToDoList(ObservableList<ToDo> list) {
        this.todoList = list;
    }

    public void onSaveClicked(ActionEvent actionEvent) {
        if (selected != null) {
            selected.setName(nameTextField.getText());
            selected.setDescription(descriptionTextArea.getText());
            selected.setPriorityId(((Priority) priorityComboBox.getSelectionModel().getSelectedItem()).getId());
            selected.setStatusId(((Status) statusComboBox.getSelectionModel().getSelectedItem()).getId());
            ToDo.update(selected);
        } else {
            ToDo.add(new ToDo(0, nameTextField.getText(), descriptionTextArea.getText(),
                    ((Status) statusComboBox.getSelectionModel().getSelectedItem()).getId(),
                    ((Priority) priorityComboBox.getSelectionModel().getSelectedItem()).getId()));
        }
    }

    public void onCancelClicked(ActionEvent actionEvent) {
        
    }

    /** Tastenkombinationen:
     * <ul>
     *     <li><b>STRG + S: </b>Speichern. </li>
     *     <li><b>STRG + C: </b>Abbrechen. </li>
     * </ul>
     *
     * @param keyEvent KeyEvent mit keyCode.
     */
    public void KeyListener(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();

        if (keyEvent.isControlDown()) {
            if (keyCode == KeyCode.S) {
                onSaveClicked(new ActionEvent());
            } else if (keyCode == KeyCode.C) {
                onCancelClicked(new ActionEvent());
            }
        }
    }
}
