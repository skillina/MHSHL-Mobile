package net.skillina.mhshl_android;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Alex on 8/3/2016.
 */
public class Penalty {
    public final String gameID;
    public final String teamID;
    public final int key;

    public final int player;
    public final int duration;
    public final int period;

    public final int time;
    public int timeRemaining;
    public int goalsWhile;
    public boolean scoredOn;
    public final String offense;

    public final int season;

    public Penalty(){
        gameID = teamID = offense = "NUL";
        key = player = duration = period = time = timeRemaining = goalsWhile = season = 0;
        scoredOn = false;
    }

    public Penalty(String game, String team, int p, int dur, int per, int t, String penalty, int s, int k){
        gameID = game;
        teamID = team;
        player = p;
        duration = dur;
        period = per;
        time = t;
        offense = penalty;
        timeRemaining = 0;
        goalsWhile = 0;
        scoredOn = false;
        season = s;
        key = k;
    }

    public Penalty(Cursor c){
        key = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_KEY));
        gameID = c.getString(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_GAME_ID));
        teamID = c.getString(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_TEAM_ID));
        offense = c.getString(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_OFFENSE));
        player = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_PLAYER));
        duration = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_DURATION));
        period = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_PERIOD));
        time = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_TIME));
        season = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_SEASON));
        timeRemaining = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_TIME_REMAINING));
        goalsWhile = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_GOALS_DURING_PENALTY));
        scoredOn = 0 < c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PenaltyEntry.COLUMN_NAME_SCORED_ON));

    }

    public Penalty(String s){

        String[] attributes = s.split(",");

        int index = 0;

        key = Integer.parseInt(attributes[index++]);
        gameID = attributes[index++];
        teamID = attributes[index++];
        player = Integer.parseInt(attributes[index++]);
        duration = Integer.parseInt(attributes[index++]);
        period = Integer.parseInt(attributes[index++]);

        time = Integer.parseInt(attributes[index++]);
        timeRemaining = Integer.parseInt(attributes[index++]);

        goalsWhile = Integer.parseInt(attributes[index++]);
        scoredOn = !attributes[index++].equals("");
        offense = attributes[index++];
        season = Integer.parseInt(attributes[index++]);

    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_KEY, key);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_GAME_ID, gameID);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_TEAM_ID, teamID);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_PLAYER, player);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_DURATION, duration);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_PERIOD, period);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_TIME, time);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_TIME_REMAINING, timeRemaining);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_GOALS_DURING_PENALTY, goalsWhile);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_SCORED_ON, scoredOn);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_OFFENSE, offense);
        values.put(DatabaseContract.PenaltyEntry.COLUMN_NAME_SEASON, season);

        return values;
    }

    public static
    String[] projection = {
            DatabaseContract.PenaltyEntry.COLUMN_NAME_GAME_ID,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_TEAM_ID,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_PLAYER,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_DURATION,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_PERIOD,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_TIME,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_TIME_REMAINING,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_GOALS_DURING_PENALTY,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_SCORED_ON,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_OFFENSE,
            DatabaseContract.PenaltyEntry.COLUMN_NAME_SEASON
    };
}
