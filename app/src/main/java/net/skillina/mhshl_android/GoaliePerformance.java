package net.skillina.mhshl_android;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Alex on 8/3/2016.
 */
public class GoaliePerformance {
    public final String gameID;
    public final String teamID;
    public final int player;
    public final int key;

    public int seconds;
    public int goalsAgainst;
    public int shotsAgainst;
    public int saves;

    public final int season;

    public GoaliePerformance(){
        gameID = teamID = "NUL";
        player = key = season = 0;

        seconds = 0;
        shotsAgainst = goalsAgainst = saves = 0;
    }

    public GoaliePerformance(String gID, String tID, int p, int s, int k){
        gameID = gID;
        teamID = tID;
        player = p;
        season = s;

        key = k;
        seconds = 0;
        shotsAgainst = goalsAgainst = saves = 0;
    }

    public GoaliePerformance(Cursor c){

        key = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalieEntry.COLUMN_NAME_KEY));
        gameID = c.getString(c.getColumnIndexOrThrow(DatabaseContract.GoalieEntry.COLUMN_NAME_GAME_ID));
        teamID = c.getString(c.getColumnIndexOrThrow(DatabaseContract.GoalieEntry.COLUMN_NAME_TEAM_ID));
        player = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalieEntry.COLUMN_NAME_PLAYER));
        seconds = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalieEntry.COLUMN_NAME_MINUTES));
        goalsAgainst = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalieEntry.COLUMN_NAME_GOALS_AGAINST));
        shotsAgainst = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalieEntry.COLUMN_NAME_SHOTS_AGAINST));
        season = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GoalieEntry.COLUMN_NAME_SEASON));

        saves = shotsAgainst - goalsAgainst;


    }

    public GoaliePerformance(String s){
        String[] attributes = s.split(",");

        int index = 0;

        key = Integer.parseInt(attributes[index++]);
        gameID = attributes[index++];
        teamID = attributes[index++];
        player = Integer.parseInt(attributes[index++]);
        seconds = Integer.parseInt(attributes[index++]);
        goalsAgainst = Integer.parseInt(attributes[index++]);

        shotsAgainst = Integer.parseInt(attributes[index++]);
        season = Integer.parseInt(attributes[index++]);
    }

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.GoalieEntry.COLUMN_NAME_KEY, key);
        values.put(DatabaseContract.GoalieEntry.COLUMN_NAME_GAME_ID, gameID);
        values.put(DatabaseContract.GoalieEntry.COLUMN_NAME_TEAM_ID, teamID);
        values.put(DatabaseContract.GoalieEntry.COLUMN_NAME_PLAYER, player);
        values.put(DatabaseContract.GoalieEntry.COLUMN_NAME_MINUTES, seconds);
        values.put(DatabaseContract.GoalieEntry.COLUMN_NAME_GOALS_AGAINST, goalsAgainst);
        values.put(DatabaseContract.GoalieEntry.COLUMN_NAME_SHOTS_AGAINST, shotsAgainst);
        values.put(DatabaseContract.GoalieEntry.COLUMN_NAME_SEASON, season);

        return values;
    }

    public static String[] projection = {
            DatabaseContract.GoalieEntry.COLUMN_NAME_GAME_ID,
            DatabaseContract.GoalieEntry.COLUMN_NAME_TEAM_ID,
            DatabaseContract.GoalieEntry.COLUMN_NAME_PLAYER,
            DatabaseContract.GoalieEntry.COLUMN_NAME_MINUTES,
            DatabaseContract.GoalieEntry.COLUMN_NAME_GOALS_AGAINST,
            DatabaseContract.GoalieEntry.COLUMN_NAME_SHOTS_AGAINST,
            DatabaseContract.GoalieEntry.COLUMN_NAME_SEASON
    };
}
