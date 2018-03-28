package nl.seventa.estetika.async;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import nl.seventa.estetika.domain.Movie;

public class MovieAsyncTask extends AsyncTask<String, Void, String> {
    private MovieListener listener = null;
    private final String TAG = this.getClass().getSimpleName();

    public MovieAsyncTask(MovieListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {

        InputStream inputStream;
        int responsCode = -1;
        // The URL recieved through .execute()
        String personUrl = strings[0];
        // Defining the return
        String response = "";

        Log.i(TAG, "doInBackground - " + personUrl);
        try {
            //Create an URL object
            URL url = new URL(personUrl);
            //Open a connection on the URL
            URLConnection urlConnection = url.openConnection();

            if (!(urlConnection instanceof HttpURLConnection)) {
                return null;
            }

            //Initialise HTTP connection
            HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
            httpConnection.setAllowUserInteraction(false);
            httpConnection.setInstanceFollowRedirects(true);
            httpConnection.setRequestMethod("GET");

            // Execute HTTP request
            httpConnection.connect();

            //Checking response
            responsCode = httpConnection.getResponseCode();
            if (responsCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpConnection.getInputStream();
                response = getStringFromInputStream(inputStream);
                Log.i(TAG, "doInBackground response = " + response);
            } else {
                Log.e(TAG, "Error, invalid response");
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground MalformedURLEx " + e.getLocalizedMessage());
            return null;
        } catch (IOException e) {
            Log.e(TAG, "doInBackground IOException " + e.getLocalizedMessage());
            return null;
        }
        return response;
    }

    /**
     * onPostExecute verwerkt het resultaat uit de doInBackground methode.
     *
     * @param response
     */
    protected void onPostExecute(String response) {

        Log.i(TAG, "onPostExecute " + response);

        // Check of er een response is
        if (response == null || response.equals("")) {
            Log.e(TAG, "This response empty YEET!!!");
            return;
        }

        JSONObject object;
        try {
            // Top level json object
            object = new JSONObject(response);

            //Get movie id
            int id = object.getInt("id");
            String title = object.getString("title");

            Log.i(TAG, "-----------" + title + "------------");

            //get genres
            JSONArray genres = object.getJSONArray("genres");
            JSONObject genreObject = (JSONObject) genres.get(0);

            String genre = genreObject.getString("name");

            //Get duration
            String duration = Integer.toString(object.getInt("runtime"));

            //Get image source
            String imgUrl = "https://image.tmdb.org/t/p/w185" + object.getString("poster_path");

            //Get description
            String description = object.getString("overview");

            Log.i(TAG, "Got movie " + id);

            // Create new Movie object
            Movie movie = new Movie();
            movie.setMovieId(id);
            movie.setTitle(title);
            movie.setUrl(imgUrl);
            movie.setDuration(duration);
            movie.setGenre(genre);
            movie.setDescription(description);

            //Callback with new movie data
            listener.onMovieListener(movie);
        } catch (JSONException ex) {
            Log.e(TAG, "onPostExecute JSONException " + ex.getLocalizedMessage());
        }
    }

    //Convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}