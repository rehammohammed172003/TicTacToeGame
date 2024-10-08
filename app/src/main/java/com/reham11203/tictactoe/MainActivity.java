package com.reham11203.tictactoe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.card.MaterialCardView;
import com.reham11203.tictactoe.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding binding;
    private int playerOneScoreCount, playerTwoScoreCount, roundCount;
    private boolean activePlayer; // true = player, false = bot (if bot is playing)
    private boolean isBotOpponent;
    private BotGameActivity bot;

    // p1 = 0
    // p2 = 1
    // empty = 2
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, //rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, //columns
            {0, 4, 8}, {2, 4, 6}   //cross
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textPlayer1.setText(getIntent().getStringExtra("player1"));
        isBotOpponent = getIntent().getStringExtra("player2").equals("Bot");

        if (isBotOpponent) {
            binding.textPlayer2.setText("Computer");
            bot = new BotGameActivity(); // Initialize the bot
        } else {
            binding.textPlayer2.setText(getIntent().getStringExtra("player2"));
        }

        binding.btnBack.setOnClickListener(v -> finish());

        if (getIntent().getStringExtra("gameMode").equals("x")) {
            binding.imageXo.setImageResource(R.drawable.xo);
            binding.imagePlayer1.setImageResource(R.drawable.red_player);
            binding.imagePlayer2.setImageResource(R.drawable.blue_player);
        } else if (getIntent().getStringExtra("gameMode").equals("o")) {
            binding.imageXo.setImageResource(R.drawable.ox);
            binding.imagePlayer1.setImageResource(R.drawable.blue_player);
            binding.imagePlayer2.setImageResource(R.drawable.red_player);
        }

        binding.btn0.setOnClickListener(this);
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.btn4.setOnClickListener(this);
        binding.btn5.setOnClickListener(this);
        binding.btn6.setOnClickListener(this);
        binding.btn7.setOnClickListener(this);
        binding.btn8.setOnClickListener(this);

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true; // Player starts first
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View view) {

        if (!activePlayer && isBotOpponent) {
            // If it's the bot's turn, do nothing when a player clicks
            return;
        }

        ImageView selectedButton = (ImageView) view;
        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length() - 1));

        if (gameState[gameStatePointer] != 2) {
            // If the selected cell is already occupied, don't do anything
            return;
        }

        makeMove(selectedButton, gameStatePointer);

        if (checkWinner()) {
            declareWinner();
        } else if (roundCount == 9) {
            Toast.makeText(this, "It's a Draw", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(this::playAgain, 500);
        } else if (isBotOpponent) {
            // Let the bot make a move
            new Handler().postDelayed(this::botMove, 500); // 1-second delay for bot to "think"
        }
        binding.btnReset.setOnClickListener(view1 -> {
            playAgain();
            playerOneScoreCount = 0;
            playerTwoScoreCount = 0;
            updatePlayerScore();
        });
    }

    private void makeMove(ImageView selectedButton, int gameStatePointer) {
        if (activePlayer) {
            selectedButton.setAlpha(1.0f);
            selectedButton.setImageResource(R.drawable.x);
            gameState[gameStatePointer] = 0;
        } else {
            selectedButton.setAlpha(1.0f);
            selectedButton.setImageResource(R.drawable.o);
            gameState[gameStatePointer] = 1;
        }
        roundCount++;
        activePlayer = !activePlayer;
    }

    private void botMove() {
        int botMove = bot.getBotMove(gameState);
        if (botMove != -1) {
            ImageView botButton = getButton(botMove);
            makeMove(botButton, botMove);

            if (checkWinner()) {
                declareWinner();
            }
        }
    }

    private void declareWinner() {
        if (!activePlayer) { // This means the player just won
            playerOneScoreCount++;
            Toast.makeText(this, binding.textPlayer1.getText() + " Won", Toast.LENGTH_SHORT).show();
            showWinningDialog();
        } else {
            playerTwoScoreCount++;
            Toast.makeText(this, binding.textPlayer2.getText() + " Won", Toast.LENGTH_SHORT).show();
            showLosingDialog();
        }
        updatePlayerScore();
        new Handler().postDelayed(this::playAgain, 500);
    }

    private boolean checkWinner() {
        boolean winnerResult = false;
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    private void updatePlayerScore() {
        binding.textPlayerOneScore.setText(String.format(String.valueOf(playerOneScoreCount)));
        binding.textPlayerTwoScore.setText(String.format(String.valueOf(playerTwoScoreCount)));
    }

    private void playAgain() {
        roundCount = 0;
        activePlayer = true;
        for (int i = 0; i < 9; i++) {
            gameState[i] = 2;
            getButton(i).setAlpha(0.0f);
        }
    }

    private ImageView getButton(int i) {
        switch (i) {
            case 0: return binding.btn0;
            case 1: return binding.btn1;
            case 2: return binding.btn2;
            case 3: return binding.btn3;
            case 4: return binding.btn4;
            case 5: return binding.btn5;
            case 6: return binding.btn6;
            case 7: return binding.btn7;
            case 8: return binding.btn8;
            default: throw new IllegalArgumentException("Invalid button index");
        }
    }

    private void showWinningDialog(){
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayoutWinningDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.winning_dialog, constraintLayout);
        MaterialCardView btnDialog1 = view.findViewById(R.id.btnDialog1);
        MaterialCardView btnDialog2 = view.findViewById(R.id.btnDialog2);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        btnDialog1.setOnClickListener(v ->{
            dialog.dismiss();
            playAgain();
        } );
        btnDialog2.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameModeActivity.class);
            startActivity(intent);
        });
        dialog.show();
        final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.win2);
        mp.start();
    }

    private void showLosingDialog(){
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayoutLosingDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.losing_dialog, constraintLayout);
        MaterialCardView btnDialog3 = view.findViewById(R.id.btnDialog3);
        MaterialCardView btnDialog4 = view.findViewById(R.id.btnDialog4);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        btnDialog3.setOnClickListener(v ->{
            dialog.dismiss();
            playAgain();
        } );
        btnDialog4.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameModeActivity.class);
            startActivity(intent);
        });
        dialog.show();
        final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.lose1);
        mp.start();
    }

}
