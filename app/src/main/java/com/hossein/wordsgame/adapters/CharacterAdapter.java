package com.hossein.wordsgame.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hossein.wordsgame.R;
import com.hossein.wordsgame.data.CharacterPlaceHolder;

import java.util.ArrayList;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<CharacterPlaceHolder> characterPlaceHolders = new ArrayList<>();

    public CharacterAdapter(List<CharacterPlaceHolder> characterPlaceHolders) {
        this.characterPlaceHolders = characterPlaceHolders;
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

    public class CharacterViewHolder extends RecyclerView.ViewHolder{
        private TextView charTv;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            charTv = itemView.findViewById(R.id.tv_char);
        }

        public void bind(CharacterPlaceHolder characterPlaceHolder){
            charTv.setText(characterPlaceHolder.getCharacter().toString());
        }
    }
}
