package dung.ly.n01327929;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends BaseAdapter
{
    Context context;
    int myLayout;
    List<ImageItem> arrayList;

    public ImageAdapter(Context context, int myLayout, List<ImageItem> arrayList)
    {
        this.context = context;
        this.myLayout = myLayout;
        this.arrayList = arrayList;
    }



    @Override
    public int getCount()
    {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);

        TextView txtview = (TextView) convertView.findViewById(R.id.text_view_name);
        ImageView imgview = (ImageView) convertView.findViewById(R.id.img_spinner);

        txtview.setText(arrayList.get(position) .ImageName);
        imgview.setImageBitmap(arrayList.get(position) .Image);

        return convertView;
    }
}
