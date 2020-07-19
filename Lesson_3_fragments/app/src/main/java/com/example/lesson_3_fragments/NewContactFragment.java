package com.example.lesson_3_fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lesson_3_fragments.entity.Contact;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewContactFragment extends Fragment {


    private static final String EDIT_CONTACT = "EDIT_CONTACT";


    private Contact contact;


    public NewContactFragment() {
        // Required empty public constructor
    }

    public static NewContactFragment newInstance(Contact contact) {
        NewContactFragment fragment = new NewContactFragment();
        Bundle args = new Bundle();
        args.putParcelable(EDIT_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            contact = getArguments().getParcelable(EDIT_CONTACT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_contact, container, false);
        Button saveButton = view.findViewById(R.id.save_button);
        EditText editTextName = view.findViewById(R.id.name_ET);
        EditText editTextPhoneNumber = view.findViewById(R.id.phoneNumber_ET);
        EditText editTextAbout = view.findViewById(R.id.About);

        if (contact != null) {
            editTextName.setText(contact.name);
            editTextPhoneNumber.setText(contact.phoneNumber);
            editTextAbout.setText(contact.about);
        }

        saveButton.setOnClickListener(v -> {
            if (!editTextName.getText().toString().isEmpty() &&
                    !editTextPhoneNumber.getText().toString().isEmpty()) {
                if (contact == null) {
                    Contact contact = new Contact(
                            editTextName.getText().toString(),
                            editTextPhoneNumber.getText().toString(),
                            editTextAbout.getText().toString()

                    );
                    MainFragment.contacts.add(contact);
                } else {
                    contact.name = editTextName.getText().toString();
                    contact.phoneNumber = editTextPhoneNumber.getText().toString();
                    contact.about = editTextAbout.getText().toString();
                    MainFragment.contacts.set(contact.id, contact);
                }

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new MainFragment())
                        .commit();
            } else {
                String error = getResources().getString(R.string.empty_fields);
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
            }


        });

        return view;
    }
}