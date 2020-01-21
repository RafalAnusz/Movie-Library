package com.rafal.movielibrary.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import static com.rafal.movielibrary.model.Movie.Status.Planned;
import static com.rafal.movielibrary.model.Movie.Status.Watched;

public class DataRepository {

    private static volatile DataRepository INSTANCE;

    public static DataRepository getRepository(final Context context) {
        if(INSTANCE == null) {
            synchronized (DataRepository.class) {
                if(INSTANCE == null) {
                    INSTANCE = new DataRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    private MovieDao movieDao;

    private DataRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        movieDao = db.movieDao();
    }

    public LiveData<Movie> getMovie(long id) {
        return movieDao.getMovie(id);
    }

    public LiveData<List<Movie>> getMoviesPlanned() {
        return movieDao.loadAlphabetizedMovies(Planned.getNum());
    }

    public LiveData<List<Movie>> getMoviesWatched() {
        return movieDao.loadAlphabetizedMovies(Watched.getNum());
    }

    public void updateMovies(Movie... movies) {
        new updateAsyncTask(movieDao).execute(movies);
    }

    public void deleteMovies(Movie... movies) {
        new deleteAsyncTask(movieDao).execute(movies);
    }

    public void insertMovies (Movie ... movies) {
        new insertAsyncTask(movieDao).execute(movies);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao asyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movies) {
            asyncTaskDao.insertMovies(movies);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao asyncTaskDao;

        updateAsyncTask(MovieDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movies) {
            asyncTaskDao.updateMovies(movies);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao asyncTaskDao;

        deleteAsyncTask(MovieDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movies) {
            asyncTaskDao.deleteMovies(movies);
            return null;
        }
    }
}
