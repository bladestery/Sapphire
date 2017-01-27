/*******************************************************************************
 * Copyright (c) 2011 ETH Zurich.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Paolo Carta - Implementation
 *     Theus Hossmann - Implementation
 *     Dominik Schatzmann - Message specification
 ******************************************************************************/
package ch.ethz.twimight.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.view.ViewPager;
import ch.ethz.twimight.R;
import ch.ethz.twimight.fragments.SearchTweetsFragment;
import ch.ethz.twimight.fragments.SearchUsersFragment;
import ch.ethz.twimight.fragments.adapters.FragmentListPagerAdapter;
import ch.ethz.twimight.listeners.TabListener;
import ch.ethz.twimight.util.TwimightSuggestionProvider;

public class SearchableActivity extends TwimightBaseActivity /*
															 * implements
															 * OnInitCompletedListener
															 */{

	// private static final String TAG = "SearchableActivity";

	private SearchUsersFragment mUserFragment;
	private SearchTweetsFragment mTweetsFragment;

	public static final String EXTRA_KEY_INITIAL_TAB = "EXTRA_KEY_INITIAL_TAB";
	public static final String EXTRA_INITIAL_TAB_TWEETS = "EXTRA_INITIAL_TAB_TWEETS";
	public static final String EXTRA_INITIAL_TAB_USERS = "EXTRA_INITIAL_TAB_USERS";

	ViewPager mViewPager;
	public static String mQuery;
	FragmentListPagerAdapter mPagerAdapter;
	Intent mIntent;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(null);
		setContentView(R.layout.main);

		mViewPager = (ViewPager) findViewById(R.id.viewpager);

		mTweetsFragment = new SearchTweetsFragment();
		mUserFragment = new SearchUsersFragment();

		mPagerAdapter = new FragmentListPagerAdapter(getFragmentManager());
		mPagerAdapter.addFragment(mTweetsFragment);
		mPagerAdapter.addFragment(mUserFragment);

		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(actionBar.getSelectedNavigationIndex());

		// action bar
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between pages, select the
				// corresponding tab.
				getActionBar().setSelectedNavigationItem(position);
			}
		});

		Tab tab = actionBar.newTab().setText(getString(R.string.tweets)).setTabListener(new TabListener(mViewPager));
		actionBar.addTab(tab);

		tab = actionBar.newTab().setText(getString(R.string.users)).setTabListener(new TabListener(mViewPager));
		actionBar.addTab(tab);
		
		mIntent = getIntent();
		// Get the intent and get the query
		setInitialTab(getIntent());

	}

	private void setInitialTab(Intent intent) {
		int initialPosition = 0;
		if (intent.hasExtra(EXTRA_KEY_INITIAL_TAB)) {
			String initialTab = intent.getStringExtra(EXTRA_KEY_INITIAL_TAB);

			if (EXTRA_INITIAL_TAB_TWEETS.equals(initialTab)) {
				initialPosition = 0;
			} else if (EXTRA_INITIAL_TAB_USERS.equals(initialTab)) {
				initialPosition = 1;
			}
			intent.removeExtra(EXTRA_KEY_INITIAL_TAB);
		}
		mViewPager.setCurrentItem(initialPosition);
	}

	private void processIntent(Intent intent) {
		if (intent.hasExtra(SearchManager.QUERY)) {
			// if (!intent.getStringExtra(SearchManager.QUERY).equals(query))
			mQuery = intent.getStringExtra(SearchManager.QUERY);
			getActionBar().setSubtitle("\"" + mQuery + "\"");
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
					TwimightSuggestionProvider.AUTHORITY, TwimightSuggestionProvider.MODE);
			suggestions.saveRecentQuery(mQuery, null);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		processIntent(intent);
		setInitialTab(intent);
		mTweetsFragment.notifyNewQuery();
		mUserFragment.notifyNewQuery();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		processIntent(mIntent);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mPagerAdapter = null;
		mViewPager = null;

	}

}
