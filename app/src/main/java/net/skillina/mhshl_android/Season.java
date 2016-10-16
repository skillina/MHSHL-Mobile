package net.skillina.mhshl_android;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Alex on 7/29/2016.
 */
public class Season {

    public final int pointstreakID;
    public final int year;
    public final int playoff;
    public final int varsity;
    public final int key;

    public Season(){
        pointstreakID = year = playoff = varsity = key = 0;
    }

    public Season(int id, int y, int p, int v, int k){
        pointstreakID = id;
        year = y;
        playoff = p;
        key = k;
        varsity = v;
    }

    public Season(Cursor c){
        key = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.SeasonEntry.COLUMN_NAME_KEY));
        pointstreakID = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.SeasonEntry.COLUMN_NAME_ID));
        year = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.SeasonEntry.COLUMN_NAME_YEAR));
        playoff = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.SeasonEntry.COLUMN_NAME_PLAYOFFS));
        varsity = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.SeasonEntry.COLUMN_NAME_VARSITY));

    }

    public Season(String s){

        String[] attributes = s.split(",");

        int index = 0;

        key = Integer.parseInt((attributes[index++]));
        pointstreakID = Integer.parseInt(attributes[index++]);
        year = Integer.parseInt(attributes[index++]);
        playoff = Integer.parseInt(attributes[index++]);
        varsity = Integer.parseInt(attributes[index++]);

    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.SeasonEntry.COLUMN_NAME_KEY, key);
        values.put(DatabaseContract.SeasonEntry.COLUMN_NAME_ID, pointstreakID);
        values.put(DatabaseContract.SeasonEntry.COLUMN_NAME_YEAR, year);
        values.put(DatabaseContract.SeasonEntry.COLUMN_NAME_PLAYOFFS, playoff);
        values.put(DatabaseContract.SeasonEntry.COLUMN_NAME_VARSITY, varsity);

        return values;
    }

    public static String[] projection = {
            DatabaseContract.SeasonEntry.COLUMN_NAME_ID,
            DatabaseContract.SeasonEntry.COLUMN_NAME_YEAR,
            DatabaseContract.SeasonEntry.COLUMN_NAME_PLAYOFFS,
            DatabaseContract.SeasonEntry.COLUMN_NAME_VARSITY
    };
}
