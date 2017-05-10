package net.skillina.mhshl_android;

/**
 * Created by Alex on 7/19/2016.
 */
public final class TeamUtils {
    public TeamUtils(){ }


    public static String resolveTeamRank(String abbr, int season){
        DatabaseHelper dbh = new DatabaseHelper(App.context);
        Team team = dbh.getTeam(abbr, season);
        dbh.close();
        return "#" + String.valueOf(team.rank);
    }

}
