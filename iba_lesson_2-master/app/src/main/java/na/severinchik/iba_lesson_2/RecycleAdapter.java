package na.severinchik.iba_lesson_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.UserViewHolder> {

    Context context;
    ArrayList<User> list;
    LayoutInflater layoutInflater;

    public RecycleAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        Button call;
        TextView userName;
        TextView phoneNumber;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            call = itemView.findViewById(R.id.call_button);
            userName = itemView.findViewById(R.id.userName_TV);
            phoneNumber = itemView.findViewById(R.id.phoneNumber_TV);
        }

        public void bind(User user) {
            userName.setText(user.userName);
            phoneNumber.setText(user.phoneNumber);

            call.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + user.phoneNumber));
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            });
        }
    }
}
