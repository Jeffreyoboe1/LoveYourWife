package jeffpadgett.LoveYourWife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    String TAG;
    int REQUEST_INVITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d("ParameterPassed", "onCreate in MainActivity called.");
        TAG = "MainActivity";
        REQUEST_INVITE = 13254;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(true, new ForegroundToBackgroundTransformer());


        // get last completed day, jump to the next challenge you have to complete.
        SharedPreferences sharedPref = getSharedPreferences("LASTCOMPLETED",0);
        int lastCompleted = sharedPref.getInt("LASTCOMPLETED", 0);

        if (lastCompleted != 0 ){
            Log.d("LASTCOMPLETED", lastCompleted + "");
            mViewPager.setCurrentItem(lastCompleted);  // do not need + 1, because pager starts at 0.
        }

        // get the Intent, if coming from Challenge Completed Page.  Move to appropriate next page to start off...
        int nextDay = getIntent().getIntExtra("NEXTDAY", 0);

        if (nextDay != 0) {
            Log.d("INTTEST", ""+ nextDay);
            mViewPager.setCurrentItem(nextDay);  // do not need + 1, because pager starts at 0.
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_share_app) {
            onInviteClicked();


        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

         // maybe unnecessary

        /*
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_day, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }*/

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return Day.newInstance(position);

        }

        @Override
        public int getCount() {
            // Show 30 total pages.
            return 30;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
                case 3:
                    return "SECTION 4";
                case 4:
                    return "SECTION 5";
                case 5:
                    return "SECTION 6";
                case 6:
                    return "SECTION 7";
                case 7:
                    return "SECTION 8";
                case 8:
                    return "SECTION 9";
                case 9:
                    return "SECTION 10";
                case 10:
                    return "SECTION 11";
                case 11:
                    return "SECTION 12";
                case 12:
                    return "SECTION 13";
                case 13:
                    return "SECTION 14";
                case 14:
                    return "SECTION 15";
                case 15:
                    return "SECTION 16";
                case 16:
                    return "SECTION 17";
                case 17:
                    return "SECTION 18";
                case 18:
                    return "SECTION 19";
                case 19:
                    return "SECTION 20";
                case 20:
                    return "SECTION 21";
                case 21:
                    return "SECTION 22";
                case 22:
                    return "SECTION 23";
                case 23:
                    return "SECTION 24";
                case 24:
                    return "SECTION 25";
                case 25:
                    return "SECTION 26";
                case 26:
                    return "SECTION 27";
                case 27:
                    return "SECTION 28";
                case 28:
                    return "SECTION 29";
                case 29:
                    return "SECTION 30";
                case 30:
                    return "SECTION 31";

            }
            return null;
        }
    }

    private void onInviteClicked() {



        Task<ShortDynamicLink> shortTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://www.google.com/")) // put website here
                .setDynamicLinkDomain("loveyourwife.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                .buildShortDynamicLink().addOnCompleteListener(new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();

                            String dynamicString = shortLink.toString();


                            String msg = "Hi, I have been using this app called LoveYourWife.  It can help improve your marriage. " + dynamicString;
                            String subject = "Love Your Wife";
                            String msgHtml = String.format("<p>Hi!  I've been using this app called Love Your Wife, and wanted to recommend it to you.  "
                                    + "It has great ideas to improve your marriage and love your wife better. " +
                                    " Click on this link to check it out. " + "<a href=\"%s\">Love Your Wife</a>!</p>", dynamicString);

                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                            sendIntent.putExtra(Intent.EXTRA_HTML_TEXT, msgHtml);
                            sendIntent.setType("text/plain");
                            startActivity(Intent.createChooser(sendIntent, "Recommend Love Your Wife to a friend"));

                        } else {
                            // Error
                            // ...
                            Toast.makeText(MainActivity.this, "error " + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });









    }

}
