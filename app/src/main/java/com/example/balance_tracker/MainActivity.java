package com.example.balance_tracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements PersonAdapter.ItemClicked {

    TextView tvName,tvBalance;
    EditText etAmount;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);
        tvBalance = findViewById(R.id.tvBalance);
        etAmount = findViewById(R.id.etAmount);
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
