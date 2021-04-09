package dung.ly.n01327929;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dung.ly.n01327929.R;

import static android.os.Looper.getMainLooper;

public class HomeFragment extends Fragment
{
    TextView textvdate, textvtime;
    SharedPreferences sharedPreferences;
    String timeformat = "";


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        View view = root;
        sharedPreferences = this.getActivity().getApplication().getSharedPreferences("Data", Context.MODE_PRIVATE);
        timeformat = sharedPreferences.getString("time","24h");
        Date currentDate = Calendar.getInstance().getTime();
        textvdate = (TextView) view.findViewById(R.id.text_date);
        textvtime = (TextView) view.findViewById(R.id.text_time);
        showtime(timeformat);
        textvdate.setText(currentDate.toString());
        return root;
    }

    public void showtime(String time)
    {
        if (time.equals("12h"))
        {
            final Handler timeupdate = new Handler(getMainLooper());
            timeupdate.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    textvtime.setText(new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date()));
                    timeupdate.postDelayed(this, 1000);
                }
            }, 10);
        }
        else
        {
            final Handler timeupdate = new Handler(getMainLooper());
            timeupdate.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    textvtime.setText(new SimpleDateFormat("HH:mm:ss ", Locale.getDefault()).format(new Date()));
                    timeupdate.postDelayed(this, 1000);
                }
            }, 10);
        }

    }

}