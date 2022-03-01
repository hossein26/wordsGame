package com.hossein.wordsgame;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hossein.wordsgame.data.Level;

import java.util.ArrayList;
import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.LevelViewHolder> {
    private List<Level> levels = new ArrayList<>();

    public LevelAdapter(List<Level> levels) {
        this.levels = levels;
    }

    @NonNull
    @Override
    public LevelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LevelViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.level_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull LevelViewHolder holder, int position) {
        holder.bindLevel(levels.get(position));
    }

    @Override
    public int getItemCount() {
        return levels.size();
    }

    public class LevelViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public LevelViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvLevel);
        }

        private void bindLevel(Level level) {
            textView.setText(String.valueOf(level.getId()));
        }

    }
}
