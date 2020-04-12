package ca.mohawk.jefftrinh.ui.home.movie_info;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import ca.mohawk.jefftrinh.DialogFragment;
import ca.mohawk.jefftrinh.DownloadImageTask;
import ca.mohawk.jefftrinh.R;

/**
 * Implementation of Movie Info section in Home screen
 * This is the child fragment of HomeFragment
 *
 * @author Khac Duy Trinh
 */
public class MovieInfoFragment extends Fragment implements View.OnClickListener {
    JSONObject movieInfoJson;
    ImageView moviePoster;
    TextView titleTextView;
    TextView dateTextView;
    TextView directorsTextView;
    TextView actorsTextView;
    TextView genresTextView;
    TextView plotTextView;
    View root;
    Button addMovieButton;
    Context c;

    String title;
    String year;
    String posterUrl;
    String note;

    public View onCreateView ( @NonNull LayoutInflater inflater ,
                               ViewGroup container , Bundle savedInstanceState ) {
        root = inflater.inflate(R.layout.fragment_movie, container, false);
        c = getActivity();

        moviePoster = root.findViewById(R.id.imageView);
        titleTextView = root.findViewById(R.id.titleTextView);
        dateTextView = root.findViewById(R.id.dateTextView);
        directorsTextView = root.findViewById(R.id.directorsTextView);
        actorsTextView = root.findViewById(R.id.actorsTextView);
        genresTextView = root.findViewById(R.id.genresTextView);
        plotTextView = root.findViewById(R.id.plotTextView);
        addMovieButton = root.findViewById(R.id.addMovieButton);

        plotTextView.setMovementMethod(new ScrollingMovementMethod ());

        addMovieButton.setOnClickListener ( this );

        // Read -Restore data from preferences
        SharedPreferences settings = getActivity().getPreferences(0);
        String movieInfo = settings.getString("movieInfo", "{\"Title\":\"Joker\",\"Year\":\"2019\",\"Rated\":\"R\",\"Released\":\"04 Oct 2019\",\"Runtime\":\"122 min\",\"Genre\":\"Crime, Drama, Thriller\",\"Director\":\"Todd Phillips\",\"Writer\":\"Todd Phillips, Scott Silver, Bob Kane (based on characters created by), Bill Finger (based on characters created by), Jerry Robinson (based on characters created by)\",\"Actors\":\"Joaquin Phoenix, Robert De Niro, Zazie Beetz, Frances Conroy\",\"Plot\":\"In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society. He then embarks on a downward spiral of revolution and bloody crime. This path brings him face-to-face with his alter-ego: the Joker.\",\"Language\":\"English\",\"Country\":\"USA, Canada\",\"Awards\":\"Won 2 Oscars. Another 84 wins & 190 nominations.\",\"Poster\":\"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.6\\/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"68%\"},{\"Source\":\"Metacritic\",\"Value\":\"59\\/100\"}],\"Metascore\":\"59\",\"imdbRating\":\"8.6\",\"imdbVotes\":\"709,240\",\"imdbID\":\"tt7286456\",\"Type\":\"movie\",\"DVD\":\"17 Dec 2019\",\"BoxOffice\":\"N\\/A\",\"Production\":\"Warner Bros. Pictures\",\"Website\":\"N\\/A\",\"Response\":\"True\"}");

//      Convert String to JSON object
        try {
            movieInfoJson = new JSONObject(movieInfo);
//            Log.d("Movie Info ", movieInfoJson.getString("Title"));
            title = movieInfoJson.getString("Title");
            String directors = movieInfoJson.getString("Director");
            year = movieInfoJson.getString("Year");
            String released = movieInfoJson.getString("Released");
            String actors = movieInfoJson.getString("Actors");
            String genre = movieInfoJson.getString("Genre");
            String plot = movieInfoJson.getString("Plot");
            posterUrl = movieInfoJson.getString("Poster");

            titleTextView.setText(title + " (" + year + ")");
            dateTextView.setText(released);
            directorsTextView.setText(directors);
            actorsTextView.setText(actors);
            genresTextView.setText(genre);
            plotTextView.setText(plot);
            Bitmap posterBitmap = new DownloadImageTask().execute(posterUrl).get();
            moviePoster.setImageBitmap(posterBitmap);
        } catch ( JSONException e ) {
            e.printStackTrace();
        } catch ( InterruptedException e ) {
            e.printStackTrace ( );
        } catch ( ExecutionException e ) {
            e.printStackTrace ( );
        }

        return root;
    }

    @Override
    public void onClick( View v) {
        DialogFragment dialogFragment = new DialogFragment();
        dialogFragment.show(getParentFragmentManager(), "dialog");
//        Toast.makeText(c, "Movie was added to the Collection" , Toast.LENGTH_LONG).show();
    }

}
