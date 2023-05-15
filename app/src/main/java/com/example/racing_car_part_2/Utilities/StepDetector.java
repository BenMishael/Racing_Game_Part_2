package com.example.racing_car_part_2.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.racing_car_part_2.Interfaces.StepCallback;


public class StepDetector {
    private Sensor sensor;
    private SensorManager sensorManager;

    private StepCallback stepCallback;

    private int stepCounterX = 0;
    private int stepCounterY = 0;
    private long timestamp = 0;

    private SensorEventListener sensorEventListener;

    public StepDetector(Context context, StepCallback stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];

                calculateStep(x, y);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }

    private void calculateStep(float x, float y) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x > 3.0) {
                stepCounterX++;
                stepCallback.moveLeft(); // Move right when tilted right
            } else if (x < -3.0) {
                stepCounterX++;
                stepCallback.moveRight(); // Move left when tilted left
            }
            if (y > 3.0) {
                stepCounterY++;
                stepCallback.increaseSpeed(); // Increase speed when tilted forward
            } else if (y < -3.0) {
                stepCounterY++;
                stepCallback.decreaseSpeed(); // Decrease speed when tilted backward
            } else {
                // No tilt detected in any direction
                // You could implement something here if needed
            }
        }
    }

    public int getStepsY() {
        return this.stepCounterY;
    }

    public int getStepsX() {
        return this.stepCounterX;
    }

    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

}