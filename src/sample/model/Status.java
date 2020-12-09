package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.db.AbstractDatabase;
import sample.model.db.MySQLConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Status {
    private int id;
    private String name;

    public Status() {

    }

    public Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ObservableList<Status> getList()  {
        ObservableList<Status>list = FXCollections.observableArrayList();

        AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at",3306,"d0345761");

        PreparedStatement statement = null;
        try {
            statement = conn.getConnection().prepareStatement("SELECT * from gr2_status");
            ResultSet results = statement.executeQuery();

        while (results.next()){
            Status tmp = new Status(results.getInt("status_id"),results.getString("name"));
            list.add(tmp);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void addItem(Status s) {
        AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at", 3306, "d0345761");

        try {
            PreparedStatement statement = conn.getConnection().prepareStatement("INSERT INTO gr2_status (name) VALUES ('" + s.getName()+"')");
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void updateItem(Status s) {
        AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at", 3306, "d0345761");

        try {
            PreparedStatement statement = conn.getConnection().prepareStatement("UPDATE `gr2_status` SET `name` = '"+s.getName()+"' WHERE `gr2_status`.`status_id` = '"+s.getId()+"'");
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void deleteItem(int id) {
        AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at", 3306, "d0345761");

        try {
            PreparedStatement statement = conn.getConnection().prepareStatement("DELETE from gr2_status WHERE status_id ='" + id +"'");

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
