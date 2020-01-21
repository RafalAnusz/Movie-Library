package com.rafal.movielibrary.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.rafal.movielibrary.R;

import java.io.File;

public class BindingAdapters {

    @BindingAdapter({"isGone"})
    public static void bindIsGone(@NonNull View view, boolean isGone) {
        if(isGone) {
            view.setVisibility(view.GONE);
        } else {
            view.setVisibility(view.VISIBLE);
        }
    }

    @BindingAdapter({"imageFromUri"})
    public static void bindImageFromUri(@NonNull ImageView view, Uri uri) {
        Glide.with(view.getContext())
                .load(uri)
                .fallback(R.drawable.ic_no_cover)
                .placeholder(R.drawable.ic_no_cover)
                .error(R.drawable.ic_clear_black_24dp)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

    @BindingAdapter({"starsAdapter"})
    public static void bindStarsAdapter(@NonNull View view, int rating) {
        new updateStarsAsyncTask(view).execute(rating);
    }

    private static class updateStarsAsyncTask extends AsyncTask<Integer, Void, Void> {
        private int[][] stars = new int[][]{{R.id.star1, 0}, {R.id.star2, 0}, {R.id.star3, 0},
                {R.id.star4, 0}, {R.id.star5, 0}};
        private View view;

        public updateStarsAsyncTask(View view) {
            this.view = view;
        }

        @Override
        protected Void doInBackground(Integer... rating) {
            for(int i = 0; i < stars.length; i++) {
                if(i < rating[0]) stars[i][1] = R.drawable.ic_star_gold_24dp;
                else stars[i][1] = R.drawable.ic_star_border_gold_24dp;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            for(int[] star : stars) {
                ((MaterialButton)view.findViewById(star[0])).setIconResource(star[1]);
            }
        }
    }
}
