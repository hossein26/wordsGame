package com.hossein.wordsgame.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hossein.wordsgame.OnRecyclerViewItemClickListener;
import com.hossein.wordsgame.R;
import com.hossein.wordsgame.adapters.CharacterAdapter;
import com.hossein.wordsgame.data.CharacterPlaceHolder;
import com.hossein.wordsgame.data.GamePlayUtil;
import com.hossein.wordsgame.data.Level;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {
    private static final String TAG = "GameFragment";
    private Level level;
    private CharacterAdapter guessCharacterAdapter;
    private View guessActionContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        level = getArguments() != null ? getArguments().getParcelable("level") : null;
        guessActionContainer = view.findViewById(R.id.frame_game_guessActionContainer);

        //rv of game char
        RecyclerView rvGameCharacter = view.findViewById(R.id.rv_game_character);
        rvGameCharacter.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        List<Character> uniqueCharacters = GamePlayUtil.extractUniqueCharacter(level.getWords());
        List<CharacterPlaceHolder> characterPlaceHolders = new ArrayList<>();
        for (int i = 0; i < uniqueCharacters.size(); i++) {
            CharacterPlaceHolder characterPlaceHolder = new CharacterPlaceHolder();
            characterPlaceHolder.setCharacter(uniqueCharacters.get(i));
            characterPlaceHolders.add(characterPlaceHolder);
        }
        CharacterAdapter characterAdapter = new CharacterAdapter(characterPlaceHolders);
        rvGameCharacter.setAdapter(characterAdapter);
        characterAdapter.setOnRecyclerViewItemClickListener((item, position) -> {
            guessActionContainer.setVisibility(View.VISIBLE);
            guessCharacterAdapter.add(item);
        });

        //rv of guess char
        RecyclerView rvGuessCharacters = view.findViewById(R.id.rv_game_guess);
        rvGuessCharacters.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        guessCharacterAdapter = new CharacterAdapter();
        rvGuessCharacters.setAdapter(guessCharacterAdapter);
    }
}
