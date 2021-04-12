package dung.ly.n01327929;

import android.app.AlertDialog;
import android.app.Dialog;
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
        jsontv = root.findViewById(R.id.jsontvlonlat);
        btnzip = root.findViewById(R.id.btnzip);
        editText = root.findViewById(R.id.editextzipcode);
        btnzip.setOnClickListener(v ->
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            String text = editText.getText().toString();
            if (text.equals(""))
            {
                dialog.setTitle("Assignment 4:");
                dialog.setMessage("Zipcode can not empty");
                dialog.setIcon(R.drawable.ic_warning);
                dialog.show();
                editText.setError("This can not empty");
                jsontv.setText("");
            }
            else if (text.length() <=4)
            {
                dialog.setTitle("Assignment 4:");
                dialog.setMessage("Zipcode can not less than 5");
                dialog.setIcon(R.drawable.ic_warning);
                dialog.show();
                editText.setError("Zipcode can not less than 5");
                jsontv.setText("");
            }
            else
            {
                new ReadJSON().execute("https://samples.openweathermap.org/data/2.5/weather?zip=["+text+"],us&appid=b6907d289e10d714a6e88b30761fae22");
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
                JSONObject longlat = object.getJSONObject("coord");
                double lon = longlat.getDouble("lon");
                double lat = longlat.getDouble("lat");
                JSONObject main = object.getJSONObject("main");
                double temp = main.getDouble("temp") - 273.15;
                int humi = main.getInt("humidity");
                String name = object.getString("name");
                jsontv.setText(String.format("Name: %s \nLon: %.2f \tLat: %.2f \nHumidity: %d \nTemperature: %.2fC \nZipcode: %s", name, lon, lat, humi, temp,editText.getText().toString()));


            } catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }
}