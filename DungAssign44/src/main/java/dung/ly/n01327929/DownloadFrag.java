package dung.ly.n01327929;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class DownloadFrag extends Fragment
{
    Spinner ImgSpinner;
    ArrayList<ImageButton> imgbtn = new ArrayList<ImageButton>();
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_download, container, false);
        ImgSpinner = (Spinner) view.findViewById(R.id.spinner);
        new loadImage().execute("https://www.gardendesign.com/pictures/images/675x529Max/site_3/helianthus-yellow-flower-pixabay_11863.jpg","https://cdn.britannica.com/s:400x225,c:crop/97/158797-050-ABECB32F/North-Cascades-National-Park-Lake-Ann-park.jpg");
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item,imgbtn);
        ImgSpinner.setAdapter(arrayAdapter);
        return view;
    }


    //LoadImage from internet
    private class loadImage extends AsyncTask<String, Void, ArrayList<Bitmap>>
    {

        @Override
        protected ArrayList<Bitmap> doInBackground(String... strings)
        {
            ArrayList<Bitmap> Lightmap = new ArrayList<Bitmap>();
            try
            {
                for(int i = 0 ;i< strings.length;i++)
                {
                    URL url = new URL(strings[i]);
                    InputStream inputStream = url.openConnection().getInputStream();
                    Lightmap.add(BitmapFactory.decodeStream(inputStream));
                }

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

            return Lightmap;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmap)
        {
            super.onPostExecute(bitmap);
            for(int i = 0 ;i < bitmap.size();i++)
            {
                Bitmap result = bitmap.get(i);
                imgbtn.add((ImageButton) view.findViewById(getResources().getIdentifier("imgbtn"+(i+1), "id", getActivity().getPackageName())));
                imgbtn.get(i).setImageBitmap(result);
            }



        }
    }

}