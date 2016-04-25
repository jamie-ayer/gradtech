package com.ga.gradtech.Cards.CalendarProvider;

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
import android.widget.CalendarView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Contains all methods for CalendarProvider Card
 * Created by samsiu on 4/20/16.
 */
public class CalendarViewHolderConfigurer {
    private static final String TAG = CalendarViewHolderConfigurer.class.getCanonicalName();

    private static String mUserCalendarId;

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

    protected CalendarProviderCardViewHolder mVh7;
    protected Activity mMainActivity;

    /**
     * Constructore for CalendarProvider Configurer
     * @param vh7
     * @param mainActivity
     */
    public CalendarViewHolderConfigurer(CalendarProviderCardViewHolder vh7, Activity mainActivity) {
        this.mVh7 = vh7;
        this.mMainActivity = mainActivity;
    }

    /**
     * Initializes the CalendarProvider
     */
    public void initCalendarProvider(){

        mUserCalendarId = fetchCalendars();
        Log.d(TAG, "onBindViewHolder: ===>>>>> UserCalendarId: " + mUserCalendarId);

        setCalendarTouchListener();
        setShowEventButtonListener();
        setAddEventButtonListener();

        setDeleteButtonEventListener();
        setShareCalendarButtonListener();

    }

    /**
     * Gets Title from EditText and returns specified default if empty
     * @param defaultTitle
     * @return String title
     */
    private String getCalendarEditTextTitle(String defaultTitle){
        String title = mVh7.mCalendarTitleEditText.getText().toString();
        title = title.isEmpty() ? defaultTitle : title;
        return title;
    }

    /**
     * Gets Location from EditText and returns specified default if empty
     * @param defaultLocation
     * @return String location
     */
    private String getCalendarLocationEditText(String defaultLocation){
        String location = mVh7.mCalendarLocationEditText.getText().toString();
        location = location.isEmpty() ? defaultLocation : location;
        return location;
    }

    /**
     * Gets Description from EditText and returns specified default if empty
     * @param defaultDescription
     * @return String description
     */
    private String getCalendarDescriptionEditText(String defaultDescription){
        String description = mVh7.mCalendarDescriptionEditText.getText().toString();
        description = description.isEmpty() ? defaultDescription : description;
        return description;
    }

    /**
     * Gets Hour from EditText and returns specified default if empty
     * @param defaultHour
     * @return String hourInput
     */
    private String getCalendarHourEditText(String defaultHour){
        String hourInput = mVh7.mCalendarTimeEditText.getText().toString();
        hourInput = hourInput.isEmpty() ? defaultHour : hourInput;
        return hourInput;
    }

