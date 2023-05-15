package com.example.racing_car_part_2;

import android.app.Application;

import com.example.racing_car_part_2.Utilities.MySharedPreferences;
import com.example.racing_car_part_2.Utilities.SignalGenerator;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MySharedPreferences.init(this);
        SignalGenerator.init(this);
    }
}