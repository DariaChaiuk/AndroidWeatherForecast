package com.weatherforecast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DayGVAdapter extends ArrayAdapter<DayInfo> {
    public DayGVAdapter(@NonNull Context context, ArrayList<DayInfo> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        DayInfo dayInfo = getItem(position);
        TextView datDate = listItemView.findViewById(R.id.day_date);
        TextView maxTemp = listItemView.findViewById(R.id.max_temp);
        TextView minTemp = listItemView.findViewById(R.id.min_temp);
        TextView avgTemp = listItemView.findViewById(R.id.avg_temp);
        TextView condition = listItemView.findViewById(R.id.condition);

        ImageView img = listItemView.findViewById(R.id.weather_img);

        datDate.setText(dayInfo.date);
        maxTemp.setText(String.format("max:%.1f C", dayInfo.maxTemp));
        minTemp.setText(String.format("min:%.1f C", dayInfo.minTemp));
        avgTemp.setText(String.format("avg:%.1f C", dayInfo.avgTemp));
        condition.setText(String.valueOf(dayInfo.condition));

        img.setImageResource(dayInfo.getWeatherImg());
        return listItemView;
    }
}
