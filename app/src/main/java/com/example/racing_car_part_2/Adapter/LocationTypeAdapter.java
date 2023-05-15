package com.example.racing_car_part_2.Adapter;

import android.location.Location;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class LocationTypeAdapter extends TypeAdapter<Location> {
    @Override
    public void write(JsonWriter out, Location value) throws IOException {
        // Not needed for serialization
    }

    @Override
    public Location read(JsonReader in) throws IOException {
        Location location = new Location("");
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "latitude":
                    location.setLatitude(in.nextDouble());
                    break;
                case "longitude":
                    location.setLongitude(in.nextDouble());
                    break;
                case "accuracy":
                    location.setAccuracy((float) in.nextDouble());
                    break;
                case "time":
                    location.setTime(in.nextLong());
                    break;
                case "speed":
                    location.setSpeed((float) in.nextDouble());
                    break;
                case "bearing":
                    location.setBearing((float) in.nextDouble());
                    break;
                case "altitude":
                    location.setAltitude(in.nextDouble());
                    break;
                case "provider":
                    location.setProvider(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return location;
    }
}
