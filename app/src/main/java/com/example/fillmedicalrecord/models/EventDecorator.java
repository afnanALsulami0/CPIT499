package com.example.fillmedicalrecord.models;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.fillmedicalrecord.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final HashSet<CalendarDay> dates;
    private Context activity;
    public EventDecorator(int color, Collection<CalendarDay> dates, Context activity) {
        this.color = color;
        this.dates = new HashSet<>(dates);
        this.activity = activity;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
        view.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.selc));

    }
}