package com.assafbt.countriestv;

import android.util.Log;

public class Country {

    public String name;
    public String nativeName;
    public int area;


    public static final String TABLE_NAME = "CountryDB";

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_NATIVE_NAME = "nativeName";
    public static final String COLUMN_AREA = "area";


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_NAME +    " TEXT PRIMARY KEY,"
                    + COLUMN_NATIVE_NAME +     " TEXT,"
                    + COLUMN_AREA +   " INTEGER"
                    + ");";

    public Country() { }

    public Country(String name, String nativeName, int area) {

        Log.i("Country", "constractor");

        /*     */
        this.name = name;
        this.nativeName = nativeName;
        this.area = area;
        Log.i("Country", "this.name " + getName());

        /*     */
        setName(name);
        setNativeName(nativeName);
        setArea(area);
        Log.i("Country", "setName" + getName());
    }

    //Country Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //Country Native Name
    public String getNativeName() {
        return nativeName;
    }
    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    //Country Area
    public int getArea() {
        return area;
    }
    public void setArea(int area) {
        this.area = area;
    }
}
