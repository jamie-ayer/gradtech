package com.ga.gradtech.Cards.Meetup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ga.gradtech.Cards.Meetup.Model.Event;
import com.ga.gradtech.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by leisforkokomo on 4/21/16.
 */
public class MeetupAdapter extends ArrayAdapter {
    List<Event> data = new ArrayList<>();
    Event event;
    View rowItem;
    ImageView eventImage;
    TextView nameTextView;
    TextView timeTextView;
    TextView addressTextView;
    TextView cityTextView;
    TextView descriptionTextView;

    public MeetupAdapter(Context context, ArrayList<Event> objects) {
        super(context, -1, objects);
        this.data = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_meetup, parent, false);
        initializedViews();
        event = data.get(position);
        setViews();

        return rowItem;
    }

    private void initializedViews(){
        eventImage = (ImageView) rowItem.findViewById(R.id.meetup_listItem_imageView_id);
        nameTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_name_textView_id);
        timeTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_time_textView_id);
        addressTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_address_textView_id);
        cityTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_city_textView_id);
        descriptionTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_description_textView_id);
    }

    private void setViews(){
        nameTextView.setText(event.getName());
//        Date time= new java.util.Date((long)timeStamp*1000);
        timeTextView.setText(event.getTime() + "");
        addressTextView.setText(event.getVenue().getAddress_1());
        cityTextView.setText(event.getVenue().getCity());
        descriptionTextView.setText(event.getDescription());
    }
}
