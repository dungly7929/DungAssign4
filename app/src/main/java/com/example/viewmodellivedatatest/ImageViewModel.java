package com.example.viewmodellivedatatest;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ImageViewModel extends AndroidViewModel {
    //
    private static final String TAG = "ImageUpdate";
    private MutableLiveData<List<ImageInfo>> images;
    private String imageName;
    private String imageLink;
    //
    public ImageViewModel(Application application) {
        super(application);
    }
    //
    public MutableLiveData<List<ImageInfo>> getImages(String imgLink, String imgName) {
        if (images == null) {
            images = new MutableLiveData<List<ImageInfo>>();
        }
        loadImage(imgLink, imgName);
        return images;
    }
    //
    private class DownloadImageTask extends AsyncTask<String, Void, List<ImageInfo> > {

        //return a bitmap given its URL
        protected List<ImageInfo> doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Log.d("urlDisplay", urlDisplay);

            ArrayList<ImageInfo> result = new ArrayList<>(0);

            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
                Log.d("Bitmap decoding", "complete");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            imageLink=urlDisplay;
            imageName=urls[1];
            Log.d("image name", imageName);

            ImageInfo imageInfo = new ImageInfo(bmp,imageLink, imageName);
            //return bmp;
            result.add(imageInfo);
            return result;
        }
        protected void onPostExecute(List<ImageInfo> result) {
            //bmImage.setImageBitmap(result);
            // Update the Live Data data value.
            images.setValue(result);
        }
    }
    //
    public void loadImage(String imgLink, String imageName) {
        //http://content.sportslogos.net/logos/6/227/full/4578_toronto_raptors-primary-2016.png
        new DownloadImageTask().execute(imgLink,imageName);
    }


}
