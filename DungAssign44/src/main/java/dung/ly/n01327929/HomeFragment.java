//Name: DUNG LY         ID: N01327929
package dung.ly.n01327929;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.os.Looper.getMainLooper;

public class HomeFragment extends Fragment
{
    TextView textvdate, textvtime;
    SharedPreferences sharedPreferences;
    String timeformat = "";
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        view = root;
        sharedPreferences = this.getActivity().getApplication().getSharedPreferences(getString(R.string.Data), Context.MODE_PRIVATE);
        timeformat = sharedPreferences.getString(getString(R.string.time), getString(R.string._24h));
        int bgcolor = sharedPreferences.getInt(getString(R.string.bgcolor), 0);
        boolean sw = sharedPreferences.getBoolean(getString(R.string.orisw), false);
        Date currentDate = Calendar.getInstance().getTime();
        textvdate = (TextView) view.findViewById(R.id.dungtext_date);
        textvtime = (TextView) view.findViewById(R.id.dungtext_time);
        showtime(timeformat);
        changebg(bgcolor);
        textvdate.setText(currentDate.toString());
        if (sw == false)
        {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else
        {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        return root;
    }

    private void changebg(int number)
    {

        if (number == 1)
        {
            view.setBackgroundColor(getResources().getColor(R.color.purple));
        } else if (number == 2)
        {
            view.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (number == 3)
        {
            view.setBackgroundColor(getResources().getColor(R.color.pink));
        }

    }

    public void showtime(String time)
    {
        if (time.equals(getString(R.string._12h)))
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
        } else
        {
            final Handler timeupdate = new Handler(getMainLooper());
            timeupdate.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    textvtime.setText(new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()));
                    timeupdate.postDelayed(this, 1000);
                }
            }, 10);
        }

    }

}