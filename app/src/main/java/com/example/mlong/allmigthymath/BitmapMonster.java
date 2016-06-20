package com.example.mlong.allmigthymath;

import android.graphics.Bitmap;

/**
 * Created by Long Huynh on 20.06.2016.
 */
public class BitmapMonster {
    private Bitmap bitmap;
    private int width, height, numFrames;

    public BitmapMonster(Bitmap bitmap, int width, int height, int numFrames) {
        this.bitmap = bitmap;
        this.width = width;
        this.height = height;
        this.numFrames = numFrames;
    }


    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getNumFrames() {
        return numFrames;
    }

    public void setNumFrames(int numFrames) {
        this.numFrames = numFrames;
    }
}
