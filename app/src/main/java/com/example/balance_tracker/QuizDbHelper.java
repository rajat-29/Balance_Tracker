package com.example.balance_tracker;

import android.content.Context;

import java.util.ArrayList;

public class QuizDbHelper
{

    private final Context ourContext;

    public  QuizDbHelper (Context context)
    {
        ourContext=context;
    }

    public ArrayList<Person> getArray()
    {
        ArrayList<Person> result = new ArrayList<Person>();
        result.add(new Person("Rajat","100"));
        result.add(new Person("Rishabh","110"));
        return result;
    }


}
