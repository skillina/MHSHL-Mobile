package layout;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.skillina.mhshl_android.App;
import net.skillina.mhshl_android.DatabaseHelper;
import net.skillina.mhshl_android.MainActivity;
import net.skillina.mhshl_android.R;
import net.skillina.mhshl_android.Team;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DivisionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DivisionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DivisionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_DIVISION = "division";

    // TODO: Rename and change types of parameters
    private String division;

    private OnFragmentInteractionListener mListener;

    public DivisionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param division The division to be initialized.
     * @return A new instance of fragment DivisionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DivisionFragment newInstance(String division) {
        DivisionFragment fragment = new DivisionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DIVISION, division);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            division = getArguments().getString(ARG_DIVISION);
        }
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View baseView = inflater.inflate(R.layout.fragment_division, container, false);

        ((TextView) baseView.findViewById(R.id.division_header)).setText(division);

        DatabaseHelper dbh = new DatabaseHelper(App.context);
        int season = ((MainActivity)getActivity()).getSeasonInt();
        ArrayList<Team> teams = sortTeamsByRank(dbh.getTeamsFromDivision(division, season));

        for(int i = 0; i < teams.size(); i++){
            ((LinearLayout)baseView.findViewById(R.id.division_container)).addView(teams.get(i).getView(getActivity()));
        }

        dbh.close();

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
