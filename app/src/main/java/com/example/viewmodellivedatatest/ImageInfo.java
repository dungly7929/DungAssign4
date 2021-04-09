package com.example.viewmodellivedatatest;

import android.graphics.Bitmap;

public class ImageInfo {
    private Bitmap bmpImage;
    private String imageLink;
    private String imageName;
    //
    public ImageInfo(Bitmap bmpImage, String imageLink, String imageName) {
        this.bmpImage = bmpImage;
        this.imageLink = imageLink;
        this.imageName = imageName;
    }

    public Bitmap getBmpImage() {
        return bmpImage;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getImageName() {
        return imageName;
    }

    @Override
    public String toString() {
        return "ImageInfo{" +
                ", imageLink='" + imageLink + '\'' +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
