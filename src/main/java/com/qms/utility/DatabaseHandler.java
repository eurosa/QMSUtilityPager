package com.qms.utility;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Name
    private static final String DATABASE_NAME = "QmsUtilityDB";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_QMS_UTILITY = "qmsutility";

    // Table Columns Name
    private static final String KEY_ID = "id";
    private static final String KEY_INST_NAME = "instName";
    private static final String KEY_TIME_DATE = "timeDate";
    private static final String KEY_BANK_ID = "bankId";
    private static final String KEY_COUNTER_NAME = "counterName";
    private static final String KEY_TOKEN_SLIP_B = "tokenSlipB";
    private static final String KEY_TOKEN_SLIP_A = "tokenSlipA";
    private static final String KEY_TOKEN_SLIP_9 = "tokenSlip9";
    private static final String KEY_C_TIME = "cTime";
    private static final String KEY_COPY_NO = "copyNo";
    private static final String KEY_TOTAL_COUNTER = "totalCounter";

    private static final String KEY_LABEL_1 = "cntLabelOne";
    private static final String KEY_LABEL_2 = "cntLabelTwo";
    private static final String KEY_LABEL_3 = "cntLabelThree";
    private static final String KEY_LABEL_4 = "cntLabelFour";
    private static final String KEY_LABEL_5 = "cntLabelFive";
    private static final String KEY_LABEL_6 = "cntLabelSix";
    private static final String KEY_LABEL_7 = "cntLabelSeven";
    private static final String KEY_LABEL_8 = "cntLabelEight";
    private static final String KEY_LABEL_9 = "cntLabelNine";
    private static final String KEY_LABEL_10 = "cntLabelTen";
    private static final String KEY_LABEL_11 = "cntLabelEleven";
    private static final String KEY_LABEL_12 = "cntLabelTweleve";
    private static final String KEY_LABEL_13 = "cntLabelThirteen";
    private static final String KEY_LABEL_14 = "cntLabelFourteen";
    private static final String KEY_LABEL_15 = "cntLabelFifteen";
    private static final String KEY_LABEL_16 = "cntLabelSixteen";

    Context context;

    private final ArrayList<DataModel> qms_list = new ArrayList<>();

    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_QMS_UTILITY + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_INST_NAME + " TEXT,"
                + KEY_TIME_DATE + " TEXT ,"+ KEY_BANK_ID + " TEXT ,"+ KEY_COUNTER_NAME + " TEXT, "+ KEY_TOKEN_SLIP_B + " TEXT ,"
                + KEY_TOKEN_SLIP_A + " TEXT ,"+ KEY_TOKEN_SLIP_9 + " TEXT ,"+ KEY_C_TIME + " TEXT,"+ KEY_COPY_NO + " TEXT ,"
                + KEY_TOTAL_COUNTER + " TEXT ,"+ KEY_LABEL_1 + " TEXT ,"+ KEY_LABEL_2 + " TEXT ,"+ KEY_LABEL_3 + " TEXT ,"
                + KEY_LABEL_4 + " TEXT ,"+ KEY_LABEL_5 + " TEXT ,"+ KEY_LABEL_6 + " TEXT ,"+ KEY_LABEL_7 + " TEXT ,"
                + KEY_LABEL_8 + " TEXT ,"+ KEY_LABEL_9 + " TEXT ,"+ KEY_LABEL_10 + " TEXT ,"+ KEY_LABEL_11 + " TEXT ,"
                + KEY_LABEL_12 + " TEXT ,"+ KEY_LABEL_13 + " TEXT ,"+ KEY_LABEL_14 + " TEXT ,"+ KEY_LABEL_15 + " TEXT ,"
                + KEY_LABEL_16 + " TEXT "+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older Table if already Exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QMS_UTILITY);

        // Create tables again
        onCreate(db);
    }


    // Adding new contact
    public void Add_QmsUtility(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_INST_NAME, dataModel.getInstName()); // Name
        values.put(KEY_BANK_ID, dataModel.getBankId()); //  Email
        values.put(KEY_COUNTER_NAME, dataModel.getCounterName()); // Name
        values.put(KEY_TOKEN_SLIP_B, dataModel.getTokenSlipB()); //  Email
        values.put(KEY_TOKEN_SLIP_A, dataModel.getTokenSlipA()); // Name
        values.put(KEY_TOKEN_SLIP_9, dataModel.getTokenSlip9()); //  Email
        values.put(KEY_TOTAL_COUNTER, dataModel.getTotalCounter()); // Name
        values.put(KEY_COPY_NO, dataModel.getCopyNo()); //  Email
        values.put(KEY_TIME_DATE, dataModel.getTimeDate()); // Name
        values.put(KEY_C_TIME, dataModel.getcTime()); //  Email

        values.put(KEY_LABEL_1, dataModel.getCntLabelOne()); // Name
        values.put(KEY_LABEL_2, dataModel.getCntLabelTwo()); //  Email
        values.put(KEY_LABEL_3, dataModel.getCntLabelThree()); // Name
        values.put(KEY_LABEL_4, dataModel.getCntLabelFour()); //  Email
        values.put(KEY_LABEL_5, dataModel.getCntLabelFive()); // Name
        values.put(KEY_LABEL_6, dataModel.getCntLabelSix()); //  Email
        values.put(KEY_LABEL_7, dataModel.getCntLabelSeven()); // Name
        values.put(KEY_LABEL_8, dataModel.getCntLabelEight()); //  Email
        values.put(KEY_LABEL_9, dataModel.getCntLabelNine()); // Name
        values.put(KEY_LABEL_10, dataModel.getCntLabelTen()); //  Email
        values.put(KEY_LABEL_11, dataModel.getCntLabelEleven()); // Name
        values.put(KEY_LABEL_12, dataModel.getCntLabelTweleve()); //  Email
        values.put(KEY_LABEL_13, dataModel.getCntLabelThirteen()); // Name
        values.put(KEY_LABEL_14, dataModel.getCntLabelFourteen()); //  Email
        values.put(KEY_LABEL_15, dataModel.getCntLabelFifteen()); // Name
        values.put(KEY_LABEL_16, dataModel.getCntLabelSixteen()); //  Email

        // Inserting Row
        long rowInserted = db.insert(TABLE_QMS_UTILITY, null, values);

        if(rowInserted != -1)
            Toast.makeText(context, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        db.close(); // Close Database Connection
    }

