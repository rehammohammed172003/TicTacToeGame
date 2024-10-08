package com.reham11203.tictactoe;

import java.util.ArrayList;
import java.util.Random;

public class BotGameActivity {

    private Random random = new Random();

    public int getBotMove(int[] gameState) {
        // Create a list of available positions
        ArrayList<Integer> availablePositions = new ArrayList<>();
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i] == 2) {
                availablePositions.add(i);
            }
        }

        // If there are available positions, pick a random one
        if (!availablePositions.isEmpty()) {
            int randomIndex = random.nextInt(availablePositions.size());
            return availablePositions.get(randomIndex);
        }

        // Return -1 if no positions are available (shouldn't happen in a normal game)
        return -1;
    }
}
