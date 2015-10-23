/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.4
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.example.test;

public class Shape {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  public Shape(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  public static long getCPtr(Shape obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        _shapeJNI.delete_Shape(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setX(double value) {
    _shapeJNI.Shape_x_set(swigCPtr, this, value);
  }

  public double getX() {
    return _shapeJNI.Shape_x_get(swigCPtr, this);
  }

  public void setY(double value) {
    _shapeJNI.Shape_y_set(swigCPtr, this, value);
  }

  public double getY() {
    return _shapeJNI.Shape_y_get(swigCPtr, this);
  }

  public void move(double dx, double dy) {
    _shapeJNI.Shape_move(swigCPtr, this, dx, dy);
  }

  public double area() {
    return _shapeJNI.Shape_area(swigCPtr, this);
  }

  public double perimeter() {
    return _shapeJNI.Shape_perimeter(swigCPtr, this);
  }

  public static void setNshapes(int value) {
    _shapeJNI.Shape_nshapes_set(value);
  }

  public static int getNshapes() {
    return _shapeJNI.Shape_nshapes_get();
  }

}
