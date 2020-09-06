package na.severinchik.lesson5_aac.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction_table")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int uid;
    public String name;
    public long date;
    public boolean type;
    public float value;

    @Ignore
    public Transaction(String name, long date, boolean type, float value) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.value = value;
    }

    public Transaction(int uid, String name, long date, boolean type, float value) {
        this.uid = uid;
        this.name = name;
        this.date = date;
        this.type = type;
        this.value = value;
    }
}
