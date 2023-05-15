package com.example.racing_car_part_2.Comparators;

import static java.lang.Integer.parseInt;

import com.example.racing_car_part_2.Model.Player;

import java.util.Comparator;

public class Player_Comparators {
        private static Comparator<Player> scoreComparator = new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                int score1 = parseInt(p1.getPlayerScore());
                int score2 = parseInt(p2.getPlayerScore());
                return score2 - score1;
            }
        };

        public static Comparator<Player> getComparedByScore(){
            return scoreComparator;
        }

}
