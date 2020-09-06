package na.severinchik.lesson5_aac.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import na.severinchik.lesson5_aac.Database;


import java.util.List;

import na.severinchik.lesson5_aac.dao.TransactioDao;
import na.severinchik.lesson5_aac.entity.Transaction;

public class TransactionRepository {

    private TransactioDao transactioDao;
    private LiveData<List<Transaction>> data;

    public TransactionRepository(Application application){
        Database database = Database.getINSTANCE(application);
        transactioDao = database.transactioDao;
        data = transactioDao.getAllTransactions();
    }

    public void insert(Transaction transaction){
        new AsyncInsertTransaction(transactioDao).execute(transaction);
    }

    public LiveData<List<Transaction>> getTransactions(){
        return data;
    }

    class AsyncInsertTransaction extends AsyncTask<Transaction,Void,Void>{

        private TransactioDao transactioDao;

        AsyncInsertTransaction(TransactioDao transactioDao){
            this.transactioDao =transactioDao;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactioDao.insert(transactions[0]);
            return null;
        }
    }
}
