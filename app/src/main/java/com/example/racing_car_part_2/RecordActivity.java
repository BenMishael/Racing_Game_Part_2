package com.example.racing_car_part_2;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.racing_car_part_2.Fragments.ListFragment;
import com.example.racing_car_part_2.Fragments.MapFragment;
import com.example.racing_car_part_2.Interfaces.PlayersListClick_Callback;
import com.example.racing_car_part_2.Model.Player;

public class RecordActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private PlayersListClick_Callback playerCallback = new PlayersListClick_Callback() {
        @Override
        public void itemClicked(Player player, int position, Location location) {
            mapFragment.zoomOnRecord(player);
        }

        @Override
        public void zoomOnRecord(Player player) {
            mapFragment.zoomOnRecord(player);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        initFragments();

        beginTransactions();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_FRAME_map, mapFragment).commit();
    }

    private void initFragments() {
        listFragment = new ListFragment();
        listFragment.setPlayerCallback(playerCallback);
        mapFragment = new MapFragment();
    }
}
