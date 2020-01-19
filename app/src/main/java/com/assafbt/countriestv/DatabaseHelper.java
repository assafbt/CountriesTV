package com.assafbt.countriestv;
/*
 * *
 *  * Created by Assaf Biton
 *
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private String TAG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "country_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }//DatabaseHelper(Context

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create notes table
        sqLiteDatabase.execSQL(Country.CREATE_TABLE);
        Log.i(TAG, "isDatabaseIntegrityOk " + sqLiteDatabase.isDatabaseIntegrityOk());
    }//onCreate(SQLiteDatabase

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Country.TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }//onUpgrade(SQLiteDatabase

    public long insertCountry(Country country) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i(TAG+" insertCountry,", "db = " + db.toString());

        ContentValues values = new ContentValues();

        values.put(Country.COLUMN_NAME, country.getName());
        values.put(Country.COLUMN_NATIVE_NAME, country.getNativeName());
        values.put(Country.COLUMN_AREA, country.getArea());

        // insert row
        long id = db.insert(Country.TABLE_NAME, null, values);
        Log.i(TAG +" insertCountry,", "added in possition: " + id);
        Log.i(TAG +" insertCountry, value - ", values.toString());

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }//insertCountry

    public List<Country> getAllCountries() { // #################
        String funTag = TAG + ", getAllCountries:";
        String query =  "SELECT  * FROM " + Country.TABLE_NAME ;

        List<Country> mCountryList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Country mCountry;

        if (cursor.moveToFirst()) {
            do {
                mCountry = new Country();
                mCountry.setName(cursor.getString(cursor.getColumnIndex(Country.COLUMN_NAME)));
                mCountry.setNativeName(cursor.getString(cursor.getColumnIndex(Country.COLUMN_NATIVE_NAME)));
                mCountry.setArea(cursor.getInt(cursor.getColumnIndex(Country.COLUMN_AREA)));

                Log.i(funTag, "mCountry.name -" + mCountry.name );
                Log.i(funTag, "mCountry.nativeName -" + mCountry.nativeName );
                Log.i(funTag, "mCountry.area -" + mCountry.area );

                mCountryList.add(mCountry);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        return mCountryList;
    }//getAllCountries

    public List<Country> getSortedCountries(String columName, String sortBy) {
        String funTag = TAG + ", getAllCountries:";
        String query;
        switch (columName)
        {
            case "name":
                query =  "SELECT  * FROM " + Country.TABLE_NAME + " ORDER BY " + Country.COLUMN_NAME + " " + sortBy;
                break;
            case "area":
                query =  "SELECT  * FROM " + Country.TABLE_NAME + " ORDER BY " + Country.COLUMN_AREA + " " + sortBy;
                break;
            case "none":
                query =  "SELECT  * FROM " + Country.TABLE_NAME;
                break;
            default:
                query =  "SELECT  * FROM " + Country.TABLE_NAME;
                break;
        }

        List<Country> mCountryList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Country mCountry;

        if (cursor.moveToFirst()) {
            do {
                mCountry = new Country();
                mCountry.setName(cursor.getString(cursor.getColumnIndex(Country.COLUMN_NAME)));
                mCountry.setNativeName(cursor.getString(cursor.getColumnIndex(Country.COLUMN_NATIVE_NAME)));
                mCountry.setArea(cursor.getInt(cursor.getColumnIndex(Country.COLUMN_AREA)));

                Log.i(funTag, "mCountry.name -" + mCountry.name );
                Log.i(funTag, "mCountry.nativeName -" + mCountry.nativeName );
                Log.i(funTag, "mCountry.area -" + mCountry.area );

                mCountryList.add(mCountry);
            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();

        return mCountryList;
    }//getSortedCountries


    public int getDatabaseVersion()
    {
        return this.DATABASE_VERSION;
    }

    public String getDatabaseName()
    {
        return this.DATABASE_NAME;
    }

}//DatabaseHelper extends
