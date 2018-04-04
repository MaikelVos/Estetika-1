package nl.seventa.estetika;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import nl.seventa.estetika.async.MovieListAsyncTask;
import nl.seventa.estetika.async.MovieListener;
import nl.seventa.estetika.domain.Movie;

import static nl.seventa.estetika.datalayer.Reserved_db.DATABASE_NAME;
import static nl.seventa.estetika.datalayer.Reserved_db.DB_EMAIL;
import static nl.seventa.estetika.datalayer.Reserved_db.DB_INDEX;
import static nl.seventa.estetika.datalayer.Reserved_db.DB_MOVIE_ID;

public class TicketListActivity extends AppCompatActivity implements MovieListener {
    private final String TAG = this.getClass().getSimpleName();
    private ArrayList<Integer> movieIDs;
    private Context mcontext;
    private ListView moviesLV;
    private EditText emailET;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movies;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);
        this.mcontext = this;
        this.movieIDs = new ArrayList<>();
        this.moviesLV = findViewById(R.id.moviesLV);
        this.movies = new ArrayList<>();
        this.emailET = findViewById(R.id.emailET);

        this.emailET.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    email = emailET.getText().toString();
                    getMovies(email);
                    fillMovies();
                    return true;
                }
                return false;

            }
        });




//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getApplicationContext(), MovieDetailActivity.class);
//                intent.putExtra(MovieDetailActivity.MOVIE_NAME, movies.get(i).getTitle());
//                intent.putExtra(MovieDetailActivity.ID_INSTANCE, movies.get(i).getMovieId());
//                startActivity(intent);
//            }
//        });



        movieAdapter = new MovieAdapter(this, 0, movies);
        this.moviesLV.setAdapter(movieAdapter);

//        moviesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent i = new Intent(getApplicationContext(), TicketDetailActivity.class);
//
//                Log.i(TAG, "Movie: " + movies.get(position));
//                i.putExtra("MOVIE", movies.get(position));
//                i.putExtra("EMAIL", email);
//                startActivity(i);
//
//            }
//        });
    }

    private void getMovies(String email) {
        movieIDs.clear();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(mcontext.getDatabasePath("reservations").getPath(),
                null,
                SQLiteDatabase.OPEN_READWRITE);

        String query = "SELECT DISTINCT * FROM " + DATABASE_NAME +
                " WHERE " + DB_EMAIL + " = " + " '" + email + "' " +
                " ORDER BY " + DB_INDEX + " DESC";
        Cursor cursor = db.rawQuery(query, null);

        for (int i = 0; i < cursor.getCount(); i++) {

            cursor.moveToPosition(i);
            int movieId = cursor.getInt(cursor.getColumnIndex(DB_MOVIE_ID));
            if(!movieIDs.contains(movieId)){
                movieIDs.add(movieId);
            }
        }
        Log.i(TAG, "movieId" + movieIDs.toString());
    }

    private void fillMovies() {
        String filter = getResources().getString(R.string.language_filter);
        String url = "http://api.themoviedb.org/3/discover/movie?api_key=a50da447e13e19ad7c800e66c94868e7&language=" + filter;
        MovieListAsyncTask task = new MovieListAsyncTask(this);
        Log.i(TAG, filter);
        task.execute(url);
    }


    @Override
    public void onMovieListener(Movie movie) {
        if (movieIDs.contains(movie.getMovieId())) {
            movies.add(movie);
            movieAdapter.notifyDataSetChanged();
            Log.i(TAG, "Dataset changed");
        }
    }
}