package com.revature.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		Task newTask = new Task();

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
		String sql = "insert into tasks(description, due_date) values (?,?)returning id;";
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

	@Override
	public boolean updateTask(Task task) {
		String sql = "update tasks set description = ?, is_completed = ?, due_date = ? where id = ?;";
		int rowsChanged = -1;

		try (Connection c = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = c.prepareStatement(sql);

			ps.setString(1, task.getName());
			ps.setBoolean(2, task.isCompleted());
			ps.setDate(3, Date.valueOf(task.getDueDate()));
			ps.setInt(4, task.getId());

			rowsChanged = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rowsChanged < 1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean deleteTaskById(int id) {
		String sql = "delete from tasks where id = ?;";
		int rowsChanged = -1;

		try (Connection c = ConnectionUtil.getConnectionFromEnv()) {
			PreparedStatement ps = c.prepareStatement(sql);

			ps.setInt(1, id);

			rowsChanged = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (rowsChanged < 1) {
			return false;
		}

		return true;
	}

}
