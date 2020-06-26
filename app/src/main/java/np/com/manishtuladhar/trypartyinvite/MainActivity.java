package np.com.manishtuladhar.trypartyinvite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import np.com.manishtuladhar.trypartyinvite.data.WaitListDBHelper;
import np.com.manishtuladhar.trypartyinvite.data.WaitListContract;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDB;

    private EditText mNewGuestNameEditText,mNewPartySizeEditText;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNewGuestNameEditText = findViewById(R.id.person_name_edit_text);
        mNewPartySizeEditText = findViewById(R.id.party_count_edit_text);

        //rv
        RecyclerView waitlistRecyclerView = findViewById(R.id.all_guests_list_view);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //db
        WaitListDBHelper dbHelper = new WaitListDBHelper(this);
        mDB = dbHelper.getWritableDatabase();
        //insert fake data
        //TestUtil.insertFakeData(mDB);
        //get all data
        Cursor cursor = getAllGuests();

        mAdapter = new GuestListAdapter(this,cursor);
        waitlistRecyclerView.setAdapter(mAdapter);

        //swipe helper for rv
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                //do nothing
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //get id
                long id = (long) viewHolder.itemView.getTag();
                //remove guest
                removeGuest(id);
                //update
                mAdapter.swapCursor(getAllGuests());
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                Paint p = new Paint();
                //red
                p.setARGB(255,255,0,0);
                if(dX>0)
                {
                    //right swipe
                    c.drawRect((float)itemView.getLeft(),(float)itemView.getTop(),dX,(float)itemView.getBottom(),p);
                }
                else{
                    //left swipe
                    c.drawRect((float)itemView.getRight() + dX,(float)itemView.getTop(),(float)itemView.getRight(),(float)itemView.getBottom(),p);
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(waitlistRecyclerView);
    }



    /**
     * This method is called when user click on the Add to waitlist button
     *
     * @param view : button
     */
    public void addToWaitList(View view) {
        if(mNewGuestNameEditText.getText().length() == 0 || mNewPartySizeEditText.getText().length() ==0)
        {
            return;
        }
        //default party size
        int partySize = 1;
        try{
            partySize =Integer.parseInt(mNewPartySizeEditText.getText().toString());
        }
        catch (NumberFormatException e)
        {
            Log.e(TAG, "addToWaitList: Failed parse party size" );
        }

        //add  new guest
        addNewGuest(mNewGuestNameEditText.getText().toString(),partySize);

        //update cursor with new items in the database
        mAdapter.swapCursor(getAllGuests());

        //clear edit text
        mNewPartySizeEditText.clearFocus();
        mNewPartySizeEditText.getText().clear();
        mNewGuestNameEditText.getText().clear();
    }

    // ===================== DATABASE OPERATIONS ===========================

    /**
     * Query the mDB and get all guests form the waitlist table
     *
     * @return Cursor containing the list of guests
     */
    private Cursor getAllGuests() {
        return mDB.query(WaitListContract.WaitListEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitListContract.WaitListEntry.COLUMN_TIMESTAMP);
    }

    /**
     * Adds new guest to the database
     * @param name :
     * @param partySize :
     */
    private void addNewGuest(String name,int partySize){
        ContentValues cv = new ContentValues();
        cv.put(WaitListContract.WaitListEntry.COLUMN_GUEST_NAME,name);
        cv.put(WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE,partySize);
        mDB.insert(WaitListContract.WaitListEntry.TABLE_NAME,null,cv);
    }

    /**
     * Removes the guest
     * @param id : guest id
     * @return true: if removed successfully, false: if failed
     */
    private boolean removeGuest(long id)
    {
        return mDB.delete(WaitListContract.WaitListEntry.TABLE_NAME,
                WaitListContract.WaitListEntry._ID + "=" +id,
                null)>0;
    }

}
