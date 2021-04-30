package com.qms.utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SpinnerDialog extends Dialog {
    private ArrayList<DataModel> mList;
    private Context mContext;
    private Spinner mSpinner;

    public interface DialogListener {
        public void ready(int n);
        public void cancelled();
    }

    private DialogListener mReadyListener;

    public SpinnerDialog(Context context, ArrayList<DataModel> list, DialogListener readyListener) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        mList = new ArrayList<DataModel>();
        mList = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.spinner_dialog);
        mSpinner = findViewById (R.id.dialog_spinner);
        ArrayAdapter<DataModel> adapter = new ArrayAdapter<DataModel> (mContext, android.R.layout.simple_spinner_dropdown_item, mList);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DataModel qmsUtility = (DataModel) parent.getSelectedItem();
                Toast.makeText(getContext(), "Country ID: "+qmsUtility.getID()+",  Country Name : "+qmsUtility.getInstName(), Toast.LENGTH_SHORT).show();
                // mSpinner.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // mSpinner.setSelection(adapter.getPosition(myItem));//Optional to set the selected item.

        Button buttonOK =  findViewById(R.id.dialogOK);
        Button buttonCancel =  findViewById(R.id.dialogCancel);
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                int n = mSpinner.getSelectedItemPosition();
                mReadyListener.ready(n);
                SpinnerDialog.this.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                mReadyListener.cancelled();
                SpinnerDialog.this.dismiss();
            }
        });


    }



}