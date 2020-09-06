package na.severinchik.lesson5_aac.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transaction")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    int uid;
    public String name;
    public long date;
    public boolean type;
    public float value;

    public Transaction(String name, long date, boolean type, float value) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.value = value;
    }

}
