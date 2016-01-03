package com.thalkz.everest.lists;

import com.thalkz.everest.objects.Player;

import java.util.ArrayList;

/**
 * PlayerList is a list of allplayers
 */

public class PlayerList {
    static ArrayList<Player> playerList = new ArrayList<>();

    public PlayerList(ArrayList<Player> playerList) {
    }

    public static Player getByName(String name){
        for(int i = 0; i<playerList.size(); i++){
            if(playerList.get(i).getName().equals(name)){
                return playerList.get(i);
            }
        }
        return null;
    }

    public static int getRankByName(String name){
        for(int i = 0; i<playerList.size(); i++){
            if(playerList.get(i).getName().equals(name)){
                return i+1;
            }
        }
        return 0;
    }

    public static void remplace(Player[] pList){
        playerList.clear();
        for(int i = 0; i<pList.length ; i++){
            playerList.add(pList[i]);
        }
    }

}
