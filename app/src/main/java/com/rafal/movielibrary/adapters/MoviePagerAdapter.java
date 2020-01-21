package com.rafal.movielibrary.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.rafal.movielibrary.PlannedListFragment;
import com.rafal.movielibrary.WatchedListFragment;

public class MoviePagerAdapter extends FragmentStateAdapter {

    public static final int PLANNED_PAGE_INDEX = 0;
    public static final int WATCHED_PAGE_INDEX = 1;

    public MoviePagerAdapter(Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case PLANNED_PAGE_INDEX:
                return new PlannedListFragment();
            case WATCHED_PAGE_INDEX:
                return new WatchedListFragment();
            default:
                throw new IndexOutOfBoundsException();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
