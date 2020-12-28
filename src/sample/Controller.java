package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.model.Priority;
import sample.model.Status;
import sample.model.ToDo;

import java.io.IOException;

public class Controller {
    public ListView<ToDo> toDoListView;
    public ComboBox<Status> statusComboBox;
    public ComboBox<Priority> priorityComboBox;
    public Pane contentPane;
    public TextField ticketnameTextField;
    public MenuItem deleteMenuItem;
    public static Stage statusStage;
    public static Stage userStage;

    public void initialize() {
        Status noStatusSelected = new Status(0, "Bitte auswählen ");
        Priority noPrioritySelected = new Priority(0, "Bitte auswählen ");
        ObservableList<Status> status = Status.getList();
        ObservableList<Priority> priorities = Priority.getList();

        status.add(0, noStatusSelected);
        priorities.add(0, noPrioritySelected);

        toDoListView.setItems(ToDo.getList());
        statusComboBox.setItems(status);
        priorityComboBox.setItems(priorities);

        statusComboBox.getSelectionModel().select(noStatusSelected);
        priorityComboBox.getSelectionModel().select(noPrioritySelected);
    }

    public void onStatusClicked(ActionEvent actionEvent) {
        Parent root = null;

        try {
            Stage stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("StatusGUI.fxml"));
            stage.setTitle("Stati");
            stage.setScene(new Scene(root));
            stage.show();
            statusStage = stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onPriorityClicked(ActionEvent actionEvent) {
        Parent root = null;

        try {
            Stage stage = new Stage();

            root = FXMLLoader.load(getClass().getResource("priority.fxml"));
            stage.setTitle("Prioritäten");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onUserClicked(ActionEvent actionEvent) {
        Parent root = null;

        try {
            Stage stage = new Stage();

            root = FXMLLoader.load(getClass().getResource("BearbeiterGUI.fxml"));
            stage.setTitle("Benutzer");
            stage.setScene(new Scene(root));
            stage.show();
            userStage = stage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onToDoClicked(MouseEvent mouseEvent) {
        ToDo selectedElement = toDoListView.getSelectionModel().getSelectedItem();

        if (selectedElement != null) {
            // Stelle die Daten des gewählten ToDos auf der rechten Seite dar.

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("todo.fxml"));
                Pane root = loader.load();

                ToDoController controller = loader.getController();

                controller.setToDo(selectedElement);
                controller.setToDoList(toDoListView.getItems());

                contentPane.getChildren().add(controller.toDoPane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void onCloseClicked(ActionEvent actionEvent) {
        Stage stage = Main.mainStage;
        stage.close();
    }

    public void onNewClicked(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("todo.fxml"));
            Pane root = loader.load();
            ToDoController controller = loader.getController();

            controller.setToDo(null);
            controller.setToDoList(toDoListView.getItems());

            contentPane.getChildren().add(controller.toDoPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onDelete(ActionEvent actionEvent) {
        if (toDoListView.getSelectionModel().getSelectedItem() != null) {
            ToDo.delete(toDoListView.getSelectionModel().getSelectedItem().getTodoId());
        }
    }

    public void onUpdate(ActionEvent actionEvent) {
        toDoListView.setItems(ToDo.getList(statusComboBox.getSelectionModel().getSelectedItem().getId(),
                priorityComboBox.getSelectionModel().getSelectedItem().getId(), ticketnameTextField.getText()));
    }

    /** Tastenkombinationen:
     * <ul>
     *     <li><b>STRG + N: </b>Neues ToDo erstellen. </li>
     *     <li><b>STRG + U: </b>Aktualisieren. </li>
     *     <li><b>ENTF: </b>ToDo löschen. </li>
     * </ul>
     *
     * @param keyEvent KeyEvent mit keyCode.
     */
    public void KeyListener(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();

        if (keyEvent.isControlDown()) {
            if (keyCode == KeyCode.N) {
                onNewClicked(new ActionEvent());
            } else if (keyCode == KeyCode.U) {
                onUpdate(new ActionEvent());
            }
        } else {
            if (keyCode == KeyCode.DELETE) {
                onDelete(new ActionEvent());
            }
        }
    }
}