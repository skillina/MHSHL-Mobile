package net.skillina.mhshl_android;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Alex on 7/18/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper implements AdapterView.OnItemSelectedListener{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MHSHL.db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db){
        System.out.println("Creating Database");
        db.execSQL(DatabaseContract.SQL_CREATE_ENTRIES_SEASONS);
        db.execSQL(DatabaseContract.SQL_CREATE_ENTRIES_GAMES);
        db.execSQL(DatabaseContract.SQL_CREATE_ENTRIES_TEAMS);
        db.execSQL(DatabaseContract.SQL_CREATE_ENTRIES_PLAYERS);
        db.execSQL(DatabaseContract.SQL_CREATE_ENTRIES_GOALIES);
        db.execSQL(DatabaseContract.SQL_CREATE_ENTRIES_GOALS);
        db.execSQL(DatabaseContract.SQL_CREATE_ENTRIES_PENALTIES);
        db.execSQL(DatabaseContract.SQL_CREATE_ENTRIES_CONTEXT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        System.out.println("Upgrading Database");
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES_SEASONS);
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES_GAMES);
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES_TEAMS);
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES_PLAYERS);
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES_GOALIES);
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES_GOALS);
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES_PENALTIES);
        db.execSQL(DatabaseContract.SQL_DELETE_ENTRIES_CONTEXT);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        // Do nothing, for now...
    }

    public void onNothingSelected(AdapterView<?> parent){

    }

    public String[] getSeasonArray(){
        ArrayList<String> tmp = new ArrayList<String>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT " + DatabaseContract.SeasonEntry.COLUMN_NAME_YEAR + " FROM " + DatabaseContract.SeasonEntry.TABLE_NAME, null);

        if(c.getCount() <= 0) {
            tmp.add("ERR: No seasons found");
        }else{
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++){
                tmp.add(String.valueOf(c.getInt(c.getColumnIndexOrThrow(DatabaseContract.SeasonEntry.COLUMN_NAME_YEAR))));
                if(!c.isLast() && !c.isAfterLast())
                    c.moveToNext();
            }
        }

        c.close();
        db.close();

        String[] stringArray = Arrays.copyOf(tmp.toArray(), tmp.toArray().length, String[].class);
        for(int i = 0; i < stringArray.length; i++){
            if(!stringArray[i].startsWith("ERR"))
                stringArray[i] = stringArray[i] + " - " + String.valueOf(Integer.valueOf(stringArray[i]) + 1);
        }
        return stringArray;

    }

    public ArrayList<Team> getTeamsFromDivision(String division, int season){
        ArrayList<Team> teams = new ArrayList<>();

        if(division.equalsIgnoreCase("Central")){
            teams.add(getTeam("DMO", season));
            teams.add(getTeam("DMC", season));
            teams.add(getTeam("AMS", season));
            teams.add(getTeam("MCM", season));
        }else if(division.equalsIgnoreCase("East")){
            teams.add(getTeam("QCB", season));
            teams.add(getTeam("CDR", season));
            teams.add(getTeam("DBQ", season));
            teams.add(getTeam("WAT", season));
        }else if(division.equalsIgnoreCase("West")){
            teams.add(getTeam("KCJ", season));
            teams.add(getTeam("LJS", season));
            teams.add(getTeam("OJL", season));
            teams.add(getTeam("SCM", season));
        }

        return teams;
    }

    public Game getGame(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.GameEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GameEntry.COLUMN_NAME_POINTSTREAK_ID + " = " + String.valueOf(id), null);

        if(c.getCount() <= 0) {
            c.close();
            return new Game();
        }else{
            c.moveToFirst();
            Game g = new Game(c);
            c.close();
            return g;
        }
    }

    public ArrayList<Game> getGames(int season){
        System.out.println("Getting full season games");
        ArrayList<Game> games = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.GameEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GameEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season) +
                " ORDER BY " + DatabaseContract.GameEntry.COLUMN_NAME_YEAR + " DESC, " +
                DatabaseContract.GameEntry.COLUMN_NAME_GAME_ID + " DESC" , null);

        if(c.getCount() <= 0) {
            return games;
        }else{
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++){
                games.add(new Game(c));
                if(!c.isLast())
                    c.moveToNext();
            }
        }

        c.close();
        db.close();

        return games;
    }

    public ArrayList<Game> getGames(String team, int season){
        ArrayList<Game> games = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.GameEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GameEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season) + " AND (" +
                DatabaseContract.GameEntry.COLUMN_NAME_HOME_TEAM + " = '" + String.valueOf(team) + "' OR " +
                DatabaseContract.GameEntry.COLUMN_NAME_AWAY_TEAM + " = '" + String.valueOf(team) + "')" +
                " ORDER BY " + DatabaseContract.GameEntry.COLUMN_NAME_YEAR + " DESC, " +
                DatabaseContract.GameEntry.COLUMN_NAME_GAME_ID + " DESC" , null);
        if(c.getCount() <= 0) {
            return games;
        }else{
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++){
                games.add(new Game(c));
                if(!c.isLast() && !c.isAfterLast())
                    c.moveToNext();
            }
        }

        c.close();
        db.close();

        return games;
    }

    public ArrayList<Goal> getGoalsFromGame(String gameID){
        ArrayList<Goal> goals = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GoalEntry.COLUMN_NAME_GAME_ID + " = '" + gameID + "'", null);

        if(c.getCount() <= 0) {
            return goals;
        }else{
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++){
                goals.add(new Goal(c));
                if(!c.isLast() && !c.isAfterLast())
                    c.moveToNext();
            }
        }

        c.close();
        db.close();

        return goals;
    }

    public ArrayList<Penalty> getPenaltiesFromPlayer(int pointstreakID, int season){
        ArrayList<Penalty> penalties = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.PenaltyEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_PLAYER + " = " + String.valueOf(pointstreakID) + " AND " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season), null);

        if(c.getCount() <= 0) {
            return penalties;
        }else{
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++){
                penalties.add(new Penalty(c));
                if(!c.isLast() && !c.isAfterLast())
                    c.moveToNext();
            }
        }

        c.close();
        db.close();

        return penalties;
    }

    public ArrayList<Goal> getGoalsFromPlayer(int pointstreakID, int season){
        ArrayList<Goal> goals = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SCORER + " = " + String.valueOf(pointstreakID) + " AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season), null);

        if(c.getCount() <= 0) {
            return goals;
        }else{
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++){
                goals.add(new Goal(c));
                if(!c.isLast() && !c.isAfterLast())
                    c.moveToNext();
            }
        }

        c.close();
        db.close();

        return goals;
    }

    public int countGoalsFromPlayer(int pointstreakID, int season){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE " +
                                                        DatabaseContract.GoalEntry.COLUMN_NAME_SCORER  + " = " + String.valueOf(pointstreakID) + " AND " +
                                                        DatabaseContract.GoalEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season));
        return (int) statement.simpleQueryForLong();
    }

    public int countGoalsFromPlayer(int pointstreakID, int season, int pp){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SCORER  + " = " + String.valueOf(pointstreakID) + " AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season) + " AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_POWERPLAY + " = " + String.valueOf(pp));
        return (int) statement.simpleQueryForLong();
    }

    public int countAssistsFromPlayer(int pointstreakID, int season){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement statement = db.compileStatement("SELECT COUNT(*) FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE (" +
                DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_1 + " = " + String.valueOf(pointstreakID) + " OR " +
                DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_2 + " = " + String.valueOf(pointstreakID) + ") AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season));
        return (int) statement.simpleQueryForLong();
    }

    public int countPIMsFromPlayer(int pointstreakID, int season){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteStatement statement = db.compileStatement("SELECT TOTAL(" + DatabaseContract.PenaltyEntry.COLUMN_NAME_DURATION + ") FROM " + DatabaseContract.PenaltyEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_PLAYER + " = " + String.valueOf(pointstreakID) + " AND " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season));
        return (int) statement.simpleQueryForLong();
    }

    public int[] getImportantCounts(int pointstreakID, int season){

        SQLiteDatabase db = getReadableDatabase();

        SQLiteStatement countGoals = db.compileStatement("SELECT COUNT(*) FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SCORER  + " = " + String.valueOf(pointstreakID) + " AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season));
        SQLiteStatement countPPGoals = db.compileStatement("SELECT COUNT(*) FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SCORER  + " = " + String.valueOf(pointstreakID) + " AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season) + " AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_POWERPLAY + " = " + String.valueOf(1));
        SQLiteStatement countSHGoals = db.compileStatement("SELECT COUNT(*) FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SCORER  + " = " + String.valueOf(pointstreakID) + " AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season) + " AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_POWERPLAY + " = " + String.valueOf(-1));
        SQLiteStatement countAssists = db.compileStatement("SELECT COUNT(*) FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE (" +
                DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_1 + " = " + String.valueOf(pointstreakID) + " OR " +
                DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_2 + " = " + String.valueOf(pointstreakID) + ") AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season));
        SQLiteStatement countPIMs = db.compileStatement("SELECT TOTAL(" + DatabaseContract.PenaltyEntry.COLUMN_NAME_DURATION + ") FROM " + DatabaseContract.PenaltyEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_PLAYER + " = " + String.valueOf(pointstreakID) + " AND " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season));

        int[] tmp = {(int) countGoals.simpleQueryForLong(), (int) countPPGoals.simpleQueryForLong(),
                    (int) countSHGoals.simpleQueryForLong(), (int) countAssists.simpleQueryForLong(),
                    (int) countPIMs.simpleQueryForLong()};
        return tmp;
    }

    public ArrayList<Goal> getAssistsFromPlayer(int pointstreakID, int season){
        ArrayList<Goal> goals = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE (" +
                DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_1 + " = " + String.valueOf(pointstreakID) + " OR " +
                DatabaseContract.GoalEntry.COLUMN_NAME_ASSIST_2 + " = " + String.valueOf(pointstreakID) + ") AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(season), null);

        if(c.getCount() <= 0) {
            return goals;
        }else{
            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++){
                goals.add(new Goal(c));
                if(!c.isLast() && !c.isAfterLast())
                    c.moveToNext();
            }
        }

        c.close();
        db.close();

        return goals;
    }

    public Season getSeason(int id){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.SeasonEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.SeasonEntry.COLUMN_NAME_ID + " = " + String.valueOf(id), null);

        if(c.getCount() <= 0){
            c.close();
            db.close();
            return new Season();
        }else {
            c.moveToFirst();
            Season s = new Season(c);
            c.close();
            db.close();
            return s;
        }
    }

    public Season getSeason(int year, int varsity){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.SeasonEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.SeasonEntry.COLUMN_NAME_YEAR + " = " + String.valueOf(year) + " AND " +
                DatabaseContract.SeasonEntry.COLUMN_NAME_VARSITY + " = " + String.valueOf(varsity), null);

        if(c.getCount() <= 0){
            c.close();
            db.close();
            return new Season();
        }else{
            c.moveToFirst();
            Season s = new Season(c);
            c.close();
            db.close();
            return s;
        }

    }

    public Team getTeam(int id, int s) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.TeamEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.TeamEntry.COLUMN_NAME_ID + " = " + String.valueOf(id) + " AND " +
                DatabaseContract.TeamEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(s), null);

        if (c.getCount() <= 0){
            c.close();
            db.close();
            return new Team();
        }else{
            c.moveToFirst();
            Team t = new Team(c);
            c.close();
            db.close();
            return t;
        }
    }

    public Team getTeam(String abbreviation, int s){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.TeamEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.TeamEntry.COLUMN_NAME_ABBREVIATION + " = '" + abbreviation + "' AND " +
                DatabaseContract.TeamEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(s), null);
        if(c.getCount() <= 0) {
            c.close();
            db.close();
            return new Team();
        }else{
            c.moveToFirst();
            Team t = new Team(c);
            c.close();
            db.close();
            return t;
        }

    }

    public Player getPlayer(int id, int s) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.PlayerEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.PlayerEntry.COLUMN_NAME_POINTSTREAK_ID + " = " + String.valueOf(id) + " AND " +
                DatabaseContract.PlayerEntry.COLUMN_NAME_SEASON + " = " + String.valueOf(s), null);
        db.close();
        if (c.getCount() <= 0){
            c.close();
            db.close();
            return new Player();
        }else {
            c.moveToFirst();
            Player p = new Player(c);
            c.close();
            db.close();
            return p;
        }
    }

    public Goal getGoal(String gameID, int period, int time) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.GoalEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GoalEntry.COLUMN_NAME_GAME_ID + " = '" + gameID + "' AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_PERIOD + " = " + String.valueOf(period) + " AND " +
                DatabaseContract.GoalEntry.COLUMN_NAME_TIME + " = " + String.valueOf(time), null);

        if (c.getCount() <= 0) {
            c.close();
            db.close();
            return new Goal();
        } else {
            c.moveToFirst();
            Goal g = new Goal(c);
            c.close();
            db.close();
            return g;
        }
    }

    public Penalty getPenalty(String gameID, int player, int period, int time, String offense) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.PenaltyEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_GAME_ID + " = '" + gameID + "' AND " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_OFFENSE + " = '" + offense + "' AND " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_PERIOD + " = " + String.valueOf(period) + " AND " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_PLAYER + " = " + String.valueOf(player) + " AND " +
                DatabaseContract.PenaltyEntry.COLUMN_NAME_TIME + " = " + String.valueOf(time), null);

        if (c.getCount() <= 0){
            c.close();
            db.close();
            return new Penalty();
        }else {
            c.moveToFirst();
            Penalty p = new Penalty(c);
            c.close();
            db.close();
            return p;
        }
    }

    public GoaliePerformance getGoaliePerformance(String gameID, int player) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseContract.GoalieEntry.TABLE_NAME + " WHERE " +
                DatabaseContract.GoalieEntry.COLUMN_NAME_GAME_ID + " = '" + gameID + "' AND " +
                DatabaseContract.GoalieEntry.COLUMN_NAME_PLAYER + " = " + String.valueOf(player), null);

        if (c.getCount() <= 0) {
            c.close();
            db.close();
            return new GoaliePerformance();
        } else {
            c.moveToFirst();
            GoaliePerformance gp = new GoaliePerformance(c);
            c.close();
            db.close();
            return gp;
        }
    }
    public void addSeason(Season s){
        if(getSeason(s.pointstreakID).pointstreakID != s.pointstreakID){
            SQLiteDatabase db = getWritableDatabase();
            // System.out.println("Adding Season " + String.valueOf(s.pointstreakID) + " (" + String.valueOf(s.year) + ")");

            long newRowID = db.insert(DatabaseContract.SeasonEntry.TABLE_NAME,
                    DatabaseContract.SeasonEntry.COLUMN_NAME_PLAYOFFS, s.getContentValues());
            db.close();
        }else {
            // System.out.println("Season " + String.valueOf(s.pointstreakID) + " (" + String.valueOf(s.year) + ")" + " already exists. Skipped.");
        }
    }

    public void addTeam(Team t){
        if(getTeam(t.pointstreakID, t.season).pointstreakID != t.pointstreakID){
            SQLiteDatabase db = getWritableDatabase();
            // System.out.println("Adding Team " + t.abbreviation);

            long newRowID = db.insert(DatabaseContract.TeamEntry.TABLE_NAME,
                    DatabaseContract.TeamEntry.COLUMN_NAME_CITY, t.getContentValues());
            db.close();
        }else {
            // System.out.println("Team " + t.abbreviation + " (" + String.valueOf(t.season) + ") already exists. Skipped.");
        }
    }

    public void addGame(Game g){
        if(getGame(g.pointstreakID).pointstreakID != g.pointstreakID){
            SQLiteDatabase db = getWritableDatabase();
            // System.out.println("Adding Game " + g.gameID);

            long newRowID = db.insert(DatabaseContract.GameEntry.TABLE_NAME,
                    DatabaseContract.GameEntry.COLUMN_NAME_START_TIME, g.getContentValues());
            db.close();
        }else {
            // System.out.println("Game " + g.gameID + " already exists. Skipped.");
        }
    }

    public void addPlayer(Player p){
        if(getPlayer(p.pointstreakID, p.season).pointstreakID != p.pointstreakID){
            SQLiteDatabase db = getWritableDatabase();
            // System.out.println("Adding Player " + p.name + " (" + String.valueOf(p.season));

            long newRowID = db.insert(DatabaseContract.PlayerEntry.TABLE_NAME,
                    DatabaseContract.PlayerEntry.COLUMN_NAME_ASSISTS, p.getContentValues());
            db.close();
        }else {
            // System.out.println("Player " + p.name+ " already exists. Skipped.");
        }
    }

    public void addGoal(Goal g){
        if(getGoal(g.gameID, g.period, g.time).scorer != g.scorer) {
            SQLiteDatabase db = getWritableDatabase();
            long newRowID = db.insert(DatabaseContract.GoalEntry.TABLE_NAME,
                    DatabaseContract.GoalEntry.COLUMN_NAME_SCORER, g.getContentValues());
            db.close();
        }
    }

    public void addPenalty(Penalty p){
        if(getPenalty(p.gameID, p.player, p.period, p.time, p.offense).player != p.player){
            // System.out.println(getPenalty(p.gameID, p.player, p.period, p.time, p.offense).player + "," + p.player);
            SQLiteDatabase db = getWritableDatabase();
            long newRowID = db.insert(DatabaseContract.PenaltyEntry.TABLE_NAME,
                    DatabaseContract.PenaltyEntry.COLUMN_NAME_OFFENSE, p.getContentValues());
            db.close();
        }
    }

    public void addGoaliePerformance(GoaliePerformance g){
        if(getGoaliePerformance(g.gameID, g.player).player != g.player){
            SQLiteDatabase db = getWritableDatabase();
            // System.out.println("Adding GoaliePerformance " + g.gameID + " " + String.valueOf(g.player));

            long newRowID = db.insert(DatabaseContract.GoalieEntry.TABLE_NAME,
                    DatabaseContract.GoalieEntry.COLUMN_NAME_PLAYER, g.getContentValues());
            db.close();
        }else {
            // System.out.println("Game " + g.gameID + " " + String.valueOf(g.player) + " already exists. Skipped.");
        }
    }
}
