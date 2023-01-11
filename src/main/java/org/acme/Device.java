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
        int min = 60;
        int max = 90;

        int first; //valore fisualizzato solo quando otteniamo un valore critico

        first = (int) (Math.random() * (max - min)) + min;  //calcolo valore random tra min e max

        //vede se il valore random è maggiore di 90
        if(Math.random() > 0.5) {
            if(first >= 89) {
                return (int) (Math.random() * (140 - first)) + first; // crea un valore tra 90 e 140
            }
        }


        //vede se il valore random è
        if(Math.random() > 0.5) {
            if(first == 60) {
                return (int) (Math.random() * (50 - first)) + first; // crea un valore tra 50 e 60
            }
        }

        return first;
        //return random.nextInt((max-min)+min);
    }

    public void setHeartFreq(int heartFreq) {
        this.heartFreq = heartFreq;
    }

    public int getTemp() {
        int min = 35;
        int max = 37;

        int first;

        first = (int) (Math.random() * (max - min)) + min;

        if(Math.random() > 0.5 ) {
            if(first > 37) {
                return (int) (Math.random() * (42 - first)) + first;
            }
        }

        if(Math.random() > 0.5) {
            if(first < 35) {
                return (int) (Math.random() * (33 - first)) + first; // crea un valore tra 33 e 35
            }
        }

        return first;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getOssigenazione() {
        int min = 80;
        int max = 100;

        int first;

        first = (int) (Math.random() * (max - min)) + min;

        if(Math.random() > 0.5) {
            if(first < 75) {
                return (int) (Math.random() * (45 - first)) + first; // crea un valore tra 65 e 45
            }
        }

        return first;
    }

    public void setOssigenazione(int ossigenazione) {
        this.ossigenazione = ossigenazione;
    }

    public int getColesterolo() {
        int min = 170;
        int max = 230;

        int first;

        first = (int) (Math.random() * (max - min)) + min;

        if(Math.random() > 0.5 ) {
            if(first > 230) {
                return (int) (Math.random() * (400 - first)) + first;
            }
        }

        if(Math.random() > 0.5) {
            if(first < 170) {
                return (int) (Math.random() * (100 - first)) + first; // crea un valore tra 100 e 170
            }
        }

        return first;
    }

    public void setColesterolo(int colesterolo) {
        this.colesterolo = colesterolo;
    }

    public int getPressione() {
        int min = 78;
        int max = 129;

        int first;

        first = (int) (Math.random() * (max - min)) + min;

        if(Math.random() > 0.5 ) {
            if(first > 129) {
                return (int) (Math.random() * (180 - first)) + first;
            }
        }

        if(Math.random() > 0.5) {
            if(first < 78) {
                return (int) (Math.random() * (30 - first)) + first; // crea un valore tra 30 e 78
            }
        }

        return first;
    }

    public void setPressione(int pressione) {
        this.pressione = pressione;
    }

    @Override
    public String toString(){
        return "{\"deviceName\":\""+deviceName+"\",\"heartFrequency\":"+getHeartFrequency()+",\"temp\":"+getTemp()+",\"ossigenazione\":"+getOssigenazione()+",\"colesterolo\":"+ getColesterolo()+",\"pressione\":"+getPressione()+"}";
    }

}
