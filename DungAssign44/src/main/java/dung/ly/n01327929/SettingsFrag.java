package dung.ly.n01327929;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsFrag extends Fragment
{
    SharedPreferences sharedPreferences;
    View view;
    Button btnapply;
    RadioGroup btngroup;
    RadioButton format12, format24;

    private void setupid()
    {
        format12 = (RadioButton) view.findViewById(R.id.radiobtn12h);
        format24 = (RadioButton) view.findViewById(R.id.radiobtn24h);
        btnapply = (Button) view.findViewById(R.id.btnsettingapply);
        btngroup = (RadioGroup) view.findViewById(R.id.timebtngroup);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        setupid();
        sharedPreferences = this.getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String timeformat = sharedPreferences.getString("time", "24h");
        if(btngroup.getCheckedRadioButtonId() == -1)
        {
            if(timeformat.equals("12h"))
            {
                format12.setChecked(true);
            }
            else if(timeformat.equals("24h"))
            {
                format24.setChecked(true);
            }
        }

        btnapply.setOnClickListener(v ->
        {
            sendData();

        });
        return view;
    }

    private void sendData()
    {
        String timeformat = "";
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (format12.isChecked())
        {
            timeformat = format12.getText().toString();
        } else if (format24.isChecked())
        {
            timeformat = format24.getText().toString();
        }
        editor.putString("time", timeformat);
        editor.apply();
    }
}