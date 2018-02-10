package actigraph.deviceapi.exampleapp;

import android.provider.BaseColumns;

/**
 * Created by Andy on 2/9/2018.
 */

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String EPOCH = "epoch";
        public static final String STEPS = "steps";
        public static final String TIMESTAMP = "timestamp";
    }
}