package com.example.racing_car_part_2.Model;

import android.location.Location;

public class Player implements Comparable<Player> {

    private String name = "";
    private String score = "0";
    private double playerLatitude;
    private double playerLongitude;

    public Player() {}

    public String getPlayerName() {
        return name;
    }

    public Player setPlayerName(String name) {
        this.name = name;
        return this;
    }

    public String getPlayerScore() {
        return score;
    }

    public Player setPlayerScore(String score) {
        this.score = score;
        return this;
    }

    public Location getLocation() {
        Location playerLocation = new Location(getPlayerName() +"'s Location");
        playerLocation.setLongitude(this.playerLongitude);
        playerLocation.setLatitude(this.playerLatitude);
        return playerLocation;
    }

    public Player setLocation(Location location) {
        this.playerLatitude = location.getLatitude();
        this.playerLongitude = location.getLongitude();
        return this;
    }

    @Override
    public String toString() {
        return "Player{name='" + name + "', score='" + score + "', location=" + locationToString() + "}";
    }

    private String locationToString() {
        Location playerLocation = new Location(getPlayerName() +"'s Location");
        return "{latitude=" + playerLocation.getLatitude() + ", longitude=" + playerLocation.getLongitude() + "}";
    }

    @Override
    public int compareTo(Player other) {
        // compare players by score
        int score1 = Integer.parseInt(this.score);
        int score2 = Integer.parseInt(other.score);
        return Integer.compare(score2, score1);
    }
}
