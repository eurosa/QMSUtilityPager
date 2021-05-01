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

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class SpinnerDialog extends AlertDialog {
    private ArrayList<String> mList;
    private Context mContext;
    private Spinner mSpinner;
    private AdapterView<?> mParent;

    public interface DialogListener {
        public void ready(String n);
        public void cancelled();
    }

    private DialogListener mReadyListener;

    public SpinnerDialog(Context context, ArrayList<String> list, DialogListener readyListener) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        mList = new ArrayList<>();
        mList = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.spinner_dialog);
        mSpinner = findViewById (R.id.dialog_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<> (mContext, android.R.layout.simple_spinner_dropdown_item, mList);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner.setAdapter(adapter);


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              //  DataModel qmsUtility = (DataModel) parent.getSelectedItem();
               // Toast.makeText(getContext(), "Country ID: "+qmsUtility.getID()+",  Country Name : "+qmsUtility.getInstName(), Toast.LENGTH_SHORT).show();
                mParent = parent;
                // mSpinner.getSelectedItem();
            // Toast.makeText(getContext(), "Country ID: "+parent.getItemAtPosition(position).toString()+",  Country Name : ", Toast.LENGTH_SHORT).show();
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

                mReadyListener.ready(mParent.getItemAtPosition(n).toString());
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