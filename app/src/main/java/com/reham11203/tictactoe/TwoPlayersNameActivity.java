package com.reham11203.tictactoe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.reham11203.tictactoe.databinding.ActivityTwoPlayersNameBinding;

public class TwoPlayersNameActivity extends AppCompatActivity {

    ActivityTwoPlayersNameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTwoPlayersNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn2Launch.setOnClickListener(v -> {
            if (binding.editTextName1.getText().toString().isEmpty()) {
                binding.editTextName1.setError("Please enter a name");
            }
            else if (binding.editTextName2.getText().toString().isEmpty()) {
                binding.editTextName2.setError("Please enter a name");
            }
            else {
                Intent intent = new Intent(TwoPlayersNameActivity.this, MainActivity.class);
                intent.putExtra("player1", binding.editTextName1.getText().toString());
                intent.putExtra("player2", binding.editTextName2.getText().toString());
                intent.putExtra("gameMode", getIntent().getStringExtra("gameMode"));
                startActivity(intent);
            }
        });

    }
}