package com.example.racing_car_part_2.Fragments;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racing_car_part_2.Adapter.PlayerAdapter;
import com.example.racing_car_part_2.Interfaces.PlayersListClick_Callback;
import com.example.racing_car_part_2.Logic.DataManager;
import com.example.racing_car_part_2.Model.Player;
import com.example.racing_car_part_2.R;
import com.example.racing_car_part_2.Utilities.SignalGenerator;

public class ListFragment extends Fragment {

    private RecyclerView main_LST_players;
    private PlayersListClick_Callback playerCallback;

    public void setPlayerCallback(PlayersListClick_Callback sendClicked) {
        this.playerCallback = sendClicked;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        findViews(view);
        initViews();
        return view;
    }

    private void initViews() {
        PlayerAdapter PlayerAdapter = new PlayerAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_players.setAdapter(PlayerAdapter);
        main_LST_players.setLayoutManager(linearLayoutManager);
        PlayerAdapter.setPlayersListClick_callback(new PlayersListClick_Callback() {
            @Override
            public void itemClicked(Player Player, int position, Location location) {
                //SignalGenerator.getInstance().toast("check", Toast.LENGTH_SHORT);
                SignalGenerator.getInstance().toast(Player.getPlayerName(), Toast.LENGTH_SHORT);
                playerCallback.zoomOnRecord(Player);
            }

            @Override
            public void zoomOnRecord(Player player) {
                if(playerCallback != null)
                    playerCallback.zoomOnRecord(player);
            }
        });
    }

    private void findViews(View view) {
        main_LST_players = view.findViewById(R.id.main_LST_players);
    }
}