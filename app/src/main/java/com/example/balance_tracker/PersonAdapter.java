package com.example.balance_tracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>
{

    ArrayList<Person> people;

    ItemClicked activity;

    public interface ItemClicked
    {
        void onitemClicked(int index);
        void notifyDataChange();
    }

    public PersonAdapter(Context context, ArrayList<Person> list)
    {

        people = list;
        activity = (ItemClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvName;
        ImageView delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tvName);
            delete=itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name=tvName.getText().toString();

                    QuizDbHelper db=new QuizDbHelper((Context) activity);

                    activity.onitemClicked(db.findindex(name));

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String name = tvName.getText().toString().trim();

                    AlertDialog.Builder builder = new AlertDialog.Builder((Context) activity);
                    builder.setTitle("Delete " +name);
                    builder.setMessage("Are You Sure to Delete " +name);

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            QuizDbHelper db=new QuizDbHelper((Context) activity);
                            int n = db.findindex(name);

                            if(n!=-1) db.deleteEntry(String.valueOf(n));
                            activity.notifyDataChange();
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            });
        }
    }

    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonAdapter.ViewHolder viewHolder, int i) {

        viewHolder.itemView.setTag(people.get(i));

        viewHolder.tvName.setText(people.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return people.size();
    }
}
