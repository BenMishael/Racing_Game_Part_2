package com.example.racing_car_part_2.Logic;


import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.racing_car_part_2.Adapter.PlayerAdapter;
import com.example.racing_car_part_2.GameActivity;
import com.example.racing_car_part_2.Interfaces.AddPlayerToList_Callback;
import com.example.racing_car_part_2.Model.GameType;
import com.example.racing_car_part_2.Model.Player;
import com.example.racing_car_part_2.RecordActivity;
import com.example.racing_car_part_2.Utilities.SignalGenerator;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;
public class GameManager {
    private Context mainContext;
    private ShapeableImageView [] heartsArray;
    private ShapeableImageView [][] rocksMatrix;
    private ShapeableImageView [][] coinsMatrix;
    private ShapeableImageView [] carsArray;
    private TextView scoreTextView;
    private TextView speedTextView;
    private static int numOfLanes = DataManager.getNumOfLanes();
    private static int numOfRows = DataManager.getNumOfRows();
    private static int carLeftestLane = DataManager.getCarLeftestLane();
    private static int carRightestLane = DataManager.getCarRightestLane();
    private static int carCenterLane = DataManager.getCarCenterLane();
    private static int coinSoundID = DataManager.getCoinSoundId();
    private static int crashSoundID = DataManager.getCrashSoundId();
    private static int slowDelay = DataManager.getSlowDelay();
    private static int fastDelay = DataManager.getFastDelay();
    private static int numOfLife = DataManager.getNumOfLife();
    private static int coinOrRock = DataManager.getCoinOrRock();
    private static int coinChance = DataManager.getCoinChance();
    private MediaPlayer mediaPlayer;
    private int currentPlayerScore;
    private CountDownTimer gameTimer;
    private int numOfCrashes;
    private int currDelay;
    private int carPosition;
    private boolean isGameOver;
    private PlayerAdapter playAdapter;

    private AddPlayerToList_Callback addPlayerToList_callback = new AddPlayerToList_Callback() {
        @Override
        public void addPlayerFromGameOver(Player player) {
            playAdapter.addPlayerFromGameOver(player);
        }
    };

    private static Random rand = new Random();


    public GameManager(Context context, String gameType) {
        numOfCrashes = 0;
        currentPlayerScore = 0;
        isGameOver = false;
        carPosition = carCenterLane;
        this.playAdapter = new PlayerAdapter();
        this.playAdapter.setAddPlayerToList_callback(this.addPlayerToList_callback);
        this.carsArray = GameActivity.getCarsArray();
        this.heartsArray = GameActivity.getHeartsArray();
        this.rocksMatrix = GameActivity.getRocksMatrix();
        this.coinsMatrix = GameActivity.getCoinsMatrix();
        this.scoreTextView = GameActivity.getScoreTextView();
        this.speedTextView  = GameActivity.getSpeedModeTextView();
        this.mainContext = context;
        setGameType(gameType);
        startTimer();
    }

    private void setGameType(String gameType) {
        if (gameType.equals(GameType.SENSORS)) {
            speedTextView.setText("Slow Mode");
            currDelay = slowDelay;
        } else if (gameType.equals(GameType.FAST)) {
            speedTextView.setText("Fast Mode");
            currDelay = fastDelay;
        } else if (gameType.equals(GameType.SLOW)) {
            speedTextView.setText("Slow Mode");
            currDelay = slowDelay;
        } else {
            speedTextView.setText("Slow Mode");
            currDelay = slowDelay;
        }
    }

    public void startTimer() {
        if (gameTimer == null && isGameOver == false) {
            gameTimer = new CountDownTimer(Integer.MAX_VALUE,getDelay()) {
                @Override
                public void onTick(long millisUntilFinished) {
                    raceOnTick();
                }

                @Override
                public void onFinish() {
                    gameTimer.cancel();
                }
            }.start();
        }
    }

    private long getDelay() {
        return currDelay;
    }

    public void stopTime() {
        if (gameTimer != null)
            gameTimer.cancel();
        gameTimer = null;
    }

    public void moveRight() {
        if (carPosition < carRightestLane){
            carsArray[carPosition].setVisibility(INVISIBLE);
            carsArray[carPosition + 1].setVisibility(VISIBLE);
            carPosition++;
        }
    }

    public void moveLeft() {
        if (carPosition > carLeftestLane){
            carsArray[carPosition].setVisibility(INVISIBLE);
            carsArray[carPosition - 1].setVisibility(VISIBLE);
            carPosition--;
        }
    }

    private void gameOver() {
        stopTime();
        isGameOver = true;
        gameOverDialog();
    }

