package net.skillina.mhshl_android;

import android.provider.BaseColumns;

/**
 * Created by Alex on 7/17/2016.
 */
public final class DatabaseContract {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES_SEASONS =
            "CREATE TABLE " + SeasonEntry.TABLE_NAME + " ( " +
            SeasonEntry.COLUMN_NAME_ID + " INTEGER, " +
            SeasonEntry.COLUMN_NAME_KEY + " INTEGER UNIQUE, " +
            SeasonEntry.COLUMN_NAME_YEAR + " INTEGER, " +
            SeasonEntry.COLUMN_NAME_PLAYOFFS + " INTEGER, " +
            SeasonEntry.COLUMN_NAME_VARSITY + " INTEGER );";

    public static final String SQL_CREATE_ENTRIES_PLAYERS =
            " CREATE TABLE " + PlayerEntry.TABLE_NAME + " ( " +
            PlayerEntry.COLUMN_NAME_TEAMID + TEXT_TYPE + COMMA_SEP +
            PlayerEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
            PlayerEntry.COLUMN_NAME_POINTSTREAK_ID + " INTEGER," +
            PlayerEntry.COLUMN_NAME_JERSEY_NUMBER + " INTEGER," +
            PlayerEntry.COLUMN_NAME_SEASON + " INTEGER," +
            PlayerEntry.COLUMN_NAME_GAMES_PLAYED + " INTEGER," +
            PlayerEntry.COLUMN_NAME_GOALS + " INTEGER," +
            PlayerEntry.COLUMN_NAME_ASSISTS + " INTEGER," +
            PlayerEntry.COLUMN_NAME_POWERPLAY_GOALS + " INTEGER," +
            PlayerEntry.COLUMN_NAME_SHORTHANDED_GOALS + " INTEGER," +
            PlayerEntry.COLUMN_NAME_PENALTY_MINUTES + " INTEGER," +
            PlayerEntry.COLUMN_NAME_GOAL_STREAK + " INTEGER," +
            PlayerEntry.COLUMN_NAME_POINT_STREAK + " INTEGER," +
            PlayerEntry.COLUMN_NAME_MINUTES_PLAYED + " INTEGER," +
            PlayerEntry.COLUMN_NAME_SHOTS_ON + " INTEGER," +
            PlayerEntry.COLUMN_NAME_GOALS_AGAINST + " INTEGER," +
            PlayerEntry.COLUMN_NAME_GOALS_AGAINST_AVERAGE + " REAL," +
            PlayerEntry.COLUMN_NAME_SAVES + " INTEGER," +
            PlayerEntry.COLUMN_NAME_SAVE_PERCENTAGE + " REAL," +
            PlayerEntry.COLUMN_NAME_KEY + " INTEGER UNIQUE," +
            PlayerEntry.COLUMN_NAME_IS_GOALIE + " INTEGER );";

    public static final String SQL_CREATE_ENTRIES_TEAMS =
            " CREATE TABLE " + TeamEntry.TABLE_NAME + " ( " +
            TeamEntry.COLUMN_NAME_ABBREVIATION + TEXT_TYPE + COMMA_SEP +
            TeamEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
            TeamEntry.COLUMN_NAME_CITY + TEXT_TYPE + COMMA_SEP +
            TeamEntry.COLUMN_NAME_ID + " INTEGER," +
            TeamEntry.COLUMN_NAME_GAMES_PLAYED + " INTEGER," +
            TeamEntry.COLUMN_NAME_WINS + " INTEGER," +
            TeamEntry.COLUMN_NAME_LOSSES + " INTEGER," +
            TeamEntry.COLUMN_NAME_OVERTIME_LOSSES + " INTEGER," +
            TeamEntry.COLUMN_NAME_SHOOTOUT_LOSSES + " INTEGER," +
            TeamEntry.COLUMN_NAME_POINTS + " INTEGER," +
            TeamEntry.COLUMN_NAME_GOALS_FOR + " INTEGER," +
            TeamEntry.COLUMN_NAME_GOALS_AGAINST + " INTEGER," +
            TeamEntry.COLUMN_NAME_PENALTY_MINUTES + " INTEGER," +
            TeamEntry.COLUMN_NAME_STREAK + " INTEGER," +
            TeamEntry.COLUMN_NAME_KEY + " INTEGER UNIQUE," +
            TeamEntry.COLUMN_NAME_SEASON + " INTEGER );";

