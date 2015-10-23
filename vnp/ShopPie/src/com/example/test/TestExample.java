package com.example.test;

import android.view.View;
import android.widget.Toast;

public class TestExample {
	static {
		System.loadLibrary("example");
	}

	public static String testArea() {
		double area = _shapeJNI.Circle_area(10l, new Circle(10.0d));
		return String.valueOf(area);
	}
	
	public static  String testExample(){
		example.setFoo(10.0);
		return String.valueOf(example.getFoo());
	}
	
	
}
