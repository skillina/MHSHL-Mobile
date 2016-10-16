package net.skillina.mhshl_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.renderscript.ScriptGroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Alex on 7/28/2016.
 */
public class SyncManager {

    public SyncManager(){}

    public static void synchronize(Context context){
        System.out.println("Synchronize call initiated");
        NetManager nm = new NetManager(context);
        nm.queueData("getSeasons", "v", "");
        nm.queueData("getScoringEvents", "v", "");
        nm.queueData("getPenaltyEvents", "v", "");
        nm.queueData("getGoaliePerformances", "v", "");

        nm.queueData("getGames", "v", "");
        nm.queueData("getTeams", "v", "");
        nm.queueData("getPlayers", "v", "");

        nm.startTransactions();
    }

    public static void process(String page){
        String id = page.substring(0,2);
        page = page.substring(2);

        System.out.println("Received page to process using page method, ID:" + id);

        if(id.equalsIgnoreCase("SN"))
            processSeasons(page);
        else if(id.equalsIgnoreCase("TM"))
            processTeams(page);
        else if(id.equalsIgnoreCase("GM"))
            processGames(page);
        else if(id.equalsIgnoreCase("PL"))
            processPlayers(page);
        else if(id.equalsIgnoreCase("SE"))
            processGoals(page);
        else if(id.equalsIgnoreCase("PE"))
            processPenalties(page);
        else if(id.equalsIgnoreCase("GP"))
            processGoaliePerformances(page);
        else
            System.out.print("Error Processing:\n" + id+page);
    }

