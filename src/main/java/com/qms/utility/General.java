package com.qms.utility;

import android.content.Context;
import android.graphics.Typeface;
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
    private View rootView;
    private boolean center_align_check = true;;

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
        rootView  = inflater.inflate(R.layout.fragment_general, container, false);

        btnInstitute=rootView.getRootView().findViewById(R.id.btnInstitute);
        btnBankId=rootView.findViewById(R.id.btnBankId);
        sendTimeDate=rootView.findViewById(R.id.sendTimeDate);
        counterTimeDate=rootView.findViewById(R.id.counterTimeDate);

        sendTotalCounter=rootView.findViewById(R.id.sendTotalCounter);
        sendCopyNo=rootView.findViewById(R.id.sendCopyNo);
        sendCTime=rootView.findViewById(R.id.sendCTime);
        sendTokenSlip9=rootView.findViewById(R.id.sendTokenSlip9);

        sendTokenSlipA=rootView.findViewById(R.id.sendTokenSlipA);
        sendTokenSlipB=rootView.findViewById(R.id.sendTokenSlipB);


        instEditText=rootView.getRootView().findViewById(R.id.instEditText);
        bankIdEditText=rootView.findViewById(R.id.bankIdEditText);
        timeDateEditText=rootView.findViewById(R.id.timeDateEditText);
        counterNameEditText=rootView.findViewById(R.id.counterNameEditText);

        totalCounterEditText=rootView.findViewById(R.id.totalCounterEditText);
        copyNoEditText=rootView.findViewById(R.id.copyNoEditText);
        cTimeEditText=rootView.findViewById(R.id.cTimeEditText);
        tokenSlip9EditText=rootView.findViewById(R.id.tokenSlip9EditText);

        tokenSlipAEditText=rootView.findViewById(R.id.tokenSlipAEditText);
        tokenSlipBEditText=rootView.findViewById(R.id.tokenSlipBEditText);
        // sendData("Andrews");
        // onRefresh();

        return  rootView;
    }


    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        textFontChange(instEditText);
        textFontChange(bankIdEditText);
        textFontChange(timeDateEditText);
        textFontChange(cTimeEditText);
        textFontChange(counterNameEditText);
        textFontChange(totalCounterEditText);
        textFontChange(copyNoEditText);
        textFontChange(tokenSlip9EditText);
        textFontChange(tokenSlipAEditText);
        textFontChange(tokenSlipBEditText);
        btnBankId.setEnabled(false);
        sendTimeDate.setEnabled(false);
        sendCopyNo.setEnabled(false);
        sendCTime.setEnabled(false);
        sendTotalCounter.setEnabled(false);

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

                if (bankIdEditText.getText().length() != 0)
                {
                    if (bankIdEditText.getText().length() == 2)
                    {
                        //   MessageBox.Show("The maximum amount in text box cant be more than 2");

                        btnBankId.setEnabled(true);

                    }
                    else
                    {

                        btnBankId.setEnabled(false);
                    }
                }
                else
                {
                    btnBankId.setEnabled(false);
                }

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

                if (timeDateEditText.getText().length() != 0)
                {
                    if (timeDateEditText.getText().length() == 12)
                    {
                        //   MessageBox.Show("The maximum amount in text box cant be more than 2");

                        sendTimeDate.setEnabled(true);

                    }
                    else
                    {

                        sendTimeDate.setEnabled(false);
                    }
                }
                else
                {
                    sendTimeDate.setEnabled(false);
                }

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

                if (cTimeEditText.getText().length() != 0)
                {
                    if (cTimeEditText.getText().length() == 4)
                    {
                        //   MessageBox.Show("The maximum amount in text box cant be more than 2");

                        sendCTime.setEnabled(true);

                    }
                    else
                    {

                        sendCTime.setEnabled(false);
                    }
                }
                else
                {
                    sendCTime.setEnabled(false);
                }

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

                if (totalCounterEditText.getText().length() != 0)
                {
                    if (totalCounterEditText.getText().length() == 4)
                    {
                        //   MessageBox.Show("The maximum amount in text box cant be more than 2");

                        sendTotalCounter.setEnabled(true);

                    }
                    else
                    {

                        sendTotalCounter.setEnabled(false);
                    }
                }
                else
                {
                    sendTotalCounter.setEnabled(false);
                }

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

                if (copyNoEditText.getText().length() != 0)
                {
                    if (copyNoEditText.getText().length() == 1)
                    {
                        //   MessageBox.Show("The maximum amount in text box cant be more than 2");

                        sendCopyNo.setEnabled(true);

                    }
                    else
                    {

                        sendCopyNo.setEnabled(false);
                    }
                }
                else
                {
                    sendCopyNo.setEnabled(false);
                }

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
               String instData  = fixedLengthString(instEditText.getText().toString(), 28);

                String instEditTextData = "$BnkL"+instData+";";
                ((DeviceList)getActivity()).sendData(instEditTextData);

            }
        });
        btnBankId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bankID  = fixedLengthString(bankIdEditText.getText().toString(), 2);
                String bankIdEditTextData = "$BANK"+bankID+";";
                ((DeviceList)getActivity()).sendData(bankIdEditTextData);
                //Toast.makeText(getContext(), "Bank id has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                center_align_check = false;
                String timeDate  = fixedLengthString(timeDateEditText.getText().toString(), 12);
                String timeDateEditTextData = "$TIME"+timeDate.toString()+";";
                ((DeviceList)getActivity()).sendData(timeDateEditTextData);
                center_align_check = true;
                //Toast.makeText(getContext(), "Time and Date has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        counterTimeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String counterName  = fixedLengthString(counterNameEditText.getText().toString(), 7);
                String counterNameEditTextData = "$CTID"+counterName+";";
                ((DeviceList)getActivity()).sendData(counterNameEditTextData);
                //Toast.makeText(getContext(), "Counter Time and Date has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        totalCounterEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String totalCounter  = fixedLengthString(totalCounterEditText.getText().toString(), 4);
                String totalCounterEditTextData = "$CONTR"+totalCounter+";";
                ((DeviceList)getActivity()).sendData(totalCounterEditTextData);
                //Toast.makeText(getContext(), "Total Counter has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendCopyNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String copyNo  = fixedLengthString(copyNoEditText.getText().toString(), 1);
                String copyNoEditTextData = "$COPY"+copyNo+";";
                ((DeviceList)getActivity()).sendData(copyNoEditTextData);
                //Toast.makeText(getContext(), "sendCopyNo", Toast.LENGTH_SHORT).show();
            }
        });
        sendCTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cTime  = fixedLengthString(cTimeEditText.getText().toString(), 4);
                String cTimeEditTextData = "$CLTM"+cTime+";";
                ((DeviceList)getActivity()).sendData(cTimeEditTextData);
                //Toast.makeText(getContext(), "Time has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendTokenSlip9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenSlip9  = fixedLengthString(tokenSlip9EditText.getText().toString(), 28);
                String tokenSlip9EditTextData = "$TSL9"+tokenSlip9+";";
                ((DeviceList)getActivity()).sendData(tokenSlip9EditTextData);
                //Toast.makeText(getContext(), "Token Slip 9 has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendTokenSlipA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenSlipA  = fixedLengthString(tokenSlipAEditText.getText().toString(), 28);
                String tokenSlipAEditTextData = "$TSLA"+tokenSlipA+";";
                ((DeviceList)getActivity()).sendData(tokenSlipAEditTextData);
                //Toast.makeText(getContext(), "Token Slip A has been successfully sent", Toast.LENGTH_SHORT).show();
            }
        });
        sendTokenSlipB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tokenSlipB  = fixedLengthString(tokenSlipBEditText.getText().toString(), 28);
                String tokenSlipBEditTextData = "$TSLB"+tokenSlipB+";";
                ((DeviceList)getActivity()).sendData(tokenSlipBEditTextData);
                //Toast.makeText(getContext(), "Token Slip B has been successfully sent", Toast.LENGTH_SHORT).show();
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


    private String fixedLengthString(String textData , int length)
    {
        String stringData = null;
        // String stringData = textData.rightPad(lenght, ' ').Substring(0, length);
        // String stringData = leftpad(textData,28);
        if (center_align_check) {
             stringData = rightpad(centerString(length, textData.trim()), length);
        }else{
            stringData = rightpad(textData.trim(), length);

        }
        return stringData;
    }

    public static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }

    public static String center(String text, int len){
        String out = String.format("%"+len+"s%s%"+len+"s", "",text,"");
        float mid = (out.length()/2);
        float start = mid - (len/2);
        float end = start + len;
        return out.substring((int)start, (int)end);
    }


    private String leftpad(String text, int length) {
        return String.format("%" + length + "." + length + "s", text);
    }

    private String rightpad(String text, int length) {
        return String.format("%-" + length + "." + length + "s", text);
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

    public void changeTextOfGeneralFragment(DataModel dataModel)
    {

        bankIdEditText.setText(dataModel.getBankId());
        instEditText.setText(dataModel.getInstName());
        timeDateEditText.setText(dataModel.getTimeDate());
        cTimeEditText.setText(dataModel.getcTime());
        counterNameEditText.setText(dataModel.getCounterName());
        tokenSlipAEditText.setText(dataModel.getTokenSlipA());
        tokenSlipBEditText.setText(dataModel.getTokenSlipB());
        tokenSlip9EditText.setText(dataModel.getTokenSlip9());
        copyNoEditText.setText(dataModel.getCopyNo());
        totalCounterEditText.setText(dataModel.getTotalCounter());

        // rootView.setBackgroundResource(R.color.colorPrimaryDark);

    }
    public void setValueToEditText() {

       // instEditText.setText("090");
        Toast.makeText(context, "Fragment : Refresh called."+dataModel.getInstName(),
                Toast.LENGTH_SHORT).show();
    }

    private void sendData(String comm)
    {

        mCallback.communicate(comm);

    }
    public EditText getInstEditText(){
        return instEditText;
    }


   public void textFontChange(EditText editText){

       Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"fonts/josefin-sans/JosefinSans-Bold.ttf");
       editText.setTypeface(type);
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