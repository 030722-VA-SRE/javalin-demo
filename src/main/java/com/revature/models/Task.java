package com.revature.models;

import java.time.LocalDate;
import java.util.Objects;

public class Task {
	
	private int id;
	private String name;
	private LocalDate dueDate;
	private boolean isCompleted;

	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Task(int id, String name, LocalDate dueDate, boolean isCompleted) {
		super();
		this.id = id;
		this.name = name;
		this.dueDate = dueDate;
		this.isCompleted = isCompleted;
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
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", dueDate=" + dueDate + ", isCompleted=" + isCompleted + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dueDate, id, isCompleted, name);
	}

	/*- 
	 * Override equals to be able to compare objects, otherwise it will compare the memory address for those objects
	 */
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
	
}
