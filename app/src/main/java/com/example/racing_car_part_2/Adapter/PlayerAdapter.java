package com.example.racing_car_part_2.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racing_car_part_2.Interfaces.AddPlayerToList_Callback;
import com.example.racing_car_part_2.Interfaces.PlayersListClick_Callback;
import com.example.racing_car_part_2.Logic.DataManager;
import com.example.racing_car_part_2.Model.Player;
import com.example.racing_car_part_2.Model.PlayersList;
import com.example.racing_car_part_2.R;
import com.example.racing_car_part_2.Utilities.MySharedPreferences;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private PlayersList Players;
    private PlayersListClick_Callback playersListClick_callback;
    private AddPlayerToList_Callback addPlayerToList_callback;

    public PlayerAdapter() {
        String fromSP = MySharedPreferences.getInstance().getString(DataManager.getPlayersListJsonKey(),null);
        if(fromSP != null){
            Log.d("FromJS:",fromSP);
            this.Players = new Gson().fromJson(fromSP,PlayersList.class);
        } else{
            this.Players = DataManager.getPlayers();
            savePlayersList();
        }
    }

    private void savePlayersList() {
        String playersListJson = new Gson().toJson(this.Players,PlayersList.class);
        Log.d("ToJs:",playersListJson);
        MySharedPreferences.getInstance().putString(DataManager.getPlayersListJsonKey(),playersListJson);
    }

    public void setPlayersListClick_callback(PlayersListClick_Callback playersListClick_callback) {
        this.playersListClick_callback = playersListClick_callback;
    }

    public void setAddPlayerToList_callback(AddPlayerToList_Callback addPlayerToList_callback){
        this.addPlayerToList_callback = addPlayerToList_callback;
    }

    public void addPlayerFromGameOver(Player player){
        this.Players.addPlayer(player);
        savePlayersList();
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("passed VT:", "" + viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        PlayerViewHolder PlayerViewHolder = new PlayerViewHolder(view);
        return PlayerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player Player = getItem(position);
        holder.Player_LBL_name.setText(Player.getPlayerName());
        holder.Player_LBL_score.setText(Player.getPlayerScore() + "");
    }

    @Override
    public int getItemCount() {
        return this.Players == null ? 0 : Players.getListSize();
    }

    private Player getItem(int position) {
        return this.Players.getPlayerByPosition(position);
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView Player_LBL_name;
        private MaterialTextView Player_LBL_score;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            Player_LBL_name = itemView.findViewById(R.id.player_LBL_name);
            Player_LBL_score = itemView.findViewById(R.id.player_LBL_score);

            itemView.setOnClickListener(v -> {
                if (playersListClick_callback != null) {
                    Player player = getItem(getAdapterPosition());
                    playersListClick_callback.itemClicked(player, getAdapterPosition(), player.getLocation());
                }
            });
        }
    }
}
