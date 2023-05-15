package com.example.racing_car_part_2.Interfaces;

import android.location.Location;

import com.example.racing_car_part_2.Model.Player;

public interface PlayersListClick_Callback {
    void itemClicked(Player player, int position, Location location);
    void zoomOnRecord(Player player);
}
