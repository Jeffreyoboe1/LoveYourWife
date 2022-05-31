package jeffpadgett.LoveYourWife;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.util.function.Function;

public class MainActivity extends AppCompatActivity implements DayFragment.OnDayCompletedListener {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private String TAG;
    int REQUEST_INVITE;
    int lastCompleted;
    private static final String TEST_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String LOVE_YOUR_WIFE_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-2213157796139553/4556772431";
    private static final String LOVE_YOUR_WIFE_V2_INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-2213157796139553/6221412312";
    private static final String TEST_BANNER_AD_UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
    private static final String LOVE_YOUR_WIFE_BANNER_AD_UNIT_ID = "ca-app-pub-2213157796139553/5280225966";
    private static final String LOVE_YOUR_WIFE_V2_BANNER_AD_UNIT_ID = "ca-app-pub-2213157796139553/3417772111";
    // use for actual device testing of production-like ads from admob- to use with adRequest
    private static final String TEST_DEVICE = "8D254604C542C16046E353C4773A21DC";
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("ParameterPassed", "onCreate in MainActivity called.");
        TAG = "MainActivity";
        REQUEST_INVITE = 13254;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mViewPager = findViewById(R.id.container);

        AdView bannerAd = findViewById(R.id.bannerAd);
        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);

        //TODO:  replace with my legit app id, which is in my email.  This is a test.
        MobileAds.initialize(this, initializationStatus -> {});
        loadInterstitial(null);

        //TODO:  replace this builder with the commented one.
        //AdRequest adRequest = new AdRequest.Builder().build();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(true, new ForegroundToBackgroundTransformer());

        // get last completed day, jump to the next challenge you have to complete.
        SharedPreferences sharedPreferences = getSharedPreferences("LASTCOMPLETED", 0);
        lastCompleted = sharedPreferences.getInt("LASTCOMPLETED", 0);

        if (lastCompleted != 0) {
            Log.d("LASTCOMPLETED", lastCompleted + "");
            mViewPager.setCurrentItem(lastCompleted);  // do not need + 1, because pager starts at 0.
        }

        // get the Intent, if coming from Challenge Completed Page or the Trophy Page.  Move to appropriate next page to start off...
        int nextDay = getIntent().getIntExtra("NEXTDAY", 0);

        if (nextDay == 123) {
            mViewPager.setCurrentItem(0);
        } else if (nextDay != 0) {
            Log.d("INTTEST", "" + nextDay);
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

        switch (id) {
            case R.id.action_share_app:
                onInviteClicked();
                break;
            case R.id.action_recommend:
                onInviteClicked();
                break;
            case R.id.action_jumpBeginning:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.action_jumpEnd:
                mViewPager.setCurrentItem(29);
                break;
            case R.id.action_jumpCurrent:
                mViewPager.setCurrentItem(lastCompleted);
                break;
            case R.id.action_trophyPage:
                Intent intent = new Intent(MainActivity.this, TrophyPage.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
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
            return DayFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 30 total pages.
            return 30;
        }
    }

    public void onInviteClicked() {
        Task<ShortDynamicLink> shortTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("http://jeffreypadgett.wixsite.com/loveyourwife")) // put website here
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
                            String msg = getString(R.string.RecommendMessage) + dynamicString;
                            String subject = getString(R.string.RecommendSubject);
                            Intent sendIntent = new Intent();
                            sendIntent.setAction(Intent.ACTION_SEND);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                            sendIntent.setType("text/plain");
                            startActivity(Intent.createChooser(sendIntent, getString(R.string.RecommendToFriendTitle)));
                        } else {
                            // Error
                            Log.e(TAG, task.getException().getLocalizedMessage());
                            Toast.makeText(MainActivity.this, getString(R.string.Invitation_Error), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onDayCompleted(final int day, Button button) {
        if (mInterstitialAd != null) {
            Log.d(TAG, "show loaded ad");

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.d(TAG, "The ad was dismissed.");
                    // Called when fullscreen content is dismissed.
                    Intent intent = new Intent(MainActivity.this , ChallengeComplete.class);
                    intent.putExtra("DAY", day);
                    startActivity(intent);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    Log.w(TAG, "The ad failed to show: " + adError.getMessage());
                    // Called when fullscreen content failed to show.
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.d(TAG, "The ad was shown.");
                    // Called when fullscreen content is shown.
                    // Make sure to set your reference to null so you don't
                    // show it a second time.
                    mInterstitialAd = null;
                    SharedPreferences sharedPrefComplete = getSharedPreferences("COMPLETED", Context.MODE_PRIVATE);
                    SharedPreferences.Editor compEditor = sharedPrefComplete.edit();
                    compEditor.putBoolean(Integer.toString(day -1), true);
                    compEditor.commit();

                    SharedPreferences sharedPreferences = getSharedPreferences("LASTCOMPLETED", 0);
                    boolean dayIsGreater = day > sharedPreferences.getInt("LASTCOMPLETED", 0);

                    if (dayIsGreater) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("LASTCOMPLETED", day);
                        editor.commit();
                    }

                    button.setText(getString(R.string.completed));
                    loadInterstitial(null);
                }
            });

            mInterstitialAd.show(this);
        } else {
            // TODO: test without internet... possibly display toast instead (ad was not ready).
            Log.d(TAG, "Ad was not loaded, going to ChallengeComplete without loading ad.");

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setMessage(R.string.error_completing_dialog_message)
                    .setTitle(R.string.error_completing_dialog_title)
                    .setPositiveButton(R.string.try_again, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    loadInterstitial(new OnAdLoaded() {
                        @Override
                        public void onAdLoaded() {
                            onDayCompleted(day, button);
                        }
                    });
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    private void loadInterstitial(@Nullable OnAdLoaded onAdLoaded) {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, LOVE_YOUR_WIFE_V2_INTERSTITIAL_AD_UNIT_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        if (onAdLoaded != null) {
                            onAdLoaded.onAdLoaded();
                        }
                        Log.d(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        Log.w(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }
}

interface OnAdLoaded {
    public void onAdLoaded();
}

