package net.skillina.mhshl_android;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Alex on 8/1/2016.
 */
public class Player {
    public final String team;
    public final String name;
    public final int pointstreakID;
    public final int number;
    public final int season;
    public final int goalie;
    public final int key;

    public int gp;
    public int goals;
    public int assists;
    public int ppGoals;
    public int shGoals;
    public int penaltyMinutes;
    public int goalStreak;
    public int pointStreak;

    public int minutesPlayed;
    public int shotsOn;
    public int goalsAgainst;
    public float goalsAgainstAverage;
    public int saves;
    public float savePercentage;

    public Player(){
        team = name = "NUL";

        pointstreakID = number = season = goalie = key = 0;

        gp = 0;
        goals = assists = ppGoals = shGoals = 0;
        penaltyMinutes = 0;
        goalStreak = pointStreak = 0;

        minutesPlayed = 0;
        shotsOn = 0;
        goalsAgainst = saves = 0;
        goalsAgainstAverage = savePercentage = 0;
    }

    public Player(String t, String n, int id, int num, int s, int g, int k){
        team = t;
        name =n;
        pointstreakID = id;
        number = num;
        season = s;
        goalie = g;
        key = k;

        gp = 0;
        goals = assists = ppGoals = shGoals = 0;
        penaltyMinutes = 0;
        goalStreak = pointStreak = 0;

        minutesPlayed = 0;
        shotsOn = 0;
        goalsAgainst = saves = 0;
        goalsAgainstAverage = savePercentage = 0;
    }

    public Player(Cursor c){
        key = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_KEY));
        team = c.getString(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_TEAMID));
        name = c.getString(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_NAME));
        pointstreakID = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_POINTSTREAK_ID));
        season = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_SEASON));
        number = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_JERSEY_NUMBER));
        goalie = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_IS_GOALIE));

        gp = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_GAMES_PLAYED));
        goals = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_GOALS));
        assists = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_ASSISTS));
        ppGoals = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_POWERPLAY_GOALS));
        shGoals = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_SHORTHANDED_GOALS));
        penaltyMinutes = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_PENALTY_MINUTES));
        goalStreak = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_GOAL_STREAK));
        pointStreak = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_POINT_STREAK));
        minutesPlayed = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_MINUTES_PLAYED));

        shotsOn = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_SHOTS_ON));
        goalsAgainst = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_GOALS_AGAINST));
        goalsAgainstAverage = c.getFloat(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_GOALS_AGAINST_AVERAGE));
        saves = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_SAVES));
        savePercentage = c.getFloat(c.getColumnIndexOrThrow(DatabaseContract.PlayerEntry.COLUMN_NAME_SAVE_PERCENTAGE));
    }

    public Player(String s){

        String[] attributes = s.split(",");

        int index = 0;

        key = Integer.parseInt(attributes[index++]);
        team = attributes[index++];
        name = attributes[index++];
        pointstreakID = Integer.parseInt(attributes[index++]);
        number = Integer.parseInt(attributes[index++]);
        season = Integer.parseInt(attributes[index++]);
        gp = Integer.parseInt(attributes[index++]);
        goalie = Integer.parseInt(attributes[index++]);
        goals = Integer.parseInt(attributes[index++]);
        assists = Integer.parseInt(attributes[index++]);
        ppGoals = Integer.parseInt(attributes[index++]);
        shGoals = Integer.parseInt(attributes[index++]);
        penaltyMinutes = Integer.parseInt(attributes[index++]);
        goalStreak = Integer.parseInt(attributes[index++]);
        pointStreak = Integer.parseInt(attributes[index++]);

        minutesPlayed = 0;
        shotsOn = 0;
        goalsAgainst = saves = 0;
        goalsAgainstAverage = savePercentage = 0;

    }


    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_KEY, key);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_TEAMID, team);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_NAME, name);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_POINTSTREAK_ID, pointstreakID);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_SEASON, season);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_JERSEY_NUMBER, number);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_IS_GOALIE, goalie);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_GAMES_PLAYED, gp);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_GOALS, goals);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_ASSISTS, assists);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_POWERPLAY_GOALS, ppGoals);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_SHORTHANDED_GOALS, shGoals);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_GOAL_STREAK, goalStreak);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_POINT_STREAK, pointStreak);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_MINUTES_PLAYED, minutesPlayed);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_SHOTS_ON, shotsOn);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_GOALS_AGAINST, goalsAgainst);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_GOALS_AGAINST_AVERAGE, goalsAgainstAverage);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_SAVES, saves);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_SAVE_PERCENTAGE, savePercentage);
        values.put(DatabaseContract.PlayerEntry.COLUMN_NAME_PENALTY_MINUTES, penaltyMinutes);

        return values;
    }

    public static String[] projection = {
            DatabaseContract.PlayerEntry.COLUMN_NAME_TEAMID,
            DatabaseContract.PlayerEntry.COLUMN_NAME_NAME,
            DatabaseContract.PlayerEntry.COLUMN_NAME_POINTSTREAK_ID,
            DatabaseContract.PlayerEntry.COLUMN_NAME_SEASON,
            DatabaseContract.PlayerEntry.COLUMN_NAME_JERSEY_NUMBER,
            DatabaseContract.PlayerEntry.COLUMN_NAME_IS_GOALIE,
            DatabaseContract.PlayerEntry.COLUMN_NAME_GAMES_PLAYED,
            DatabaseContract.PlayerEntry.COLUMN_NAME_GOALS,
            DatabaseContract.PlayerEntry.COLUMN_NAME_ASSISTS,
            DatabaseContract.PlayerEntry.COLUMN_NAME_POWERPLAY_GOALS,
            DatabaseContract.PlayerEntry.COLUMN_NAME_SHORTHANDED_GOALS,
            DatabaseContract.PlayerEntry.COLUMN_NAME_GOAL_STREAK,
            DatabaseContract.PlayerEntry.COLUMN_NAME_POINT_STREAK,
            DatabaseContract.PlayerEntry.COLUMN_NAME_MINUTES_PLAYED,
            DatabaseContract.PlayerEntry.COLUMN_NAME_SHOTS_ON,
            DatabaseContract.PlayerEntry.COLUMN_NAME_GOALS_AGAINST,
            DatabaseContract.PlayerEntry.COLUMN_NAME_GOALS_AGAINST_AVERAGE,
            DatabaseContract.PlayerEntry.COLUMN_NAME_SAVES,
            DatabaseContract.PlayerEntry.COLUMN_NAME_SAVE_PERCENTAGE,
            DatabaseContract.PlayerEntry.COLUMN_NAME_PENALTY_MINUTES
    };


}