/*
    //  Getting single contact
        DataModel Get_QmsUtility(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QMS_UTILITY, new String[] { KEY_ID, KEY_NAME, KEY_EMAIL }, KEY_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        DataModel qmsUtility = new DataModel();
        // DataModel contact = new DataModel(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        // return contact
        cursor.close();
        db.close();

        return qmsUtility;
    }
*/

    // Getting All QmsUtility
    public ArrayList<DataModel> Get_QmsUtility() {
        try {
            qms_list.clear();

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_QMS_UTILITY;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    DataModel contact = new DataModel();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                   // Toast.makeText(context, "Something: "+cursor.getString(1),
                      //      Toast.LENGTH_SHORT).show();
                    contact.setInstName(cursor.getString(1));
                    // contact.setEmail(cursor.getString(2));
                    // contact.setImage(cursor.getBlob(3));
                    // Adding contact to list
                    qms_list.add(contact);
                } while (cursor.moveToNext());
            }

            // return contact list
            cursor.close();
            db.close();
            return qms_list;
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("all_qmsUtility", "" + e);
        }

        return qms_list;
    }


    // Updating single qmsUtility
    public int Update_QmsUtility(DataModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(KEY_NAME, contact.getName());
        //values.put(KEY_EMAIL, contact.getEmail());
        //values.put(KEY_IMAGE, contact.getImage());

        // updating row
        return db.update(TABLE_QMS_UTILITY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }


    // Deleting single qmsUtility
    public void Delete_QmsUtility(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QMS_UTILITY, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


    // Getting qmsUtility Count
    public int Get_Total_QmsUtility() {
        String countQuery = "SELECT  * FROM " + TABLE_QMS_UTILITY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return qms utility
        return cursor.getCount();
    }

}