package net.skillina.mhshl_android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;


public class ScheduleFragment extends Fragment {

    private static final String ARG_TEAM = "team";

    // TODO: Rename and change types of parameters
    private String team = "NUL";

    private OnFragmentInteractionListener mListener;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    public static ScheduleFragment seasonSchedule() {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEAM, "NUL");
        fragment.setArguments(args);
        return fragment;
    }


    public static ScheduleFragment teamSchedule(String abbr) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEAM, abbr);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            team = getArguments().getString(ARG_TEAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.fragment_schedule, container, false);


        ArrayList<Game> games;
        DatabaseHelper dbh = new DatabaseHelper(App.context);

        int season = ((MainActivity)getActivity()).getSeasonInt();

        System.out.println("Team:" + team);

        if(team == "NUL")
            games = dbh.getGames(season);
        else
            games = dbh.getGames(team, season);

        dbh.close();

        System.out.println("Games: " + String.valueOf(games.size()));

        for(int i = 0; i < games.size(); i++){
            ((LinearLayout) baseView.findViewById(R.id.schedule_target)).addView(games.get(i).getView(getActivity()));
        }

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
