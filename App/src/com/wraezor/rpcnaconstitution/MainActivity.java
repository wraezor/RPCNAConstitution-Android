package com.wraezor.rpcnaconstitution;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private String[] mMenuStringItems;
    //private CharSequence mDrawerTitle;
    //private CharSequence mTitle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    //private SpinnerAdapter mSpinnerAdapter;
    private LinearLayout mContent;
    private ScrollView mScrollView;
    
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        
        //mTitle = mDrawerTitle = getTitle();
        mMenuStringItems = getResources().getStringArray(R.array.navdrawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.menu_drawer);
        mContent = (LinearLayout) findViewById(R.id.content_area);
        mScrollView = (ScrollView) findViewById(R.id.scrollview_area);
        
        // Populate initial textview
        TextView mBodyTextView = new TextView(this);
        mBodyTextView.setText("Use the navigation bar to select an option");
        mContent.addView(mBodyTextView);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.menu_item, mMenuStringItems));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                //getActionBar().setSubtitle("Covenant of Communicant Membership");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                //getActionBar().setSubtitle("Covenant of Communicant Membership");
            }
        };
        
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        /** Enabling dropdown list navigation for the action bar */
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        
        /** Defining Navigation listener */
        ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                //Toast.makeText(getBaseContext(), "You selected item: " + itemPosition  , Toast.LENGTH_SHORT).show();
                final int itemLocation = itemPosition;
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                    	mScrollView.scrollTo(0, mContent.getChildAt(itemLocation).getTop());
                    }
                });
                return false;
            }
        };
        
        
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> mSpinnerAdapter = ArrayAdapter.createFromResource(getActionBar().getThemedContext(), R.array.history_menu_items, android.R.layout.simple_spinner_item);
		//ArrayAdapter<String> mSpinnerAdapter2 = new ArrayAdapter<String>(getActionBar().getThemedContext(), R.layout.spinner_item, R.array.history_menu_items);
		
		// Specify the layout to use when the list of choices appears
		mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        /** Setting dropdown items and item navigation listener for the actionbar */
        getActionBar().setListNavigationCallbacks(mSpinnerAdapter, navigationListener);  
        
        
	  
    }

	
	private void selectItem(int position) {
		
	    //String documentText = getResources().getStringArray(R.array.RPC_documents)[position];	    
		//mBodyTextView.setText(Html.fromHtml(documentText));
		
		// Empty old content
		if (mContent.getChildCount() == 1) {
			mContent.removeAllViewsInLayout();
		}
		
        // Populate content
		String[] itemText = getResources().getStringArray(R.array.history_text);
		for (int i = 0; i < itemText.length; i++) {
	        TextView mBodyTextView = new TextView(this);
	        mBodyTextView.setText(Html.fromHtml(itemText[i]));
	        mContent.addView(mBodyTextView);
		}

        
		mDrawerList.setItemChecked(position, true);
	    setTitle(mMenuStringItems[position]);
	    mDrawerLayout.closeDrawer(mDrawerList);
	    mScrollView.scrollTo(0, 0);
	}

	@Override
	public void setTitle(CharSequence title) {
	    //mTitle = title;
	    //getActionBar().setTitle(mTitle);
	}
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        selectItem(position);
	    }
	}
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }    
}

// End
