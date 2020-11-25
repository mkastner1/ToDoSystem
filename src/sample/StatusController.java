package sample;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.model.Status;

public class StatusController {


    public TextField nameTextfield;
    public ListView<Status> statusListView;

    public void initialize(){
        statusListView.setItems(Status.getList());
    }

    public void itemSelected(MouseEvent mouseEvent) {
    Status s = statusListView.getSelectionModel().getSelectedItem();
    if(s != null){
        nameTextfield.setText(s.getName());
    }
    }
}
