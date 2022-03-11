package com.revature.models;

import java.time.LocalDate;
import java.util.Objects;

public class Task {

	private int id;
	private String name;
	private boolean isCompleted;
	public static int counter;

	public Task() {
		super();
		// TODO Auto-generated constructor stub
		id = counter;
		counter++;
	}

	public Task(String name, boolean isCompleted) {
		this();
		this.name = name;
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

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", name=" + name + ", isCompleted=" + isCompleted + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isCompleted, name);
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
		return id == other.id && isCompleted == other.isCompleted && Objects.equals(name, other.name);
	}

}
