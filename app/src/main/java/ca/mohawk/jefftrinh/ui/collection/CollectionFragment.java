package ca.mohawk.jefftrinh.ui.collection;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import ca.mohawk.jefftrinh.DBManager;
import ca.mohawk.jefftrinh.DatabaseHelper;
import ca.mohawk.jefftrinh.DownloadImageTask;
import ca.mohawk.jefftrinh.R;

public class CollectionFragment extends Fragment {
    View root;

    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper.TITLE, DatabaseHelper.NOTE };

    final int[] to = new int[] { R.id.titleTextView, R.id.noteTextView };


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_collection, container, false);

        dbManager = new DBManager(getActivity());
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        listView = root.findViewById(R.id.collectionList);

        adapter = new SimpleCursorAdapter(getActivity (), R.layout.activity_listview, cursor, from, to, 0);

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);


        return root;
    }
}
