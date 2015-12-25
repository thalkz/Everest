package com.thalkz.everest;

/**
 * Player is the object referring to a user
 * It contains every information Everest needs about the player
 */

public class Player {

    private String Id;
    private String pName;
    private int pPoints;
    private int pVictories;
    private int pDefeats;
    private int pIndicator;
    private String pFloor;
    private String pPromo;

    Player(String pName, int pVictories, int pDefeats, int pPoints, String pFloor, String pPromo){
        this.pName = pName;
        this.pPoints = pPoints;
        this.pVictories = pVictories;
        this.pDefeats = pDefeats;
        this.pFloor = pFloor;
        this.pPromo = pPromo;
    }

    Player(String pName, String pFloor, String pPromo){
        this(pName, 0, 0, MainActivity.INITIAL_POINTS , pFloor, pPromo);
    }

    public int getPoints() {
        return pPoints;
    }

    public String getName() {
        return pName;
    }

    public int getDefeats() {
        return pDefeats;
    }

    public int getVictories() {
        return pVictories;
    }

    public String getId() {
        return Id;
    }

    public int getIndic(){ return pIndicator; }

    public void addVictory(){
        pVictories++;
    }

    public void addDefeat(){
        pDefeats++;
    }
}
