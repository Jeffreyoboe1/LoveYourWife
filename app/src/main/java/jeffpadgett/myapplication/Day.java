package jeffpadgett.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;



public class Day extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Day.java";
    private static String sharedPrefKey;
    private static String sharedPrefPreviousKey;


    TextView tvDayNumber;
    TextView textView1;
    EditText editText;
    Button btnComplete;

    // for the locked fragment
    TextView tvLockedDayNumber;
    TextView tvLocked;
    TextView tvLockedCompleteOtherDay;

    // TODO: Rename and change types of parameters
    private int mParam1;


    //private OnFragmentInteractionListener mListener;

    public Day() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1;
     * @return A new instance of fragment Day.
     */
    // TODO: Rename and change types and number of parameters
    public static Day newInstance(int param1) {
        Day fragment = new Day();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1, 0);
            Log.d(TAG, "mParam in onCreate is " +mParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this

        final int day = mParam1 + 1;
        sharedPrefPreviousKey = (mParam1-1) + "";

        //if previous challenge not complete, call the locked layout
        final SharedPreferences sharedPreviousCompleted = getActivity().getSharedPreferences("COMPLETED", Context.MODE_PRIVATE);
        final Boolean previousDayCompleted = sharedPreviousCompleted.getBoolean(sharedPrefPreviousKey, false);

        //if not first challenge or 2nd challenge and previous day is not completed
        if (mParam1!=0 && mParam1!=1 && mParam1 !=2 && previousDayCompleted == false) {
            View v = inflater.inflate(R.layout.fragment_locked, container, false);

            tvLocked = (TextView)v.findViewById(R.id.tvLocked);
            tvLockedDayNumber = (TextView)v.findViewById(R.id.tvLockedDayNumber);
            tvLockedCompleteOtherDay =(TextView)v.findViewById(R.id.tvCompleteOtherDay);

            tvLockedDayNumber.setText("Day " + day);
            tvLockedCompleteOtherDay.setText("Complete the challenge on day "  + (day-1) + " to unlock.");
            return v;
        } else {


            View v = inflater.inflate(R.layout.fragment_day, container, false);

            textView1 = (TextView) v.findViewById(R.id.textView1);
            editText = (EditText) v.findViewById(R.id.edtComments);
            tvDayNumber = (TextView) v.findViewById(R.id.tvDayNumber);
            btnComplete = (Button) v.findViewById(R.id.button);


            tvDayNumber.setText("Day " + day);

            sharedPrefKey = mParam1 + "";

            // set the User's previous comments
            final SharedPreferences sharedPref = getActivity().getSharedPreferences("THOUGHTS", Context.MODE_PRIVATE);
            String fillEditText = sharedPref.getString(sharedPrefKey, "");
            editText.setText(fillEditText);

            // set whether this day has been completed.... now for some reason... this stuff here does not work...
            // sometimes it will fill the wrong day... maybe it has to do with the auto pager change...
            final SharedPreferences sharedPrefCompleted = getActivity().getSharedPreferences("COMPLETED", Context.MODE_PRIVATE);
            final Boolean dayCompleted = sharedPrefCompleted.getBoolean(sharedPrefKey, false);

            if (dayCompleted) {
                btnComplete.setText("Completed!");
            } else {
                btnComplete.setText("Complete");
            }


            switch (mParam1) {
                case 0:
                    textView1.setText(R.string.day1text);
                    break;
                case 1:
                    textView1.setText(R.string.day2text);

            }


            btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // has this day been completed?

                    if (!editText.getText().toString().isEmpty()) {

                        btnComplete.setText("Completed!");

                        Intent intent = new Intent(getActivity(), ChallengeComplete.class);
                        startActivity(intent);

                        // go to the next page

                    /*
                    ViewPager pager = (ViewPager) container;
                    int currentPage = pager.getCurrentItem();
                    if (currentPage < 30) {
                        pager.setCurrentItem(currentPage + 1);
                    } else {  // if on last page.. then toast
                        Toast.makeText(getActivity(), "Congratulations!", Toast.LENGTH_SHORT).show();
                    }
                    */
                    } else {
                        Toast.makeText(getActivity(), "You must record some thoughts to advance.", Toast.LENGTH_LONG).show();
                    }

                }

            });


            return v;
        }


    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onPause() {

        SharedPreferences sharedPref = getActivity().getSharedPreferences("THOUGHTS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        sharedPrefKey = ""+ mParam1;
        if (editText!=null) {
            editor.putString(sharedPrefKey, editText.getText().toString());
            editor.commit();
        }

        if (btnComplete != null) {
            if (btnComplete.getText().toString().equals("Completed!")) {
                Log.d(TAG, "button match.  shared pref key is " + sharedPrefKey + " param is " + mParam1);
                SharedPreferences sharedPrefComplete = getActivity().getSharedPreferences("COMPLETED", Context.MODE_PRIVATE);
                SharedPreferences.Editor compEditor = sharedPrefComplete.edit();
                compEditor.putBoolean(sharedPrefKey, true);
                compEditor.commit();

            }
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }



    /*

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    */


}
