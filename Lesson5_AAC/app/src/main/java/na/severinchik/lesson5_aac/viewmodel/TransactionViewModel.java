package na.severinchik.lesson5_aac.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import na.severinchik.lesson5_aac.entity.Transaction;
import na.severinchik.lesson5_aac.repository.TransactionRepository;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository transactionRepository;
    private LiveData<List<Transaction>> transactions;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        transactionRepository = new TransactionRepository(application);
        transactions = transactionRepository.getTransactions();
    }

    public void insert(Transaction transaction) {
        transactionRepository.insert(transaction);
    }

    public LiveData<List<Transaction>> getTransactions() {
        return transactions;
    }

    public void delete(Transaction transaction){
        transactionRepository.delete(transaction.uid);
    }

    public float getSummary() {
        float result = 0;
        for (Transaction t : transactions.getValue()) {
            if (t.type) {
                result += t.value;
            } else result -= t.value;
        }
        return result;
    }

    public ItemTouchHelper.SimpleCallback getItemTouchHelperCallback() {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                delete(transactions.getValue().get(viewHolder.getAdapterPosition()));

            }
        };
    }
}
