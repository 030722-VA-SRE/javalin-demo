package com.revature.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Task;
import com.revature.util.ConnectionUtil;

public class TaskPostgres implements TaskDao {

	@Override
	public List<Task> getAllTasks() {
		String sql = "select * from tasks;";
		List<Task> tasks = new ArrayList<>();

		try (Connection c = ConnectionUtil.getConnectionFromEnv()) {
			Statement s = c.createStatement();
			ResultSet rs = s.executeQuery(sql);

			while (rs.next()) {
				Task newTask = new Task();
				newTask.setId(rs.getInt("id"));
				newTask.setName(rs.getString("name"));
				newTask.setCompleted(rs.getBoolean("is_completed"));
				newTask.setDueDate(rs.getDate("due_date").toLocalDate());

				tasks.add(newTask);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tasks;
	}

	@Override
	public List<Task> getTasksByCompletion(boolean isCompleted) {
		String sql = "select * from tasks where is_completed = ?;";
		List<Task> tasks = new ArrayList<>();

		try (Connection c = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = c.prepareStatement(sql);

			ps.setBoolean(1, isCompleted);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Task newTask = new Task();
				newTask.setId(rs.getInt("id"));
				newTask.setName(rs.getString("name"));
				newTask.setCompleted(rs.getBoolean("is_completed"));
				newTask.setDueDate(rs.getDate("due_date").toLocalDate());
				
				tasks.add(newTask);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tasks;
	}

	@Override
	public Task getTaskById(int id) {
		String sql = "select * from tasks where id = ?;";
		Task newTask = null;

		try (Connection c = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = c.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				newTask = new Task();
				newTask.setId(rs.getInt("id"));
				newTask.setName(rs.getString("name"));
				newTask.setCompleted(rs.getBoolean("is_completed"));
				newTask.setDueDate(rs.getDate("due_date").toLocalDate());

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newTask;
	}

	@Override
	public int createTask(Task task) {
		String sql = "insert into tasks(name, due_date) values (?,?)returning id;";
		int generatedId = -1;

		try (Connection c = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = c.prepareStatement(sql);

			ps.setString(1, task.getName());
			ps.setDate(2, Date.valueOf(task.getDueDate()));

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				generatedId = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generatedId;
	}

	// TODO: implement updateTask
	@Override
	public boolean updateTask(Task task) {
		return false;
	}

	// TODO: implement deleteTaskById
	@Override
	public boolean deleteTaskById(int id) {
		return false;
	}

	@Override
	public List<Task> getTasksByDueDate(LocalDate dueDate) {
		String sql = "select * from tasks where due_date = ?;";
		List<Task> tasks = new ArrayList<>();

		try (Connection c = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = c.prepareStatement(sql);

			ps.setDate(1, Date.valueOf(dueDate));

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Task newTask = new Task();
				newTask.setId(rs.getInt("id"));
				newTask.setName(rs.getString("name"));
				newTask.setCompleted(rs.getBoolean("is_completed"));
				newTask.setDueDate(rs.getDate("due_date").toLocalDate());
				
				tasks.add(newTask);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tasks;
	}

	@Override
	public List<Task> getTasksByCompletionAndDueDate(boolean isCompleted, LocalDate dueDate) {
		String sql = "select * from tasks where is_completed = ? and due_date =?;";
		List<Task> tasks = new ArrayList<>();

		try (Connection c = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = c.prepareStatement(sql);

			ps.setBoolean(1, isCompleted);
			ps.setDate(2, Date.valueOf(dueDate));

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Task newTask = new Task();
				newTask.setId(rs.getInt("id"));
				newTask.setName(rs.getString("name"));
				newTask.setCompleted(rs.getBoolean("is_completed"));
				newTask.setDueDate(rs.getDate("due_date").toLocalDate());
				
				tasks.add(newTask);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tasks;
	}

}
