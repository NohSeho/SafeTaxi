package com.safetaxik.adapter;

import com.safetaxik.R;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class RidingPagerAdapter extends PagerAdapter {
	LayoutInflater	inflater;

	public RidingPagerAdapter(Context context) {
		super();
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return 2;
	}

	@Override
	public Object instantiateItem(View pager, int position) {
		View v = null;
		if (position == 0) {
			v = inflater.inflate(R.layout.search_fragment, null);
		} else {
			v = inflater.inflate(R.layout.maps_fragment, null);
		}

		((ViewPager) pager).addView(v, 0);

		return v;
	}

	@Override
	public void destroyItem(View pager, int position, Object view) {
		((ViewPager) pager).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View pager, Object obj) {
		return pager == obj;
	}

	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {
		super.restoreState(state, loader);
	}

	@Override
	public Parcelable saveState() {
		return super.saveState();
	}

	@Override
	public void startUpdate(View container) {
		super.startUpdate(container);
	}

	@Override
	public void finishUpdate(View container) {
		super.finishUpdate(container);
	}
}
