package com.example.racing_car_part_2.Logic;



import android.location.Location;

import com.example.racing_car_part_2.Model.Player;
import com.example.racing_car_part_2.Model.PlayersList;

import com.example.racing_car_part_2.R;

import java.util.ArrayList;

public class DataManager {
    private static final int MAX_NUM_OF_PLAYERS = 10;
    private static int[][] rockIds = {
            { R.id.main_IMG_rock_00, R.id.main_IMG_rock_01, R.id.main_IMG_rock_02, R.id.main_IMG_rock_03, R.id.main_IMG_rock_04 },
            { R.id.main_IMG_rock_10, R.id.main_IMG_rock_11, R.id.main_IMG_rock_12, R.id.main_IMG_rock_13, R.id.main_IMG_rock_14 },
            { R.id.main_IMG_rock_20, R.id.main_IMG_rock_21, R.id.main_IMG_rock_22, R.id.main_IMG_rock_23, R.id.main_IMG_rock_24 },
            { R.id.main_IMG_rock_30, R.id.main_IMG_rock_31, R.id.main_IMG_rock_32, R.id.main_IMG_rock_33, R.id.main_IMG_rock_34 },
            { R.id.main_IMG_rock_40, R.id.main_IMG_rock_41, R.id.main_IMG_rock_42, R.id.main_IMG_rock_43, R.id.main_IMG_rock_44 },
            { R.id.main_IMG_rock_50, R.id.main_IMG_rock_51, R.id.main_IMG_rock_52, R.id.main_IMG_rock_53, R.id.main_IMG_rock_54 }
    };

    private static int[][] coinIds = {
            { R.id.main_IMG_coin_00, R.id.main_IMG_coin_01, R.id.main_IMG_coin_02, R.id.main_IMG_coin_03, R.id.main_IMG_coin_04 },
            { R.id.main_IMG_coin_10, R.id.main_IMG_coin_11, R.id.main_IMG_coin_12, R.id.main_IMG_coin_13, R.id.main_IMG_coin_14 },
            { R.id.main_IMG_coin_20, R.id.main_IMG_coin_21, R.id.main_IMG_coin_22, R.id.main_IMG_coin_23, R.id.main_IMG_coin_24 },
            { R.id.main_IMG_coin_30, R.id.main_IMG_coin_31, R.id.main_IMG_coin_32, R.id.main_IMG_coin_33, R.id.main_IMG_coin_34 },
            { R.id.main_IMG_coin_40, R.id.main_IMG_coin_41, R.id.main_IMG_coin_42, R.id.main_IMG_coin_43, R.id.main_IMG_coin_44 },
            { R.id.main_IMG_coin_50, R.id.main_IMG_coin_51, R.id.main_IMG_coin_52, R.id.main_IMG_coin_53, R.id.main_IMG_coin_54 }
    };

    private static int[] carIds = {
            R.id.main_IMG_car_0,
            R.id.main_IMG_car_1,
            R.id.main_IMG_car_2,
            R.id.main_IMG_car_3,
            R.id.main_IMG_car_4
    };

    private static int[] heartsIds = {
            R.id.main_IMG_heart1,
            R.id.main_IMG_heart2,
            R.id.main_IMG_heart3
    };

