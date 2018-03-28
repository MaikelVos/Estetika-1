package nl.seventa.estetika;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import nl.seventa.estetika.async.GenreAsyncTask;
import nl.seventa.estetika.async.GenreListener;
import nl.seventa.estetika.async.MovieAsyncTask;
import nl.seventa.estetika.async.MovieListAsyncTask;
import nl.seventa.estetika.async.MovieListener;
import nl.seventa.estetika.domain.Genre;
import nl.seventa.estetika.domain.Movie;

public class MovieListActivity extends AppCompatActivity implements MovieListener, GenreListener{
    private final String TAG = this.getClass().getSimpleName();

    private ListView listView;
    private Spinner genreSpinner;
    private ArrayList<Movie> movies = new ArrayList<>();
    private ArrayList<Genre> genres = new ArrayList<>();
    private ArrayList<String> genreStrings = new ArrayList<>();
    private ArrayAdapter<String> genreAdapter;
    private MovieAdapter movieAdapter;
    private String genreQuery = "";

    public static final String MOVIES_INSTANCE = "Movies";
    //public static DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

//Checks if there is a saved instance state and if it contains a movies array
        if(savedInstanceState != null && savedInstanceState.getSerializable(MOVIES_INSTANCE) != null){
            movies = (ArrayList<Movie>) savedInstanceState.getSerializable(MOVIES_INSTANCE);
        }else {
            movies = new ArrayList<>();
            fillMovies();
        }

        fillGenres();

        genreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genreStrings);

        movieAdapter = new MovieAdapter(this, 0, movies);

        genreSpinner = findViewById(R.id.genreSpinner);
        genreSpinner.setAdapter(genreAdapter);

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedGenre = (String) adapterView.getItemAtPosition(i);
                Log.i(TAG, "Selected genre: " + selectedGenre);

                //If the selected index is greater then 0, camera string equals the selected camera
                if (adapterView.getSelectedItemPosition() > 0) {

                    for(Genre genre:genres){
                        if(genre.getName().equals(selectedGenre)){
                            genreQuery = "&with_genres=" + genre.getId();
                        }
                    }
                } else {
                    genreQuery = "";
                }
                movies.clear();
                fillMovies();
                genreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        listView = findViewById(R.id.movieListView);
        listView.setAdapter(movieAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.ID_INSTANCE, movies.get(i).getMovieId());
                startActivity(intent);
            }
        });
    }

    //Creates and executes an Async task to fetch data from the API
    private void fillGenres(){
        genreStrings.add(getResources().getString(R.string.spinner_all));
        String filter = getResources().getString(R.string.language_filter);
        String url = "http://api.themoviedb.org/3/genre/movie/list?api_key=a50da447e13e19ad7c800e66c94868e7&language=" + filter;
        GenreAsyncTask task = new GenreAsyncTask(this);
        task.execute(url);
    }

    private void fillMovies(){
        String filter = getResources().getString(R.string.language_filter);
        String url = "http://api.themoviedb.org/3/discover/movie?api_key=a50da447e13e19ad7c800e66c94868e7&language=" + filter + genreQuery;
        MovieListAsyncTask task = new MovieListAsyncTask(this);
        Log.i(TAG, filter);
        task.execute(url);
    }

    //Listens for completion of the Async task
    @Override
    public void onMovieListener(Movie movie) {
        movies.add(movie);
        movieAdapter.notifyDataSetChanged();
        Log.i(TAG, "Dataset changed");
    }

    @Override
    public void onGenreListener(Genre genre) {
        genres.add(genre);
        genreStrings.add(genre.getName());
        genreAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MOVIES_INSTANCE, movies);
        Log.i(TAG, "Saved instance state");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movies = (ArrayList<Movie>) savedInstanceState.getSerializable(MOVIES_INSTANCE);
        Log.i(TAG, "Saved instance restored");
    }
}
