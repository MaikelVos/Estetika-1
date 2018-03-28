package nl.seventa.estetika;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nl.seventa.estetika.domain.Movie;

public class MovieAdapter extends ArrayAdapter {
    private final String TAG = this.getClass().getSimpleName();
    private List<Movie> movies = new ArrayList();
    private TextView movieItemTitleTextView;
    private ImageView movieItemImageView;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List movies) {
        super(context, resource, movies);
        this.movies = movies;
    }


    @Override
    public int getCount() {
        return movies.size();
    }

    //Inflate item for ListView
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = movies.get(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);

        movieItemTitleTextView = convertView.findViewById(R.id.movieItemTitleTextView);
        movieItemImageView = convertView.findViewById(R.id.movieItemImageView);

        movieItemTitleTextView.setText(movie.getTitle());
        Picasso.with(getContext()).load(movie.getUrl()).into(movieItemImageView);

        return convertView;
    }
}
