package com.mpandey.reactivegroovy;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView
import groovy.transform.CompileStatic;

@CompileStatic
class MainActivity extends Activity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;


    //ViewPager.setOffscreenPageLimit(int) way to control how many pages

    private static final String TAG = MainActivity.class.getName();
    private String[] sections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sections = ["In_Theaters","DVD_Releases","Opening","In_Box_Office"];
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager(), this, sections);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
        for (String section : sections) {
            actionBar.addTab(actionBar.newTab().setText(getName(section)).setTabListener(this));
        }
    }


    /* String name in resource files can not have spaces,
       hence replace the _ with spaces to get the actual desired
       values.
    */
    public String getName(String withUnderscores) {
        return withUnderscores.replaceAll("_", " ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private Context context;
        private String[] sections;

        public SectionsPagerAdapter(FragmentManager fm, Context context, String[] sections) {
            super(fm);
            this.context = context;
            this.sections = sections;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position, context);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return sections.length;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private int position;
        private static String [] sections;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Context context) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt("position", sectionNumber);
            fragment.setArguments(args);
            fragment.sections = ["In_Theaters","DVD_Releases","Opening","In_Box_Office"];
            fragment.position = sectionNumber;
            Log.d(TAG, "creating a new fragment");
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            int labelId = getResources().getIdentifier(sections[position], "string", getActivity().getPackageName());
            String url = getResources().getString(labelId);
            String KEY = getString(R.string.ROTTEN_KEY);
            GridAdapter gridAdapter = new GridAdapter(this.getActivity(), url + KEY);


            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            GridView gridview = (GridView) rootView.findViewById(R.id.gridView);
            Log.w(TAG,gridAdapter.toString());
            gridview.setAdapter(gridAdapter);
            gridview.setOnItemClickListener({AdapterView<?> parent, View v, int position, long id ->

                Movie movie = gridAdapter.getItem(position);
                Intent intent = new Intent(this.getActivity(), MovieActivity.class);
                intent.putExtra("movie", movie);
                this.getActivity().startActivity(intent);
            });
            Log.d(TAG, "creating new grid");
            return rootView;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(getArguments() != null) {
                position = getArguments().getInt("position");
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.w(TAG,"Destroying");
        }
    }

}