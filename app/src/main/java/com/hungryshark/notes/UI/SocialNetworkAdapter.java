package com.hungryshark.notes.UI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hungryshark.notes.R;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.MyViewHolder> {

    private final String[] dataSource;
    private MyClickListener myClickListener;
    private MyLongClickListener myLongClickListener;

    public SocialNetworkAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    public void SetOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public void setMyLongClickListener(MyLongClickListener myLongClickListener) {
        this.myLongClickListener = myLongClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(dataSource[position]);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public interface MyClickListener {
        void onItemClick(View view, int position);
    }

    public interface MyLongClickListener {
        void onLongItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView.setOnClickListener(v -> {
                if (myClickListener != null) {
                    myClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            textView.setOnLongClickListener(v -> {
                if (myLongClickListener != null) {
                    myLongClickListener.onLongItemClick(v, getAdapterPosition());
                }

                return false;
            });
        }

        public void onBind(String s) {
            textView.setText(s);
        }
    }
}