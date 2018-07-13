package jeffpadgett.LoveYourWife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;

public class Day extends  Fragment {

    OnPurchaseButtonClicked mCallback;
    ShowAd mCallback2;

    // Container Activity must implement this interface
    public interface OnPurchaseButtonClicked {
        public void beginPurchaseFlow();
    }

    public interface ShowAd {
        public void showAd(Intent intent);
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_ISBILLINGFEATURESUPPORTED = "param2";
    private static final String ARG_CONTENT_PURCHASED= "param3";
    private static final String TAG = "Day.java";
    private static String sharedPrefKey;
    private static String sharedPrefPreviousKey;


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
    Button btnPurchase;
    Button btnRemoveAds;
    // TODO: Rename and change types of parameters
    private int mParam1;
    private int isBillingFeatureSupported;
    Boolean contentPurchased;


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
    public static Day newInstance(int param1, int isBillingFeatureSupported, boolean contentPurchased) {
        Day fragment = new Day();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_ISBILLINGFEATURESUPPORTED, isBillingFeatureSupported);
        args.putBoolean(ARG_CONTENT_PURCHASED, contentPurchased);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1, 0);
            isBillingFeatureSupported = getArguments().getInt(ARG_ISBILLINGFEATURESUPPORTED, -1);
            contentPurchased = getArguments().getBoolean(ARG_CONTENT_PURCHASED, false);
            //Log.d(TAG, "mParam in onCreate is " +mParam1);
            //Log.d(TAG, "contentPurchased in onCreate of Day Frag is: " + contentPurchased);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this

        // TODO: check to see if the content is purchase - momentarily created as an argument
       // contentPurchased = false;

        final int day = mParam1 + 1;
        sharedPrefPreviousKey = (mParam1-1) + "";

        //if previous challenge not complete, call the locked layout
        final SharedPreferences sharedPreviousCompleted = getActivity().getSharedPreferences("COMPLETED", Context.MODE_PRIVATE);
        final Boolean previousDayCompleted = sharedPreviousCompleted.getBoolean(sharedPrefPreviousKey, false);




        // if you have not purchased the rest of the content:



        // if on day 7-30, and content not purchased, display unpurchased
        // if on day 7-30, content purchased, then display either the lock or the app
        // if on day 1-6, display either the lock or the app.


        //if not first or 2nd challenge, and previous day is not completed
        if (mParam1>6 && contentPurchased == false) {
            View v = inflater.inflate(R.layout.fragment_unpurchased, container, false);

            tvLocked = v.findViewById(R.id.tvLocked);
            tvLockedDayNumber = v.findViewById(R.id.tvLockedDayNumber);
            btnPurchase = v.findViewById(R.id.btnPurchase);
            tvLockedDayNumber.setText("Day " + day);



            btnPurchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Log.d(TAG, "is billing feature supported = " + isBillingFeatureSupported);
                    if (isBillingFeatureSupported == BillingClient.BillingResponse.FEATURE_NOT_SUPPORTED) {
                        Toast.makeText(getActivity(), R.string.BillingNotSupported, Toast.LENGTH_LONG).show();

                    } else {
                        //Log.d(TAG, "billing supported: " +isBillingFeatureSupported);

                        mCallback.beginPurchaseFlow();

                    }


                }
            });





            return v;
        }
        else if (mParam1!=0 && mParam1!=1 && previousDayCompleted == false) {
            View v = inflater.inflate(R.layout.fragment_locked, container, false);

            tvLocked = (TextView)v.findViewById(R.id.tvLocked);
            tvLockedDayNumber = (TextView)v.findViewById(R.id.tvLockedDayNumber);
            tvLockedCompleteOtherDay =(TextView)v.findViewById(R.id.tvCompleteOtherDay);

            tvLockedDayNumber.setText("Day " + day);
            tvLockedCompleteOtherDay.setText("Complete the challenge on day "  + (day-1) + " to unlock.");
            return v;
        } else {  // if on day 1, day 2, or if previous challenge is completed, and content is purchased


            View v = inflater.inflate(R.layout.fragment_day, container, false);



            textView1 = (TextView) v.findViewById(R.id.textView1);
            editText = (EditText) v.findViewById(R.id.edtComments);
            tvDayNumber = (TextView) v.findViewById(R.id.tvDayNumber);
             btnComplete = (Button) v.findViewById(R.id.button);
            edtTextLayout = v.findViewById(R.id.textInputLayout);
            imageView = v.findViewById(R.id.imageView);
            btnRemoveAds = v.findViewById(R.id.btnRemoveAds);

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

                    // has this day been completed?


//TODO: change back to 25
                    if (editText.getText().length() > 20) {

                        btnComplete.setText("Completed!");

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LASTCOMPLETED", 0);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("LASTCOMPLETED", day);
                        editor.commit();



                        Intent intent = new Intent(getActivity(), ChallengeComplete.class);
                       intent.putExtra("DAY", day);
                       // startActivity(intent);

                        mCallback2.showAd(intent);


                    }
                    else {
                        Toast.makeText(getActivity(), "You can say a little more than that! ", Toast.LENGTH_LONG).show();
                    }

                }

            });

            if (contentPurchased) {
                btnRemoveAds.setVisibility(View.GONE);
            } else {
                btnRemoveAds.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mCallback.beginPurchaseFlow();
                    }
                });
            }


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
                //Log.d(TAG, "button match.  shared pref key is " + sharedPrefKey + " param is " + mParam1);
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

        Activity a;
        if (context instanceof Activity){
            a=(Activity) context;

            try {
                mCallback = (OnPurchaseButtonClicked) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a.toString()
                        + " must implement OnHeadlineSelectedListener");
            }

            try {
                mCallback2 = (ShowAd) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a.toString() + " must implement ShowAd Listener");
            }


        }

    }

}
