package ca.mohawk.jefftrinh;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatDialogFragment;


public class DialogFragment extends AppCompatDialogFragment implements View.OnClickListener {
    TextInputEditText noteEditText;
    Button cancelButton;
    Button addButton;
    Context c;
    String title;
    String year;
    String poster;
    String note;
    DBManager dbManager;

    public DialogFragment() {
        // Required empty public constructor
    }

    // Save our view to use in our dialog methods
    View myview = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        // We saved the inflated layout in our myview variable
        myview = inflater.inflate(R.layout.fragment_dialog, container, false);
        c = getActivity();
        // Attach the OnClickListener to the button
        cancelButton = myview.findViewById(R.id.cancelButton);
        addButton = myview.findViewById(R.id.addButton);
        noteEditText = myview.findViewById(R.id.noteEditText);

        // Declares new DBManager object and Opens connection
        dbManager = new DBManager(getActivity());
        dbManager.open();

        cancelButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

        // Don't forget to return the view
        return myview;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cancelButton:
                DialogFragment.this.getDialog().cancel();
                break;

            case R.id.addButton:
                // Pulls Product name and Serial number from Edit Text fields
                // Read -Restore data from preferences
                SharedPreferences settings = getActivity().getPreferences(0);
                String movieInfo = settings.getString("movieInfo", "{\"Title\":\"Joker\",\"Year\":\"2019\",\"Rated\":\"R\",\"Released\":\"04 Oct 2019\",\"Runtime\":\"122 min\",\"Genre\":\"Crime, Drama, Thriller\",\"Director\":\"Todd Phillips\",\"Writer\":\"Todd Phillips, Scott Silver, Bob Kane (based on characters created by), Bill Finger (based on characters created by), Jerry Robinson (based on characters created by)\",\"Actors\":\"Joaquin Phoenix, Robert De Niro, Zazie Beetz, Frances Conroy\",\"Plot\":\"In Gotham City, mentally troubled comedian Arthur Fleck is disregarded and mistreated by society. He then embarks on a downward spiral of revolution and bloody crime. This path brings him face-to-face with his alter-ego: the Joker.\",\"Language\":\"English\",\"Country\":\"USA, Canada\",\"Awards\":\"Won 2 Oscars. Another 84 wins & 190 nominations.\",\"Poster\":\"https:\\/\\/m.media-amazon.com\\/images\\/M\\/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.6\\/10\"},{\"Source\":\"Rotten Tomatoes\",\"Value\":\"68%\"},{\"Source\":\"Metacritic\",\"Value\":\"59\\/100\"}],\"Metascore\":\"59\",\"imdbRating\":\"8.6\",\"imdbVotes\":\"709,240\",\"imdbID\":\"tt7286456\",\"Type\":\"movie\",\"DVD\":\"17 Dec 2019\",\"BoxOffice\":\"N\\/A\",\"Production\":\"Warner Bros. Pictures\",\"Website\":\"N\\/A\",\"Response\":\"True\"}");

                // Convert String to JSON object
                try {
                    JSONObject movieInfoJson = new JSONObject (movieInfo);
                    title = movieInfoJson.getString("Title");
                    year = movieInfoJson.getString("Year");
                    poster = movieInfoJson.getString("Poster");
                    note = noteEditText.getText().toString ();
                }
                catch ( JSONException e) {
                    e.printStackTrace();
                }

                // TODO: Check if name is empty
                // Inserts new record to SQLite
                try {
                    dbManager.insert(title, year, note);
                    DialogFragment.this.getDialog().cancel();
                    Toast.makeText(c, "Movie " + title + " added to the Collection" , Toast.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "Oops, there is an error..", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
    }
}

