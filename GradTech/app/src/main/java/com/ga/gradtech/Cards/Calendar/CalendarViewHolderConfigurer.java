package com.ga.gradtech.Cards.Calendar;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import java.util.Calendar;

/**
 * Created by samsiu on 4/20/16.
 */
public class CalendarViewHolderConfigurer {
    private static final String TAG = CalendarViewHolderConfigurer.class.getCanonicalName();

    private static final int CALENDAR_ID = 11;

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private static final int PROJECTION_EVENT_ID_INDEX = 0;
    private static final int PROJECTION_EVENT_CALENDAR_INDEX = 1;
    private static final int PROJECTION_EVENT_ORGANIZER_INDEX = 2;
    private static final int PROJECTION_EVENT_TITLE_INDEX = 3;
    private static final int PROJECTION_EVENT_LOCATION_INDEX = 4;
    private static final int PROJECTION_EVENT_DESCRIPTION_INDEX = 5;
    private static final int PROJECTION_EVENT_DTSTART_INDEX = 6;
    private static final int PROJECTION_EVENT_DTEND_INDEX = 7;


    long APRIL_1_2016;
    long MAY_1_2016;

    CalendarCardViewHolder vh10;
    int position;
    Activity mainActivity;
    ListAdapter listAdapter;

    public CalendarViewHolderConfigurer(CalendarCardViewHolder vh10, int position, Activity mainActivity) {
        this.vh10 = vh10;
        this.position = position;
        this.mainActivity = mainActivity;
    }


    public void setShareCalendarButtonListener(){
        vh10.mCalenderShareEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = vh10.mCalendarTitleEditText.getText().toString();
                title = title.isEmpty() ? "Title" : title;

                String location = vh10.mCalendarLocationEditText.getText().toString();
                location = location.isEmpty() ? "Location" : location;

                String description = vh10.mCalendarDescriptionEditText.getText().toString();
                description = description.isEmpty() ? "Description" : description;

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, description);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title + ": " + location);
                mainActivity.startActivity(Intent.createChooser(intent, "Share to"));
            }
        });
    }


    public void updateEvent(){
        vh10.mCalendarUpdateEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    // The method returns all the calendars associated with your email. The property ID is a
    // calendar id. You have to choose one type of calendar you would love to work on in the
    // methods below
    public void fetchCalendars() {

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] CALENDAR_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
        };

        // Run query
        ContentResolver cr = mainActivity.getContentResolver();

        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //return;
        }

        Cursor cur = cr.query(uri,
                CALENDAR_PROJECTION,
                null,//selection,
                null,//selectionArgs,
                null);

        // Get the field values
        cur.moveToFirst();
        for(int i = 0; i < cur.getCount(); i++){
            String calendarId = cur.getString(PROJECTION_ID_INDEX);
            String displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            String accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            String ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            Log.d(TAG, "onCreate: ===>>> CalendarID: " + calendarId);
            Log.d(TAG, "onCreate: ===>>> DisplayName: " + displayName);
            Log.d(TAG, "onCreate: ===>>> AccountName: " + accountName);
            Log.d(TAG, "onCreate: ===>>> OwnerName: " + ownerName);
            Log.d(TAG, "onCreate: \n");
            cur.moveToNext();
        }

    }


    public void setAddEventButtonListener(){
        vh10.mCalendarAddEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertEventInCalendar();
            }
        });
    }

    public void insertEventInCalendar() {

        Log.d(TAG, "insertEventInCalendar: ===>>>> Calendar Insert");

        String title = vh10.mCalendarTitleEditText.getText().toString();
        title = title.isEmpty() ? "Title" : title;

        String location = vh10.mCalendarLocationEditText.getText().toString();
        location = location.isEmpty() ? "Location" : location;

        String description = vh10.mCalendarDescriptionEditText.getText().toString();
        description = description.isEmpty() ? "Description" : description;


        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2016, Calendar.APRIL, 10, 19, 0);
        long startMillis = beginTime.getTimeInMillis();

        Calendar endTime = Calendar.getInstance();
        endTime.set(2016, Calendar.APRIL, 10, 22, 0);
        long endMillis = endTime.getTimeInMillis();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.CALENDAR_ID, CALENDAR_ID);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, location);

        Uri uri = mainActivity.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
        Log.d(TAG, "insertEventInCalendar: CONTENT_URI ===>>>" + uri);
        // returns the ID of the row so that you can use it when updating the event
        long eventId = Long.parseLong(uri.getLastPathSegment());
        Log.d(TAG, "add id = " + eventId);


    }

    public void setShowEventButtonListener(){
        vh10.mCalendarGetEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchEvents();
            }
        });
    }

    public void fetchEvents() {
        Log.d(TAG, "fetchEvents: ====> Fetch Events Column: " + CalendarContract.Events.CALENDAR_ID);
        Uri uri = CalendarContract.Events.CONTENT_URI;
        Log.d(TAG, "fetchEvents: ===>>> uri: " + uri);
        String [] columns = new String[] { //columns I want to return
                CalendarContract.Events._ID,
                CalendarContract.Events.CALENDAR_ID, // Calendars._ID
                CalendarContract.Events.ORGANIZER,
                CalendarContract.Events.TITLE,
                CalendarContract.Events.EVENT_LOCATION,
                CalendarContract.Events.DESCRIPTION,
                CalendarContract.Events.DTSTART,
                CalendarContract.Events.DTEND,
        };

        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, Calendar.APRIL, 1);
        APRIL_1_2016 = calendar.getTimeInMillis();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2016, Calendar.MAY, 1);
        MAY_1_2016 = calendar2.getTimeInMillis();

        String filter = CalendarContract.Events.DTSTART + " > ? AND " +
                CalendarContract.Events.DTEND + " < ? AND " + CalendarContract.Events.CALENDAR_ID + " = " + CALENDAR_ID;

        String[] filterArgs = new String[] {String.valueOf(APRIL_1_2016), String.valueOf(MAY_1_2016)};

        String sortOrder = CalendarContract.Events.DTSTART + " DESC LIMIT 100";


        Cursor cursor = mainActivity.getContentResolver().query(
                uri,
                columns,
                filter,
                filterArgs,
                sortOrder
        );

        cursor.moveToFirst();
        //Log.d(TAG, "fetchEvents: ==>>> column Name " + cursor.getString(PROJECTION_EVENT_DESCRIPTION_INDEX));

        listAdapter = new SimpleCursorAdapter(
                mainActivity,
                android.R.layout.simple_expandable_list_item_2,
                cursor,
                new String[] {CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION},
                new int[] {android.R.id.text1, android.R.id.text2},
                0
        );

        vh10.mCalendarListView.setAdapter(listAdapter);

    }

}
