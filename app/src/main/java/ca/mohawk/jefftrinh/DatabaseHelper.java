package ca.mohawk.jefftrinh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DatabaseHelper class
 * to create Movie Finder SQLite database when the app runs for the first time
 * to delete Movie Finder SQLite database when the app deleted
 *
 * @author Khac Duy Trinh
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    public static final String TABLE_NAME = "MOVIES";

    // Table columns
    public static final String _ID = "_id";
    public static final String TITLE = "movie_title";
    public static final String YEAR = "movie_year";
    public static final String NOTE = "movie_note";

    // Database Information
    static final String DB_NAME = "MOVIEFINDER_MOVIES.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT NOT NULL, " + YEAR + " TEXT NOT NULL, " + NOTE + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
