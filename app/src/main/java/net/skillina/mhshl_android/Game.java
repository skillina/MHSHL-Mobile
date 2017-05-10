package net.skillina.mhshl_android;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Alex on 8/1/2016.
 */
public class Game {
    public final String gameID;
    public final int pointstreakID;
    public final int season;
    public final int key;

    public final int day;
    public final int month;
    public final int year;
    public final int startTime;

    public final String homeTeam;
    public final String awayTeam;

    public int homeScore;
    public int awayScore;

    public int homeShots;
    public int awayShots;

    public int period;
    public int time;

    public Game(){
        gameID = homeTeam = awayTeam = "NUL";
        pointstreakID = season = key = day = month = year = startTime = 0;

        homeScore = awayScore = 0;
        homeShots = awayShots = 0;
        period = 1;
        time = 17*60;
    }

    public Game(String gID, int pID, int s, int d, int m, int y, int t, String home, String away, int k){
        gameID = gID;
        pointstreakID = pID;
        season = s;

        day = d;
        month = m;
        year = y;
        startTime = t;
        homeTeam = home;
        awayTeam = away;
        key = k;

        homeScore = awayScore = 0;
        homeShots = awayShots = 0;
        period = 1;
        time = 17*60;
    }

    public Game(Cursor c){
        key = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_KEY));
        gameID = c.getString(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_GAME_ID));
        pointstreakID = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_POINTSTREAK_ID));
        season = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_SEASON));

        month = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_MONTH));
        day = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_DAY));
        year = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_YEAR));
        startTime = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_START_TIME));

        homeTeam = c.getString(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_HOME_TEAM));
        awayTeam = c.getString(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_AWAY_TEAM));
        homeScore = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_HOME_SCORE));
        awayScore = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_AWAY_SCORE));
        homeShots = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_HOME_SHOTS));
        awayShots = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_AWAY_SHOTS));

        period = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_PERIOD));
        time = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.GameEntry.COLUMN_NAME_TIME));
    }

    public Game(String s){

        String[] attributes = s.split(",");

        int index = 0;

        key = Integer.parseInt(attributes[index++]);
        gameID = attributes[index++];
        month = Integer.parseInt(attributes[index++]);
        day = Integer.parseInt(attributes[index++]);
        year = Integer.parseInt(attributes[index++]);
        startTime = Integer.parseInt(attributes[index++]);

        homeTeam = attributes[index++];
        awayTeam = attributes[index++];

        homeShots = Integer.parseInt(attributes[index++]);
        awayShots = Integer.parseInt(attributes[index++]);

        period = Integer.parseInt(attributes[index++]);
        time = Integer.parseInt(attributes[index++]);

        pointstreakID = Integer.parseInt(attributes[index++]);
        season = Integer.parseInt(attributes[index++]);

        homeScore = Integer.parseInt(attributes[index++]);
        awayScore = Integer.parseInt(attributes[index++]);
    }

    private int countGoals(ArrayList<Goal> goals, String abbr){
        int count = 0;
        for(int i = 0; i < goals.size(); i++){
            if(goals.get(i).teamID.equalsIgnoreCase(abbr))
                count++;
        }
        return count;
    }

    public LinearLayout getView(Context context){

        LinearLayout layout = new LinearLayout(context);

        DatabaseHelper dbh = new DatabaseHelper(context);
        Team home = dbh.getTeam(homeTeam, season);
        Team away = dbh.getTeam(awayTeam, season);
        dbh.close();

        ((Activity) context).getLayoutInflater().inflate(R.layout.layout_game, layout);
        ( (ImageView) layout.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(homeTeam));
        ( (TextView) layout.findViewById(R.id.homeSeed)).setText("#" + home.rank);

        ( (ImageView) layout.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(awayTeam));
        ( (TextView) layout.findViewById(R.id.awaySeed)).setText("#" + away.rank);

        ( (TextView) layout.findViewById(R.id.homeScore)).setText(String.valueOf(homeScore));
        ( (TextView) layout.findViewById(R.id.awayScore)).setText(String.valueOf(awayScore));


        if(period == 1 && time == 17*60){
            ( (TextView) layout.findViewById(R.id.date)).setText(String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year));
            ( (TextView) layout.findViewById(R.id.time)).setText(String.valueOf(startTime));
        }else if(time >= 0){
            ( (TextView) layout.findViewById(R.id.date)).setText(String.valueOf(time));
            ( (TextView) layout.findViewById(R.id.time)).setText("Period " + String.valueOf(period));
        }else{
            String suffix = "";
            if(time == -2)
                suffix = "-OT";
            if(time == -3)
                suffix = "-SO";
            ( (TextView) layout.findViewById(R.id.date)).setText("Final" + suffix);
            ( (TextView) layout.findViewById(R.id.time)).setText(String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year));
        }
        return layout;
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_KEY, key);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_GAME_ID, gameID);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_POINTSTREAK_ID, pointstreakID);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_SEASON, season);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_DAY, day);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_MONTH, month);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_YEAR, year);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_START_TIME, startTime);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_HOME_TEAM, homeTeam);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_AWAY_TEAM, awayTeam);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_HOME_SCORE, homeScore);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_AWAY_SCORE, awayScore);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_HOME_SHOTS, awayShots);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_PERIOD, period);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_TIME, time);
        values.put(DatabaseContract.GameEntry.COLUMN_NAME_SEASON, season);

        return values;
    }

    public static String[] projection = {
            DatabaseContract.GameEntry.COLUMN_NAME_GAME_ID,
            DatabaseContract.GameEntry.COLUMN_NAME_MONTH,
            DatabaseContract.GameEntry.COLUMN_NAME_DAY,
            DatabaseContract.GameEntry.COLUMN_NAME_YEAR,
            DatabaseContract.GameEntry.COLUMN_NAME_START_TIME,
            DatabaseContract.GameEntry.COLUMN_NAME_HOME_TEAM,
            DatabaseContract.GameEntry.COLUMN_NAME_AWAY_TEAM,
            DatabaseContract.GameEntry.COLUMN_NAME_HOME_SCORE,
            DatabaseContract.GameEntry.COLUMN_NAME_AWAY_SCORE,
            DatabaseContract.GameEntry.COLUMN_NAME_HOME_SHOTS,
            DatabaseContract.GameEntry.COLUMN_NAME_AWAY_SHOTS,
            DatabaseContract.GameEntry.COLUMN_NAME_PERIOD,
            DatabaseContract.GameEntry.COLUMN_NAME_TIME,
            DatabaseContract.GameEntry.COLUMN_NAME_POINTSTREAK_ID,
            DatabaseContract.GameEntry.COLUMN_NAME_SEASON
    };
}
