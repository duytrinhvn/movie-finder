package ca.mohawk.jefftrinh.ui.collection;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import ca.mohawk.jefftrinh.DBManager;
import ca.mohawk.jefftrinh.DatabaseHelper;
import ca.mohawk.jefftrinh.R;

/**
 * Implementation of the Collection screen
 *
 * @author Khac Duy Trinh
 */
public class CollectionFragment extends Fragment {
    View root;

    private DBManager dbManager;

    private ListView listView;

    private SimpleCursorAdapter adapter;

    final String[] from = new String[] { DatabaseHelper.TITLE, DatabaseHelper.NOTE };

    final int[] to = new int[] { R.id.titleTextView, R.id.noteTextView };

    public View onCreateView ( @NonNull LayoutInflater inflater ,
                               ViewGroup container , Bundle savedInstanceState ) {
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
