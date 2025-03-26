package dao;

import entity.Task;

import java.util.List;

public interface TaskDAO {
    void insert(Task task);
    void update(Task task);
    Task findById(int id);
    void deleteById(int id);
    List<Task> findAllByUserId(int userId);
}