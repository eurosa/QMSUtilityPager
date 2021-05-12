package com.qms.utility;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class SpinnerDialog extends AlertDialog {
    public ArrayList<String> mList;
    private Context mContext;
    public Spinner mSpinner;
    public AdapterView<?> mParent;
    public ArrayAdapter<String> adapter;
    public DataModel dataModel;
    private EditText record_name;

    public interface DialogListener {
        public void ready(String n);
        public void cancelled();
    }

    private DialogListener mReadyListener;

    public SpinnerDialog(Context context, ArrayList<String> list,DataModel model, DialogListener readyListener) {
        super(context);
        mReadyListener = readyListener;
        mContext = context;
        mList = new ArrayList<>();
        mList = list;
        dataModel = model;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.spinner_dialog);
        mSpinner = findViewById (R.id.dialog_spinner);
        adapter = new ArrayAdapter<String> (mContext, R.layout.spinner_item, mList);
        record_name = findViewById(R.id.record_name);
        // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        /*********************************************************************************************
        * Initial Select item position using dataModel
        *
        * *******************************************************************************************/
        mSpinner.setSelection(dataModel.getSelectionPosition());

        // ******************************************************************************************


        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

              //  DataModel qmsUtility = (DataModel) parent.getSelectedItem();
               // Toast.makeText(getContext(), "Country ID: "+record_name.getText().toString(), Toast.LENGTH_SHORT).show();
                mParent = parent;
                if (position == 0){
                    record_name.setText("");
                }else {
                    record_name.setText(parent.getItemAtPosition(position).toString());
                }
                // record_name.setText(dataModel.getRecordName());
                // mSpinner.getSelectedItem();
            // Toast.makeText(getContext(), "Country ID: "+parent.getItemAtPosition(position).toString()+",  Country Name : ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
             //   Toast.makeText(getContext(), "Nothing ID: "+record_name.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // mSpinner.setSelection(adapter.getPosition(myItem));//Optional to set the selected item.

        Button buttonOK =  findViewById(R.id.dialogOK);
        Button buttonCancel =  findViewById(R.id.dialogCancel);
        buttonOK.setOnClickListener(new android.view.View.OnClickListener(){
            public void onClick(View v) {
                dataModel.setRecordName(record_name.getText().toString());
                int n = mSpinner.getSelectedItemPosition();
                // Toast.makeText(getContext(), "Record Name: "+mParent.getItemAtPosition(n).toString(), Toast.LENGTH_SHORT).show();
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

   /* private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }*/



}