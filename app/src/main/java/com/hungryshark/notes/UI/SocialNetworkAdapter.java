package com.hungryshark.notes.UI;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hungryshark.notes.CardNote;
import com.hungryshark.notes.R;
import com.hungryshark.notes.data.CardsSource;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.MyViewHolder> {

    private final CardsSource dataSource;
    private final Fragment fragment;
    private MyClickListener myClickListener;
    private MyLongClickListener myLongClickListener;
    private int menuPosition;

    public SocialNetworkAdapter(CardsSource dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    public int getMenuPosition() {
        return menuPosition;
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
        holder.onBind(dataSource.getCardNote(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface MyClickListener {
        void onItemClick(View view, int position);
    }

    public interface MyLongClickListener {
        void onLongItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;
        private final TextView date;

        @SuppressLint("NewApi")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            date = itemView.findViewById(R.id.date);
            registerContextMenu(itemView);
            itemView.setOnClickListener(v -> {
                if (myClickListener != null) {
                    myClickListener.onItemClick(v, getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(v -> {
                menuPosition = getLayoutPosition();
                itemView.showContextMenu(10, 10);
                return true;
            });
        }

        public void onBind(CardNote cardNote) {
            textView.setText(cardNote.getTitle());
            date.setText(cardNote.getDate());
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }
    }
}