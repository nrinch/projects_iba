package com.example.lesson_3_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lesson_3_fragments.entity.Contact;

import java.util.ArrayList;


public class MainFragment extends Fragment implements ContactAdapter.iClickItem {

    public static ArrayList<Contact> contacts = new ArrayList();

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ContactAdapter(contacts,this));
        Button buttonAdd = view.findViewById(R.id.button_add);
        buttonAdd.setOnClickListener(v ->{
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,new NewContactFragment())
                    .commit();
        } );
        return view;
    }

    @Override
    public void onClickItem(Contact contact) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,NewContactFragment.newInstance(contact))
                .commit();
    }
}