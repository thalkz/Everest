package com.thalkz.everest;

/**
 * An Event is an item of the Journal
 * It can either be a match, a message or something else to display in the Journal
 */
public class Event {

    private String Id;
    private int eType;
    private String eMessage;
    private String ePlayer1;
    private String ePlayer2;
    private int eGain1;
    private int eGain2;
    private int eYear;
    private int eMonth;
    private int eDayOfMonth;
    private int eDayOfWeek;
    private int eHour;
    private int eMin;
    private int eSeason;
    private String ePoster;

    public Event(int eType, String eMessage, String ePlayer1, String ePlayer2,
                 int eGain1, int eGain2, int eYear, int eMonth, int eDayOfMonth, int eDayOfWeek,
                 int eHour, int eMin, int eSeason, String ePoster) {

        this.eType = eType;
        this.eMessage = eMessage;
        this.ePlayer1 = ePlayer1;
        this.ePlayer2 = ePlayer2;
        this.eGain1 = eGain1;
        this.eGain2 = eGain2;
        this.eYear = eYear;
        this.eMonth = eMonth;
        this.eDayOfMonth = eDayOfMonth;
        this.eDayOfWeek = eDayOfWeek;
        this.eHour = eHour;
        this.eMin = eMin;
        this.eSeason = eSeason;
        this.ePoster = ePoster;

    }

    public Event(int eType, String eMessage, int eYear, int eMonth, int eDayOfMonth, int eDayOfWeek,
                 int eHour, int eMin, int eSeason, String ePoster) {

        this(eType,eMessage, "" , "" , 0,0,eYear,eMonth,eDayOfMonth,eDayOfWeek, eHour,eMin,eSeason,ePoster);

    }
}
