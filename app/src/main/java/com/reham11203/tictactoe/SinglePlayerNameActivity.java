package com.reham11203.tictactoe;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.reham11203.tictactoe.databinding.ActivitySinglePlayerNameBinding;


public class SinglePlayerNameActivity extends AppCompatActivity {

    ActivitySinglePlayerNameBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySinglePlayerNameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btn1Launch.setOnClickListener(v -> {
            if (binding.editTextName.getText().toString().isEmpty()) {
                binding.editTextName.setError("Please enter a name");
            }
            else {
                Intent intent = new Intent(SinglePlayerNameActivity.this, MainActivity.class);
                intent.putExtra("player1", binding.editTextName.getText().toString());
                intent.putExtra("player2", getIntent().getStringExtra("player2"));
                intent.putExtra("gameMode", getIntent().getStringExtra("gameMode"));
                startActivity(intent);
            }
        });



    }
}