    /**
     * Returns a string in the format MM/DD/YYYY
     * @return String today
     */
    private String getTodayString(){
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DATE);
        String today = month+"/"+day+"/"+year;
        return today;
    }

    /**
     * Parses date EditText to milliseconds
     * @return long startMillis
     */
    private long parseInputDateToMilliseconds(){
        String hourInput = getCalendarHourEditText("12");
        String today = getTodayString();

        //Parse Date Input
        String date = mVh7.mCalendarDateEditText.getText().toString();
        date = date.isEmpty() ? today : date;
        String[] time = date.split("/");
        int month = Integer.parseInt(time[0]);
        int day = Integer.parseInt(time[1]);
        int year = Integer.parseInt(time[2]);
        int hour = Integer.parseInt(hourInput);

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(year, month, day, hour, 0);
        long startMillis = beginTime.getTimeInMillis();
        return startMillis;
    }


    /**
     * Returns the calendarId associated with user's email
     * @return String userCalendarId
     */
    private String fetchCalendars() {
        Log.d(TAG, "fetchCalendars: ===>> FETCH CALENDARS");
        String userCalendarId;
        String userEmail = "samsiu6@gmail.com";

        // Query Parameters
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] CALENDAR_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
        };
        ContentResolver cr = mMainActivity.getContentResolver();
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.CALENDAR_DISPLAY_NAME + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[] {userEmail, userEmail, userEmail};

        // Run Query
        Cursor cur = cr.query(uri, CALENDAR_PROJECTION, selection, selectionArgs, null);

        // Get the field values
        cur.moveToFirst();
        if(cur.getCount() == 0){
            userCalendarId = "1"; // Set 1 as default calendar if no calendars are found; Temp fix;
            Toast.makeText(mMainActivity, "Please Sync Your Google Account", Toast.LENGTH_LONG).show();
        }else{
            String calendarId = cur.getString(PROJECTION_ID_INDEX);
            String displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            String accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            String ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            Log.d(TAG, "onCreate: ===>>> CalendarID: " + calendarId);
            Log.d(TAG, "onCreate: ===>>> DisplayName: " + displayName);
            Log.d(TAG, "onCreate: ===>>> AccountName: " + accountName);
            Log.d(TAG, "onCreate: ===>>> OwnerName: " + ownerName);
            Log.d(TAG, "onCreate: \n");
            cur.close();
            userCalendarId = calendarId;
        }
        return userCalendarId;
    }


    /**
     * Set listener on Share Button to share events on accessible apps
     */
    private void setShareCalendarButtonListener(){
        mVh7.mCalenderShareEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get String input and set default if empty
                String title = getCalendarEditTextTitle("Title");
                String location = getCalendarLocationEditText("Location");
                String description = getCalendarDescriptionEditText("Description");

                // Creates intent showing sharing options
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, description);
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title + ": " + location);
                mMainActivity.startActivity(Intent.createChooser(intent, "Share to"));
            }
        });
    }

    /**
     * Set listener to delete event button
     */
    private void setDeleteButtonEventListener(){
        mVh7.mCalendarDeleteEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEventByTitle();
                fetchEvents();
            }
        });
    }

    /**
     * Deletes the events based on text inside Input Title
     */
    private void deleteEventByTitle(){
        // Setup Query Parameters
        /*
        //Code for deleting by eventId
        String WHERE = CalendarContract.Events._ID + " = ? ";
        String[] args = new String[]{"280"};
         */
        String WHERE = CalendarContract.Events.TITLE + " = ? ";
        String removeTitle = mVh7.mCalendarTitleEditText.getText().toString();
        removeTitle = removeTitle.isEmpty() ? "Title" : removeTitle;
        String[] args = new String[]{removeTitle};
        Uri uri = CalendarContract.Events.CONTENT_URI;

        // Delete Rows
        int rowsDeleted = mMainActivity.getContentResolver().delete(uri, WHERE, args);
        if(rowsDeleted == 0){
            Log.d(TAG, "onClick: ROW NOT DELETED");
        }
        Log.d(TAG, "onClick:==>>> Delete Button Clicked " + uri);
    }


    /**
     * Set listener for Add Event Button
     */
    private void setAddEventButtonListener(){
        mVh7.mCalendarAddEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertEventInCalendar();
                fetchEvents();
            }
        });
    }

    /**
     * Insert Event into Calendar
     */
    private void insertEventInCalendar() {
        Log.d(TAG, "insertEventInCalendar: ===>>>> Calendar Insert");

        String title = getCalendarEditTextTitle("Title");
        String location = getCalendarLocationEditText("Location");
        String description = getCalendarDescriptionEditText("Description");

        long startMillis = parseInputDateToMilliseconds();
        long endMillis = startMillis + 3600000; //Add 1 hour to event time

        // Query Parameters
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, title);
        values.put(CalendarContract.Events.DESCRIPTION, description);
        values.put(CalendarContract.Events.CALENDAR_ID, mUserCalendarId);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, location);

        // Run Query
        Uri uri = mMainActivity.getContentResolver().insert(CalendarContract.Events.CONTENT_URI, values);
        Log.d(TAG, "insertEventInCalendar: Insert Event Clicked ===>>>" + uri);

        // returns the ID of the row to be used as eventId
        long eventId = Long.parseLong(uri.getLastPathSegment());
        Log.d(TAG, "add id = " + eventId);
    }


    /**
     * Change Input of Date EditText when Date in datepicker selected
     */
    private void setCalendarTouchListener(){
        mVh7.mCalendarCaledarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mVh7.mCalendarDateEditText.setText(month + "/" + dayOfMonth + "/" + year);
            }
        });
    }

    /**
     * Set Listener on Show Events button to display current events
     */
    private void setShowEventButtonListener(){
        mVh7.mCalendarGetEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchEvents();
            }
        });
    }

    /**
     * Query all events in users Calendar
     */
    private void fetchEvents() {
        //Query Parameters
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

        // Begin and end times are two weeks before and after today
        long today = parseInputDateToMilliseconds();
        long twoWeeksBefore = today - 1209600000;
        long twoWeeksAfter = today + 1209600000;

        // Query string and arguments
        String filter = CalendarContract.Events.DTSTART + " > ? AND " +
                CalendarContract.Events.DTEND + " < ? AND " +
                CalendarContract.Events.CALENDAR_ID + " = " + mUserCalendarId;
        String[] filterArgs = new String[] {String.valueOf(twoWeeksBefore), String.valueOf(twoWeeksAfter)};
        String sortOrder = CalendarContract.Events.DTSTART + " DESC LIMIT 4";

        //Run Query
        Cursor cursor = mMainActivity.getContentResolver().query(
                uri,
                columns,
                filter,
                filterArgs,
                sortOrder
        );

        // Display Results
        cursor.moveToFirst();
        ListAdapter listAdapter = new SimpleCursorAdapter(
                mMainActivity,
                android.R.layout.simple_expandable_list_item_2,
                cursor,
                new String[] {CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION },
                new int[] {android.R.id.text1, android.R.id.text2},
                0
        );
        mVh7.mCalendarListView.setAdapter(listAdapter);
    }


}
