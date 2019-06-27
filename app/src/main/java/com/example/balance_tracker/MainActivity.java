package com.example.balance_tracker;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClicked {

    TextView tvName,tvBalance;
    EditText etAmount;
    Button btnAdd;
    FloatingActionButton fab;
    FragmentManager fragmentManager;
    ListFrag listFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        tvBalance = findViewById(R.id.tvBalance);
        etAmount = findViewById(R.id.etAmount);
        btnAdd = findViewById(R.id.btnAdd);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fragmentManager = this.getSupportFragmentManager();


        listFrag = (ListFrag) fragmentManager.findFragmentById(R.id.listFrag);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPersonDialog(v);
            }
        });
    }

    @Override
    public void onitemClicked(int index) {
        System.out.println("click hua");

        QuizDbHelper db=new QuizDbHelper(this);
        Person name = db.getIndexData(index);

        tvName.setText(name.getName());
        tvBalance.setText(name.getBalance());
    }

    private void addNewPersonDialog(View v)
    {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        View rView = getLayoutInflater().inflate(R.layout.addpersondialogbox,null);

        final EditText textadd = (EditText)rView.findViewById(R.id.addPerson);
        Button btnok = (Button)rView.findViewById(R.id.AddPersonDone);
        Button btncancel = (Button)rView.findViewById(R.id.cancelPerson);

        alert.setView(rView);

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textadd.getText().toString().trim();
                System.out.println(name);
                QuizDbHelper db = new QuizDbHelper(getApplicationContext());
                db.fillQuestionsTable(name, "0");
                notifyDataChange();
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }


    public void notifyDataChange()
    {
        listFrag.notifyDataChanged();
    }

}