    private static void processSeasons(String page){
        System.out.println("Received seasons to process...");
        page = page.replace("[", "");
        page = page.replace("]", "");
        page = page.replace("(", "");

        DatabaseHelper dbh = new DatabaseHelper(App.context);
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.beginTransaction();

        String[] seasons = page.split("[)]");

        try {
            for (int i = 0; i < seasons.length - 1; i++) {
                Season season = new Season(seasons[i]);
                ContentValues cv = season.getContentValues();
                db.replace(DatabaseContract.SeasonEntry.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        db.close();
        dbh.close();

        System.out.println("Done.");

    }

    private static void processTeams(String page){
        System.out.println("Received teams to process...");
        page = page.replace("[", "");
        page = page.replace("]", "");
        page = page.replace("(", "");


        String[] teams = page.split("[)]");
        ArrayList<Team> teamList = new ArrayList<>();

        for(int i = 0; i < teams.length - 1; i++)
            teamList.add(new Team(teams[i]));

        DatabaseHelper dbh = new DatabaseHelper(App.context);
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.beginTransaction();

        try {
            for (int i = 0; i < teamList.size(); i++) {
                ContentValues cv = teamList.get(i).getContentValues();
                db.replace(DatabaseContract.TeamEntry.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        db.close();
        dbh.close();

        System.out.println("Done.");

    }

    private static void processGames(String page){
        System.out.println("Received games to process...");
        page = page.replace("[", "");
        page = page.replace("]", "");
        page = page.replace("(", "");


        String[] games = page.split("[)]");
        ArrayList<Game> gameList = new ArrayList<>();
        for(int i = 0; i < games.length - 1; i++)
            gameList.add(new Game(games[i]));


        DatabaseHelper dbh = new DatabaseHelper(App.context);
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.beginTransaction();


        try {
            for (int i = 0; i < gameList.size(); i++) {
                ContentValues cv = gameList.get(i).getContentValues();
                db.replace(DatabaseContract.GameEntry.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        db.close();
        dbh.close();

        System.out.println("Done.");

    }

    private static void processPlayers(String page){
        System.out.println("Received players to process...");
        page = page.replace("[", "");
        page = page.replace("]", "");
        page = page.replace("(", "");



        String[] players = page.split("[)]");

        System.out.println("Adding Players to ArrayList");
        ArrayList<Player> playerList = new ArrayList<>();
        for(int i = 0; i < players.length -1; i++) {
            System.out.println("Adding Player " + String.valueOf(i));
            playerList.add(new Player(players[i]));
        }

        DatabaseHelper dbh = new DatabaseHelper(App.context);
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.beginTransaction();

        System.out.println("Adding Players to database");
        try {
            for (int i = 0; i < playerList.size(); i++) {
                ContentValues cv = playerList.get(i).getContentValues();
                db.replace(DatabaseContract.PlayerEntry.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        db.close();
        dbh.close();

        System.out.println("Done.");

    }

    private static void processGoals(String page){
        System.out.println("Received goals to process...");
        page = page.replace("[", "");
        page = page.replace("]", "");
        page = page.replace("(", "");

        DatabaseHelper dbh = new DatabaseHelper(App.context);
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.beginTransaction();

        String[] goals = page.split("[)]");

        try {
            for (int i = 0; i < goals.length - 1; i++) {
                Goal goal = new Goal(goals[i]);
                ContentValues cv = goal.getContentValues();
                db.replace(DatabaseContract.GoalEntry.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        db.close();
        dbh.close();

        System.out.println("Done.");

    }


    private static void processPenalties(String page){
        System.out.println("Received Penalties to process...");
        page = page.replace("[", "");
        page = page.replace("]", "");
        page = page.replace("(", "");

        DatabaseHelper dbh = new DatabaseHelper(App.context);
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.beginTransaction();

        String[] penalties = page.split("[)]");

        try {
            for (int i = 0; i < penalties.length - 1; i++) {
                Penalty penalty = new Penalty(penalties[i]);
                ContentValues cv = penalty.getContentValues();
                db.replace(DatabaseContract.PenaltyEntry.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        db.close();
        dbh.close();

        System.out.println("Done.");
    }

    private static void processGoaliePerformances(String page){
        System.out.println("Received goaliePerformances to process...");
        System.out.print(page);
        page = page.replace("[", "");
        page = page.replace("]", "");
        page = page.replace("(", "");

        DatabaseHelper dbh = new DatabaseHelper(App.context);
        SQLiteDatabase db = dbh.getWritableDatabase();
        db.beginTransaction();

        String[] goaliePerformances = page.split("[)]");

        try {
            for (int i = 0; i < goaliePerformances.length - 1; i++) {
                GoaliePerformance gp = new GoaliePerformance(goaliePerformances[i]);
                ContentValues cv = gp.getContentValues();
                db.replace(DatabaseContract.GoalieEntry.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        }finally{
            db.endTransaction();
        }
        db.close();
        dbh.close();

        System.out.println("Done.");
    }


    /*

    public static void process(InputStream is){
        String id = "";

        for(int i = 0; i < 2; i++)
            try {
                id += (char) is.read();
            }catch(IOException e) {

            }

        System.out.println("Received page to process using InputStream method, ID:" + id);

        if(id.equalsIgnoreCase("SN"))
            processSeasons(is);
        else if(id.equalsIgnoreCase("TM"))
            processTeams(is);
        else if(id.equalsIgnoreCase("GM"))
            processGames(is);
        else if(id.equalsIgnoreCase("PL"))
            processPlayers(is);
        else if(id.equalsIgnoreCase("SE"))
            processGoals(is);
        else if(id.equalsIgnoreCase("PE"))
            processPenalties(is);
        else if(id.equalsIgnoreCase("GP"))
            processGoaliePerformances(is);
        else
            System.out.print("Error Processing:\n" + id);
    }

    private static String getValue(InputStream is){
        String tmp = "";
        try {

            char c = (char) is.read();
            if(c == ',' || c == ')' || c == ']')
                return "0";

            do {
                tmp += c;
                c = (char) is.read();
            } while (c != ',' && c != ')' && c != ']');

            return tmp;

        }catch(IOException e){
            return "0";
        }
    }


    private static void processSeasons(InputStream is){
        System.out.println("Received seasons to process...");

        try {

            DatabaseHelper dbh = new DatabaseHelper(App.context);

            while(is.read() != '(') ; // Do nothing until start of entry

            while(is.read() != ']') {
                int seasonID = Integer.parseInt(getValue(is));
                int year = Integer.parseInt(getValue(is));
                int playoffs = Integer.parseInt(getValue(is));
                int varsity = Integer.parseInt(getValue(is));

                Season s = new Season(seasonID, year, playoffs, varsity);
                dbh.addSeason(s);
            }
            System.out.println("Done.");
        }catch(IOException e){

        }

    }


    private static void processTeams(InputStream is){
        System.out.println("Received teams to process...");

        try {

            DatabaseHelper dbh = new DatabaseHelper(App.context);

            while(is.read() != '(') ; // Do nothing until start of entry

            while(is.read() != ']') {

                String abbr = getValue(is);
                String name = getValue(is);
                String city = getValue(is);
                int id = Integer.parseInt(getValue(is));
                int season = Integer.parseInt(getValue(is));

                Team t = new Team(abbr, name, city, id, season);
                dbh.addTeam(t);
            }
            System.out.println("Done.");
        }catch(IOException e) {

        }

    }
    private static void processGames(InputStream is){
        System.out.println("Received games to process...");

        try {

            DatabaseHelper dbh = new DatabaseHelper(App.context);

            while(is.read() != '(') ; // Do nothing until start of entry

            while(is.read() != ']') {

                String id = getValue(is);
                int month = Integer.parseInt(getValue(is));
                int day = Integer.parseInt(getValue(is));
                int year = Integer.parseInt(getValue(is));
                int startTime = Integer.parseInt(getValue(is));

                String home = getValue(is);
                String away = getValue(is);

                int homeShots = Integer.parseInt(getValue(is));
                int awayShots = Integer.parseInt(getValue(is));

                int period = Integer.parseInt(getValue(is));
                int time = Integer.parseInt(getValue(is));

                int pointstreakID = Integer.parseInt(getValue(is));
                int season = Integer.parseInt(getValue(is));

                Game g = new Game(id, pointstreakID, season, day, month, year, startTime, home, away);
                g.homeShots = homeShots;
                g.awayShots = awayShots;
                g.period = period;
                g.time = time;

                dbh.addGame(g);
            }
            System.out.println("Done.");
        }catch(IOException e) {

        }

    }
    private static void processPlayers(InputStream is){
        System.out.println("Received players to process...");

        try {

            DatabaseHelper dbh = new DatabaseHelper(App.context);

            while(is.read() != '(') ; // Do nothing until start of entry

            while(is.read() != ']') {

                String team = getValue(is);
                String name = getValue(is);
                int id = Integer.parseInt(getValue(is));
                int num = Integer.parseInt(getValue(is));
                int season = Integer.parseInt(getValue(is));
                int gamesPlayed = Integer.parseInt(getValue(is));
                int goalie = Integer.parseInt(getValue(is));

                Player p = new Player(team, name, id, num, season, goalie);
                p.gp = gamesPlayed;

                dbh.addPlayer(p);
            }
            System.out.println("Done.");
        }catch(IOException e) {

        }

    }
    private static void processPenalties(InputStream is){
        System.out.println("Received penalties to process...");

        try {

            DatabaseHelper dbh = new DatabaseHelper(App.context);

            while(is.read() != '(') ; // Do nothing until start of entry

            while(is.read() != ']') {

                String id = getValue(is);
                String team = getValue(is);
                int player = Integer.parseInt(getValue(is));
                int duration = Integer.parseInt(getValue(is));
                int period = Integer.parseInt(getValue(is));

                int time = Integer.parseInt(getValue(is));
                int timeRemaining = Integer.parseInt(getValue(is));

                int goalsWhile = Integer.parseInt(getValue(is));
                String scoredOn = getValue(is);
                String offense = getValue(is);
                int season = Integer.parseInt(getValue(is));

                Penalty p = new Penalty(id, team, player, duration, period, time, offense, season);
                p.timeRemaining = timeRemaining;
                p.goalsWhile = goalsWhile;
                p.scoredOn = !scoredOn.equals("");
                dbh.addPenalty(p);
            }
            System.out.println("Done.");
        }catch(IOException e) {

        }

    }
    private static void processGoals(InputStream is){
        System.out.println("Received goals to process...");

        try {

            DatabaseHelper dbh = new DatabaseHelper(App.context);

            while(is.read() != '(') ; // Do nothing until start of entry

            while(is.read() != ']') {

                String id = getValue(is);
                String team = getValue(is);
                int scorer = Integer.parseInt(getValue(is));
                int assist1 = Integer.parseInt(getValue(is));
                int assist2 = Integer.parseInt(getValue(is));

                int period = Integer.parseInt(getValue(is));
                int time = Integer.parseInt(getValue(is));

                int powerplay = Integer.parseInt(getValue(is));
                int season = Integer.parseInt(getValue(is));

                Goal g = new Goal(id, team, scorer, assist1, assist2, period, time, powerplay, season);

                dbh.addGoal(g);
            }
            System.out.println("Done.");
        }catch(IOException e) {

        }

    }
    private static void processGoaliePerformances(InputStream is){
        System.out.println("Received goalie performances to process...");

        try {

            DatabaseHelper dbh = new DatabaseHelper(App.context);

            while(is.read() != '(') ; // Do nothing until start of entry

            while(is.read() != ']') {

                String id = getValue(is);
                String team = getValue(is);
                int player = Integer.parseInt(getValue(is));
                int minutes = Integer.parseInt(getValue(is));
                int ga = Integer.parseInt(getValue(is));

                int sa = Integer.parseInt(getValue(is));
                int season = Integer.parseInt(getValue(is));

                GoaliePerformance g = new GoaliePerformance(id, team, player, season);
                g.seconds = minutes;
                g.goalsAgainst = ga;
                g.shotsAgainst = sa;

                dbh.addGoaliePerformance(g);
            }
            System.out.println("Done.");
        }catch(IOException e) {

        }

    }

    */

}


