package com.adamrowe.main;

import com.adamrowe.jsonstreaming.JsonSearchFragment;
import com.example.jsonreaddata.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

//Home Activity
public class MainActivity extends FragmentActivity 
{	
	//Tab Pager Adapter to instantiate fragments when toggled
	private PhysiciansTabPagerAdapter physiciansTabPagerAdapter;
	
	//ViewPager to toggle between Web and Local Physician Search Fragments
	private ViewPager physiciansSearchViewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity_layout);
		
		physiciansTabPagerAdapter = new PhysiciansTabPagerAdapter(getSupportFragmentManager());
		physiciansSearchViewPager = (ViewPager) findViewById(R.id.searchTypesPager);
		physiciansSearchViewPager.setAdapter(physiciansTabPagerAdapter);
	}
	
	public ViewPager getPhysiciansSearchViewPager()
	{
		return physiciansSearchViewPager;
	}
	
	private class PhysiciansTabPagerAdapter extends FragmentStatePagerAdapter 
	{
		public PhysiciansTabPagerAdapter(FragmentManager fm) 
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position) 
		{
			switch (position) 
			{
				case 0:
					return new JsonSearchFragment(); //Default is Web
				case 1:
					return new JsonSearchFragment(); //Second is Local
				default:
					return null;			
			}
		}
		
		@Override
        public CharSequence getPageTitle(int position) 
		{
			if(position == 0) //WebPhysiciansSearchFragment
			{
				return getResources().getString(R.string.web_search);
			}
			else // if(position == 1) LocalPhysiciansSearchFragment
			{
				return getResources().getString(R.string.physicians_search);
			}
        }

		@Override
		public int getCount() 
		{
			return 2;
		}
		
	}
}
