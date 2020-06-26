package np.com.manishtuladhar.trypartyinvite.data;

import android.provider.BaseColumns;

public class WaitListContract {

    // Create an inner class named WaitlistEntry class that implements the BaseColumns interface
    public static final class WaitListEntry implements BaseColumns {
        // nside create a static final members for the table name and each of the db columns
        public static final String TABLE_NAME = "waitlist";
        public static final String COLUMN_GUEST_NAME = "guestName";
        public static final String COLUMN_PARTY_SIZE = "partySize";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
