package com.qms.utility;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                General generalFragment = new General();
                return generalFragment;
            case 1:
                CounterLabel counterLabelFragment = new CounterLabel();
                return counterLabelFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}