package com.vnpgame.undersea;

public class Score {
	private int id = -1;
	private String name = null;
	private String score = null;
	private String level = null;

	public Score(int id, String name, String score, String level) {
		super();
		this.id = id;
		this.name = name;
		this.score = score;
		this.level = level;
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Score() {
		super();
	}
}