package org.com.vnp.defenserun.data;

import java.util.Random;

public class Charactor {
	private Random random = new Random();
	private int level = 1;
	private int powerMin = 1;
	private int powerMax = 3;
	private int moneyX = 1;
	private int manalot = 1; // 3/100 nhan doi dam
	private int bogiap = 1; // 3% bo giap
	private int powerX = 1;
	private int numberGun = 1;
	private int levelSkill = 1;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPowerMin() {
		return powerMin;
	}

	public void setPowerMin(int powerMin) {
		this.powerMin = powerMin;
	}

	public int getPowerMax() {
		return powerMax;
	}

	public void setPowerMax(int powerMax) {
		this.powerMax = powerMax;
	}

	public int getMoneyX() {
		return moneyX;
	}

	public void setMoneyX(int moneyX) {
		this.moneyX = moneyX;
	}

	public int getManalot() {
		return manalot;
	}

	public void setManalot(int manalot) {
		this.manalot = manalot;
	}

	public int getPowerX() {
		return powerX;
	}

	public void setPowerX(int powerX) {
		this.powerX = powerX;
	}

	public int getNumberGun() {
		return numberGun;
	}

	public void setNumberGun(int numberGun) {
		this.numberGun = numberGun;
	}

	public int getLevelSkill() {
		return levelSkill;
	}

	public void setLevelSkill(int levelSkill) {
		this.levelSkill = levelSkill;
	}

	public int getBogiap() {
		return bogiap;
	}

	public void setBogiap(int bogiap) {
		this.bogiap = bogiap;
	}

	public boolean isBogiap() {
		return new Random().nextInt(100) <= bogiap;
	}

	public int realPower() {
		int pow = powerMin + random.nextInt(powerMax - powerMin);
		pow += levelSkill * 2 + level * 2;
		pow *= powerX;
		
		if (random.nextInt(100) <= manalot) {
			pow *= 2;
		}
		
		return pow;
	}
	
	public int realMoney(int money){
		return money * moneyX;
	}
}
