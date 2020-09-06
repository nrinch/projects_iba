package na.severinchik.lesson5_aac.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import na.severinchik.lesson5_aac.R;
import na.severinchik.lesson5_aac.entity.Transaction;
import na.severinchik.lesson5_aac.viewmodel.TransactionViewModel;

public class NewTransactionDialog extends DialogFragment {

    TransactionViewModel transactionViewModel;

    EditText name;
    EditText value;
    Switch aSwitch;
    boolean type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.new_transaction_dialog, container, false);
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme);

        getDialog().setContentView(view);
        name = view.findViewById(R.id.name_transaction_dialog);
        value = view.findViewById(R.id.value_transaction_dialog);
        aSwitch = view.findViewById(R.id.type_transaction_dialog);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                type = isChecked;
            }
        });
        view.findViewById(R.id.add_new_transaction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaction transaction = new Transaction(
                        name.getText().toString(),
                        System.currentTimeMillis(),
                        type,
                        Float.valueOf(value.getText().toString())
                );
                if (!transaction.name.isEmpty()) {
                    transactionViewModel.insert(transaction);
                }
                dismiss();
            }
        });
        return super.onCreateView(inflater, container, savedInstanceState);


    }

}
