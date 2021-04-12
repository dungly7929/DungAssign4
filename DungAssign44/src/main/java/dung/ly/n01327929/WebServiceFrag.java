//Name: DUNG LY         ID: N01327929
package dung.ly.n01327929;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WebServiceFrag extends Fragment
{
    TextView jsontv;
    Button btnzip;
    EditText editText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_webserver, container, false);
        jsontv = root.findViewById(R.id.dungjsontvlonlat);
        btnzip = root.findViewById(R.id.dungbtnzip);
        editText = root.findViewById(R.id.dungeditextzipcode);
        btnzip.setOnClickListener(v ->
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            String text = editText.getText().toString();
            if (text.equals(""))
            {
                dialog.setTitle(R.string.assignmet4);
                dialog.setMessage(R.string.zipcant);
                dialog.setIcon(R.drawable.ic_warning);
                dialog.show();
                editText.setError(getString(R.string.noempty));
                jsontv.setText("");
            }
            else if (text.length() <=4)
            {
                dialog.setTitle(R.string.assignmet4);
                dialog.setMessage(R.string.zipcantless5);
                dialog.setIcon(R.drawable.ic_warning);
                dialog.show();
                editText.setError(getString(R.string.zipcantless5));
                jsontv.setText(R.string.blank);
            }
            else
            {
                new ReadJSON().execute(getString(R.string.jsonurl1)+text+getString(R.string.jsonurl2));
            }

        });

        return root;
    }

    private class ReadJSON extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            StringBuilder content = new StringBuilder();
            try
            {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";

                while ((line = bufferedReader.readLine()) != null)
                {
                    content.append(line);
                }
                bufferedReader.close();

            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            return content.toString();
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            try
            {
                JSONObject object = new JSONObject(s);
                JSONObject longlat = object.getJSONObject(getString(R.string.coord));
                double lon = longlat.getDouble(getString(R.string.lon));
                double lat = longlat.getDouble(getString(R.string.lat));
                JSONObject main = object.getJSONObject(getString(R.string.main));
                double temp = main.getDouble(getString(R.string.temp)) - 273.15;
                int humi = main.getInt(getString(R.string.humidity));
                String name = object.getString(getString(R.string.name));
                jsontv.setText(String.format(getString(R.string.result), name, lon, lat, humi, temp,editText.getText().toString()));


            } catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }
}