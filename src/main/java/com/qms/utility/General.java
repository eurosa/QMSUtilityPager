package com.qms.utility;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class General extends Fragment {
    Context context;
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

    //**************** General Fragment End **************************************************
    public General(Context c) {
        // Required empty public constructor
        context = c;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView  = inflater.inflate(R.layout.fragment_general, container, false);
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



        btnInstitute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String instEditTextData = "$BnkL"+instEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(instEditTextData);
                Toast.makeText(getContext(), "button in fragment 1", Toast.LENGTH_SHORT).show();
            }
        });
        btnBankId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bankIdEditTextData = "$BnkL"+bankIdEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(bankIdEditTextData);
                Toast.makeText(getContext(), "button bank id", Toast.LENGTH_SHORT).show();
            }
        });
        sendTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String timeDateEditTextData = "$TIME"+timeDateEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(timeDateEditTextData);
                Toast.makeText(getContext(), "Time and Date", Toast.LENGTH_SHORT).show();
            }
        });
        counterTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String counterNameEditTextData = "$CTID"+counterNameEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(counterNameEditTextData);
                Toast.makeText(getContext(), "Counter Time and Date", Toast.LENGTH_SHORT).show();
            }
        });
        totalCounterEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String totalCounterEditTextData = "$CNTR"+totalCounterEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(totalCounterEditTextData);
                Toast.makeText(getContext(), "Send Total Counter", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), "sendCTime", Toast.LENGTH_SHORT).show();
            }
        });
        sendTokenSlip9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenSlip9EditTextData = "$TSL9"+tokenSlip9EditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(tokenSlip9EditTextData);
                Toast.makeText(getContext(), "button in fragment 1", Toast.LENGTH_SHORT).show();
            }
        });
        sendTokenSlipA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenSlipAEditTextData = "$TSLA"+tokenSlipAEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(tokenSlipAEditTextData);
                Toast.makeText(getContext(), "button in fragment 1", Toast.LENGTH_SHORT).show();
            }
        });
        sendTokenSlipB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenSlipBEditTextData = "$TSLB"+tokenSlipBEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(tokenSlipBEditTextData);
                Toast.makeText(getContext(), "button in fragment 1", Toast.LENGTH_SHORT).show();
            }
        });

        return  rootView;
    }
}