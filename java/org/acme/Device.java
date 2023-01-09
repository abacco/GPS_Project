package org.acme;

import java.util.Date;
import java.util.Random;

public class Device {

    private String deviceName = null;
    private int heartFreq = 0;  //frequenza cardiaca
    private int temp = 0;  //temperatura corporea

    private int ossigenazione = 0;  //ossigenazione

    private int colesterolo = 0;  //colesterolo

    private int pressione = 0;  //pressione

    private Random random = new Random();

    public Device() {
    }
    public Device(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }



    public int getHeartFrequency() {
        int min = 50;
        int max = 140;

        int first; //valore fisualizzato solo quando otteniamo un valore critico

        first = (int) (Math.random() * (max - min)) + min;

        if(first >= 90) {
            return (int) (Math.random() * (max - first)) + first;
        }

        if(first <= 60) {
            return (int) (Math.random() * (min - first)) + first;
        }

        else return first;
        //return random.nextInt((max-min)+min);
    }

    public void setHeartFreq(int heartFreq) {
        this.heartFreq = heartFreq;
    }

    public int getTemp() {
        int min = 34;
        int max = 42;

        int first;

        first = (int) (Math.random() * (max - min)) + min;

        if(first >= 39) {
            return (int) (Math.random() * (max - first)) + first;
        }
        else return first;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getOssigenazione() {
        int min = 20;
        int max = 100;

        int first;

        first = (int) (Math.random() * (max - min)) + min;

        if(first <= 80) {
            return (int) (Math.random() * (min - first)) + first;
        }
        else return first;
    }

    public void setOssigenazione(int ossigenazione) {
        this.ossigenazione = ossigenazione;
    }

    public int getColesterolo() {
        int min = 50;
        int max = 400;

        int first;

        first = (int) (Math.random() * (max - min)) + min;

        if(first >= 200) {
            return (int) (Math.random() * (max - first)) + first;
        }
        else return first;
    }

    public void setColesterolo(int colesterolo) {
        this.colesterolo = colesterolo;
    }

    public int getPressione() {
        int min = 30;
        int max = 180;

        int first;

        first = (int) (Math.random() * (max - min)) + min;

        if(first >= 135) {
            return (int) (Math.random() * (max - first)) + first;
        }
        else return first;
    }

    public void setPressione(int pressione) {
        this.pressione = pressione;
    }

    @Override
    public String toString(){
        return "{\"deviceName\":\""+deviceName+"\",\"heartFrequency\":"+getHeartFrequency()+",\"temp\":"+getTemp()+",\"ossigenazione\":"+getOssigenazione()+",\"colesterolo\":"+ getColesterolo()+",\"pressione\":"+getPressione()+"}";
    }

}
