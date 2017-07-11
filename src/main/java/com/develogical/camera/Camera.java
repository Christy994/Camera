package com.develogical.camera;

public class Camera {

    private Sensor sensor;
    private MemoryCard memoryCard;
    private WriteCompleteListener writeCompleteListener;
    private boolean isSensorOn;




    public Camera(Sensor sensor, MemoryCard memoryCard, WriteCompleteListener writeCompleteListener)
    {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
        this.writeCompleteListener = writeCompleteListener;
        this.isSensorOn = false;
    }

    public void pressShutter() {
        if(this.isSensorOn)
        {
            this.memoryCard.write(this.sensor.readData(), this.writeCompleteListener);
        }
    }

    public void powerOn() {
        //Turn on sensor
        this.sensor.powerUp();
        this.isSensorOn = true;
    }

    public void powerOff() {
        //Turn off sensor
        this.sensor.powerDown();
        this.isSensorOn = false;
    }
}

