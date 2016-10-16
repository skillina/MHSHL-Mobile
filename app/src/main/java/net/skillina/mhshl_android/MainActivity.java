package net.skillina.mhshl_android;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import android.net.Uri;

import layout.DivisionFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RankingFragment.OnFragmentInteractionListener,
        ScheduleFragment.OnFragmentInteractionListener, TeamFragment.OnFragmentInteractionListener, DivisionFragment.OnFragmentInteractionListener {

    private NavigationView navigationView;
    private Spinner seasonSpinner;
    private Spinner varsitySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        SyncManager.synchronize(this);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        seasonSpinner = (Spinner) navigationView.getMenu().findItem(R.id.season_spinner).getActionView();
        varsitySpinner = (Spinner) navigationView.getMenu().findItem(R.id.varsity_spinner).getActionView();

       // createRankingList();
        changeMainFragment(new RankingFragment());
        updateDrawerSpinners();
    }

    public void changeMainFragment(Fragment fragment){
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.replace(R.id.fragment_target, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    public void updateDrawerSpinners(){

        System.out.println("Updating drawer spinners");

        DatabaseHelper dbh = new DatabaseHelper(this.getApplicationContext());

        ArrayAdapter<String> seasonSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dbh.getSeasonArray());
        seasonSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seasonSpinner.setAdapter(seasonSpinnerAdapter);
        seasonSpinner.setOnItemSelectedListener(dbh);

        ArrayAdapter<CharSequence> varsitySpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.varsity_spinner_array, android.R.layout.simple_spinner_item);
        varsitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        varsitySpinner.setAdapter(varsitySpinnerAdapter);
        varsitySpinner.setOnItemSelectedListener(dbh);

        dbh.close();
    }

    public int getSeasonYear(){
        String year = seasonSpinner.getSelectedItem().toString();
        year = year.split(" - ")[0];

        try{
            return Integer.valueOf(year);
        }catch(Exception e){
            return 0;
        }
    }

    public int getVarsity(){
        String varsity = varsitySpinner.getSelectedItem().toString();
        if(varsity.equals("Varsity"))
            return 1;
        else
            return 0;
    }

    public int getSeasonInt(){
        DatabaseHelper dbh = new DatabaseHelper(this);
        int year = getSeasonYear();
        int varsity = getVarsity();
        int season = dbh.getSeason(year, varsity).pointstreakID;
        dbh.close();
        return season;
    }

    public void onFragmentInteraction(Uri uri){

    }
    /*

    private TextView getDivisionTitle(String division){
        TextView returnthis = new TextView(this.getApplicationContext());
        returnthis.setText(division);
        returnthis.setGravity(Gravity.CENTER);
        returnthis.setBackgroundColor(Color.RED);
        returnthis.setTextColor(Color.WHITE);
        return returnthis;
    }

    private LinearLayout resolveTeamLayout(String abbr){
        LinearLayout ll = new LinearLayout(this.getApplicationContext());
        getLayoutInflater().inflate(R.layout.layout_team, ll);
        ( (ImageView) ll.findViewById(R.id.logo)).setImageResource(LeagueUtils.resolveTeamLogo(abbr));
        ( (TextView) ll.findViewById(R.id.name)).setText(LeagueUtils.resolveTeamName(abbr));
        ( (TextView) ll.findViewById(R.id.record)).setText(LeagueUtils.resolveTeamRecord(abbr));

        return ll;
    }

    private void createRankingList(){
        LinearLayout target = (LinearLayout) findViewById(R.id.layout_maintarget);
        clearTargetViews();
        target.addView(getDivisionTitle("West"));
        target.addView(resolveTeamLayout("kcj"));
        target.addView(resolveTeamLayout("ojl"));
        target.addView(resolveTeamLayout("scm"));
        target.addView(resolveTeamLayout("ljs"));


        target.addView(getDivisionTitle("Central"));
        target.addView(resolveTeamLayout("dmc"));
        target.addView(resolveTeamLayout("mcm"));
        target.addView(resolveTeamLayout("dmo"));
        target.addView(resolveTeamLayout("ams"));

        target.addView(getDivisionTitle("East"));
        target.addView(resolveTeamLayout("dbq"));
        target.addView(resolveTeamLayout("wat"));
        target.addView(resolveTeamLayout("qcb"));
        target.addView(resolveTeamLayout("cdr"));
    }



    // These still need to be resolved

    private LinearLayout getStatsHeader(){
        LinearLayout rt = new LinearLayout(this.getApplicationContext());
        getLayoutInflater().inflate(R.layout.layout_playerheading, rt);

        return rt;
    }

    private LinearLayout resolveTeamSchedule(String abbr){
        String home = "DMC";
        String away = "LJS";
        LinearLayout target = new LinearLayout(this.getApplicationContext());
        target.setOrientation(LinearLayout.VERTICAL);

        LinearLayout l1 = new LinearLayout(this.getApplicationContext());

        getLayoutInflater().inflate(R.layout.layout_game, l1);
        ( (ImageView) l1.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l1.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l1.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l1.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l1.findViewById(R.id.homeScore)).setText("5");
        ( (TextView) l1.findViewById(R.id.awayScore)).setText("1");

        ( (TextView) l1.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l1.findViewById(R.id.time)).setText("11/1/2014");
        target.addView(l1);


        LinearLayout l2 = new LinearLayout(this.getApplicationContext());
        home = "DMC";
        away="LJS";

        getLayoutInflater().inflate(R.layout.layout_game, l2);
        ( (ImageView) l2.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l2.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l2.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l2.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l2.findViewById(R.id.homeScore)).setText("1");
        ( (TextView) l2.findViewById(R.id.awayScore)).setText("2");

        ( (TextView) l2.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l2.findViewById(R.id.time)).setText("11/1/2014");
        target.addView(l2);


        LinearLayout l3 = new LinearLayout(this.getApplicationContext());
        home = "KCJ";
        away="DMC";

        getLayoutInflater().inflate(R.layout.layout_game, l3);
        ( (ImageView) l3.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l3.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l3.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l3.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l3.findViewById(R.id.homeScore)).setText("2");
        ( (TextView) l3.findViewById(R.id.awayScore)).setText("0");

        ( (TextView) l3.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l3.findViewById(R.id.time)).setText("11/8/2014");
        target.addView(l3);


        LinearLayout l4 = new LinearLayout(this.getApplicationContext());
        home = "KCJ";
        away="DMC";

        getLayoutInflater().inflate(R.layout.layout_game, l4);
        ( (ImageView) l4.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l4.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l4.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l4.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l4.findViewById(R.id.homeScore)).setText("1");
        ( (TextView) l4.findViewById(R.id.awayScore)).setText("4");

        ( (TextView) l4.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l4.findViewById(R.id.time)).setText("11/9/2014");
        target.addView(l4);


        LinearLayout l5 = new LinearLayout(this.getApplicationContext());
        home = "DMC";
        away="AMS";

        getLayoutInflater().inflate(R.layout.layout_game, l5);
        ( (ImageView) l5.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l5.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l5.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l5.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l5.findViewById(R.id.homeScore)).setText("2");
        ( (TextView) l5.findViewById(R.id.awayScore)).setText("1");

        ( (TextView) l5.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l5.findViewById(R.id.time)).setText("11/14/2014");
        target.addView(l5);


        LinearLayout l6 = new LinearLayout(this.getApplicationContext());
        home = "OJL";
        away="DMC";

        getLayoutInflater().inflate(R.layout.layout_game, l6);
        ( (ImageView) l6.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l6.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l6.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l6.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l6.findViewById(R.id.homeScore)).setText("4");
        ( (TextView) l6.findViewById(R.id.awayScore)).setText("3");

        ( (TextView) l6.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l6.findViewById(R.id.time)).setText("11/15/2014");
        target.addView(l6);


        LinearLayout l7 = new LinearLayout(this.getApplicationContext());
        home = "OJL";
        away="DMC";

        getLayoutInflater().inflate(R.layout.layout_game, l7);
        ( (ImageView) l7.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l7.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l7.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l7.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l7.findViewById(R.id.homeScore)).setText("4");
        ( (TextView) l7.findViewById(R.id.awayScore)).setText("2");

        ( (TextView) l7.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l7.findViewById(R.id.time)).setText("11/16/2014");
        target.addView(l7);


        LinearLayout l8 = new LinearLayout(this.getApplicationContext());
        home = "DMC";
        away="QCB";

        getLayoutInflater().inflate(R.layout.layout_game, l8);
        ( (ImageView) l8.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l8.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l8.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l8.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l8.findViewById(R.id.homeScore)).setText("2");
        ( (TextView) l8.findViewById(R.id.awayScore)).setText("0");

        ( (TextView) l8.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l8.findViewById(R.id.time)).setText("11/22/2014");
        target.addView(l8);

        return target;
    }

    private void clearTargetViews(){

        ((LinearLayout) findViewById(R.id.layout_maintarget)).removeAllViews();
        ((LinearLayout) findViewById(R.id.layout_stationarytarget)).removeAllViews();
    }

    private void createSchedule(){
        LinearLayout target = (LinearLayout) findViewById(R.id.layout_maintarget);
        clearTargetViews();
        String home = "QCB";
        String away = "AMS";

        LinearLayout l1 = new LinearLayout(this.getApplicationContext());

        getLayoutInflater().inflate(R.layout.layout_game, l1);
        ( (ImageView) l1.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l1.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l1.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l1.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l1.findViewById(R.id.homeScore)).setText("2");
        ( (TextView) l1.findViewById(R.id.awayScore)).setText("4");

        ( (TextView) l1.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l1.findViewById(R.id.time)).setText("11/1/2014");
        target.addView(l1);


        LinearLayout l2 = new LinearLayout(this.getApplicationContext());
        home = "OJL";
        away="DBQ";

        getLayoutInflater().inflate(R.layout.layout_game, l2);
        ( (ImageView) l2.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l2.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l2.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l2.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l2.findViewById(R.id.homeScore)).setText("2");
        ( (TextView) l2.findViewById(R.id.awayScore)).setText("3");

        ( (TextView) l2.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l2.findViewById(R.id.time)).setText("11/1/2014");
        target.addView(l2);


        LinearLayout l3 = new LinearLayout(this.getApplicationContext());
        home = "WAT";
        away="DMO";

        getLayoutInflater().inflate(R.layout.layout_game, l3);
        ( (ImageView) l3.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l3.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l3.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l3.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l3.findViewById(R.id.homeScore)).setText("4");
        ( (TextView) l3.findViewById(R.id.awayScore)).setText("2");

        ( (TextView) l3.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l3.findViewById(R.id.time)).setText("11/1/2014");
        target.addView(l3);


        LinearLayout l4 = new LinearLayout(this.getApplicationContext());
        home = "DMC";
        away="LJS";

        getLayoutInflater().inflate(R.layout.layout_game, l4);
        ( (ImageView) l4.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l4.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l4.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l4.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l4.findViewById(R.id.homeScore)).setText("5");
        ( (TextView) l4.findViewById(R.id.awayScore)).setText("1");

        ( (TextView) l4.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l4.findViewById(R.id.time)).setText("11/1/2014");
        target.addView(l4);


        LinearLayout l5 = new LinearLayout(this.getApplicationContext());
        home = "SCM";
        away="KCJ";

        getLayoutInflater().inflate(R.layout.layout_game, l5);
        ( (ImageView) l5.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l5.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l5.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l5.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l5.findViewById(R.id.homeScore)).setText("2");
        ( (TextView) l5.findViewById(R.id.awayScore)).setText("5");

        ( (TextView) l5.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l5.findViewById(R.id.time)).setText("11/1/2014");
        target.addView(l5);


        LinearLayout l6 = new LinearLayout(this.getApplicationContext());
        home = "QCB";
        away="AMS";

        getLayoutInflater().inflate(R.layout.layout_game, l6);
        ( (ImageView) l6.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l6.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l6.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l6.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l6.findViewById(R.id.homeScore)).setText("1");
        ( (TextView) l6.findViewById(R.id.awayScore)).setText("2");

        ( (TextView) l6.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l6.findViewById(R.id.time)).setText("11/2/2014");
        target.addView(l6);


        LinearLayout l7 = new LinearLayout(this.getApplicationContext());
        home = "OJL";
        away="DBQ";

        getLayoutInflater().inflate(R.layout.layout_game, l7);
        ( (ImageView) l7.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l7.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l7.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l7.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l7.findViewById(R.id.homeScore)).setText("2");
        ( (TextView) l7.findViewById(R.id.awayScore)).setText("3");

        ( (TextView) l7.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l7.findViewById(R.id.time)).setText("11/2/2014");
        target.addView(l7);


        LinearLayout l8 = new LinearLayout(this.getApplicationContext());
        home = "DMC";
        away="LJS";

        getLayoutInflater().inflate(R.layout.layout_game, l8);
        ( (ImageView) l8.findViewById(R.id.homeLogo)).setImageResource(LeagueUtils.resolveTeamLogo(home));
        ( (TextView) l8.findViewById(R.id.homeSeed)).setText(TeamUtils.resolveTeamRank(home));

        ( (ImageView) l8.findViewById(R.id.awayLogo)).setImageResource(LeagueUtils.resolveTeamLogo(away));
        ( (TextView) l8.findViewById(R.id.awaySeed)).setText(TeamUtils.resolveTeamRank(away));

        ( (TextView) l8.findViewById(R.id.homeScore)).setText("1");
        ( (TextView) l8.findViewById(R.id.awayScore)).setText("3");

        ( (TextView) l8.findViewById(R.id.date)).setText("FINAL");
        ( (TextView) l8.findViewById(R.id.time)).setText("11/2/2014");
        target.addView(l8);

    }

    private void createTeamPage(String team){
        LinearLayout target = (LinearLayout) findViewById(R.id.layout_maintarget);
        LinearLayout stationaryTarget = (LinearLayout) findViewById(R.id.layout_stationarytarget);
        clearTargetViews();

        stationaryTarget.addView(resolveTeamLayout(team));
        target.addView(resolveTeamSchedule(team));

    }

    private void createPlayerList(){
        LinearLayout target = (LinearLayout) findViewById(R.id.layout_maintarget);
        clearTargetViews();
        target.addView(getStatsHeader());
    }

    */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.action_settings) {

            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ranking) {
            //createRankingList();
            changeMainFragment(new RankingFragment());
        } else if (id == R.id.nav_schedule) {
            //createSchedule();
            changeMainFragment(ScheduleFragment.seasonSchedule());
        } else if (id == R.id.nav_teams) {
            //createTeamPage("DMC");
            changeMainFragment(TeamFragment.newInstance("DMC"));
        } else if (id == R.id.nav_leaders) {
            //createPlayerList();
            changeMainFragment(new RankingFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
