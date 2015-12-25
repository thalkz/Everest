package com.thalkz.everest;

import java.util.Calendar;

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

        Calendar now = Calendar.getInstance();
        int DayOfMonth = now.get(Calendar.DAY_OF_MONTH);
        int Month = now.get(Calendar.MONTH)+1;
        int Year = now.get(Calendar.YEAR);

        if(DayOfMonth==eDayOfMonth&&Month==eMonth&&Year==eYear){
            return "Ajd à " + Integer.toString(eHour) + ":" + Integer.toString(eMin);
        }else if(DayOfMonth==(eDayOfMonth+1)&&Month==eMonth&&Year==eYear)
        {
            return "Hier à " + Integer.toString(eHour) + ":" + Integer.toString(eMin);
        }else{
            return Integer.toString(eDayOfMonth) + " " + getShortMonthName(eMonth) + " à " + Integer.toString(eHour) + ":" + Integer.toString(eMin);
        }
    }

    public String getPoster() {
        return "posté par " + ePoster;
    }

    public String getShortMonthName(int m){
        switch(m){
            case 1:
                return "Jan.";
            case 2:
                return "Fév.";
            case 3:
                return "Mar.";
            case 4:
                return "Avr.";
            case 5:
                return "Mai.";
            case 6:
                return "Juin.";
            case 7:
                return "Juil.";
            case 8:
                return "Aou.";
            case 9:
                return "Sep.";
            case 10:
                return "Oct.";
            case 11:
                return "Nov.";
            case 12:
                return "Déc.";
            default:
                return "";
        }
    }

}
