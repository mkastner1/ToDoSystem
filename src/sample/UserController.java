package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Status;
import sample.model.User;
import sample.model.db.AbstractDatabase;
import sample.model.db.MySQLConnector;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserController {
    public TextField nameTextField;
    public TextField streetTextField;
    public TextField zipTextField;
    public TextField locationTextField;
    public ListView userListView;
    public User selectedItem = null;
    PreparedStatement statement = null;
    AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at",3306,"d0345761");

    public void initialize(){
        userListView.setItems(User.getList());
    }
    
    public void itemSelected(MouseEvent mouseEvent) {
        User u = (User) userListView.getSelectionModel().getSelectedItem();
        if(u != null){
            nameTextField.setText(u.getName());
            streetTextField.setText(u.getStreet());
            zipTextField.setText(String.valueOf(u.getZip()));
            locationTextField.setText(u.getCity());
            selectedItem = u;

        }
    }


    public void saveClicked(ActionEvent actionEvent) {
        if(selectedItem == null){
            try {
                statement = conn.getConnection().prepareStatement("INSERT INTO gr2_editor (editor_id,zip,street,name,city) VALUES (null, '"+zipTextField.getText()+"','"+streetTextField.getText()+"','"+nameTextField.getText()+"','"+locationTextField.getText()+"')");
                statement.execute();
                userListView.setItems(User.getList());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                statement = conn.getConnection().prepareStatement("UPDATE `gr2_editor` SET `name` = '"+nameTextField.getText()+"', `street` = '"+streetTextField.getText()+"', `zip` = '"+zipTextField.getText()+"', `city` = '"+locationTextField.getText()+"' WHERE `gr2_editor`.`editor_id` = '"+selectedItem.getId()+"'");
                statement.execute();
                userListView.setItems(User.getList());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        clearAll();
    }

    public void cancleClicked(ActionEvent actionEvent) {
        Stage stage =Controller.userStage;
        stage.close();
    }

    public void newClicked(ActionEvent actionEvent) {
        selectedItem = null;
        clearAll();
        userListView.getSelectionModel().clearSelection();
    }

    public void deleteClicked(ActionEvent actionEvent) {
        try {
            statement = conn.getConnection().prepareStatement("DELETE from gr2_editor WHERE editor_id ='" + selectedItem.getId() +"'");
            statement.executeUpdate();
            userListView.setItems(User.getList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        clearAll();
    }
    private void clearAll(){
        nameTextField.clear();
        streetTextField.clear();
        locationTextField.clear();
        zipTextField.clear();

    }

    /** Tastenkombinationen:
     * <ul>
     *     <li><b>STRG + S: </b>Speichern. </li>
     *     <li><b>STRG + C: </b>Abbrechen. </li>
     *     <li><b>STRG + N: </b>Neue Priorität. </li>
     *     <li><b>ENTF: </b>Priorität löschen. </li>
     * </ul>
     *
     * @param keyEvent KeyEvent mit keyCode.
     */
    public void KeyListener(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();

        if (keyEvent.isControlDown()) {
            if (keyCode == KeyCode.S) {
                saveClicked(new ActionEvent());
            } else if (keyCode == KeyCode.N) {
                newClicked(new ActionEvent());
            } else if (keyCode == KeyCode.C){
                cancleClicked(new ActionEvent());
            }
        } else {
            if (keyCode == KeyCode.DELETE) {
                deleteClicked(new ActionEvent());
            }
        }
    }
}
