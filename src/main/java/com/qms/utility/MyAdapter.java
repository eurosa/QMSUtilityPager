package com.qms.utility;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    DataModel model;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs, DataModel dataModel) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        model = dataModel;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                General generalFragment = new General(context, model);
                return generalFragment;
            case 1:
                CounterLabel counterLabelFragment = new CounterLabel(model);
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