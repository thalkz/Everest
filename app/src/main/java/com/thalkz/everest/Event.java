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

    public String getMessage() {
        return eMessage;
    }

    public String getFormattedDate() {

        return Integer.toString(eDayOfMonth) + " " + getShortMonthName(eMonth) + " à " + Integer.toString(eHour) + ":" + Integer.toString(eMin);

    }

    public String getPoster() {
        return "posté par " + ePoster;
    }

    public String getShortMonthName(int m){
        switch(m){
            case 0:
                return "Jan.";
            case 1:
                return "Fév.";
            case 2:
                return "Mar.";
            case 3:
                return "Avr.";
            case 4:
                return "Mai.";
            case 5:
                return "Juin.";
            case 6:
                return "Juil.";
            case 7:
                return "Aou.";
            case 8:
                return "Sep.";
            case 9:
                return "Oct.";
            case 10:
                return "Nov.";
            case 11:
                return "Déc.";
        }
        return null;
    }

}
