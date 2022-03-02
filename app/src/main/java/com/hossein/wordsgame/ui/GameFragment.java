package com.hossein.wordsgame.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
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
    private Button btnAccept;
    private Button btnCancel;
    private CharacterAdapter wordsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        level = getArguments() != null ? getArguments().getParcelable("level") : null;
        Log.d(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        guessActionContainer = view.findViewById(R.id.frame_game_guessActionContainer);
        btnAccept = view.findViewById(R.id.btn_game_acceptAction);
        btnCancel = view.findViewById(R.id.btn_game_cancel);

        //rv of game char
        RecyclerView rvGameCharacter = view.findViewById(R.id.rv_game_character);
        rvGameCharacter.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        List<Character> uniqueCharacters = GamePlayUtil.extractUniqueCharacter(level.getWords());
        List<CharacterPlaceHolder> characterPlaceHolders = new ArrayList<>();
        for (int i = 0; i < uniqueCharacters.size(); i++) {
            CharacterPlaceHolder characterPlaceHolder = new CharacterPlaceHolder();
            characterPlaceHolder.setVisible(true);
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
        btnAccept.setOnClickListener(v ->{
            String word = guessCharacterAdapter.getWord();
            for (int i = 0; i < level.getWords().size(); i++) {
                if (word.equalsIgnoreCase(level.getWords().get(i))){
                    btnCancel.performClick();
                    wordsAdapter.makeWordVisible(word);
                    Log.d(TAG, "onViewCreated: " + word);
                    return;
                }
            }
            btnCancel.performClick();
            Snackbar.make(requireContext(), v,"صحیح نیست!", Snackbar.LENGTH_SHORT).show();
        });
        btnCancel.setOnClickListener(v ->{
            guessActionContainer.setVisibility(View.GONE);
            guessCharacterAdapter.clear();
        });

        //rv of game words
        RecyclerView rvGameWords = view.findViewById(R.id.rv_game_words);
        int maxLength = 0;
        for (int i = 0; i < level.getWords().size(); i++) {
            if (level.getWords().get(i).length() > maxLength){
                maxLength = level.getWords().get(i).length();
            }
        }
        rvGameWords.setLayoutManager(new GridLayoutManager(getContext(), maxLength, RecyclerView.VERTICAL, false));
        List<CharacterPlaceHolder> wordsCharacterPlaceHolder = new ArrayList<>();
        for (int i = 0; i < level.getWords().size(); i++) {
            for (int j = 0; j < maxLength; j++) {
                CharacterPlaceHolder characterPlaceHolder = new CharacterPlaceHolder();
                if (j < level.getWords().get(i).length()){
                    characterPlaceHolder.setCharacter(level.getWords().get(i).charAt(j));
                    characterPlaceHolder.setNull(false);
                    characterPlaceHolder.setVisible(false);
                    characterPlaceHolder.setTag(level.getWords().get(i));
                }else {
                    characterPlaceHolder.setNull(true);
                }
                wordsCharacterPlaceHolder.add(characterPlaceHolder);
            }
        }
        wordsAdapter = new CharacterAdapter(wordsCharacterPlaceHolder);
        rvGameWords.setAdapter(wordsAdapter);
    }
}
