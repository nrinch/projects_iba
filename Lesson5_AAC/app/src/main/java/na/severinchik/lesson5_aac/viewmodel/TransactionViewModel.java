package na.severinchik.lesson5_aac.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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

    public void insert(Transaction transaction){
        transactionRepository.insert(transaction);
    }

    public LiveData<List<Transaction>> getTransactions(){
        return  transactions;
    }
}
