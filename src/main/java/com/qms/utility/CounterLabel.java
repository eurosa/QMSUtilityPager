package com.qms.utility;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class CounterLabel extends Fragment {

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
    public CounterLabel(DataModel model) {
        // Required empty public constructor
        dataModel = model;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_counter_label, container, false);
        sendcntLabelOne=rootView.findViewById(R.id.sendcntLabelOne);
        // sendcntLabelOne.setOnClickListener(this);
        sendCntLabelTwo=rootView.findViewById(R.id.sendCntLabelTwo);
        sendCntLabelThree=rootView.findViewById(R.id.sendCntLabelThree);
        sendCntLabelFour=rootView.findViewById(R.id.sendCntLabelFour);

        sendCntLabelFive=rootView.findViewById(R.id.sendCntLabelFive);
        sendCntLabelSix=rootView.findViewById(R.id.sendCntLabelSix);
        sendCntLabelSeven=rootView.findViewById(R.id.sendCntLabelSeven);
        sendCntLabelEight=rootView.findViewById(R.id.sendCntLabelEight);

        sendCntLabelNine=rootView.findViewById(R.id.sendCntLabelNine);
        sendCntLabelTen=rootView.findViewById(R.id.sendCntLabelTen);

        sendCntLabelEleven=rootView.findViewById(R.id.sendCntLabelEleven);
        sendCntLabelTweleve=rootView.findViewById(R.id.sendCntLabelTweleve);
        sendCntLabelThirteen=rootView.findViewById(R.id.sendCntLabelThirteen);
        sendCntLabelFourteen=rootView.findViewById(R.id.sendCntLabelFourteen);

        sendCntLabelFifteen=rootView.findViewById(R.id.sendCntLabelFifteen);
        sendCntLabelSixteen=rootView.findViewById(R.id.sendCntLabelSixteen);

        // ++++++++++++++++++++++++++++++Counter Label EditText++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        cntLabelOneEditText=rootView.findViewById(R.id.cntLabelOneEditText);
        cntLabelTwoEditText=rootView.findViewById(R.id.cntLabelTwoEditText);
        cntLabelThreeEditText=rootView.findViewById(R.id.cntLabelThreeEditText);
        cntLabelFourEditText=rootView.findViewById(R.id.cntLabelFourEditText);

        cntLabelFiveEditText=rootView.findViewById(R.id.cntLabelFiveEditText);
        cntLabelSixEditText=rootView.findViewById(R.id.cntLabelSixEditText);
        cntLabelSevenEditText=rootView.findViewById(R.id.cntLabelSevenEditText);
        cntLabelEightEditText=rootView.findViewById(R.id.cntLabelEightEditText);

        cntLabelNineEditText=rootView.findViewById(R.id.cntLabelNineEditText);
        cntLabelTenEditText=rootView.findViewById(R.id.cntLabelTenEditText);

        cntLabelElevenEditText=rootView.findViewById(R.id.cntLabelElevenEditText);
        cntLabelTweleveEditText=rootView.findViewById(R.id.cntLabelTweleveEditText);
        cntLabelThirteenEditText=rootView.findViewById(R.id.cntLabelThirteenEditText);
        cntLabelFourteenEditText=rootView.findViewById(R.id.cntLabelFourteenEditText);

        cntLabelFifteenEditText=rootView.findViewById(R.id.cntLabelFifteenEditText);
        cntLabelSixteenEditText=rootView.findViewById(R.id.cntLabelSixteenEditText);

        sendcntLabelOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelOneEditTextData = "$LaC1"+cntLabelOneEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelOneEditTextData);
                Toast.makeText(getContext(), "sendcntLabelOne", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelTwoEditTextData = "$LaC2"+cntLabelTwoEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelTwoEditTextData);
                Toast.makeText(getContext(), "sendCntLabelTwo", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelThreeEditTextData = "$LaC3"+cntLabelThreeEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelThreeEditTextData);
                Toast.makeText(getContext(), "sendCntLabelThree", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelFourEditTextData = "$LaC4"+cntLabelFourEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelFourEditTextData);
                Toast.makeText(getContext(), "sendCntLabelFour", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelFiveEditTextData = "LaC5"+cntLabelFiveEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelFiveEditTextData);
                Toast.makeText(getContext(), "sendCntLabelFive", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelSixEditTextData = "$LaC6"+cntLabelSixEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelSixEditTextData);
                Toast.makeText(getContext(), "sendCntLabelSix", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelSeven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelSevenEditTextData = "LaC7"+cntLabelSevenEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelSevenEditTextData);
                Toast.makeText(getContext(), "sendCntLabelSeven", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelEight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelEightEditTextData = "$LaC8"+cntLabelEightEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelEightEditTextData);
                Toast.makeText(getContext(), "sendCntLabelEight", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelNine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelNineEditTextData = "$LaC9"+cntLabelNineEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelNineEditTextData);
                Toast.makeText(getContext(), "sendCntLabelNine", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelTenEditTextData = "$LaCA"+cntLabelTenEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelTenEditTextData);
                Toast.makeText(getContext(), "sendCntLabelTen", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelEleven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelEleveneEditTextData = "$LaCB"+cntLabelElevenEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelEleveneEditTextData);
                Toast.makeText(getContext(), "sendCntLabelEleven", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelTweleve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelTweleveEditTextData = "$LaCC"+cntLabelTweleveEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelTweleveEditTextData);
                Toast.makeText(getContext(), "sendCntLabelTweleve", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelThirteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelThirteenEditTextData = "$LaCD"+cntLabelThirteenEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelThirteenEditTextData);
                Toast.makeText(getContext(), "sendCntLabelThirteen", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelFourteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelFourEditTextData = "$LaCE"+cntLabelFourEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelFourEditTextData);
                Toast.makeText(getContext(), "sendCntLabelFourteen", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelFifteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelFifteenEditTextData = "$LaCF"+cntLabelFifteenEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelFifteenEditTextData);
                Toast.makeText(getContext(), "bsendCntLabelFifteen", Toast.LENGTH_SHORT).show();
            }
        });
        sendCntLabelSixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntLabelSixEditTextData = "$LaCG"+cntLabelSixEditText.getText().toString()+";";
                ((DeviceList)getActivity()).sendData(cntLabelSixEditTextData);
                Toast.makeText(getContext(), "sendCntLabelSixteen", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}