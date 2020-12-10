package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.model.Status;
import sample.model.db.AbstractDatabase;
import sample.model.db.MySQLConnector;

import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusController {


    public TextField nameTextfield;
    public ListView<Status> statusListView;
    public Status selectedItem = null;
    PreparedStatement statement = null;
    AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at",3306,"d0345761");




    public void initialize(){
        statusListView.setItems(Status.getList());
    }

    public void itemSelected(MouseEvent mouseEvent) {
    Status s = statusListView.getSelectionModel().getSelectedItem();
    if(s != null){
        nameTextfield.setText(s.getName());
        selectedItem = s;
    }
    }

    public void saveClicked(ActionEvent actionEvent) {
        Status s;

        if(selectedItem == null){
            s = new Status(0, nameTextfield.getText());
            Status.addItem(s);
            statusListView.setItems(Status.getList());
        }else {
            s = new Status(selectedItem.getId(),nameTextfield.getText());
            Status.updateItem(s);
            statusListView.setItems(Status.getList());
        }
    }

    public void newClicked(ActionEvent actionEvent) {
        selectedItem = null;
        nameTextfield.clear();
        statusListView.getSelectionModel().clearSelection();
    }

    public void deleteClicked(ActionEvent actionEvent) {
        Status.deleteItem(selectedItem.getId());
        statusListView.setItems(Status.getList());
    }

    public void cancelClicked(ActionEvent actionEvent) {
        Stage stage =Controller.statusStage;
        stage.close();
    }
}
