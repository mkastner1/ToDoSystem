package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.db.AbstractDatabase;
import sample.model.db.MySQLConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ToDo {
    private int todoId = 0;
    private String name = "";
    private String description = "";
    private int statusId = 0;
    private int priorityId = 0;

    public ToDo(int todoId, String name, String description, int statusId, int priorityId) {
        this.todoId = todoId;
        this.name = name;
        this.description = description;
        this.statusId = statusId;
        this.priorityId = priorityId;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ObservableList<ToDo> getList() {
        ObservableList<ToDo> list = FXCollections.observableArrayList();
        AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at", 3306, "d0345761");

        try {
            PreparedStatement statement = conn.getConnection().prepareStatement("SELECT * FROM gr2_todo");
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                ToDo tmp = new ToDo(results.getInt("todo_id"), results.getString("name"), results.getString("description"),
                        results.getInt("status_id"), results.getInt("priority_id"));

                list.add(tmp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    public static ObservableList<ToDo> getList(int statusId, int priorityId, String name) {
        ObservableList<ToDo> list = FXCollections.observableArrayList();
        AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at", 3306, "d0345761");
        String str = "SELECT * FROM gr2_todo ";

        if (!name.equals("")) {
            str += "WHERE name='" + name + "' ";
            if (statusId == 0) {
                if (priorityId != 0) {
                    str += "AND priority_id=" + priorityId;
                }
            } else {
                str += "AND status_id=" + statusId;
                if (priorityId != 0) {
                    str += " AND priority_id=" + priorityId;
                }
            }
        } else {
            if (statusId == 0) {
                if (priorityId != 0) {
                    str += "WHERE priority_id=" + priorityId;
                }
            } else {
                str += "WHERE status_id=" + statusId;
                if (priorityId != 0) {
                    str += " AND priority_id=" + priorityId;
                }
            }
        }

        try {
            PreparedStatement statement = conn.getConnection().prepareStatement(str);
            ResultSet results = statement.executeQuery();

            while(results.next()) {
                ToDo tmp = new ToDo(results.getInt("todo_id"), results.getString("name"), results.getString("description"),
                        results.getInt("status_id"), results.getInt("priority_id"));

                list.add(tmp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return list;
    }

    public static void update(ToDo toDo) {
        AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at", 3306, "d0345761");

        try {
            PreparedStatement statement = conn.getConnection().prepareStatement("UPDATE gr2_todo SET name='" +
                    toDo.getName() + "', description='" + toDo.getDescription() + "', status_id=" +
                    toDo.getStatusId() + ", priority_id=" + toDo.getPriorityId() + " WHERE todo_id=" +
                    toDo.getTodoId());

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void add(ToDo toDo) {
        AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at", 3306, "d0345761");

        try {
            PreparedStatement statement = conn.getConnection().prepareStatement("INSERT INTO gr2_todo " +
                    "(name, description, status_id, priority_id) VALUES ('" + toDo.name + "', '" + toDo.description +
                    "', " + toDo.statusId + ", " + toDo.priorityId + ")");

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void delete(int id) {
        AbstractDatabase conn = new MySQLConnector("d0345761", "5AHEL2021", "rathgeb.at", 3306, "d0345761");

        try {
            PreparedStatement statement = conn.getConnection().prepareStatement("DELETE FROM gr2_todo WHERE " +
                    "todo_id=" + id);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
