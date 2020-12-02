package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Priority;

public class PriorityController {
    public TextField nameTextField;
    public ListView<Priority> priorityListView;
    public Button cancelButton;
    public Priority selectedItem = null;

    public void initialize() {
        priorityListView.setItems(Priority.getList());
    }

    public void itemSelected(MouseEvent mouseEvent) {
        Priority p = priorityListView.getSelectionModel().getSelectedItem();

        if (p != null) {
            nameTextField.setText(p.getName());
            selectedItem = p;
        }
    }

    public void saveClicked(ActionEvent actionEvent) {
        Priority p = null;

        if (selectedItem != null) {
            p = new Priority(selectedItem.getId(), nameTextField.getText());

            Priority.updateItem(p);
            priorityListView.getItems().set(priorityListView.getItems().indexOf(selectedItem), p);
        } else {
            p = new Priority(0, nameTextField.getText());

            Priority.addItem(p);
            priorityListView.getItems().add(p);
        }

        selectedItem = p;
    }

    public void cancelClicked(ActionEvent actionEvent) {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public void deleteClicked(ActionEvent actionEvent) {
        if (selectedItem != null) {
            Priority.deleteItem(selectedItem.getId());
            priorityListView.getItems().remove(selectedItem);
        }
    }

    public void newClicked(ActionEvent actionEvent) {
        selectedItem = null;
        nameTextField.clear();
        priorityListView.getSelectionModel().clearSelection();
    }
}
