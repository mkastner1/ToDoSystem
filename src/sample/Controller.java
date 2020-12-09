package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.model.Priority;
import sample.model.Status;
import sample.model.ToDo;

import java.io.IOException;

public class Controller {
    public ListView toDoListView;
    public ComboBox statusComboBox;
    public ComboBox priorityComboBox;
    public TextField TicketnameTextField;
    public Pane contentPane;
    public static Stage statusStage;
    public static Stage userStage;

    public void initialize() {
        toDoListView.setItems(ToDo.getList());
        statusComboBox.setItems(Status.getList());
        priorityComboBox.setItems(Priority.getList());
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
        ToDo selectedElement = (ToDo) toDoListView.getSelectionModel().getSelectedItem();

        if (selectedElement != null) {
            // Stelle die Daten des gewählten ToDos auf der rechten Seite dar.


            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("todo.fxml"));
                Pane root = loader.load();

                ToDoController controller = (ToDoController) loader.getController();

                controller.setToDo(selectedElement);

                contentPane.getChildren().add(controller.toDoPane);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}