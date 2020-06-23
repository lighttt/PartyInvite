package np.com.manishtuladhar.trypartyinvite;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import np.com.manishtuladhar.trypartyinvite.data.WaitlistContract;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    public GuestListAdapter(Context context,Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        //Move the cursor to the passed in position, return if moveToPosition returns false
        if (!mCursor.moveToPosition(position))
        {
            return;
        }
        //get the guest's name
        String name = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME));
        // get the party size
        int partySize = mCursor.getInt(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE));
        // nameTextView text to the guest's name
        holder.nameTextView.setText(name);
        // Display the party count
        holder.partySizeTextView.setText(String.valueOf(partySize));
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView nameTextView;
        // Will display the party size number
        TextView partySizeTextView;

        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }

    }
}
