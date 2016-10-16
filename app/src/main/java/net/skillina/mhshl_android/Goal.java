package net.skillina.mhshl_android;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Alex on 8/3/2016.
 */
public class Goal {
    public final String gameID;
    public final String teamID;
    public final int scorer;
    public final int assist1;
    public final int assist2;
    public final int period;
    public final int time;
    public final int powerplay;
    public final int key;

    public final int season;

    public Goal(){
        gameID = teamID = "NUL";
        scorer = assist1 = assist2 = period = time = powerplay = season = key = 0;
    }

    public Goal(String gID, String tID, int s, int a1, int a2, int per, int t, int pp, int se, int k){
        gameID = gID;
        teamID = tID;
        scorer = s;
        assist1 = a1;
        assist2 = a2;
        period = per;
        time = t;
        powerplay = pp;
        season = se;
        key = k;
    }

    public Goal(Cursor c){
        key = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_KEY));
        gameID = c.getString(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_GAME_ID));
        teamID = c.getString(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_TEAM_ID));
        scorer = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_SCORER));
        assist1 = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_1));
        assist2 = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_2));
        period = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_PERIOD));
        time = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_TIME));
        powerplay = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_POWERPLAY));
        season = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalEntry.COLUMN_NAME_SEASON));
    }

    public Goal(String s){

        String[] attributes = s.split(",");

        int index = 0;

        key = Integer.parseInt(attributes[index++]);
        gameID = attributes[index++];
        teamID = attributes[index++];
        scorer = Integer.parseInt(attributes[index++]);
        assist1 = Integer.parseInt(attributes[index++]);
        assist2 = Integer.parseInt(attributes[index++]);

        period = Integer.parseInt(attributes[index++]);
        time = Integer.parseInt(attributes[index++]);

        powerplay = Integer.parseInt(attributes[index++]);
        season = Integer.parseInt(attributes[index++]);
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_KEY, key);
        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_GAME_ID, gameID);
        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_TEAM_ID, teamID);
        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_SCORER, scorer);
        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_1, assist1);
        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_2, assist2);
        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_PERIOD, period);
        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_TIME, time);
        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_POWERPLAY, powerplay);
        values.put(DatabaseContract.GoalEntry.COLUMN_NAME_SEASON, season);

        return values;
    }

    public static String[] projection = {
            DatabaseContract.GoalEntry.COLUMN_NAME_GAME_ID,
            DatabaseContract.GoalEntry.COLUMN_NAME_TEAM_ID,
            DatabaseContract.GoalEntry.COLUMN_NAME_SCORER,
            DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_1,
            DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_2,
            DatabaseContract.GoalEntry.COLUMN_NAME_PERIOD,
            DatabaseContract.GoalEntry.COLUMN_NAME_TIME,
            DatabaseContract.GoalEntry.COLUMN_NAME_PERIOD,
            DatabaseContract.GoalEntry.COLUMN_NAME_SEASON
    };

}
