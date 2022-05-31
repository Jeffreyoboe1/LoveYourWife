package com.loveyourwife;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

public class DayFragment extends Fragment {

    OnDayCompletedListener onDayCompletedListener;

    public interface OnDayCompletedListener {
        void onDayCompleted(int day, Button button);
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "Day.java";

    TextView tvDayNumber;
    TextView textView1;
    EditText editText;
    TextInputLayout edtTextLayout;
    Button btnComplete;
    ImageView imageView;

    // for the locked fragment
    TextView tvLockedDayNumber;
    TextView tvLocked;
    TextView tvLockedCompleteOtherDay;
    // TODO: Rename and change types of parameters
    private int mParam1;

    //private OnFragmentInteractionListener mListener;

    public DayFragment() {
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
    public static DayFragment newInstance(int param1) {
        DayFragment fragment = new DayFragment();
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
            Log.d(TAG, "mParam in onCreate is " + mParam1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final int day = mParam1 + 1;

        //if previous challenge not complete, call the locked layout
        final SharedPreferences sharedPreviousCompleted = getActivity().getSharedPreferences("COMPLETED", Context.MODE_PRIVATE);
        final Boolean previousDayCompleted = sharedPreviousCompleted.getBoolean(Integer.toString(day - 2), false);

        View v;
        if (mParam1 != 0 && mParam1 != 1 && !previousDayCompleted) {
            v = inflater.inflate(R.layout.fragment_locked, container, false);

            tvLocked = v.findViewById(R.id.tvLocked);
            tvLockedDayNumber = v.findViewById(R.id.tvLockedDayNumber);
            tvLockedCompleteOtherDay = v.findViewById(R.id.tvCompleteOtherDay);

            tvLockedDayNumber.setText("Day " + day);
            tvLockedCompleteOtherDay.setText("Complete the challenge on day " + (day - 1) + " to unlock.");
        } else {  // if on day 1, day 2, or if previous challenge is completed, and content is purchased
            v = inflater.inflate(R.layout.fragment_day, container, false);

            textView1 = v.findViewById(R.id.textView1);
            editText = v.findViewById(R.id.edtComments);
            tvDayNumber = v.findViewById(R.id.tvDayNumber);
            btnComplete = v.findViewById(R.id.button);
            edtTextLayout = v.findViewById(R.id.textInputLayout);
            imageView = v.findViewById(R.id.imageView);

            tvDayNumber.setText("Day " + day);


            // set the User's previous comments
            final SharedPreferences sharedPref = getActivity().getSharedPreferences("THOUGHTS", Context.MODE_PRIVATE);
            String fillEditText = sharedPref.getString(Integer.toString(day - 1), "");
            editText.setText(fillEditText);

            // set whether this day has been completed.... now for some reason... this stuff here does not work...
            // sometimes it will fill the wrong day... maybe it has to do with the auto pager change...
            final SharedPreferences sharedPrefCompleted = getActivity().getSharedPreferences("COMPLETED", Context.MODE_PRIVATE);
            final Boolean dayCompleted = sharedPrefCompleted.getBoolean(day - 1 + "", false);

            if (dayCompleted) {
                btnComplete.setText("Completed!");
                btnComplete.setEnabled(false);
            } else {
                btnComplete.setText("Complete");
            }


            switch (mParam1) {
                case 0:
                    textView1.setText(R.string.day1text);
                    break;
                case 1:
                    textView1.setText(R.string.day2text);
                    break;
                case 2:
                    textView1.setText(R.string.day3text);
                    break;
                case 3:
                    textView1.setText(R.string.day4text);
                    break;
                case 4:
                    textView1.setText(R.string.day5text);
                    break;
                case 5:
                    textView1.setText(R.string.day6text);
                    break;
                case 6:
                    textView1.setText(R.string.day7text);
                    break;
                case 7:
                    textView1.setText(R.string.day8text);
                    break;
                case 8:
                    textView1.setText(R.string.day9text);
                    break;
                case 9:
                    textView1.setText(R.string.day10text);
                    break;
                case 10:
                    textView1.setText(R.string.day11text);
                    break;
                case 11:
                    textView1.setText(R.string.day12text);
                    break;
                case 12:
                    textView1.setText(R.string.day13text);
                    break;
                case 13:
                    textView1.setText(R.string.day14text);
                    break;
                case 14:
                    textView1.setText(R.string.day15text);
                    break;
                case 15:
                    textView1.setText(R.string.day16text);
                    break;
                case 16:
                    textView1.setText(R.string.day17text);
                    break;
                case 17:
                    textView1.setText(R.string.day18text);
                    break;
                case 18:
                    textView1.setText(R.string.day19text);
                    break;
                case 19:
                    textView1.setText(R.string.day20text);
                    break;
                case 20:
                    textView1.setText(R.string.day21text);
                    break;
                case 21:
                    textView1.setText(R.string.day22text);
                    break;
                case 22:
                    textView1.setText(R.string.day23text);
                    break;
                case 23:
                    textView1.setText(R.string.day24text);
                    break;
                case 24:
                    textView1.setText(R.string.day25text);
                    break;
                case 25:
                    textView1.setText(R.string.day26text);
                    break;
                case 26:
                    textView1.setText(R.string.day27text);
                    break;
                case 27:
                    textView1.setText(R.string.day28text);
                    break;
                case 28:
                    textView1.setText(R.string.day29text);
                    break;
                case 29:
                    textView1.setText(R.string.day30text);
                    break;
            }

            btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (editText.getText().length() > 20) {
                        onDayCompletedListener.onDayCompleted(day, btnComplete);
                    } else {
                        Toast.makeText(getActivity(), "You can say a little more than that! ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("THOUGHTS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        int day = mParam1 + 1;
        if (editText != null) {
            editor.putString((day - 1) + "", editText.getText().toString());
            editor.commit();
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        Activity a;
        if (context instanceof Activity) {
            a = (Activity) context;

            try {
                onDayCompletedListener = (OnDayCompletedListener) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a.toString() + " must implement ShowAd Listener");
            }
        }
    }
}
