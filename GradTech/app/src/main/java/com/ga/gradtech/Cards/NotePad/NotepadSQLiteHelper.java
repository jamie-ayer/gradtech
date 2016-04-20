package com.ga.gradtech.Cards.NotePad;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by samsiu on 4/19/16.
 */
public class NotepadSQLiteHelper extends SQLiteOpenHelper {

    private static final String TAG = NotepadSQLiteHelper.class.getCanonicalName();

    // Database Settings
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "NOTEPAD_DB";
    protected static final String NOTEPAD_TABLE_NAME = "NOTEPAD";

    // Column Names of the NOTEPAD table
    public static final String COL_NOTEPAD_ID = "_id";
    public static final String COL_NOTEPAD_ITEM = "ITEM_NAME";
    public static final String COL_NOTEPAD_DESCRIPTION = "DESCRIPTION";

    public static final String[] NOTEPAD_COLUMNS = {COL_NOTEPAD_ID, COL_NOTEPAD_ITEM, COL_NOTEPAD_DESCRIPTION};

    // Create Table
    public static final String CREATE_NOTEPAD_TABLE =
            "CREATE TABLE " + NOTEPAD_TABLE_NAME +
                    "(" +
                    COL_NOTEPAD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_NOTEPAD_ITEM + " TEXT, " +
                    COL_NOTEPAD_DESCRIPTION + " TEXT )";


    public NotepadSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static NotepadSQLiteHelper instance;

    public static NotepadSQLiteHelper getInstance(Context context){
        if(instance == null){
            instance = new NotepadSQLiteHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NOTEPAD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NOTEPAD_TABLE_NAME);
        this.onCreate(db);
    }

    public Cursor getNotepadItem(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NOTEPAD_TABLE_NAME, // a. table
                NOTEPAD_COLUMNS, // b. columns
                null, // c. selections
                null, // d. selection args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit
        return cursor;
    }

    public void insertNotepadItem(int id, String item, String description){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NOTEPAD_ID, id);
        values.put(COL_NOTEPAD_ITEM, item);
        values.put(COL_NOTEPAD_DESCRIPTION, description);
        db.insert(NOTEPAD_TABLE_NAME, null, values);
        db.close();
    }

    public void updateNotepadeItem(int id, String item, String description){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NOTEPAD_DESCRIPTION, description);
        db.update(NOTEPAD_TABLE_NAME, values, "_id = ?",new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteNotepadItem(int id){
        SQLiteDatabase db = getReadableDatabase();

        String selection = "id = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        db.delete(NOTEPAD_TABLE_NAME,
                selection,
                selectionArgs);
        db.close();

    }

}
