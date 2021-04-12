package dung.ly.n01327929;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class SettingsFrag extends Fragment
{
    SharedPreferences sharedPreferences;
    View view;
    Button btnapply;
    RadioGroup btngroup, btngroup1;
    RadioButton format12, format24, pink, white, purple;
    Switch swtori;
    boolean orisw = false;

    private void setupid()
    {
        format12 = (RadioButton) view.findViewById(R.id.radiobtn12h);
        format24 = (RadioButton) view.findViewById(R.id.radiobtn24h);
        btnapply = (Button) view.findViewById(R.id.btnsettingapply);
        pink = (RadioButton) view.findViewById(R.id.radiobtnpink);
        white = (RadioButton) view.findViewById(R.id.radiobtnwhite);
        purple = (RadioButton) view.findViewById(R.id.radiobtnpurple);
        btngroup = (RadioGroup) view.findViewById(R.id.timebtngroup);
        btngroup1 = (RadioGroup) view.findViewById(R.id.bgbtngroup);
        swtori = (Switch) view.findViewById(R.id.orientationsw);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        setupid();
        sharedPreferences = this.getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String timeformat = sharedPreferences.getString("time", "24h");
        int bgcolor = sharedPreferences.getInt("bgcolor", 0);
        boolean orisw1 = sharedPreferences.getBoolean("orisw", false);
        if (!orisw1)
        {
            swtori.setChecked(false);
        } else
        {
            swtori.setChecked(true);
        }
        if (btngroup.getCheckedRadioButtonId() == -1)
        {
            if (timeformat.equals("12h"))
            {
                format12.setChecked(true);
            } else if (timeformat.equals("24h"))
            {
                format24.setChecked(true);
            }
        }

        if (btngroup1.getCheckedRadioButtonId() == -1)
        {
            if (bgcolor == 1)
            {
                purple.setChecked(true);
            } else if (bgcolor == 2)
            {
                white.setChecked(true);
            } else if (bgcolor == 3)
            {
                pink.setChecked(true);
            }
        }

        swtori.setOnCheckedChangeListener((buttonView, isChecked) ->
        {
            orisw = isChecked;
        });

            btnapply.setOnClickListener(v ->
            {
                sendData();

            });
        return view;
    }

    private void sendData()
    {
        //Change time format
        String timeformat = "";
        if (format12.isChecked())
        {
            timeformat = format12.getText().toString();
        } else if (format24.isChecked())
        {
            timeformat = format24.getText().toString();
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("time", timeformat);

        //Change background color
        int bgcolor = 0;
        if (pink.isChecked())
        {
            bgcolor = 3;
        } else if (white.isChecked())
        {
            bgcolor = 2;
        } else
        {
            bgcolor = 1;
        }
        editor.putInt("bgcolor", bgcolor);
        editor.putBoolean("orisw",orisw);
        editor.apply();
    }
}