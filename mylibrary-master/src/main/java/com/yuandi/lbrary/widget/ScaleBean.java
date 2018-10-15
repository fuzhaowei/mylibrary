package com.yuandi.lbrary.widget;

/**
 * Created by EdgeDi
 * 2018/2/12 14:52
 */

public class ScaleBean implements ScaleCommon {

    private double value;
    private int color;

    public ScaleBean() {

    }

    public ScaleBean(double value, int color) {
        this.value = value;
        this.color = color;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public int getColor() {
        return color;
    }
}
