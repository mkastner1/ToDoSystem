package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.model.Status;

public class StatusController {


    public TextField nameTextfield;
    public ListView<Status> statusListView;
    public Status selectedItem = null;

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
        if(selectedItem != null){

        }else {

        }
    }

    public void newClicked(ActionEvent actionEvent) {
        selectedItem = null;
        nameTextfield.clear();
        statusListView.getSelectionModel().clearSelection();
    }

    public void deleteClicked(ActionEvent actionEvent) {
    }

    public void cancelClicked(ActionEvent actionEvent) {
    }
}