    public void gameOverDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainContext);
        builder.setTitle("Enter Your Name");

        // Set up the input
        final EditText input = new EditText(mainContext);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String playerName = input.getText().toString();
                if (!playerName.isEmpty()) {
                    Location playerLocation = SignalGenerator.getInstance().getCurrentLocation(); // assuming a getCurrentLocation() function exists that returns the player's current location
                    Player player = new Player();
                    player.setPlayerName(playerName);
                    player.setPlayerScore(getGameScore());
                    player.setLocation(playerLocation);
                    playAdapter.addPlayerFromGameOver(player);
                    goToRecordActivity();
                }
            }
        });

        // Make the dialog non-cancelable
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();

        // Show the dialog
        dialog.show();

        // Disable the OK button until the user enters a name
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        // Add a listener to the input field to enable the OK button when the user enters a name
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                } else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    public void increaseSpeed(){
//        this.currDelay = fastDelay;
//        this.speedTextView.setText("Fast Mode");
//        stopTime();
//        startTimer();
    }

    public void decreaseSpeed(){
//        this.currDelay = slowDelay;
//        this.speedTextView.setText("Slow Mode");
//        stopTime();
//        startTimer();
    }

    public void goToRecordActivity() {
        Intent intent = new Intent(mainContext, RecordActivity.class);
        mainContext.startActivity(intent);
    }


    public String getGameScore() {
        return currentPlayerScore +"";
    }

    private void newGame() {
        /*Reset All Rocks*/
        for(int i = 0; i < numOfRows;i++)
            for(int j = 0;j < numOfLanes;j++)
                rocksMatrix[i][j].setVisibility(INVISIBLE);

        /*Reset All Coins*/
        for(int i = 0; i < numOfRows;i++)
            for(int j = 0;j < numOfLanes;j++)
                coinsMatrix[i][j].setVisibility(INVISIBLE);

        /*Reset All Car*/
        for(int i = 0;i < numOfLanes; i++)
            carsArray[i].setVisibility(INVISIBLE);

        /*Reset All Heart*/
        for(int i = 0; i < numOfLife; i++)
            heartsArray[i].setVisibility(VISIBLE);

        carsArray[carCenterLane].setVisibility(VISIBLE);

        carPosition = carCenterLane;
        currentPlayerScore = 0;
        numOfCrashes = 0;
        startTimer();
    }
    public void raceOnTick() {
        removeLastLine();
        newRockOrCoin();
        moveDownLoop();
        checkCrashOrReward();
    }

    private void addCrash() {
        if(numOfCrashes < numOfLife){
            heartsArray[numOfCrashes].setVisibility(INVISIBLE);
            numOfCrashes++;
            makeVibrate();
            playCarCrash();
            SignalGenerator.getInstance().toast(numOfLife - numOfCrashes +" Hearts Left", Toast.LENGTH_SHORT);
        }

        else{
            SignalGenerator.getInstance().toast("Game Over!", Toast.LENGTH_SHORT);
            gameOver();
        }
    }

    private void playCarCrash() {
        SignalGenerator.getInstance().makeSound(crashSoundID);
    }

    private void makeVibrate() {
        SignalGenerator.getInstance().vibrate();
    }

    private void removeLastLine() {
        for(int i = 0; i < numOfLanes; i++)
        {
            coinsMatrix[numOfRows - 1][i].setVisibility(INVISIBLE);
            rocksMatrix[numOfRows - 1][i].setVisibility(INVISIBLE);
        }
    }

    private void moveDownLoop() {
        for(int i = numOfRows - 1;i > 0 ; i--) 
            for(int j = numOfLanes - 1; j >= 0 ;j--)
                moveDownRocksAndCoins(i,j);
    }

    private void moveDownRocksAndCoins(int row,int col) {
        if(rocksMatrix[row-1][col].getVisibility() == VISIBLE){
            rocksMatrix[row-1][col].setVisibility(INVISIBLE);
            rocksMatrix[row][col].setVisibility(VISIBLE);
        }

        if(coinsMatrix[row-1][col].getVisibility() == VISIBLE){
            coinsMatrix[row-1][col].setVisibility(INVISIBLE);
            coinsMatrix[row][col].setVisibility(VISIBLE);
        }
    }

    public void newRockOrCoin(){
        int randLane = rand.nextInt(numOfLanes+1);
        int coinOrRockRand = rand.nextInt(coinOrRock);
        if (randLane < numOfLanes){
            if(coinChance > coinOrRockRand)
                coinsMatrix[0][randLane].setVisibility(VISIBLE);
            else
                rocksMatrix[0][randLane].setVisibility(VISIBLE);
        }
    }


    private void checkCrashOrReward() {
        if(carsArray[carPosition].getVisibility() == rocksMatrix[numOfRows-1][carPosition].getVisibility()
                && carsArray[carPosition].getVisibility() == VISIBLE){
            addCrash();
        }
        else if(carsArray[carPosition].getVisibility() == coinsMatrix[numOfRows-1][carPosition].getVisibility()
                && carsArray[carPosition].getVisibility() == VISIBLE){
            addCoinReward();
        }
        else{
            currentPlayerScore += 10;
            UpdateScore();
        }
    }

    private void UpdateScore() {
        if(currentPlayerScore >= 1000)
            scoreTextView.setText(""+currentPlayerScore);
        else if(currentPlayerScore >= 100)
            scoreTextView.setText("0"+currentPlayerScore);
        else if(currentPlayerScore >= 10)
            scoreTextView.setText("00"+currentPlayerScore);
        else
            scoreTextView.setText("000"+currentPlayerScore);
    }

    private void addCoinReward() {
        SignalGenerator.getInstance().toast("+100 Points",Toast.LENGTH_SHORT);
        playCoinReward();
        currentPlayerScore += 100;
        UpdateScore();
    }

    private void playCoinReward() {
        SignalGenerator.getInstance().makeSound(coinSoundID);
    }

    public void releaseMediaPlayer(){
        if(mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = null;
    }


}