    public static final String SQL_CREATE_ENTRIES_GAMES =
            " CREATE TABLE " + GameEntry.TABLE_NAME + " ( " +
            GameEntry.COLUMN_NAME_GAME_ID + TEXT_TYPE + "UNIQUE" + COMMA_SEP +
            GameEntry.COLUMN_NAME_MONTH + " INTEGER," +
            GameEntry.COLUMN_NAME_DAY + " INTEGER," +
            GameEntry.COLUMN_NAME_YEAR + " INTEGER," +
            GameEntry.COLUMN_NAME_START_TIME + " INTEGER," +
            GameEntry.COLUMN_NAME_HOME_TEAM + TEXT_TYPE + COMMA_SEP +
            GameEntry.COLUMN_NAME_AWAY_TEAM + TEXT_TYPE + COMMA_SEP +
            GameEntry.COLUMN_NAME_HOME_SCORE + " INTEGER," +
            GameEntry.COLUMN_NAME_AWAY_SCORE + " INTEGER," +
            GameEntry.COLUMN_NAME_HOME_SHOTS + " INTEGER," +
            GameEntry.COLUMN_NAME_KEY + " INTEGER UNIQUE," +
            GameEntry.COLUMN_NAME_AWAY_SHOTS + " INTEGER," +
            GameEntry.COLUMN_NAME_PERIOD + " INTEGER," +
            GameEntry.COLUMN_NAME_TIME + " INTEGER," +
            GameEntry.COLUMN_NAME_POINTSTREAK_ID + " INTEGER," +
            GameEntry.COLUMN_NAME_SEASON + " INTEGER );";

    public static final String SQL_CREATE_ENTRIES_GOALS =
            " CREATE TABLE " + GoalEntry.TABLE_NAME + " ( " +
            GoalEntry.COLUMN_NAME_GAME_ID + TEXT_TYPE + COMMA_SEP +
            GoalEntry.COLUMN_NAME_TEAM_ID + TEXT_TYPE + COMMA_SEP +
            GoalEntry.COLUMN_NAME_SCORER + " INTEGER," +
            GoalEntry.COLUMN_NAME_ASSIST_1 + " INTEGER," +
            GoalEntry.COLUMN_NAME_ASSIST_2 + " INTEGER," +
            GoalEntry.COLUMN_NAME_PERIOD + " INTEGER," +
            GoalEntry.COLUMN_NAME_KEY + " INTEGER UNIQUE," +
            GoalEntry.COLUMN_NAME_TIME + " INTEGER," +
            GoalEntry.COLUMN_NAME_POWERPLAY + " INTEGER," +
            GoalEntry.COLUMN_NAME_SEASON + " INTEGER );";

    public static final String SQL_CREATE_ENTRIES_PENALTIES =
            " CREATE TABLE " + PenaltyEntry.TABLE_NAME + " ( " +
            PenaltyEntry.COLUMN_NAME_GAME_ID + TEXT_TYPE + COMMA_SEP +
            PenaltyEntry.COLUMN_NAME_TEAM_ID + TEXT_TYPE + COMMA_SEP +
            PenaltyEntry.COLUMN_NAME_PLAYER + " INTEGER," +
            PenaltyEntry.COLUMN_NAME_DURATION + " INTEGER," +
            PenaltyEntry.COLUMN_NAME_PERIOD + " INTEGER," +
            PenaltyEntry.COLUMN_NAME_KEY + " INTEGER UNIQUE," +
            PenaltyEntry.COLUMN_NAME_TIME + " INTEGER," +
            PenaltyEntry.COLUMN_NAME_TIME_REMAINING + " INTEGER," +
            PenaltyEntry.COLUMN_NAME_GOALS_DURING_PENALTY + " INTEGER," +
            PenaltyEntry.COLUMN_NAME_SCORED_ON + " INTEGER," +
            PenaltyEntry.COLUMN_NAME_OFFENSE + TEXT_TYPE + COMMA_SEP +
            PenaltyEntry.COLUMN_NAME_SEASON + " INTEGER );";

