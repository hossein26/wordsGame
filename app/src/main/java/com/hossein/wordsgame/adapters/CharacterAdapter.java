package com.hossein.wordsgame.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hossein.wordsgame.OnRecyclerViewItemClickListener;
import com.hossein.wordsgame.R;
import com.hossein.wordsgame.data.CharacterPlaceHolder;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<CharacterPlaceHolder> characterPlaceHolders = new ArrayList<>();
    private OnRecyclerViewItemClickListener<CharacterPlaceHolder> onRecyclerViewItemClickListener;

    public CharacterAdapter(){

    }

    public CharacterAdapter(List<CharacterPlaceHolder> characterPlaceHolders) {
        this.characterPlaceHolders = characterPlaceHolders;
    }

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener<CharacterPlaceHolder> onRecyclerViewItemClickListener){
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public void add(CharacterPlaceHolder characterPlaceHolder){
        this.characterPlaceHolders.add(characterPlaceHolder);
        notifyItemInserted(characterPlaceHolders.size()-1);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clear(){
        this.characterPlaceHolders.clear();
        notifyDataSetChanged();
    }

    public String getWord(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < characterPlaceHolders.size(); i++) {
            stringBuilder.append(characterPlaceHolders.get(i).getCharacter());
        }
        return stringBuilder.toString();
    }

    public void makeWordVisible(String word){
        for (int i = 0; i < characterPlaceHolders.size(); i++) {
            if (characterPlaceHolders.get(i).getTag() != null && characterPlaceHolders.get(i).getTag().equalsIgnoreCase(word)){
                characterPlaceHolders.get(i).setVisible(true);
                notifyItemChanged(i);
            }
        }
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CharacterViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_char, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.bind(characterPlaceHolders.get(position));
    }

    @Override
    public int getItemCount() {
        return characterPlaceHolders.size();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        private TextView charTv;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            charTv = itemView.findViewById(R.id.tv_char);
        }

        public void bind(CharacterPlaceHolder characterPlaceHolder) {
            if (characterPlaceHolder.isVisible()){
                charTv.setText(characterPlaceHolder.getCharacter().toString());
                charTv.setVisibility(View.VISIBLE);
            }else {
                charTv.setVisibility(View.INVISIBLE);
            }

            if (characterPlaceHolder.isNull()){
                itemView.setBackground(null);
            }else {
                itemView.setBackgroundResource(R.drawable.background_rv_item);
            }

            itemView.setOnClickListener(view ->
                    onRecyclerViewItemClickListener.onItemClick(characterPlaceHolder, getAdapterPosition())
            );
        }
    }
}
