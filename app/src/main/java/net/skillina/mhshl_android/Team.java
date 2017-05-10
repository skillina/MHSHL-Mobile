package net.skillina.mhshl_android;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alex on 8/1/2016.
 */
public class Team {
    public final String abbreviation;
    public final String city;
    public final String name;
    public final int pointstreakID;
    public final int season;
    public final int key;

    public int gamesPlayed;
    public int wins;
    public int losses;
    public int otLosses;
    public int soLosses;

    public int points;
    public int goalsFor;
    public int goalsAgainst;

    public int penaltyMinutes;

    public int streak;

    public int rank;


    public Team(){
        abbreviation = city = name = "NUL";
        pointstreakID = season = key = 0;
        rank = 0;
        gamesPlayed = wins = losses = otLosses = soLosses = 0;
        points = goalsFor = goalsAgainst = 0;
        penaltyMinutes = 0;
        streak = 0;
    }

    public Team(String abbr, String n, String c, int pID, int s, int k){
        abbreviation = abbr;
        city = c;
        name = n;
        pointstreakID = pID;
        season = s;
        key = k;

        gamesPlayed = wins = losses = otLosses = soLosses = 0;
        points = goalsFor = goalsAgainst = 0;
        penaltyMinutes = 0;
        streak = 0;

        rank = 0;
    }

    public Team(Cursor c){
        key = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_KEY));
        abbreviation = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_ABBREVIATION));
        city = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_CITY));
        name = c.getString(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_NAME));
        pointstreakID = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_ID));
        season = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_SEASON));

        gamesPlayed = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_GAMES_PLAYED));
        wins = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_WINS));
        losses = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_LOSSES));
        otLosses = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_OVERTIME_LOSSES));
        soLosses= c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_SHOOTOUT_LOSSES));
        points = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_POINTS));
        goalsFor = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_GOALS_FOR));
        goalsAgainst = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_GOALS_AGAINST));
        penaltyMinutes = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_PENALTY_MINUTES));
        streak = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_STREAK));

        rank = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.TeamEntry.COLUMN_NAME_RANK));
    }

    public Team(String s){
        String[] attributes = s.split(",");

        int index = 0;

        key = Integer.parseInt(attributes[index++]);
        abbreviation = attributes[index++];
        name = attributes[index++];
        city = attributes[index++];
        pointstreakID = Integer.parseInt(attributes[index++]);
        season = Integer.parseInt(attributes[index++]);

        updateRightSide();
    }

    private int gameOutcome(Game g){
        if(g.time >= 0)
            return 0;

        int us;
        int them;
        int per;

        if(g.homeTeam.equalsIgnoreCase(abbreviation)){
            us = g.homeScore;
            them = g.awayScore;
        }else{
            us = g.awayScore;
            them = g.homeScore;
        }

        if(us > them)
            return 1;
        else
            return g.time;
    }

    private int goalsFor(Game g){
        if(g.homeTeam.equalsIgnoreCase(abbreviation))
            return g.homeScore;
        else
            return g.awayScore;
    }

    private int goalsAgainst(Game g){
        if(g.homeTeam.equalsIgnoreCase(abbreviation))
            return g.awayScore;
        else
            return g.homeScore;
    }

    private void updateRightSide(){
        DatabaseHelper dbh = new DatabaseHelper(App.context);

        ArrayList<Game> games = dbh.getGames(abbreviation, season);
        gamesPlayed = games.size();

        int w = 0;
        int l = 0;
        int ot = 0;
        int so = 0;

        int gf = 0;
        int ga = 0;

        for(int i = 0; i < games.size(); i++){
            int outcome = gameOutcome(games.get(i));
            switch(outcome){
                case 1: w++; break;
                case -1: l++; break;
                case -2: ot++; break;
                case -3: so++; break;
                default: System.out.println("Something went wrong determining outcome of game " + games.get(i).gameID);
            }

            gf += goalsFor(games.get(i));
            ga += goalsAgainst(games.get(i));
        }

        rank = getRank();

        wins = w;
        losses = l;
        otLosses = ot;
        soLosses = so;

        points = 2*w + ot + so;

        goalsFor = gf;
        goalsAgainst = ga;

        dbh.close();
    }

    public View getView(Context context){
        LinearLayout ll = new LinearLayout(context);
        ((Activity)context).getLayoutInflater().inflate(R.layout.layout_team, ll);

        ( (ImageView) ll.findViewById(R.id.logo)).setImageResource(LeagueUtils.resolveTeamLogo(abbreviation));
        ( (TextView) ll.findViewById(R.id.name)).setText(city + " " + name);
        ( (TextView) ll.findViewById(R.id.record)).setText(getRecordAsString());

        return ll;
    }

    public String getRecordAsString(){
        String record = "(" + String.valueOf(wins) + "-" + String.valueOf(losses) + "-" + String.valueOf(otLosses) + "-" + String.valueOf(soLosses) + ")";
        return record;
    }

    private ArrayList<Team> sortTeamsByRank(ArrayList<Team> teams){
        ArrayList<Team> sorted = new ArrayList<>();

        int reps = teams.size();
        for(int i = 0; i < reps; i++){

            int max = 0;
            int pos = 0;

            for(int j = 0; j < teams.size(); j++){
                if(teams.get(j).points > max){
                    max = teams.get(j).points;
                    pos = j;
                }
            }
            sorted.add(teams.get(pos));
            teams.remove(pos);
        }

        return sorted;
    }

    public int getRank(){
        DatabaseHelper dbh = new DatabaseHelper(App.context);
        ArrayList<Team> west = dbh.getTeamsFromDivision("west", season);
        ArrayList<Team> east = dbh.getTeamsFromDivision("east", season);
        ArrayList<Team> central = dbh.getTeamsFromDivision("central", season);

        dbh.close();

        ArrayList<Team> teams = new ArrayList<>();
        teams.addAll(west);
        teams.addAll(central);
        teams.addAll(east);
        teams = sortTeamsByRank(teams);

        for(int i = 1; i <= teams.size(); i++){
            if(teams.get(i-1).abbreviation.equals(abbreviation)){
                return i;
            }
        }
        return 0;
    }

    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_KEY, key);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_ABBREVIATION, abbreviation);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_NAME, name);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_CITY, city);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_ID, pointstreakID);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_SEASON, season);

        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_GAMES_PLAYED,gamesPlayed);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_WINS,wins);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_LOSSES,losses);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_OVERTIME_LOSSES,otLosses);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_SHOOTOUT_LOSSES,soLosses);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_POINTS,points);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_GOALS_FOR, goalsFor);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_GOALS_AGAINST,goalsAgainst);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_PENALTY_MINUTES,penaltyMinutes);
        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_STREAK,streak);

        values.put(DatabaseContract.TeamEntry.COLUMN_NAME_RANK, rank);

        return values;
    }

    public static String[] projection = {
            DatabaseContract.TeamEntry.COLUMN_NAME_ABBREVIATION,
            DatabaseContract.TeamEntry.COLUMN_NAME_CITY,
            DatabaseContract.TeamEntry.COLUMN_NAME_NAME,
            DatabaseContract.TeamEntry.COLUMN_NAME_ID,
            DatabaseContract.TeamEntry.COLUMN_NAME_SEASON
    };
}
