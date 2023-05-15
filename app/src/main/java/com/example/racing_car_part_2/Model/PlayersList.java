package com.example.racing_car_part_2.Model;

import com.example.racing_car_part_2.Comparators.Player_Comparators;
import com.example.racing_car_part_2.Logic.DataManager;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;

public class PlayersList {
    @SerializedName("playersListName")
    private String playersListName = DataManager.getPlayersListJsonKey();
    @SerializedName("playersList")
    private ArrayList<Player> playersList = new ArrayList<>();

    public PlayersList() {

    }

    public String getPlayerListName() {
        return playersListName;
    }

    public PlayersList setPlayerListName(String name) {
        this.playersListName = name;
        return this;
    }

    public ArrayList<Player> getList(){
        return playersList;
    }

    public PlayersList setPlayers(ArrayList<Player> playersList) {
        this.playersList = playersList;
        return this;
    }

    public Player getPlayerByPosition(int position) {
        if (position >= 0 && position < playersList.size()) {
            return playersList.get(position);
        }
        return null;
    }

    public int getListSize() {
        return playersList.size();
    }

    public void addPlayer(Player player) {
        playersList.add(player);
        sortList();
    }

    public void printAllPlayers() {
        for (Player player : playersList) {
            System.out.println(player);
        }
    }

    public void sortList() {
        this.playersList.sort(Player_Comparators.getComparedByScore());
    }


    public String toJson() {
        JSONObject playersListJson = new JSONObject();
        try {
            playersListJson.put("playersListName", playersListName);

            JSONArray playersJsonArray = new JSONArray();
            for (Player player : playersList) {
                JSONObject playerJson = new JSONObject();
                playerJson.put("name", player.getPlayerName());
                playerJson.put("score", player.getPlayerScore());

                if (player.getLocation() != null) {
                    JSONObject locationJson = new JSONObject();
                    locationJson.put("latitude", player.getLocation().getLatitude());
                    locationJson.put("longitude", player.getLocation().getLongitude());
                    playerJson.put("location", locationJson);
                }

                playersJsonArray.put(playerJson);
            }

            playersListJson.put("playersList", playersJsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return playersListJson.toString();
    }
}


