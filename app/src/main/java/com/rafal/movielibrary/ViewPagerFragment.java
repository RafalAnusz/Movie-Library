package com.rafal.movielibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.snackbar.Snackbar;
import com.rafal.movielibrary.adapters.MoviePagerAdapter;
import com.rafal.movielibrary.databinding.FragmentViewPagerBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import static com.rafal.movielibrary.AddMovieActivity.RESULT_MOVIE_ADDED;
import static com.rafal.movielibrary.AddMovieActivity.RESULT_MOVIE_MODIFIED;

public class ViewPagerFragment extends Fragment {

    static final int ADD_MOVIE_REQUEST = 1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentViewPagerBinding binding =
                FragmentViewPagerBinding.inflate(inflater, container, false);
        TabLayout tabLayout = binding.tabs;
        ViewPager2 viewPager = binding.viewPager;

        viewPager.setAdapter(new MoviePagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(getTabTitle(position))
        ).attach();

        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.toolbar);

        binding.setClickListener(addMovie);

        return binding.getRoot();
    }

    private String getTabTitle(int position) {
        switch (position) {
            case MoviePagerAdapter.PLANNED_PAGE_INDEX:
                return getString(R.string.planned_title);
            case MoviePagerAdapter.WATCHED_PAGE_INDEX:
                return getString(R.string.watched_title);
            default:
                return "Err";
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_MOVIE_REQUEST) {
            if (resultCode == RESULT_MOVIE_ADDED) {
                Snackbar.make(this.getView(), getString(R.string.movie_added), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        }
    }

    private View.OnClickListener addMovie = v -> {
        Intent intent = new Intent(getContext(), AddMovieActivity.class);
        startActivityForResult(intent, ADD_MOVIE_REQUEST);
    };
}