    private static final int COIN_SOUND_ID = R.raw.sound_coin;
    private static final int CRASH_SOUND_ID = R.raw.sound_car_crash;
    private static final int LEFT_BTN_ID = R.id.main_FAB_left;
    private static final int RIGHT_BTN_ID = R.id.main_FAB_right;
    private static final int BACKGROUND_ID = R.id.main_IMG_background;
    private static final int SPEEDMODE_ID = R.id.main_TEXTX_speedmode;
    private static final int CAR_LEFTEST_LANE = 0;
    private static final int CAR_RIGHTEST_LANE = DataManager.getNumOfLanes() - 1;
    private static final int CAR_CENTER_LANE = (DataManager.getNumOfLanes()-1) / 2;
    private static final int NUM_OF_LIFE = DataManager.getHeartsLength();
    private static final int NUM_OF_LANES = 5;
    private static final int NUM_OF_ROWS = 6;
    private static final int COIN_CHANCE = 20;
    private static final int COIN_OR_ROCK = 100;
    private static final int SLOW_DELAY = 1000;
    private static final int FAST_DELAY = 500;
    private static final String PLAYERS_LIST_JSON_KEY = "playersList";
    public static int getHeartsLength(){
        return heartsIds.length;
    }
    public static int getNumOfLanes(){
        return NUM_OF_LANES;
    }
    public static int getNumOfRows(){
        return NUM_OF_ROWS;
    }
    public static int getNumOfCars(){
        return carIds.length;
    }
    public static int getRockIdByIndex(int row,int col){
        return rockIds[row][col];
    }
    public static int getCoinIdByIndex(int row,int col){
        return coinIds[row][col];
    }
    public static int getHeartIdByIndex(int i){
        return heartsIds[i];
    }
    public static int getCarIdByIndex(int i){
        return carIds[i];
    }
    public static int getLeftBtnId(){
        return LEFT_BTN_ID;
    }
    public static int getRightBtnId() {
        return RIGHT_BTN_ID;
    }
    public static int getBackgroundId(){
        return BACKGROUND_ID;
    }
    public static int getCarLeftestLane(){
        return CAR_LEFTEST_LANE;
    }
    public static int getCarRightestLane(){
        return CAR_RIGHTEST_LANE;
    }
    public static int getCarCenterLane(){
        return CAR_CENTER_LANE;
    }
    public static int getCoinSoundId(){
        return COIN_SOUND_ID;
    }
    public static int getCrashSoundId() {
        return CRASH_SOUND_ID;
    }
    public static int getCoinChance() {
        return COIN_CHANCE;
    }
    public static int getCoinOrRock() {
        return COIN_OR_ROCK;
    }
    public static int getFastDelay() {
        return FAST_DELAY;
    }
    public static int getNumOfLife() {
        return NUM_OF_LIFE;
    }
    public static int getSlowDelay() {
        return SLOW_DELAY;
    }
    public static int getMaxNumOfPlayers() {return MAX_NUM_OF_PLAYERS;};

    public static PlayersList getPlayers() {
        final int numOfPlayers = MAX_NUM_OF_PLAYERS;
        PlayersList playersList = new PlayersList();
        ArrayList<Location> locations = new ArrayList<>();
        String[] playersNames = {"Yaniv", "Avi", "Eli", "Nir", "Tal", "Dan", "Yoni", "Eitan", "Ido", "Tomer"};
        double[] latitudes = {32.11316693778084, 40.7128, 41.8781, 39.9526, 37.7749, 40.7128, 41.8781, 39.9526, 37.7749, 40.7128};
        double[] longitudes = {34.817852408004015, -74.0060, -87.6298, -75.1652, -122.4194, -74.0060, -87.6298, -75.1652, -122.4194, -74.0060};

        // Afeka location :32.11316693778084, 34.817852408004015
        for (int i = 0; i < numOfPlayers; i++) {
            Location newLoc = new Location("location " + i);
            newLoc.setLatitude(latitudes[i]);
            newLoc.setLongitude(longitudes[i]);
            locations.add(newLoc);
        }

        // Create and add each player
        for (int i = 0; i < numOfPlayers; i++) {
            Player player = new Player();
            player.setPlayerName(playersNames[i]);
            player.setPlayerScore(String.valueOf(400 + i * 10));
            player.setLocation(locations.get(i));
            playersList.addPlayer(player);
        }
        playersList.sortList();
        playersList.setPlayerListName(getPlayersListJsonKey());
        return playersList;
    }

    public static String getPlayersListJsonKey() {
        return PLAYERS_LIST_JSON_KEY;
    }

    public static int getSpeedModeId() {
        return SPEEDMODE_ID;
    }
}
