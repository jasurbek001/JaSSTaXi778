package com.example.jasstaxi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.jasstaxi.R;
import com.example.jasstaxi.models.Users;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SwipeAdapter extends RecyclerView.Adapter<SwipeAdapter.SwipeViewHolder> {


    private Context context;
    private ArrayList<Users> users;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public SwipeAdapter(Context context, ArrayList<Users> users) {
        this.context = context;
        this.users = users;
    }

    private void setUsers(ArrayList<Users> users) {
        this.users = new ArrayList<>();
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.swipe_layout, parent, false);

        return new SwipeViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SwipeViewHolder holder, int position) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeRevealLayout, users.get(position).getName());
        viewBinderHelper.closeLayout(users.get(position).getName());
        holder.bindData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class SwipeViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button txtDelete;
        private SwipeRevealLayout swipeRevealLayout;


        public SwipeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            txtDelete = itemView.findViewById(R.id.btn);
            swipeRevealLayout = itemView.findViewById(R.id.swipe_layout);

            txtDelete.setOnClickListener(
                    v -> Toast.makeText(context, "Zakaz oldim", Toast.LENGTH_LONG).show()
            );
        }

        void bindData(Users users) {
            textView.setText(users.getName());
        }
    }

}
