package com.blamejared.dmt.compat.crt;

import org.openzen.zencode.java.ZenCodeType;

public class BeamObject {

    float red;
    float green;
    float blue;
    float alpha;
    float height;
    float radius_1;
    float radius_2;
    float radius_3;
    float radius_4;
    float radius_5;
    float radius_6;
    float radius_7;
    float radius_8;


    public BeamObject(float red, float green, float blue, float alpha, float height, float radius_1, float radius_2, float radius_3, float radius_4, float radius_5, float radius_6, float radius_7, float radius_8) {

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.height = height;
        this.radius_1 = radius_1;
        this.radius_2 = radius_2;
        this.radius_3 = radius_3;
        this.radius_4 = radius_4;
        this.radius_5 = radius_5;
        this.radius_6 = radius_6;
        this.radius_7 = radius_7;
        this.radius_8 = radius_8;
    }

    @ZenCodeType.Method
    public static BeamObject make(float red, float green, float blue, float alpha, float height, float radius_1, float radius_2, float radius_3, float radius_4, float radius_5, float radius_6, float radius_7, float radius_8) {

        return new BeamObject(red, green, blue, alpha, height, radius_1, radius_2, radius_3, radius_4, radius_5, radius_6, radius_7, radius_8);
    }

    public float getRed() {

        return red;
    }

    public void setRed(float red) {

        this.red = red;
    }

    public float getGreen() {

        return green;
    }

    public void setGreen(float green) {

        this.green = green;
    }

    public float getBlue() {

        return blue;
    }

    public void setBlue(float blue) {

        this.blue = blue;
    }

    public float getAlpha() {

        return alpha;
    }

    public void setAlpha(float alpha) {

        this.alpha = alpha;
    }

    public float getHeight() {

        return height;
    }

    public void setHeight(float height) {

        this.height = height;
    }

    public float getRadius_1() {

        return radius_1;
    }

    public void setRadius_1(float radius_1) {

        this.radius_1 = radius_1;
    }

    public float getRadius_2() {

        return radius_2;
    }

    public void setRadius_2(float radius_2) {

        this.radius_2 = radius_2;
    }

    public float getRadius_3() {

        return radius_3;
    }

    public void setRadius_3(float radius_3) {

        this.radius_3 = radius_3;
    }

    public float getRadius_4() {

        return radius_4;
    }

    public void setRadius_4(float radius_4) {

        this.radius_4 = radius_4;
    }

    public float getRadius_5() {

        return radius_5;
    }

    public void setRadius_5(float radius_5) {

        this.radius_5 = radius_5;
    }

    public float getRadius_6() {

        return radius_6;
    }

    public void setRadius_6(float radius_6) {

        this.radius_6 = radius_6;
    }

    public float getRadius_7() {

        return radius_7;
    }

    public void setRadius_7(float radius_7) {

        this.radius_7 = radius_7;
    }

    public float getRadius_8() {

        return radius_8;
    }

    public void setRadius_8(float radius_8) {

        this.radius_8 = radius_8;
    }

}