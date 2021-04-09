package com.example.viewmodellivedatatest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
//
public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button btnShowImage;
    private ImageView imageView;
    private ImageViewModel myViewModel;
    private ImageInfo imageInfo;
    private List<ImageInfo> lstImageInfo = new ArrayList<ImageInfo>();
    private String imageLink = "http://content.sportslogos.net/logos/6/227/full/4578_toronto_raptors-primary-2016.png";
    private String imageName = "Raptors";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        btnShowImage = (Button) findViewById(R.id.btnShowImage);
        imageView = (ImageView) findViewById(R.id.imageView);
        // Obtain (or create) an instance of the View Model
        myViewModel = ViewModelProviders.of(this).get(ImageViewModel.class);
        // Create the observer which updates the UI.
        final Observer<List<ImageInfo>> nameObserver = new Observer<List<ImageInfo>>() {
            //called when the data is changed
            @Override
            public void onChanged(@Nullable List<ImageInfo> data) {
                textView.setTextSize(24);
                //get the data fields and update the UI
                imageInfo = data.get(0);
                textView.setText(imageInfo.getImageName());
                imageView.setImageBitmap(imageInfo.getBmpImage());
            }
        };

        // Observe the LiveData, passing in this activity
        // as the LifecycleOwner and the observer.
        myViewModel.getImages(imageLink,imageName).observe(this, nameObserver);

        // Get the current data and observe it for changes.


    }

    public void showImage(View v)
    {
        //Maple Leafs logo link
        imageLink="http://content.sportslogos.net/logos/1/28/full/199.gif";
        String imageName="Maple Leafs Logo";
        imageInfo = new ImageInfo(imageInfo.getBmpImage(),imageLink, imageName);
        lstImageInfo.add(imageInfo);
        // do this through the model
        myViewModel.getImages(imageLink,imageName).setValue(lstImageInfo);
    }


}
