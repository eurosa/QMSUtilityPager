package com.qms.utility;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class General extends Fragment {
    Context context;
    public General(Context c) {
        // Required empty public constructor
        context = c;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_general, container, false);
        Button buttonInFragment1 = rootView.findViewById(R.id.btnInstitute);
        buttonInFragment1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((DeviceList)getActivity()).sendData("uuu");
                Toast.makeText(getContext(), "button in fragment 1", Toast.LENGTH_SHORT).show();

            }
        });
        return  rootView;
    }
}