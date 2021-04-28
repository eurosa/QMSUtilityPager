package com.qms.utility;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class General extends Fragment {
    Context context;
    private FragmentToActivity mCallback;

    //**************** General Fragment ******************************************************
    private EditText instEditText, timeDateEditText, bankIdEditText, counterNameEditText,
            tokenSlipBEditText, tokenSlipAEditText,
            tokenSlip9EditText, cTimeEditText, copyNoEditText, totalCounterEditText,
            cntLabelOneEditText,cntLabelTwoEditText,cntLabelThreeEditText,cntLabelFourEditText,
            cntLabelFiveEditText,cntLabelSixEditText,cntLabelSevenEditText,cntLabelEightEditText,
            cntLabelNineEditText,cntLabelTenEditText,cntLabelElevenEditText,cntLabelTweleveEditText,
            cntLabelThirteenEditText,cntLabelFourteenEditText,cntLabelFifteenEditText,cntLabelSixteenEditText;

    private Button sendcntLabelOne,sendCntLabelTwo,sendCntLabelThree,sendCntLabelFour,sendCntLabelFive,
            sendCntLabelSix,sendCntLabelSeven,sendCntLabelEight,sendCntLabelNine,sendCntLabelTen,
            sendCntLabelEleven,sendCntLabelTweleve,sendCntLabelThirteen,sendCntLabelFourteen,sendCntLabelFifteen,sendCntLabelSixteen,
            btnInstitute,btnBankId,sendTimeDate,counterTimeDate,sendTotalCounter,sendCopyNo,sendCTime,
            sendTokenSlip9,sendTokenSlipA,sendTokenSlipB;

    DataModel dataModel;
    //**************** General Fragment End **************************************************
    public General(Context c, DataModel model) {
        // Required empty public constructor
        context = c;
        dataModel = model;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Right", "onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_general, container, false);


       // sendData("Andrews");
        //onRefresh();

        return  rootView;
    }


    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        btnInstitute=rootView.findViewById(R.id.btnInstitute);
        btnBankId=rootView.findViewById(R.id.btnBankId);
        sendTimeDate=rootView.findViewById(R.id.sendTimeDate);
        counterTimeDate=rootView.findViewById(R.id.counterTimeDate);

        sendTotalCounter=rootView.findViewById(R.id.sendTotalCounter);
        sendCopyNo=rootView.findViewById(R.id.sendCopyNo);
        sendCTime=rootView.findViewById(R.id.sendCTime);
        sendTokenSlip9=rootView.findViewById(R.id.sendTokenSlip9);

        sendTokenSlipA=rootView.findViewById(R.id.sendTokenSlipA);
        sendTokenSlipB=rootView.findViewById(R.id.sendTokenSlipB);


        instEditText=rootView.findViewById(R.id.instEditText);
        bankIdEditText=rootView.findViewById(R.id.bankIdEditText);
        timeDateEditText=rootView.findViewById(R.id.timeDateEditText);
        counterNameEditText=rootView.findViewById(R.id.counterNameEditText);

        totalCounterEditText=rootView.findViewById(R.id.totalCounterEditText);
        copyNoEditText=rootView.findViewById(R.id.copyNoEditText);
        cTimeEditText=rootView.findViewById(R.id.cTimeEditText);
        tokenSlip9EditText=rootView.findViewById(R.id.tokenSlip9EditText);

        tokenSlipAEditText=rootView.findViewById(R.id.tokenSlipAEditText);
        tokenSlipBEditText=rootView.findViewById(R.id.tokenSlipBEditText);

        instEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setInstName(instEditText.getText().toString());
               // if(s.length() != 0)
                 //   instEditText.setText("");

            }

        });


        bankIdEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setBankId(bankIdEditText.getText().toString());
                // if(s.length() != 0)
                //   instEditText.setText("");

            }

        });



        timeDateEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setTimeDate(timeDateEditText.getText().toString());
                // if(s.length() != 0)
                //   instEditText.setText("");

            }

        });

        cTimeEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setcTime(cTimeEditText.getText().toString());
                // if(s.length() != 0)
                //   instEditText.setText("");

            }

        });

        counterNameEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setCounterName(counterNameEditText.getText().toString());
                // if(s.length() != 0)
                //   instEditText.setText("");

            }

        });


        totalCounterEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setTotalCounter(totalCounterEditText.getText().toString());
                // if(s.length() != 0)
                //   instEditText.setText("");

            }

        });


        copyNoEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setCopyNo(copyNoEditText.getText().toString());
                // if(s.length() != 0)
                //   instEditText.setText("");

            }

        });


        tokenSlip9EditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setTokenSlip9(tokenSlip9EditText.getText().toString());
                // if(s.length() != 0)
                //   instEditText.setText("");

            }

        });


        tokenSlipAEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setTokenSlipA(tokenSlipAEditText.getText().toString());
                // if(s.length() != 0)
                //   instEditText.setText("");

            }

        });


        tokenSlipBEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataModel.setTokenSlipB(tokenSlipBEditText.getText().toString());
                // if(s.length() != 0)
                //   instEditText.setText("");

            }

        });




        btnInstitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instEditTextData = "$BnkL"+instEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(instEditTextData);
                Toast.makeText(getContext(), "Institute Name has been successfully sent", Toast.LENGTH_SHORT).show();

            }
        });
        btnBankId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bankIdEditTextData = "$BnkL"+bankIdEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(bankIdEditTextData);
                Toast.makeText(getContext(), "Bank id has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeDateEditTextData = "$TIME"+timeDateEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(timeDateEditTextData);
                Toast.makeText(getContext(), "Time and Date has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        counterTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String counterNameEditTextData = "$CTID"+counterNameEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(counterNameEditTextData);
                Toast.makeText(getContext(), "Counter Time and Date has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        totalCounterEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String totalCounterEditTextData = "$CNTR"+totalCounterEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(totalCounterEditTextData);
                Toast.makeText(getContext(), "Total Counter has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendCopyNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copyNoEditTextData = "$LaC3"+copyNoEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData("uuu");
                Toast.makeText(getContext(), "sendCopyNo", Toast.LENGTH_SHORT).show();
            }
        });
        sendCTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cTimeEditTextData = "$CLTM"+cTimeEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cTimeEditTextData);
                Toast.makeText(getContext(), "Time has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendTokenSlip9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenSlip9EditTextData = "$TSL9"+tokenSlip9EditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(tokenSlip9EditTextData);
                Toast.makeText(getContext(), "Token Slip 9 has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendTokenSlipA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenSlipAEditTextData = "$TSLA"+tokenSlipAEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(tokenSlipAEditTextData);
                Toast.makeText(getContext(), "Token Slip A has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendTokenSlipB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenSlipBEditTextData = "$TSLB"+tokenSlipBEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(tokenSlipBEditTextData);
                Toast.makeText(getContext(), "Token Slip B has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });

        // Toast.makeText(getContext(), "Instucfv "+instEditText.getText().toString(), Toast.LENGTH_SHORT).show();




        /***************************************************************************************************
        *
         * **  Activity method call from fragment
        *
        * **************************************************************************************************/
        //((DeviceList)getActivity()).dispatchInformations("test");
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    public void onRefresh() {
        Toast.makeText(context, "Fragment : Refresh called."+dataModel.getInstName(),
                Toast.LENGTH_SHORT).show();
    }
    private void sendData(String comm)
    {

        mCallback.communicate(comm);

    }





   /* public  void getGeneralData(){
        dataModel.setInstName(instEditText.getText().toString());
        dataModel.setBankId(bankIdEditText.getText().toString());
        dataModel.setcTime(cTimeEditText.getText().toString());
        dataModel.setTotalCounter(totalCounterEditText.getText().toString());
        dataModel.setTimeDate(timeDateEditText.getText().toString());
        dataModel.setCopyNo(copyNoEditText.getText().toString());
        dataModel.setTokenSlip9(tokenSlip9EditText.getText().toString());
        dataModel.setTokenSlipA(tokenSlipAEditText.getText().toString());
        dataModel.setTokenSlipB(tokenSlipBEditText.getText().toString());
        dataModel.setCounterName(counterNameEditText.getText().toString());


    }*/
}