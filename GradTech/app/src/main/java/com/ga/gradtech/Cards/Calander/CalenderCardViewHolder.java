package com.ga.gradtech.Cards.Calander;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CalendarView;

import java.util.Calendar;

import com.ga.gradtech.R;

/**
 * Created by JamieAyer on 4/20/16.
 */
public class CalenderCardViewHolder extends RecyclerView.ViewHolder {

    public CalendarView mCalendar;

    public CalenderCardViewHolder(View view) {
        super(view);
        this.mCalendar = (CalendarView) view.findViewById(R.id.calendar);
    }
}
