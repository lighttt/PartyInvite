package np.com.manishtuladhar.trypartyinvite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import np.com.manishtuladhar.trypartyinvite.data.TestUtil;
import np.com.manishtuladhar.trypartyinvite.data.WaitListDBHelper;
import np.com.manishtuladhar.trypartyinvite.data.WaitlistContract;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //rv
        RecyclerView waitlistRecyclerView;
        waitlistRecyclerView = findViewById(R.id.all_guests_list_view);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //db
        WaitListDBHelper dbHelper = new WaitListDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
        TestUtil.insertFakeData(mDb);
        Cursor cursor = getAllGuests();


        mAdapter = new GuestListAdapter(this,cursor.getCount());
        waitlistRecyclerView.setAdapter(mAdapter);

    }


    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitlist(View view) {

    }

    /**
     * Query the mDb and get all guests from the waitlist table
     *
     * @return Cursor containing the list of guests
     */
    private Cursor getAllGuests() {
        // COMPLETED (6) Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }


}
