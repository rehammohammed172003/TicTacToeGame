package com.reham11203.tictactoe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.reham11203.tictactoe.databinding.ActivityGameModeBinding;


public class GameModeActivity extends AppCompatActivity {

    ActivityGameModeBinding binding;
    private String gameMode = "x";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.playWithBot.setOnClickListener(v -> {
            Intent intent = new Intent(GameModeActivity.this, SinglePlayerNameActivity.class);
            intent.putExtra("player2", "Bot");
            intent.putExtra("gameMode", gameMode);
            startActivity(intent);
        });

        binding.playWithFriend.setOnClickListener(v -> {
            Intent intent = new Intent(GameModeActivity.this, TwoPlayersNameActivity.class);
            intent.putExtra("gameMode", "Friend");
            intent.putExtra("gameMode", gameMode);
            startActivity(intent);
        });

        binding.x.setOnClickListener(v -> {
            binding.x.setBackgroundColor(getColor(R.color.beige));
            binding.o.setBackgroundColor(getColor(R.color.white));
            gameMode = "x";

        });

        binding.o.setOnClickListener(v -> {
            binding.o.setBackgroundColor(getColor(R.color.beige));
            binding.x.setBackgroundColor(getColor(R.color.white));
            gameMode = "o";
        });

    }

}
