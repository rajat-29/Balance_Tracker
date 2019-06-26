package com.example.balance_tracker;

import android.provider.BaseColumns;

public final class BalanceContract
{
    private BalanceContract() {}

    public static class QuestionsTable implements BaseColumns {

        public static final String TABLE_NAME = "balance_record";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BALANCE = "balance";
    }


}
