package helix.hackchat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HelixTech-Admin on 4/9/2016.
 */
public class DatabaseRecordHandler extends SQLiteOpenHelper {
    /**
     * All Static variables
     */
    private static final int DATABASE_VERSION = 1;                  // Database Version
    private static final String DATABASE_NAME = "hackchatapp";
    //private static final String DATABASE_NAME = Environment.getExternalStorageDirectory()+ File.separator + "DataBase" + File.separator +"hackchatapp";          // Database Name
    private static final String TABLE_NAME = "contacts";  // Contacts table name

    /**
     * Contacts Table Columns names
     */
    private static final String KEY_ID = "id";
    private static final String KEY_USERID = "user_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_DEVICEID = "device_id";

    public DatabaseRecordHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creating Tables
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY autoincrement not null,"
                + KEY_USERID + " INTEGER UNIQUE," + KEY_NAME + " TEXT," + KEY_MOBILE + " TEXT," + KEY_DEVICEID + " TEXT)";//DATETIME DEFAULT CURRENT_DATE"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    /**
     * Upgrading database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    /**
     * Adding new rate
     */
    long addContacts(GetSetContact atten) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long temp=0;
        values.put(KEY_USERID, atten.getUserID());
        values.put(KEY_NAME, atten.getName());
        values.put(KEY_MOBILE, atten.getMobile());
        if(checkInsertConflict(atten.getUserID())!=0)
            return 0;
        else {
            temp = db.insert(TABLE_NAME, null, values);
            db.close();
            return temp;
        }
    }

    private int checkInsertConflict(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM " +TABLE_NAME+" WHERE "+KEY_USERID+"='"+id+"'";
        Cursor cursor = db.rawQuery(Query, null);
        return cursor.getCount();
    }

//    public int getMyDetails(){
//        SQLiteDatabase db = this.getReadableDatabase();
//        String Query = "SELECT "+KEY_USERID+" FROM " +TABLE_NAME;
//        Cursor cursor = db.rawQuery(Query, null);
//        int cc = cursor.getCount();
//        int userid=0;
//        //int userid = cursor.getInt(0);
//        ///String userid1 = cursor.getString(0);
//        if (cursor.moveToFirst())
//            userid = cursor.getInt(0);
////        contact.setRate1(cursor.getString(2));
////        contact.setRate2(cursor.getString(3));
//        return userid;
//    }

    public String getNameofUser(int user_id){
        SQLiteDatabase db = this.getReadableDatabase();
        String name="";
        String Query = "SELECT "+KEY_NAME+" FROM " +TABLE_NAME+" WHERE "+KEY_USERID+"="+user_id;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst())
            name = cursor.getString(0);
        cursor.close();
        db.close();
        return name;
    }

    public List getAllContacts(int id){
        List<GetSetContact> list = new ArrayList<GetSetContact>();
        SQLiteDatabase db = this.getReadableDatabase();
        String Query = "SELECT * FROM " +TABLE_NAME+" WHERE "+KEY_USERID+"!='"+id+"'";
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.moveToFirst()) {
            do {
                GetSetContact values = new GetSetContact();
                values.setUserID(cursor.getInt(1));
                values.setName(cursor.getString(2));
                values.setMobile(cursor.getString(3));
                list.add(values);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
