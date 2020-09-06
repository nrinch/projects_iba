package na.severinchik.lesson5_aac.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import na.severinchik.lesson5_aac.R;
import na.severinchik.lesson5_aac.entity.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final LayoutInflater mInflater;
    private List<Transaction> mTransactions;

    public TransactionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.TransactionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class TransactionViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView date;
        private final TextView value;
        private final ImageView type;
        private final String format = "MMM dd HH:mm";


        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_transaction);
            date = itemView.findViewById(R.id.date_transaction);
            value = itemView.findViewById(R.id.value_transaction);
            type = itemView.findViewById(R.id.type_transaction);
        }

        private void bind(Transaction transaction) {
            if (transaction.type) {
                type.setImageResource(R.drawable.ic_baseline_call_made_24);
            } else {
                type.setImageResource(R.drawable.ic_baseline_call_received_24);
            }
        }

        private String convertFromLongToDate(long date) {
            DateFormat formatter = new SimpleDateFormat(format);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateFormatted = formatter.format(date);
            return  dateFormatted;
        }


    }
}
