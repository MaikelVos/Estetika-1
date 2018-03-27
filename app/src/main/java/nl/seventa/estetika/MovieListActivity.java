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

import nl.seventa.estetika.async.MovieAsyncTask;
import nl.seventa.estetika.async.MovieListener;
import nl.seventa.estetika.domain.Movie;

public class MovieListActivity extends AppCompatActivity implements MovieListener{
    private final String TAG = this.getClass().getSimpleName();

    private ListView listView;
    private ArrayList<Movie> movies = new ArrayList<>();
    private MovieAdapter adapter;

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

        //databaseHelper = new DatabaseHelper(this);


        adapter = new MovieAdapter(this, 0, movies);


        listView = findViewById(R.id.movieListView);
        listView.setAdapter(adapter);

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
    private void fillMovies(){
        String url = "http://api.themoviedb.org/3/discover/movie?api_key=a50da447e13e19ad7c800e66c94868e7&language=" + R.string.language_filter;
        MovieAsyncTask task = new MovieAsyncTask(this);
        task.execute(url);
    }

    //Listens for completion of the Async task
    @Override
    public void onMovieListener(Movie movie) {
        movies.add(movie);
        adapter.notifyDataSetChanged();
        Log.i(TAG, "Dataset changed");
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
