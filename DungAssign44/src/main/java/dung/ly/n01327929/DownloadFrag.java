package dung.ly.n01327929;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloadFrag extends Fragment
{
    ImageView iv;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_download, container, false);
        iv = (ImageView) root.findViewById(R.id.imgload);
        new loadImage().execute("https://www.gardendesign.com/pictures/images/675x529Max/site_3/helianthus-yellow-flower-pixabay_11863.jpg");
        return root;
    }

    private class loadImage extends AsyncTask<String, Void, Bitmap>
    {
        Bitmap bitmap1 = null;
        @Override
        protected Bitmap doInBackground(String... strings)
        {
            try
            {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();

                bitmap1 = BitmapFactory.decodeStream(inputStream);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return bitmap1;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            super.onPostExecute(bitmap);
            iv.setImageBitmap(bitmap);
        }
    }

}