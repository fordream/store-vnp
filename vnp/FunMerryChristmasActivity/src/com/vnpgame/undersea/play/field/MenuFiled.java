package com.vnpgame.undersea.play.field;

public class MenuFiled {
	private int level = 1;
	private int fail = 30;
	private int score = 0;
	public MenuFiled() {
	}
	public MenuFiled(int level, int fail, int score) {
		super();
		this.level = level;
		this.fail = fail;
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getFail() {
		return fail;
	}

	public void setFail(int fail) {
		this.fail = fail;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
