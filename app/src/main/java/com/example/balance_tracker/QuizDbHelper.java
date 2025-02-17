package com.example.balance_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import com.example.balance_tracker.BalanceContract.*;


import java.util.ArrayList;


public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BalanceTracker.db";
    public static int DATABASE_VERSION = 1;

    /* Made by Rajat Gupta */

    private SQLiteDatabase db;


    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_NAME + " TEXT, " +
                QuestionsTable.COLUMN_BALANCE + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    // fill data in person class
    public void fillQuestionsTable(String n, String bal)
    {
        db = getWritableDatabase();

        Person p1 = new Person(n, bal);
        addPerson(p1);

    }

    // add to database
    private void addPerson(Person person) {

        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_NAME, person.getName());
        cv.put(QuestionsTable.COLUMN_BALANCE, person.getBalance());


        db.insert(QuestionsTable.TABLE_NAME, null, cv);

    }

    // retrieve data from table
    public ArrayList<Person> getArray() {
        ArrayList<Person> result = new ArrayList<>();

        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Person person = new Person();
                person.setName(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_NAME)));
                person.setBalance(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_BALANCE)));
                result.add(person);
            } while (c.moveToNext());

        }
        c.close();

        return result;
    }

    // method to find details of name clicked
    public Person getIndexData(int i) {
        System.out.println(i);

        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            if (i == Integer.parseInt(c.getString(c.getColumnIndex(QuestionsTable._ID)))) {
                Person p = new Person(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_NAME)), c.getString(c.getColumnIndex(QuestionsTable.COLUMN_BALANCE)));
                return p;
            }
        }

        c.close();
        Person p = new Person("Error", "0");
        return p;
    }

    //method to find index
    public int findindex(String name)
    {
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            if (name.equals(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_NAME))))
            {
                return Integer.parseInt(c.getString(c.getColumnIndex(QuestionsTable._ID)));
            }
        }
        c.close();

        return -1;
    }

    // delete entry
    public long deleteEntry(String rowId) {
        return db.delete(QuestionsTable.TABLE_NAME,QuestionsTable._ID+ "=?",new String[]{rowId});
    }

    // update entry
    public long updateEntry(String rId, String name, String bal)
    {
        ContentValues cv = new ContentValues();

        cv.put(QuestionsTable._ID, rId);
        cv.put(QuestionsTable.COLUMN_NAME, name);
        cv.put(QuestionsTable.COLUMN_BALANCE, bal);

        return db.update(QuestionsTable.TABLE_NAME,cv,QuestionsTable._ID + "=" + rId,null);
    }


}
