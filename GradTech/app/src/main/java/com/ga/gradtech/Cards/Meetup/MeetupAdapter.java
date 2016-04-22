package com.ga.gradtech.Cards.Meetup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ga.gradtech.Cards.Meetup.Model.Event;
import com.ga.gradtech.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by leisforkokomo on 4/21/16.
 */
public class MeetupAdapter extends ArrayAdapter {
    private static final String TAG = "Meetup Adapter";
    List<Event> data = new ArrayList<>();
    Event event;
    View rowItem;
    ImageView eventImage;
    TextView nameTextView;
    TextView timeTextView;
    TextView addressTextView;
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
        descriptionTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_description_textView_id);
    }

    private void setViews(){
        nameTextView.setText(event.getName());
//        Date time= new java.util.Date((long)timeStamp*1000);
        long unixSecodes = event.getTime();
        Date date = new Date(unixSecodes*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mma z");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        String realDateTime = sdf.format(date);
        Log.i(TAG, "real date and time is " + realDateTime);
        timeTextView.setText("When: " + realDateTime);// was timeTextView.setText(event.getTime() + "");
        addressTextView.setText("Where: " + event.getVenue().getAddress_1() + ", " + event.getVenue().getCity());
        descriptionTextView.setText(event.getDescription());
        eventImage.setImageResource(R.drawable.meetup_icon);

    }
}
