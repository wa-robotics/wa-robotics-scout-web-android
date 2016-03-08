package warobotics.waroboticsscoutweb;

import android.annotation.SuppressLint;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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

        WebView webAppWebView = (WebView) findViewById(R.id.webView);
        webAppWebView.loadUrl("https://script.google.com/macros/s/AKfycbyTkgLPRNpwivT4qBYKACo7Z33WVStperAZ6YpdwHXERVjuiWc/exec?fromandroidapp");
        webAppWebView.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webAppWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
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

        //noinspection SimplifiableIfStatement
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_match_list && currentNavItem != R.id.nav_match_list) { //match list button pressed in sidebar (only if this item is not already selected)
            loadMatchPage();
            currentNavItem = R.id.nav_match_list;
        } else if (id == R.id.nav_team_search && currentNavItem != R.id.nav_team_search) { //team search button pressed in sidebar (only if this item is not already selected)
            currentNavItem = R.id.nav_team_search;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

