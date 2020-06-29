package com.example.lesson_3_fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson_3_fragments.entity.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    ArrayList <Contact> list;

    public ContactAdapter(ArrayList<Contact> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact,parent,false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactViewHolder holder, int position) {
        holder.bindValues(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView shortName;
        TextView name;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            shortName = itemView.findViewById(R.id.short_name_TV);
            name = itemView.findViewById(R.id.name_TV);
        }

        public void bindValues(Contact contact){
            shortName.setText(contact.name.charAt(0));
            name.setText(contact.name);
        }
    }
}
