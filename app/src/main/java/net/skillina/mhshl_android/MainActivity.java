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

import java.util.ArrayList;

import layout.DivisionFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RankingFragment.OnFragmentInteractionListener,
        ScheduleFragment.OnFragmentInteractionListener, TeamFragment.OnFragmentInteractionListener, DivisionFragment.OnFragmentInteractionListener {

    private NavigationView navigationView;
    private Spinner seasonSpinner;
    private Spinner varsitySpinner;

    private ArrayList<Game> games;
    private ArrayList<Team> teams;

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
