package org.acme;

import io.vertx.core.json.Json;

import java.util.Random;

public class Device {

    private String deviceName = null;
    private int heartFreq = 0;
    private int temp = 0;
    private Random random = new Random();

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
        int max = 100;
        return (int) (Math.random() * (max - min)) + min;
        //return random.nextInt((max-min)+min);
    }

    public void setHeartFreq(int heartFreq) {
        this.heartFreq = heartFreq;
    }

    public int getTemp() {
        int min = 36;
        int max = 40;
        return (int) (Math.random() * (max - min)) + min;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    @Override
    public String toString(){
        return "{\"deviceName\":\""+deviceName+"\",\"heartFrequency\":"+getHeartFrequency()+",\"temp\":"+getTemp()+"}";
    }

}