    public static final String SQL_CREATE_ENTRIES_GOALIES =
            " CREATE TABLE " + GoalieEntry.TABLE_NAME + " ( " +
            GoalieEntry.COLUMN_NAME_GAME_ID + TEXT_TYPE + COMMA_SEP +
            GoalieEntry.COLUMN_NAME_TEAM_ID + TEXT_TYPE + COMMA_SEP +
            GoalieEntry.COLUMN_NAME_PLAYER + " INTEGER," +
            GoalieEntry.COLUMN_NAME_KEY + " INTEGER UNIQUE," +
            GoalieEntry.COLUMN_NAME_MINUTES + " INTEGER," +
            GoalieEntry.COLUMN_NAME_GOALS_AGAINST + " INTEGER," +
            GoalieEntry.COLUMN_NAME_SHOTS_AGAINST + " INTEGER," +
            GoalieEntry.COLUMN_NAME_SEASON + " INTEGER );";

    public static final String SQL_CREATE_ENTRIES_CONTEXT =
            "CREATE TABLE " + ContextEntry.TABLE_NAME + " ( " +
                    ContextEntry.COLUMN_NAME_SEASON + " INTEGER," +
                    ContextEntry.COLUMN_NAME_DIVISION + " INTEGER );";



    public static final String SQL_DELETE_ENTRIES_SEASONS = "DROP TABLE IF EXISTS " + SeasonEntry.TABLE_NAME;
    public static final String SQL_DELETE_ENTRIES_PLAYERS = "DROP TABLE IF EXISTS " + PlayerEntry.TABLE_NAME;
    public static final String SQL_DELETE_ENTRIES_TEAMS = "DROP TABLE IF EXISTS " + TeamEntry.TABLE_NAME;
    public static final String SQL_DELETE_ENTRIES_GOALIES = "DROP TABLE IF EXISTS " + GoalieEntry.TABLE_NAME;
    public static final String SQL_DELETE_ENTRIES_GOALS = "DROP TABLE IF EXISTS " + GoalEntry.TABLE_NAME;
    public static final String SQL_DELETE_ENTRIES_PENALTIES = "DROP TABLE IF EXISTS " + PenaltyEntry.TABLE_NAME;
    public static final String SQL_DELETE_ENTRIES_GAMES = "DROP TABLE IF EXISTS " + GameEntry.TABLE_NAME;
    public static final String SQL_DELETE_ENTRIES_CONTEXT = "DROP TABLE IF EXISTS " + ContextEntry.TABLE_NAME;

    public DatabaseContract(){}

    public static abstract class ContextEntry implements BaseColumns{
        public static final String TABLE_NAME = "appcontext";
        public static final String COLUMN_NAME_SEASON = "season";
        public static final String COLUMN_NAME_DIVISION = "division";
    }

    public static abstract class SeasonEntry implements BaseColumns {
        public static final String TABLE_NAME = "seasons";
        // Left Side
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_PLAYOFFS = "playoffs";
        public static final String COLUMN_NAME_KEY = "majorKey";
        public static final String COLUMN_NAME_VARSITY = "varsity";
        //There is no right-side
    }

    public static abstract class PlayerEntry implements BaseColumns {
        public static final String TABLE_NAME = "players";
        // Left Side
        public static final String COLUMN_NAME_TEAMID = "team";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_POINTSTREAK_ID = "id";
        public static final String COLUMN_NAME_JERSEY_NUMBER = "number";
        public static final String COLUMN_NAME_SEASON = "season";
        public static final String COLUMN_NAME_KEY = "majorKey";
        public static final String COLUMN_NAME_GAMES_PLAYED = "gp";
        public static final String COLUMN_NAME_IS_GOALIE = "goalie";

        // Right Side
        public static final String COLUMN_NAME_GOALS = "goals";
        public static final String COLUMN_NAME_ASSISTS = "assists";
        public static final String COLUMN_NAME_POWERPLAY_GOALS = "ppg";
        public static final String COLUMN_NAME_SHORTHANDED_GOALS = "shg";
        public static final String COLUMN_NAME_PENALTY_MINUTES = "pim";

        public static final String COLUMN_NAME_GOAL_STREAK = "gstreak";
        public static final String COLUMN_NAME_POINT_STREAK = "pstreak";

        public static final String COLUMN_NAME_MINUTES_PLAYED = "minutes";
        public static final String COLUMN_NAME_SHOTS_ON = "shots";
        public static final String COLUMN_NAME_GOALS_AGAINST = "ga";
        public static final String COLUMN_NAME_GOALS_AGAINST_AVERAGE = "gaa";
        public static final String COLUMN_NAME_SAVES = "sv";
        public static final String COLUMN_NAME_SAVE_PERCENTAGE = "svp";
    }

