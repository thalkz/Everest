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

    public static Player[] search(String query){

        String fQuery = query.toLowerCase();


        if(query.equals("")){
            return new Player[0];
        }else{
            ArrayList<Player> result = new ArrayList<>();

            for(int i = 0; i<playerList.size(); i++){
                Player p = playerList.get(i);
                if(p.getName().toLowerCase().contains(fQuery)){
                    result.add(p);
                }
            }
            return result.toArray(new Player[result.size()]);
        }
    }
}
