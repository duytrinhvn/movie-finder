package ca.mohawk.jefftrinh.ui.home;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import ca.mohawk.jefftrinh.R;
import ca.mohawk.jefftrinh.ui.home.movie_info.MovieInfoFragment;

public class HomeFragment extends Fragment implements View.OnClickListener {
    EditText titleEditText;
    EditText yearEditText;
    Button searchButton;
    String API_KEY = "d7c5b659&";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        titleEditText = root.findViewById(R.id.titleEditText);
        yearEditText = root.findViewById(R.id.yearEditText);
        searchButton = root.findViewById(R.id.searchButton);

        // Add Fragments by code...  Get Fragment Manager
        FragmentManager fm = getParentFragmentManager();

        // New Fragment Transaction...
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        // Create a new instance of our Fragment class (if not already done as a global variable)
        MovieInfoFragment movieInfoFragment = new MovieInfoFragment();

        // Attach the fragment instance to a frame (viewgroup) in our layout.
        // Use .replace to ensure that any previous fragment in the frame is detached.
        fragmentTransaction.replace(R.id.movieInfoFragment, movieInfoFragment);

        // Commit when done (you can do multiple transactions in a single commit)
        fragmentTransaction.commit();

        searchButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchButton:
                // Get text from titleEditText
                String title = titleEditText.getText().toString();
                //Some url endpoint that you may have
                String myUrl = "http://www.omdbapi.com/?apikey=" + API_KEY + "plot=short&t=" + title;
                //String to place our result in
                String result;
                //Instantiate new instance of our class
                DownloadMovieInfo getRequest = new DownloadMovieInfo();
                new DownloadMovieInfo().execute(myUrl);
                try {
                    //Perform the doInBackground method, passing in our url
                    result = getRequest.execute(myUrl).get();
                } catch (InterruptedException e) {
                    result = "InterruptedException";
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    result = "ExecutionException";
                    e.printStackTrace();
                }

                if(result != null) {
                    Log.d("Movie content", result);
                }

                // Save data to Preferences
                SharedPreferences settings = getActivity().getPreferences(0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("movieInfo", result);
                editor.commit();

                // Restart MovieInfo Fragment
                MovieInfoFragment fragment = (MovieInfoFragment) getFragmentManager().findFragmentById(R.id.movieInfoFragment);

                getFragmentManager().beginTransaction()
                        .detach(fragment)
                        .attach(fragment)
                        .commit();
                break;

        }
    }

    private class DownloadMovieInfo extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        protected String doInBackground(String... urls) {
            String stringUrl = urls[0];
            String result;
            String inputLine;

            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();

            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }
            catch(Exception e){
                e.printStackTrace();
                Log.d("Connection error ", e.getMessage());
                result = null;
            }

            return result;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
//            if (result != null) {
//                Log.d("Movie content ", result);
//            }
//            else{
//                Log.d("Movie content ", "null");
//            }
        }
    }
}
