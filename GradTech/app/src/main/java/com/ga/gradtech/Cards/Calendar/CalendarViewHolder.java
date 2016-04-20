package com.ga.gradtech.Cards.Calendar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ga.gradtech.R;

/**
 * Created by samsiu on 4/20/16.
 */
public class CalendarViewHolder {

    public EditText mCalendarTitleEditText;
    public EditText mCalendarDescriptionEditText;
    public EditText mCalendarLocationEditText;
    public Button mCalendarAddEventButton;
    public Button mCalendarDeleteEventsButton;
    public Button mCalendarUpdateEventsButton;
    public Button mCalendarGetEventsButton;
    public ListView mCalendarListView;


    public CalendarViewHolder(View itemView) {
        this.mCalendarTitleEditText = (EditText)itemView.findViewById(R.id.card_calendar_title_editText);
        this.mCalendarDescriptionEditText = (EditText)itemView.findViewById(R.id.card_calendar_description_editText);
        this.mCalendarLocationEditText = (EditText)itemView.findViewById(R.id.card_calendar_location_editText);
        this.mCalendarAddEventButton = (Button)itemView.findViewById(R.id.card_calendar_add_event_button);
        this.mCalendarDeleteEventsButton = (Button)itemView.findViewById(R.id.card_calendar_delete_event_button);
        this.mCalendarUpdateEventsButton = (Button)itemView.findViewById(R.id.card_calendar_update_event_button);
        this.mCalendarGetEventsButton = (Button)itemView.findViewById(R.id.card_calendar_get_events_button);
        this.mCalendarListView = (ListView)itemView.findViewById(R.id.card_calendar_listView);
    }
}
