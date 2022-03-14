package com.revature.models;

import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Task {

	private int id;
	private String name;
	/*-
	 * LocalDate object can be serialized to a JSON string by using the following annotation and the
	 * jackson-datatype-jsr310 dependency.
	 */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
	private LocalDate dueDate;
	private boolean isCompleted;
	public static int taskCounter = 0;
	
	public Task() {
		super();
		taskCounter++;
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

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dueDate, id, isCompleted, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(dueDate, other.dueDate) && id == other.id && isCompleted == other.isCompleted
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", dueDate=" + dueDate + ", isCompleted=" + isCompleted + "]";
	}

}