    public static abstract class TeamEntry implements BaseColumns {
        public static final String TABLE_NAME = "teams";
        // Left Side
        public static final String COLUMN_NAME_ABBREVIATION = "abbr";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_SEASON = "season";
        public static final String COLUMN_NAME_GAMES_PLAYED = "gamesPlayed";
        public static final String COLUMN_NAME_KEY = "majorKey";

        // Right Side
        public static final String COLUMN_NAME_WINS = "wins";
        public static final String COLUMN_NAME_LOSSES = "losses";
        public static final String COLUMN_NAME_OVERTIME_LOSSES = "otLosses";
        public static final String COLUMN_NAME_SHOOTOUT_LOSSES = "soLosses";

        public static final String COLUMN_NAME_POINTS = "points";

        public static final String COLUMN_NAME_GOALS_FOR = "goalsFor";
        public static final String COLUMN_NAME_GOALS_AGAINST = "goalsAgainst";
        public static final String COLUMN_NAME_PENALTY_MINUTES = "pims";

        public static final String COLUMN_NAME_STREAK = "streak";
    }

    public static abstract class GameEntry implements BaseColumns {
        public static final String TABLE_NAME = "games";
        // Left Side
        public static final String COLUMN_NAME_GAME_ID = "id";
        public static final String COLUMN_NAME_MONTH = "month";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_START_TIME = "startTime";
        public static final String COLUMN_NAME_HOME_TEAM = "homeTeam";
        public static final String COLUMN_NAME_AWAY_TEAM = "awayTeam";
        public static final String COLUMN_NAME_HOME_SHOTS = "homeShots";
        public static final String COLUMN_NAME_AWAY_SHOTS = "awayShots";
        public static final String COLUMN_NAME_PERIOD = "period";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_POINTSTREAK_ID = "number";
        public static final String COLUMN_NAME_SEASON = "season";
        public static final String COLUMN_NAME_KEY = "majorKey";

        // Right Side
        public static final String COLUMN_NAME_HOME_SCORE = "homeScore";
        public static final String COLUMN_NAME_AWAY_SCORE = "awayScore";
    }

    public static abstract class GoalEntry implements BaseColumns {
        public static final String TABLE_NAME = "scoringEvents";

        // Left Side
        public static final String COLUMN_NAME_GAME_ID= "GameID";
        public static final String COLUMN_NAME_TEAM_ID = "TeamID";
        public static final String COLUMN_NAME_SCORER = "scorer";
        public static final String COLUMN_NAME_ASSIST_1 = "a1";
        public static final String COLUMN_NAME_ASSIST_2 = "a2";
        public static final String COLUMN_NAME_PERIOD = "period";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_POWERPLAY = "pp";
        public static final String COLUMN_NAME_SEASON = "season";
        public static final String COLUMN_NAME_KEY = "majorKey";

        // No right side
    }

    public static abstract class PenaltyEntry implements BaseColumns {
        // Left Side
        public static final String TABLE_NAME = "penaltyEvents";
        public static final String COLUMN_NAME_GAME_ID= "GameID";
        public static final String COLUMN_NAME_TEAM_ID = "TeamID";
        public static final String COLUMN_NAME_PLAYER = "player";
        public static final String COLUMN_NAME_DURATION = "duration";
        public static final String COLUMN_NAME_PERIOD = "period";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_TIME_REMAINING = "timeRemaining";
        public static final String COLUMN_NAME_GOALS_DURING_PENALTY = "goalsWhile";
        public static final String COLUMN_NAME_SCORED_ON = "scoredOn";
        public static final String COLUMN_NAME_OFFENSE = "offense";
        public static final String COLUMN_NAME_SEASON = "season";
        public static final String COLUMN_NAME_KEY = "majorKey";

        // no right side
    }

    public static abstract class GoalieEntry implements BaseColumns {
        public static final String TABLE_NAME = "goaliePerformances";

        // Left side
        public static final String COLUMN_NAME_GAME_ID= "GameID";
        public static final String COLUMN_NAME_TEAM_ID = "TeamID";
        public static final String COLUMN_NAME_PLAYER = "player";
        public static final String COLUMN_NAME_MINUTES = "minutes";
        public static final String COLUMN_NAME_GOALS_AGAINST = "ga";
        public static final String COLUMN_NAME_SHOTS_AGAINST = "sa";
        public static final String COLUMN_NAME_SEASON = "season";
        public static final String COLUMN_NAME_KEY = "majorKey";

        // No right side
    }




}
