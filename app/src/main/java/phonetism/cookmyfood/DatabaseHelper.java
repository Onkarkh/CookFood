package phonetism.cookmyfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "RecipesSaved";
    private static final String COL1 = "id";
    private static final String COL2 = "title";
    private static final String COL3 = "image";
    private static final String COL4 = "url";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " + COL3 + " TEXT," + COL4 + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public Boolean addData(String gtitle, String gimage, String gurl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, gtitle);
        contentValues.put(COL3, gimage);
        contentValues.put(COL4, gurl);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteRecipe(String gTitle) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM RecipesSaved WHERE title = '" + gTitle + "'";
        Log.d(TAG, "Recipe " + gTitle + " has been Deleted");
        db.execSQL(query);
    }
}
