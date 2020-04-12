package ca.mohawk.jefftrinh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask < String, Void, Bitmap > {
    protected Bitmap doInBackground ( String... urls ) {

        // Use the URL Connection interface to download the URL
        Bitmap bmp = null;

        Log.d ( "debug" , "do background " + urls[ 0 ] );
        // URL connection must be done in a try/catch
        try {
            URL url = null;
            url = new URL ( urls[ 0 ] );
            bmp = BitmapFactory.decodeStream ( url.openConnection ( ).getInputStream ( ) );

        } catch ( MalformedURLException e ) {
        } catch ( IOException e ) {
        }

        Log.d ( "debug" , "done " );
        return bmp;
    }

    // Display the image in UI
    protected void onPostExecute ( Bitmap result ) {
        super.onPostExecute(result);
//        if ( result != null ) {
//            // Find the ImageView object to use
//            ImageView imageView = findViewById ( R.id.imageView );
//            imageView.setImageBitmap ( result );
//        }
    }
}
