package com.example.racing_car_part_2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.racing_car_part_2.Model.GameType;

public class MenuActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        checkLocationPermissions();
    }

    private void checkLocationPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
        // Check if the app has permission to access location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION);
        } else {
            // Permission has already been granted, continue with using location
            // ...
        }
    }

    public void openRecords(View view) {
        goToActivity(this.getApplicationContext(),RecordActivity.class);
    }

    public void startSensorGame(View view) {
        String fastGame = GameType.SENSORS;
        goToGameActivity(this, fastGame);
    }

    public void startFastGame(View view) {
        String fastGame = GameType.FAST;
        goToGameActivity(this, fastGame);
    }

    public void startSlowGame(View view) {
        String slowGame = GameType.SLOW;
        goToGameActivity(this, slowGame);
    }

    public void goToGameActivity(Context context, String gameType) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra("GameType",gameType);
        context.startActivity(intent);
    }

    public void goToActivity(Context context, Class<?> destinationActivity) {
        Intent intent = new Intent(context, destinationActivity);
        context.startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted, continue with using location
                // ...
            } else {
                // Permission has been denied, handle accordingly
                // ...
            }
        }
    }
}
