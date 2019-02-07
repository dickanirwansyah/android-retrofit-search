package com.example.dickaspring.frontend_nodejs.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dickaspring.frontend_nodejs.R;
import com.example.dickaspring.frontend_nodejs.model.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.MyViewHolder> {

    List<Person> personList;

    public PersonAdapter(List<Person> personList){
        this.personList = personList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.person_layout, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.name.setText(personList.get(i).getName());
        myViewHolder.email.setText(personList.get(i).getEmail());
        myViewHolder.phone.setText(personList.get(i).getPhone());

        if (i % 2 == 0)
            myViewHolder.root_view.setCardBackgroundColor(Color.parseColor("#E1E1E1"));
    }

    @Override
    public int getItemCount() {
        return personList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView root_view;
        TextView name, email, phone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            root_view = (CardView) itemView.findViewById(R.id.root_view);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            email = (TextView) itemView.findViewById(R.id.txt_email);
            phone = (TextView) itemView.findViewById(R.id.txt_phone);
        }
    }
}
