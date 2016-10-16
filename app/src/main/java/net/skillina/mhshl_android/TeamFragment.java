package net.skillina.mhshl_android;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import layout.DivisionFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ID = "id";
    private static final String ARG_ABBR = "abbreviation";

    private String abbreviation;
    private int pointstreakID;

    private OnFragmentInteractionListener mListener;

    public TeamFragment() {
        // Required empty public constructor
    }

    public static TeamFragment newInstance(int id) {
        TeamFragment fragment = new TeamFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, id);
        args.putString(ARG_ABBR, "NUL");
        fragment.setArguments(args);
        return fragment;
    }

    public static TeamFragment newInstance(String abbr) {
        TeamFragment fragment = new TeamFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID, 0);
        args.putString(ARG_ABBR, abbr);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pointstreakID = getArguments().getInt(ARG_ID);
            abbreviation = getArguments().getString(ARG_ABBR);
        }

    }

    private void clearTargetViews(View view){
        ((FrameLayout) view.findViewById(R.id.layout_scheduletarget)).removeAllViews();
        ((LinearLayout) view.findViewById(R.id.layout_stationarytarget)).removeAllViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.fragment_team, container, false);


        DatabaseHelper dbh = new DatabaseHelper(App.context);
        MainActivity ma = (MainActivity) getActivity();

        Team t;
        if(pointstreakID == 0)
            t = dbh.getTeam(abbreviation, ma.getSeasonInt());
        else
            t = dbh.getTeam(pointstreakID, ma.getSeasonInt());

        LinearLayout stationaryTarget = (LinearLayout) baseView.findViewById(R.id.layout_stationarytarget);
        clearTargetViews(baseView);


        LinearLayout ll = new LinearLayout(App.context);
        ma.getLayoutInflater().inflate(R.layout.layout_team, ll);
        ( (ImageView) ll.findViewById(R.id.logo)).setImageResource(LeagueUtils.resolveTeamLogo(t.abbreviation));
        ( (TextView) ll.findViewById(R.id.name)).setText(LeagueUtils.resolveTeamName(t.abbreviation));
        ( (TextView) ll.findViewById(R.id.record)).setText(t.getRecordAsString());

        stationaryTarget.addView(ll);


        ScheduleFragment schedule = ScheduleFragment.teamSchedule(t.abbreviation);
        getFragmentManager().beginTransaction().add(R.id.layout_scheduletarget, schedule).commit();

        return baseView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
