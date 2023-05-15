package com.example.racing_car_part_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.example.racing_car_part_2.Interfaces.StepCallback;
import com.example.racing_car_part_2.Logic.DataManager;
import com.example.racing_car_part_2.Logic.GameManager;
import com.example.racing_car_part_2.Model.GameType;
import com.example.racing_car_part_2.Utilities.StepDetector;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

public class GameActivity extends AppCompatActivity {
    private static ShapeableImageView[] main_IMG_hearts;
    private static ShapeableImageView [][] main_IMG_rocks;
    private static ShapeableImageView [][] main_IMG_coins;
    private static ShapeableImageView [] main_IMG_cars;
    private static TextView main_TEXTV_score;
    private static TextView main_TEXTV_speedMode;
    private AppCompatImageView main_IMG_background;
    private FloatingActionButton main_BTN_left;
    private FloatingActionButton main_BTN_right;
    private StepDetector stepDetector;
    private String gameType;
    private GameManager gameManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findViews();
        setBackground();
        gameType = getGameType();
        gameManager = new GameManager(GameActivity.this,gameType);
    }

    public GameActivity() {

    }

    private void initStepDetector() {
        stepDetector = new StepDetector(this, new StepCallback() {

            @Override
            public void moveRight() {
                gameManager.moveRight();
            }

            @Override
            public void moveLeft() {
                gameManager.moveLeft();
            }

            @Override
            public void increaseSpeed() {
                gameManager.increaseSpeed();
            }

            @Override
            public void decreaseSpeed() {
                gameManager.decreaseSpeed();
            }
        });
    }


    private String getGameType() {
        String newGameType = null;
        Intent intent = getIntent();
        if (intent != null)
            newGameType = intent.getStringExtra("GameType");
        else
            newGameType = GameType.SLOW;

        if (newGameType != null && newGameType.equals(GameType.SENSORS)) {
            setSensorGame();
        }

        return newGameType;
    }

    private void setSensorGame() {
        main_BTN_left.setVisibility(View.INVISIBLE);
        main_BTN_right.setVisibility(View.INVISIBLE);
        initStepDetector();
    }

    private void setBackground() {
        Glide
                .with(this)
                .load(R.drawable.main_img_road)
                .centerCrop()
                .into(main_IMG_background);
    }

    private void findViews() {
        main_IMG_rocks = new ShapeableImageView[DataManager.getNumOfRows()][DataManager.getNumOfLanes()];
        for (int i = 0; i < DataManager.getNumOfRows(); i++)
            for (int j = 0; j < DataManager.getNumOfLanes(); j++)
                main_IMG_rocks[i][j] = findViewById(DataManager.getRockIdByIndex(i,j));

        main_IMG_coins = new ShapeableImageView[DataManager.getNumOfRows()][DataManager.getNumOfLanes()];
        for (int i = 0; i < DataManager.getNumOfRows(); i++)
            for (int j = 0; j < DataManager.getNumOfLanes(); j++)
                main_IMG_coins[i][j] = findViewById(DataManager.getCoinIdByIndex(i,j));
        
        main_IMG_cars = new ShapeableImageView[DataManager.getNumOfCars()];
        for (int i = 0; i < DataManager.getNumOfCars(); i++)
            main_IMG_cars[i] = findViewById(DataManager.getCarIdByIndex(i));

        main_IMG_hearts = new ShapeableImageView[DataManager.getHeartsLength()];
        for (int i = 0;i < DataManager.getHeartsLength(); i++)
            main_IMG_hearts[i] = findViewById(DataManager.getHeartIdByIndex(i));
        
        main_BTN_left = findViewById(DataManager.getLeftBtnId());
        main_BTN_right = findViewById(DataManager.getRightBtnId());
        main_TEXTV_score = findViewById(R.id.main_TEXTV_score);
        main_IMG_background = findViewById(DataManager.getBackgroundId());
        main_TEXTV_speedMode = findViewById(DataManager.getSpeedModeId());
    }

    public void moveLeft(View view) {
        gameManager.moveLeft();
    }

    public void moveRight(View view) {
        gameManager.moveRight();
    }

    public void increaseSpeed(){
        gameManager.increaseSpeed();
    }

    public void decreaseSpeed() {
        gameManager.decreaseSpeed();
    }
    
    public static ShapeableImageView[] getHeartsArray(){
        return main_IMG_hearts;
    }

    public static ShapeableImageView[] getCarsArray(){
        return main_IMG_cars;
    }

    public static ShapeableImageView[][] getRocksMatrix(){
        return main_IMG_rocks;
    }

    public static ShapeableImageView[][] getCoinsMatrix(){
        return main_IMG_coins;
    }
    public static TextView getScoreTextView(){
        return main_TEXTV_score;
    }
    public static TextView getSpeedModeTextView() {
        return main_TEXTV_speedMode;
    }

    @Override
    protected void onPause() {
        gameManager.stopTime();
        super.onPause();
    }

    @Override
    protected void onResume() {
        gameManager.startTimer();
        if(stepDetector != null)
            stepDetector.start();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        gameManager.stopTime();
        gameManager.releaseMediaPlayer();
        if(stepDetector != null)
            stepDetector.stop();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        gameManager.stopTime();
        if(stepDetector != null)
            stepDetector.stop();
        super.onStop();
    }


}