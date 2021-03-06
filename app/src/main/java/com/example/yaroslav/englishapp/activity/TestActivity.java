package com.example.yaroslav.englishapp.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.yaroslav.englishapp.DeveloperKey;
import com.example.yaroslav.englishapp.R;
import com.example.yaroslav.englishapp.fragment.FilolingviaFragment;
import com.example.yaroslav.englishapp.fragment.LingvoTestFragment;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

public class TestActivity extends YouTubeBaseActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public  int LESSON_TYPE;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mLessonTitles;
    private String openedCategory;
    private String PLAY_LIST_ID;
    private Intent demoIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        LESSON_TYPE = getIntent().getIntExtra("TEST_TYPE", -1);
        if(LESSON_TYPE == 1) {
            mLessonTitles = getResources().getStringArray(R.array.Filolingvia_lessons);
            openedCategory = "Филолингвия";
        }else if (LESSON_TYPE == 2){
            mLessonTitles = getResources().getStringArray(R.array.Lingvomap_lessons);
            openedCategory = "Лингвокарта";
        }
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mLessonTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(openedCategory);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
              if(LESSON_TYPE == 1){
                  PLAY_LIST_ID = "MoHet6GSRUM";
                  demoIntent = YouTubeStandalonePlayer.createPlaylistIntent(this, DeveloperKey.DEVELOPER_KEY,
                          PLAY_LIST_ID, 0,0,false, false);
                  startActivity(demoIntent);

              }else {
                  PLAY_LIST_ID = "PLiY5YQfYGiwk-5rqGHnFz-EuX26_rMnxv";
                  demoIntent = YouTubeStandalonePlayer.createPlaylistIntent(this, DeveloperKey.DEVELOPER_KEY,
                          PLAY_LIST_ID, 0,0,false, false);
                  startActivity(demoIntent);
              }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        if( LESSON_TYPE == 1) {
            fragment = new FilolingviaFragment();
        }
        else
        {fragment = new LingvoTestFragment();}
        Bundle args = new Bundle();
        args.putInt(LingvoTestFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mLessonTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}