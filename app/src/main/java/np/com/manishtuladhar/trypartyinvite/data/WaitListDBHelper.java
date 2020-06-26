package np.com.manishtuladhar.trypartyinvite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class WaitListDBHelper extends SQLiteOpenHelper {

    //database name
    private static final String DATABASE_NAME = "waitlist.db";
    //database version
    private static final int DATABASE_VERSION = 1;

    /**
     *   Constructor
    */
    public WaitListDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     *    Creating a new database call WaitList
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + WaitListContract.WaitListEntry.TABLE_NAME + " (" +
                WaitListContract.WaitListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WaitListContract.WaitListEntry.COLUMN_GUEST_NAME + " TEXT NOT NULL, " +
                WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE + " INTEGER NOT NULL, " +
                WaitListContract.WaitListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    /**
     *    When we update the app or create a new database drop old one
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WaitListContract.WaitListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
