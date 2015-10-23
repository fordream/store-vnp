package org.com.vnp.chickenbang.p;

public class Point {
	public double x;
	public double y;
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	public Point() {
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}public void set(double x,double y) {
		this.y = y;
		this.x = x;
	}
	
	
}
