package jeffpadgett.LoveYourWife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Day.OnPurchaseButtonClicked, Day.ShowAd{

    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private BillingClient mBillingClient;
    String TAG;
    int REQUEST_INVITE;
    int lastCompleted;
    int mBillingClientResponseCode;
    static int isFeatureSupported;
    private static final String BASE_64_ENCODED_PUBLIC_KEY = "CONSTRUCT_YOUR_KEY_AND_PLACE_IT_HERE";

    boolean isBillingServiceConnected;
    boolean mActivityContentPurchased;

    AdView bannerAd;


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

        isBillingServiceConnected = false;

        // see if the content has been purchased before and saved into sharedPref.
        SharedPreferences sharedPref = getSharedPreferences("CONTENTPURCHASED", 0);
        mActivityContentPurchased = sharedPref.getBoolean("CONTENTPURCHASED", false);

        bannerAd = new AdView(this);
        bannerAd = findViewById(R.id.bannerAd);

        if (mActivityContentPurchased == false) {
            //TODO:  replace with my legit app id, which is in my email.  This is a test.
            MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

            mInterstitialAd = new InterstitialAd(this);

            // TODO:  replace this test ad ID.
            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

            //load an ad
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            //TODO:  replace this builder with the commented one.
            //AdRequest adRequest = new AdRequest.Builder().build();

            AdRequest adRequest = new AdRequest.Builder().addTestDevice("8D254604C542C16046E353C4773A21DC").build();
            bannerAd.loadAd(adRequest);

        } else {


            RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.BELOW, R.id.toolbarLayout);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            mViewPager.setLayoutParams(params);
             bannerAd.setVisibility(View.GONE);


        }





        final Boolean clickedRemoveAds = getIntent().getBooleanExtra("REMOVE_ADS", false);

        //
        // create new Person

        mBillingClient = BillingClient.newBuilder(this).setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {

                onPurchasesUpdated2(responseCode, purchases);


            }
        }).build();

        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {

                    isFeatureSupported = mBillingClient.isFeatureSupported(BillingClient.FeatureType.IN_APP_ITEMS_ON_VR);
                    isBillingServiceConnected = true;
                    // The billing client is ready. You can query purchases here.
                    Log.d(TAG, "The billing client is ready. You can query purchases here.");

                   // TODO:  remove test consumptions
                    String purchaseToken = "inapp:" + getPackageName() + ":android.test.purchased";
                    mBillingClient.consumeAsync(purchaseToken, new ConsumeResponseListener() {
                        @Override
                        public void onConsumeResponse(int responseCode, String purchaseToken) {
                            if (responseCode==0) {
                                Log.d(TAG, "consumed android.test.purchased test.");
                            }else {
                                Log.d(TAG, "android.test.purchased not consumed, response code: " + responseCode);
                            }
                        }
                    });

                    // here we determine if the user has purchased the rest of the content, days 8-30.

                    queryPurchases();

                    if (clickedRemoveAds) {
                        beginPurchaseFlow();
                    }

                } else {
                        Log.d(TAG, "problem with billing response code is: " + billingResponseCode);
                        Toast.makeText(MainActivity.this, R.string.ProblemLoadingBillingClient, Toast.LENGTH_SHORT).show();
                        // put queryPurchases here, which will this also retry the connection
                        queryPurchases();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                isBillingServiceConnected = false;
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Toast.makeText(MainActivity.this, R.string.BillingDisconnected, Toast.LENGTH_SHORT).show();


            }
        });


     // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setPageTransformer(true, new ForegroundToBackgroundTransformer());




        // get last completed day, jump to the next challenge you have to complete.
        SharedPreferences sharedPreferences = getSharedPreferences("LASTCOMPLETED",0);
        lastCompleted = sharedPreferences.getInt("LASTCOMPLETED", 0);



        if (lastCompleted != 0 ){
            Log.d("LASTCOMPLETED", lastCompleted + "");
            mViewPager.setCurrentItem(lastCompleted);  // do not need + 1, because pager starts at 0.
        }

        // get the Intent, if coming from Challenge Completed Page or the Trophy Page.  Move to appropriate next page to start off...
        int nextDay = getIntent().getIntExtra("NEXTDAY", 0);

        if (nextDay == 123) {
            mViewPager.setCurrentItem(0);
        } else if (nextDay != 0) {
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

        switch (id){
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
            case R.id.action_removeAds:
                beginPurchaseFlow();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void beginPurchaseFlow() {
        Log.d(TAG, "main Activity received the click action");

                //skuList.add("release_ads_and_content");
                // skuList.add("gas");
                //  skuList.add("android.test.purchased");
                //  skuList.add("android.test.canceled");
                //   skuList.add("android.test.unavailable");
        //TODO:  when testing, change skuID to release_ads_and_content

        String skuId = "android.test.purchased";
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSku(skuId)
                .setType(BillingClient.SkuType.INAPP) // SkuType.SUB for subscription
                .build();
        int responseCode = mBillingClient.launchBillingFlow(MainActivity.this, flowParams);

        Log.d(TAG, "billing flow response code for " + skuId + ": "+responseCode);

        // this, when finished, even if user cancels the flow, will trigger "onPurchasesUpdated, which calls onPurchasesUpdated2();


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



            return Day.newInstance(position, isFeatureSupported, mActivityContentPurchased);

        }

        @Override
        public int getCount() {
            // Show 30 total pages.
            return 30;
        }
/*
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
        }*/
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
                            // ...
                            Toast.makeText(MainActivity.this, R.string.Error + task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });









    }

    public void queryPurchases() {

        // creates a runnable background thread that queries google play store to see what has been purchased.
        Log.d(TAG, "queryPurchases called");
        Runnable queryToExecute = new Runnable() {
            @Override
            public void run() {
                long time = System.currentTimeMillis();

                // this will return what purchases you have made with the app.  It's pretty quick, apparently works with cache.
                // if cache is cleared, it may take a while.

                Purchase.PurchasesResult purchasesResult = mBillingClient.queryPurchases(BillingClient.SkuType.INAPP);
                onQueryPurchasesFinished(purchasesResult);

            }
        };

        executeServiceRequest(queryToExecute);
    }

    private void executeServiceRequest(Runnable runnable) {

        Log.d(TAG, "execute Service Request called");
        if (isBillingServiceConnected) {
            Log.d(TAG, "runnable being called");
            runnable.run();
        } else {
            // If billing service was disconnected, we try to reconnect 1 time.
            // (feel free to introduce your retry policy here).
            Log.d(TAG, getString(R.string.BillingDisconnectStartService));
            startServiceConnection(runnable);
        }
    }

    public void startServiceConnection(final Runnable executeOnSuccess) {
        mBillingClient.startConnection(new BillingClientStateListener() {

            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                Log.d(TAG, "Setup finished. Response code: " + billingResponseCode);

                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    isBillingServiceConnected = true;


                    // query purchases here...
                    if (executeOnSuccess != null) {
                        executeOnSuccess.run();
                    }


                }
                mBillingClientResponseCode = billingResponseCode;
            }

            @Override
            public void onBillingServiceDisconnected() {
                isBillingServiceConnected = false;
            }
        });
    }

    /**
     * Handle a result from querying of purchases and report an updated list to the listener
     */
    private void onQueryPurchasesFinished(Purchase.PurchasesResult result) {

        Log.d(TAG, "onQueryPurchasesFinished is called. result is: " +result.toString());

        // Have we been disposed of in the meantime? If so, or bad result code, then quit
        if (mBillingClient == null || result.getResponseCode() != BillingClient.BillingResponse.OK) {
            Log.e(TAG, "Billing client was null or result code (" + result.getResponseCode()
                    + ") was bad - quitting");
            return;
        }

        Log.d(TAG, "Query inventory was successful.");

        List<Purchase> innerPurchaseList = result.getPurchasesList();

        if (innerPurchaseList!=null) {

            for (Purchase purchase: innerPurchaseList) {
                switch (purchase.getSku()) {
                    case "release_ads_and_content":
                        // TODO: update the UI.
                        Log.d(TAG, "release_ads_and_content has already been purchased.");
                        Log.d(TAG, "update the UI with appropriate arguments by changing boolean");
                        Log.d(TAG, "mActivityContentPurchased = " +mActivityContentPurchased);
                        mActivityContentPurchased = true; // use this to update the UI.
                        Log.d(TAG, "now mActivityContentPurchased = " +mActivityContentPurchased);
                        break;
                    case "android.test.purchased":
                        Log.d(TAG, "android.test.purchased has already been purchased.");
                        Log.d(TAG, "update the UI with appropriate arguments by changing boolean");
                        Log.d(TAG, "mActivityContentPurchased = " +mActivityContentPurchased);
                        mActivityContentPurchased = true;
                        Log.d(TAG, "now mActivityContentPurchased = " +mActivityContentPurchased);
                        break;
                }
            }


        } else if (innerPurchaseList == null) {
            mActivityContentPurchased =false;
        }

      //  onPurchasesUpdated2(BillingClient.BillingResponse.OK, result.getPurchasesList());
    }

    private void onPurchasesUpdated2(int responseCode, List<Purchase> purchaseList) {

        if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
            Log.d(TAG, "user canceled the purchase flow");
            return;
        }

        Log.d(TAG, "Purchases updated called. response code:" +  responseCode);

        if (purchaseList == null) {
            Log.d(TAG, "no purchases have been made.  Perhaps this is new user. Resetting Content Purchased to false.");
            mActivityContentPurchased = false;
        }

        if (purchaseList!= null) {

            Log.d(TAG, "purchases updated called. Purchases List: " + purchaseList.toString());

            for(Purchase purchase: purchaseList) {

                //skuList.add("release_ads_and_content");
                // skuList.add("gas");
                //  skuList.add("android.test.purchased");
                //  skuList.add("android.test.canceled");
                //   skuList.add("android.test.unavailable");
                Intent purchaseSuccess = new Intent(MainActivity.this, ThankYou.class);
                SharedPreferences sharedPreferences = getSharedPreferences("CONTENTPURCHASED", 0);
                switch (purchase.getSku()) {
                    case "release_ads_and_content":
                        Log.d(TAG, "purchased release_ads_and content");
                        Log.d(TAG, "jumping to congrats activity");


                        sharedPreferences.edit().putBoolean("CONTENTPURCHASED", true).commit();

                        startActivity(purchaseSuccess);
                      break;
                    case "android.test.purchased":
                        Log.d(TAG, "purchased android.test.purchased");
                        Log.d(TAG, "jumping to congrats activity");

                        sharedPreferences.edit().putBoolean("CONTENTPURCHASED", true).commit();

                        startActivity(purchaseSuccess);
                        // TODO: add the token, or a boolean, to shared prefs... so then this device knows it has been purchased..
                        break;


                }
                Log.d(TAG, "original json: " + purchase.getOriginalJson());
                Log.d(TAG, "order id: " + purchase.getOrderId());
                Log.d(TAG, "signature: " + purchase.getSignature());
                Log.d(TAG, "package name: " + purchase.getPackageName());
                Log.d(TAG, "purchase Token: " + purchase.getPurchaseToken());
                Log.d(TAG, "purchase Sku: " + purchase.getSku());
                Log.d(TAG, "purchase time: " + purchase.getPurchaseTime());
                Log.d(TAG, "purchase to string: " + purchase.toString());
            }

        }

    }



    @Override
    protected void onResume() {
        Log.d(TAG, "onResume called.");
        queryPurchases();
        super.onResume();
    }

   public void showAd(final Intent intent) {


        if (!mActivityContentPurchased) {
            mInterstitialAd.setAdListener(new AdListener() {

                @Override
                public void onAdClosed() {
                    //TODO:  replace builder with old one.
                    AdRequest adRequest = new AdRequest.Builder().addTestDevice("8D254604C542C16046E353C4773A21DC").build();
                    //AdRequest adRequest = new AdRequest.Builder().build();

                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                    startActivity(intent);
                    super.onAdClosed();
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                }
            });


            // I may need to add an intent... pass the intent here as a parameter.
            Log.d(TAG, "show Ad called.");
            if (mInterstitialAd.isLoaded()) {
                Log.d(TAG, "show ad loaded");

                mInterstitialAd.show();
            } else {
                Log.d(TAG, "Ad not loaded");
                startActivity(intent);
            }
        } else {
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
