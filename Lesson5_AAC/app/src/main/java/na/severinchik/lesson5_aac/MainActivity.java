package na.severinchik.lesson5_aac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;


import na.severinchik.lesson5_aac.adapter.TransactionAdapter;
import na.severinchik.lesson5_aac.dialog.NewTransactionDialog;
import na.severinchik.lesson5_aac.dialog.SettingDialog;
import na.severinchik.lesson5_aac.entity.Transaction;
import na.severinchik.lesson5_aac.listeners.ValueListener;
import na.severinchik.lesson5_aac.repository.TransactionRepository;
import na.severinchik.lesson5_aac.ui.BottomNavigation;
import na.severinchik.lesson5_aac.viewmodel.TransactionViewModel;

public class MainActivity extends AppCompatActivity {

    private TransactionViewModel transactionViewModel;
    private TransactionAdapter transactionAdapter;


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("read")) {
                final String message = intent.getStringExtra("message");
                final String sender = intent.getStringExtra("sender");
                final long time = intent.getLongExtra("time", 0);
                Transaction transaction = new Transaction(sender,time,false,parseMessege(message) );

                TransactionRepository transactionRepository = new TransactionRepository(getApplicationContext());
                transactionRepository.insert(transaction);


            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("read"));


        transactionAdapter = new TransactionAdapter(this);
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(false);

        RecyclerView recyclerView = findViewById(R.id.list_transaction);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(transactionAdapter);

        new ItemTouchHelper(transactionViewModel.getItemTouchHelperCallback()).attachToRecyclerView(recyclerView);
        final TextView textView = findViewById(R.id.totalBalance);

        transactionViewModel.getTransactions().observe(this, transactions -> {
            transactionAdapter.setData(transactions);
            textView.setText(getString(R.string.balance_template, transactionViewModel.getSummary()));
        });

        BottomNavigationView mBottomNavigationView = findViewById(R.id.bottom_navigation);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mBottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigation());

        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.add_new_transaction:
                    new NewTransactionDialog().show(getSupportFragmentManager(), "Adding new trans");
                    break;
                case R.id.settings:
                    new SettingDialog().show(getSupportFragmentManager(), "Settings");
            }
            return true;
        });


        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(ValueListener.class).build();
        WorkManager.getInstance(this).enqueue(myWorkRequest);

//        PeriodicWorkRequest myPeriodicWorkRequest = new PeriodicWorkRequest
//                .Builder(ValueListener.class, 5, TimeUnit.SECONDS)
//                .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
//                .build();
//        WorkManager.getInstance(this).enqueueUniquePeriodicWork("PeriodicWork",ExistingPeriodicWorkPolicy.KEEP,myPeriodicWorkRequest);

//        WorkManager.getInstance(this).enqueue(someWork1, someWork2, someWork13);
//
//        WorkManager.getInstance(this)
//                .beginWith(myWorkRequest1)
//                .then(myWorkRequest2)
//                .then(myWorkRequest3)
//                .enqueue();


    }


    private float parseMessege(String message) {
        String result = "";
        String[] array = message.split("\n");
        for (String string : array) {
            if (string.split(" ")[0].equals("OPLATA")) {
                result = string.split(" ")[1];
            }
        }
        float res = Float.parseFloat(result);
        return res;
    }
}