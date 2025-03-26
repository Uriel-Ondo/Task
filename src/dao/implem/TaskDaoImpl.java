package dao.implem;

import DB.DatabaseConnection;
import dao.TaskDAO;
import entity.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoImpl implements TaskDAO {

    @Override
    public void insert(Task task) {
        String sql = "INSERT INTO task (user_id, title, description, due_date, status) VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setString(2, task.getTitle());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getDueDate());
            preparedStatement.setString(5, task.getStatus());
            preparedStatement.executeUpdate();
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur d'enregistrement");
        }
    }

    @Override
    public void update(Task task) {
        String sql = "UPDATE task SET title = ?, description = ?, due_date = ?, status = ? WHERE id = ?";
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setString(3, task.getDueDate());
            statement.setString(4, task.getStatus());
            statement.setInt(5, task.getId());
            statement.executeUpdate();
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de mise à jour");
        }
    }

    @Override
    public Task findById(int id) {
        String sql = "SELECT * FROM task WHERE id = ?";
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Task(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("due_date"),
                        resultSet.getString("status")
                );
            }
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de recherche");
        }
        return null;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM task WHERE id = ?";
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de suppression");
        }
    }

    @Override
    public List<Task> findAllByUserId(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM task WHERE user_id = ?";
        try {
            Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                tasks.add(
                        new Task(
                                resultSet.getInt("id"),
                                resultSet.getInt("user_id"),
                                resultSet.getString("title"),
                                resultSet.getString("description"),
                                resultSet.getString("due_date"),
                                resultSet.getString("status")
                        )
                );
            }
            DatabaseConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur de récupération");
        }
        return tasks;
    }
}