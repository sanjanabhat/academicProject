package com.parse.starter;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Rohit on 04/05/2016.
 */
    public class CustomList extends ArrayAdapter<String>
{

    private final Activity context;
    private final ArrayList<String> things;
    private final ArrayList<Bitmap> images;

    public CustomList(Activity context, ArrayList<String> things, ArrayList<Bitmap> images)
    {
        super(context, R.layout.list_single, things);
        this.context = context;
        this.things = things;
        this.images = images;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(things.get(position));

        imageView.setImageBitmap(images.get(position));;
        return rowView;
    }


}
