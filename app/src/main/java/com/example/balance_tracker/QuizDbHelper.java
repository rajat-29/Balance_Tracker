package com.example.balance_tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import com.example.balance_tracker.BalanceContract.*;


import java.util.ArrayList;

public class QuizDbHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "BalanceTracker.db";
    public static int DATABASE_VERSION = 1;

    private SQLiteDatabase db;


    public  QuizDbHelper (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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

        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);

    }

    private void fillQuestionsTable()
    {

        Person p1 = new Person("Rajat","100");
        addPerson(p1);

    }

    private void addPerson(Person person)
    {

        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_NAME, person.getName());
        cv.put(QuestionsTable.COLUMN_BALANCE, person.getBalance());


        db.insert(QuestionsTable.TABLE_NAME, null, cv);

    }


    public ArrayList<Person> getArray()
    {
        ArrayList<Person> result = new ArrayList<>();

        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM "+ QuestionsTable.TABLE_NAME, null);

        if(c.moveToFirst())
        {
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


//    public Person getIndexData(int i)
//    {
//
//    }


}
