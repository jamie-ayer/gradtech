package com.ga.gradtech.Cards.Meetup;

import android.content.Context;
import android.text.Html;
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
 * This creates a custom ArrayAdapter capable of displaying a list of Event objects.
 * Created by leisforkokomo on 4/21/16.
 */
public class MeetupAdapter extends ArrayAdapter {
    private static final String TAG = "Meetup Adapter";
    private List<Event> data = new ArrayList<>();
    private Event event;
    private View rowItem;
    public ImageView eventImage;
    public TextView nameTextView;
    public TextView timeTextView;
    public TextView addressTextView;
    public TextView descriptionTextView;

    /**
     * Constructor for the MeetupAdapter class that defines the properties required to create a new instance of the class.
     * @param context
     * @param objects
     */
    public MeetupAdapter(Context context, ArrayList<Event> objects) {
        super(context, -1, objects);
        this.data = objects;
    }

    /**
     * This method inflates the list_item view of the fragment.
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_meetup, parent, false);
        initializedViews();
        event = data.get(position);
        setViews();
        return rowItem;
    }

    /**
     * This method initializes all the views in the fragment.
     */
    private void initializedViews(){
        eventImage = (ImageView) rowItem.findViewById(R.id.meetup_listItem_imageView_id);
        nameTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_name_textView_id);
        timeTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_time_textView_id);
        addressTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_address_textView_id);
        descriptionTextView = (TextView) rowItem.findViewById(R.id.meetup_listItem_description_textView_id);
    }

    /**
     * This method sets all the views in the fragment.
     */
    private void setViews(){
        nameTextView.setText(event.getName());
        setTimeTextView();
        addressTextView.setText("Where: " + event.getVenue().getAddress_1() + ", " + event.getVenue().getCity());
        setDescriptionTextView();
        eventImage.setImageResource(R.drawable.meetup_icon);
    }

    /**
     * This method removes html markup language from a string.
     * @param html
     * @return
     */
    private String stripHtml(String html) {
        return Html.fromHtml(html).toString();
    }

    /**
     * This method sets the timeTextView after converting Unix time to real time.
     */
    private void setTimeTextView(){
//        Date time= new java.util.Date((long)timeStamp*1000); hint from jamie
        long unixSecodes = event.getTime();
        Date date = new Date(unixSecodes*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy h:mma");
        sdf.setTimeZone(TimeZone.getTimeZone("PST"));
        String realDateTime = sdf.format(date);
        Log.i(TAG, "real date and time is " + realDateTime);
        timeTextView.setText("When: " + realDateTime);// was timeTextView.setText(event.getTime() + ""); was timeTextView.setText("When: " + realDateTime + " PM");
    }

    /**
     * This method sets the descriptionTextView after cleaning the string of html markup language.
     */
    private void setDescriptionTextView(){
        String descriptionDirty = event.getDescription();
        String descriptionClean = stripHtml(descriptionDirty);
        descriptionTextView.setText("Details: " + descriptionClean);
    }

}
