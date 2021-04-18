//Name: DUNG LY         ID: N01327929
package dung.ly.n01327929;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class DownloadFrag extends Fragment
{
    Spinner ImgSpinner;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    ArrayList<Bitmap> Lightmap = new ArrayList<Bitmap>();
    SharedPreferences sharedPreferences;
    int pos;
    ImageView imgshow = null;
    Button btndownload;
    String[] pic, url;
    View view;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_download, container, false);
        sharedPreferences = this.getActivity().getApplication().getSharedPreferences(getString(R.string.Data), Context.MODE_PRIVATE);
        url = new String[]{getString(R.string.pic1url), getString(R.string.pic2url), getString(R.string.pic3url)};
        pic = new String[]{getString(R.string.pic1), getString(R.string.pic2), getString(R.string.pic3)};
        ImgSpinner = (Spinner) view.findViewById(R.id.dungspinner);
        imgshow = (ImageView) view.findViewById(R.id.dungimgshow);
        btndownload = (Button) view.findViewById(R.id.dungbtndownload);
        CreateSpinner createSpinner = new CreateSpinner();
        createSpinner.execute(url);
        btndownload.setOnClickListener(v ->
        {

            SaveLoadImage save = new SaveLoadImage();
            save.execute(Lightmap);
        });
        boolean sw = sharedPreferences.getBoolean(getString(R.string.orisw), false);
        if (sw == false)
        {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else
        {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        return view;
    }

    //Create a spinner to load image from URL
    private class CreateSpinner extends AsyncTask<String, Void, ArrayList<Bitmap>>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<Bitmap> doInBackground(String... strings)
        {
            try
            {
                for (int i = 0; i < strings.length; i++)
                {
                    URL url = new URL(strings[i]);
                    InputStream inputStream = url.openConnection().getInputStream();
                    Lightmap.add(BitmapFactory.decodeStream(inputStream));
                }

            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (Exception e)
            {
                e.printStackTrace();
            }


            return Lightmap;
        }


        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmap1)
        {
            super.onPostExecute(bitmap1);
            ConstraintLayout r1 = (ConstraintLayout) view.findViewById(R.id.download_layout);
            ArrayList<ImageItem> arrayList = new ArrayList<ImageItem>();
            for (int i = 0; i < Lightmap.size(); i++)
            {
                arrayList.add(new ImageItem(pic[i], Lightmap.get(i)));
            }
            ImageAdapter imageAdapter = new ImageAdapter(getActivity(), R.layout.image_spinner_row, arrayList);
            ImgSpinner.setAdapter(imageAdapter);
            ImgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    pos = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }
    }

    //LoadImage from internet
    private class SaveLoadImage extends AsyncTask<ArrayList<Bitmap>, Void, ArrayList<Bitmap>>
    {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());


        @Override
        protected ArrayList<Bitmap> doInBackground(ArrayList<Bitmap>... arrayLists)
        {
            return Lightmap;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog.setMessage(getString(R.string.Please_wait));
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }


        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmap)
        {
            super.onPostExecute(bitmap);
            if (imgshow != null)
            {
                progressDialog.hide();
                imgshow.setImageBitmap(bitmap.get(pos));
                imgshow.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) imgshow.getDrawable();
                Bitmap bm = drawable.getBitmap();
                FileOutputStream outputStream = null;

                File sdCard = Environment.getExternalStorageDirectory();
                File directory = new File(sdCard.getAbsolutePath() + getString(R.string.download_path));
                directory.mkdir();
                String filename = String.format("%d.jpg", pos);
                File outFile = new File(directory, filename);
                if (outFile.exists())
                {
                    Toast.makeText(getActivity(),getString(R.string.Downloaded), Toast.LENGTH_SHORT).show();
                } else
                {
                    try
                    {
                        outputStream = new FileOutputStream(outFile);
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                        outputStream.flush();
                        outputStream.close();

                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        intent.setData(Uri.fromFile(outFile));
                        getActivity().sendBroadcast(intent);
                    } catch (FileNotFoundException e)
                    {
                        e.printStackTrace();
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }


            } else
            {
                progressDialog.show();
            }

        }
    }

}