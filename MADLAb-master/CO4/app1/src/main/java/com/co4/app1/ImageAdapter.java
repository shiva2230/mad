package com.co4.app1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private final int[] imageIds;

    public ImageAdapter(Context context, int[] imageIds) {
        this.context = context;
        this.imageIds = imageIds;
    }

    @Override
    public int getCount() {
        return imageIds.length;
    }

    @Override
    public Object getItem(int position) {
        return imageIds[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        if (gridItemView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            gridItemView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        ImageView imageView = gridItemView.findViewById(R.id.gridview);
        imageView.setImageResource(imageIds[position]);

        return gridItemView;
    }
}
