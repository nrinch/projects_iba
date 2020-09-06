package na.severinchik.lesson5_aac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import na.severinchik.lesson5_aac.adapter.TransactionAdapter;
import na.severinchik.lesson5_aac.dialog.NewTransactionDialog;
import na.severinchik.lesson5_aac.entity.Transaction;
import na.severinchik.lesson5_aac.viewmodel.TransactionViewModel;

public class MainActivity extends AppCompatActivity {

    private TransactionViewModel transactionViewModel;
    private TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transactionAdapter = new TransactionAdapter(this);
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.list_transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(transactionAdapter);
        new ItemTouchHelper(transactionViewModel.getItemTouchHelperCallback()).attachToRecyclerView(recyclerView);
        final TextView textView = findViewById(R.id.totalBalance);

        transactionViewModel.getTransactions().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                transactionAdapter.setData(transactions);
                textView.setText(getString(R.string.balance_template,transactionViewModel.getSummary()));
            }
        });
        Button button = findViewById(R.id.add_transaction);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewTransactionDialog().show(getSupportFragmentManager(),"Adding new trans");
            }
        });

    }
}