package com.develogical.camera;

public class Camera {

    private Sensor sensor;
    private MemoryCard memoryCard;
    private WriteCompleteListener writeCompleteListener;
    private boolean isSensorOn;
    private boolean busy;

    class WCL implements  WriteCompleteListener{

        @Override
        public void writeComplete() {
            writeIsComplete();
        }
    }

    public Camera(Sensor sensor, MemoryCard memoryCard)
    {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
        this.writeCompleteListener = new WCL();
        this.isSensorOn = false;
        this.busy = false;
    }

    public Camera(Sensor sensor, MemoryCard memoryCard, WriteCompleteListener wcl)
    {
        this(sensor, memoryCard);
        this.writeCompleteListener = wcl;
    }

    public void pressShutter() {
        if(this.isSensorOn)
        {
            this.memoryCard.write(this.sensor.readData(), () -> busy = false);
            this.writeInProcess();
        }
    }

    public void powerOn() {
        //Turn on sensor
        this.sensor.powerUp();
        this.isSensorOn = true;
    }

    public void powerOff() {
        if(!this.isBusy())
        {
            //Turn off sensor
            this.sensor.powerDown();
            this.isSensorOn = false;
        }

    }

    private boolean isBusy()
    {
        return this.busy;
    }

    public void writeIsComplete()
    {
        this.busy = false;
    }

    private void writeInProcess()
    {
        this.busy = true;
    }
}

