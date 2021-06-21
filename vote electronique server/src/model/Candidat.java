package model;

import java.io.Serializable;

public class Candidat implements Serializable {
	private long id;
	private String fullName;
	private int count;

	public Candidat() {
		this.count = 0;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
