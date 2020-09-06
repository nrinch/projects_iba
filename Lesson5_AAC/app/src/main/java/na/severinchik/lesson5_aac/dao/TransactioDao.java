package na.severinchik.lesson5_aac.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import na.severinchik.lesson5_aac.entity.Transaction;

@Dao
public interface TransactioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Transaction transaction);

    @Query("SELECT * FROM `transaction` ORDER BY uid DESC")
    public LiveData<List<Transaction>> getAllTransactions();
}
