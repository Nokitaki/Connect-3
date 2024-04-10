package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    boolean gameIsActive = true;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    TextView playerTurnTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and set text for the TextView to display the current player's turn
        playerTurnTextView = findViewById(R.id.player_turn);
        updatePlayerTurnText();
    }

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1000f).rotation(360).setDuration(300);
            checkForWinner();
            updatePlayerTurnText(); // Update Player Turn text after each move
        }
    }

    public void playAgain(View view) {
        gameIsActive = true;
        findViewById(R.id.playAgainLayout).setVisibility(View.INVISIBLE);
        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i< gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

        updatePlayerTurnText(); // Update Player Turn text after starting a new game
    }

    public void resetGame(View view) {
        gameIsActive = true;
        activePlayer = 0;

        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i< gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

        updatePlayerTurnText(); // Update Player Turn text after resetting the game
    }

    private void checkForWinner() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2) {

                gameIsActive = false;
                String winner = (activePlayer == 0) ? "Player 1" : "Player 2";
                showWinnerMessage(winner + " has won!");
                return;
            }
        }

        boolean gameIsOver = true;
        for (int counterState : gameState) {
            if (counterState == 2) {
                gameIsOver = false;
                break;
            }
        }

        if (gameIsOver) {
            showWinnerMessage("It's a draw");
        }
    }

    private void showWinnerMessage(String message) {
        TextView winnerMessage = findViewById(R.id.winnerMessage);
        winnerMessage.setText(message);
        findViewById(R.id.playAgainLayout).setVisibility(View.VISIBLE);
    }

    private void updatePlayerTurnText() {
        String currentPlayer = (activePlayer == 0) ? "Player 1's Turn" : "Player 2's Turn";
        playerTurnTextView.setText(currentPlayer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

