package warobotics.waroboticsscoutweb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebAppWebView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int currentNavItem = R.id.nav_match_list;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_app_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //noinspection deprecation
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true); //set My matches as selected by default in the navigation drawer
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

        Intent intent = getIntent();
        Uri data = intent.getData();

        WebView webAppWebView = (WebView) findViewById(R.id.webView);
        webAppWebView.loadUrl("https://script.google.com/macros/s/AKfycbyTkgLPRNpwivT4qBYKACo7Z33WVStperAZ6YpdwHXERVjuiWc/exec?fromandroidapp");
        webAppWebView.setWebViewClient(new WebViewClient());
        webAppWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        WebSettings webSettings = webAppWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }

        /**Use an intent to open a browser to show the feedback form */
        @JavascriptInterface
        public void openFeedbackForm() {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/a/woodward.edu/forms/d/1W2IYYH-SRWF5gKdhqbj2fdIivSLQyHm5fY-3UcNabY0/viewform"));
            startActivity(browserIntent);
        }

        /**Deselect all items in the nav drawer.  Use this to deselect the items in the nav drawer when the user navigates to a page that can't be indicated in
         * the nav drawer, such as the match details page
         */
        @JavascriptInterface
        public void deselectNavDrawerItems() {
            runOnUiThread(new Runnable() { //the code to update the UI needs to run on the main thread
                              @Override
                              public void run() {
                                  NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                                  navigationView.getMenu().getItem(0).setChecked(false); //deselect My matches in the nav drawer
                                  navigationView.getMenu().getItem(1).setChecked(false); //deselect Team search in the nav drawer

                                  currentNavItem = -1; //reset currentNavItem so that after the item is deselected in the nav drawer, tapping on any item in the nav drawer
                                                       //   (not just the one that had been selected before all items in the nav drawer were deselected) will result in a
                                                       //   response from the WebView
                              }
                          }
            );

        }
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
        getMenuInflater().inflate(R.menu.web_app_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_refresh) { //refresh button pressed
            WebView webAppWebView = (WebView) findViewById(R.id.webView);
            webAppWebView.loadUrl(webAppWebView.getUrl()); //reload the current page displayed in the webview
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadMatchPage() {
        WebView webAppWebView = (WebView) findViewById(R.id.webView);
        webAppWebView.loadUrl("https://script.google.com/macros/s/AKfycbyTkgLPRNpwivT4qBYKACo7Z33WVStperAZ6YpdwHXERVjuiWc/exec?fromandroidapp");
    }

    public void loadTeamSearchPage() {
        WebView webAppWebView = (WebView) findViewById(R.id.webView);
        webAppWebView.loadUrl("https://script.google.com/macros/s/AKfycbyTkgLPRNpwivT4qBYKACo7Z33WVStperAZ6YpdwHXERVjuiWc/exec?page=team-search&fromandroidapp");
    }

    public void loadScoutingFormPage() {
        WebView webAppWebView = (WebView) findViewById(R.id.webView);
        webAppWebView.loadUrl("https://script.google.com/a/macros/woodward.edu/s/AKfycbzjo4-KCrLdrFOcpJCwg3kwWYenjFyV8C6aAxfVZs4/exec?page=scouting-form&fromandroidapp");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_match_list && currentNavItem != R.id.nav_match_list) { //match list button pressed in sidebar (only if this item is not already selected)
            loadMatchPage();
            currentNavItem = R.id.nav_match_list;
        } else if (id == R.id.nav_team_search && currentNavItem != R.id.nav_team_search) { //team search button pressed in sidebar (only if this item is not already selected)
            loadTeamSearchPage();
            currentNavItem = R.id.nav_team_search;
        } else if (id == R.id.nav_scouting_form && currentNavItem != R.id.nav_scouting_form) { //scouting form button pressed in sidebar (only if this item is not already selected)
            loadScoutingFormPage();
            currentNavItem = R.id.nav_scouting_form;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

