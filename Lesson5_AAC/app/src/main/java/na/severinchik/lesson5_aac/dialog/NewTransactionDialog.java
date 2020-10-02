package na.severinchik.lesson5_aac.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import na.severinchik.lesson5_aac.R;
import na.severinchik.lesson5_aac.entity.Transaction;
import na.severinchik.lesson5_aac.viewmodel.ContactViewModel;
import na.severinchik.lesson5_aac.viewmodel.TransactionViewModel;

public class NewTransactionDialog extends DialogFragment {

    TransactionViewModel transactionViewModel;
    ContactViewModel contactViewModel;
    String name;
    EditText nameEditText;
    EditText valueEditText;
    Switch aSwitch;
    boolean type;
    Spinner contacts;
    Button add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_transaction_dialog, container, false);
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);
        getDialog().setContentView(view);

        init(view);

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                type = isChecked;
            }
        });
        contacts.setAdapter(contactViewModel.getAdapter());
        contacts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                name = contactViewModel.contactList.get(position).name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nameEditText.getText().toString().matches("")) {
                    name = nameEditText.getText().toString();
                }
                Transaction transaction = new Transaction(
                        name,
                        System.currentTimeMillis(),
                        type,
                        Float.valueOf(valueEditText.getText().toString())
                );
                if (!transaction.name.isEmpty()) {
                    transactionViewModel.insert(transaction);
                }
                dismiss();
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);


    }

    private void init(View view) {
        nameEditText = view.findViewById(R.id.name_transaction_dialog);
        valueEditText = view.findViewById(R.id.value_transaction_dialog);
        aSwitch = view.findViewById(R.id.type_transaction_dialog);
        contacts = view.findViewById(R.id.contacnList_dialog);
        add =view.findViewById(R.id.add_new_transaction);
    }

}
