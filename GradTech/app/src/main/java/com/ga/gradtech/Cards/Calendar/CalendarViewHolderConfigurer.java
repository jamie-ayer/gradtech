package com.ga.gradtech.Cards.Calendar;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.ga.gradtech.Cards.Facebook.FacebookCardViewHolder;

/**
 * Created by samsiu on 4/20/16.
 */
public class CalendarViewHolderConfigurer {
    private static final String TAG = CalendarViewHolderConfigurer.class.getCanonicalName();

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;


    CalendarCardViewHolder vh10;
    int position;
    Activity mainActivity;

    public CalendarViewHolderConfigurer(CalendarCardViewHolder vh10, int position, Activity mainActivity) {
        this.vh10 = vh10;
        this.position = position;
        this.mainActivity = mainActivity;
    }


    // The method returns all the calendars associated with your email. The property ID is a
    // calendar id. You have to choose one type of calendar you would love to work on in the
    // methods below
    public void fetchCalendars() {
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] EVENT_PROJECTION = new String[]{
                CalendarContract.Calendars._ID,                           // 0
                CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
                CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
        };

        // Run query
        Cursor cur = null;
        ContentResolver cr = mainActivity.getContentResolver();
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{"samsiu6@gmail.com", "com.google",
                "samsiu6@gmail.com"};
        // Submit the query and get a Cursor object back.
        //   mainActivity.checkPermission(Manifest.permission.READ_CALENDAR);

        if (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);




//        Cursor cursor = mainActivity.getContentResolver().query(
//                uri,
//                columns,
//                CalendarContract.Calendars.ACCOUNT_NAME + " = ?",
//                //TODO: insert your email address that will be associated with the calendar
//                new String[] {"samsiu6@gmail.com"},
//                null
//        );
//
//        while (cursor.moveToNext()) {
//            long id = cursor.getLong(cursor.getColumnIndex(CalendarContract.Calendars._ID));
//            String accountName = cursor.getString(1);
//            String displayName = cursor.getString(2);
//            String owner = cursor.getString(3);
//            Log.d("ContentProvider", "ID: " + id +
//                            ", account: " + accountName +
//                            ", displayName: " + displayName +
//                            ", owner: " + owner
//            );
//        }
    }

}
