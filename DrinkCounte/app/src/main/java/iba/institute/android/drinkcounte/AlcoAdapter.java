package iba.institute.android.drinkcounte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlcoAdapter extends RecyclerView.Adapter<AlcoAdapter.AlcoViewHolder> {

    public interface iClickListener {
        public void onClick(Alcohol alcohol);
    }

    Context context;
    List<Alcohol> list;
    iClickListener clickListener;

    public AlcoAdapter(Context context, List<Alcohol> list, iClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public AlcoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new AlcoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlcoViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AlcoViewHolder extends RecyclerView.ViewHolder {
        View v;

        public AlcoViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void bind(final Alcohol alcohol) {
            ImageView back = itemView.findViewById(R.id.item_background);
            TextView name = itemView.findViewById(R.id.item_name);

            back.setBackgroundColor(alcohol.color);
            name.setText(alcohol.name);

            //Ошибка была в том что наш ImageView заполняет всё пространство и не всегда происходила обработка нажатия
            //назначив обработчик нажатия на back всё исправилось
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onClick(alcohol);
                }
            });
        }
    }
}
