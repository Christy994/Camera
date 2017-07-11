package com.develogical.camera;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class CameraTest {
    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        Sensor sensor = mock(Sensor.class);//This is actually a mock, implementing the interface

        Camera camera = new Camera(sensor, mock(MemoryCard.class), mock(WriteCompleteListener.class));//Instancing an actual Camera, with our test constructor, to insert the mock into the object

        camera.powerOn();//Call my method
        verify(sensor).powerUp();//Expect the sensor to be powered up

    }

    @Test
    public void switchingOffTheCameraPowersDownTheSensor() {
        Sensor sensor = mock(Sensor.class);//This is actually a mock, implementing the interface

        Camera camera = new Camera(sensor, mock(MemoryCard.class), mock(WriteCompleteListener.class));//Instancing an actual Camera, with our test constructor, to insert the mock into the object

        camera.powerOff();//Call my method
        verify(sensor).powerDown();//Expect the sensor to be powered off
    }

    @Test
    public void pressShutterCameraOff(){
        Sensor sensor = mock(Sensor.class);//This is actually a mock, implementing the interface

        Camera camera = new Camera(sensor, mock(MemoryCard.class), mock(WriteCompleteListener.class));//Instancing an actual Camera, with our test constructor, to insert the mock into the object

        camera.pressShutter();
        verifyZeroInteractions(sensor);

    }

    @Test
    public void pressShutterCameraOn(){
        Sensor sensor = mock(Sensor.class);//This is actually a mock, implementing the interface
        MemoryCard mc = mock(MemoryCard.class);//This is actually a mock, implementing the interface
        WriteCompleteListener wcl = mock(WriteCompleteListener.class);
        byte[] myFakeData = new byte[1];
        when(sensor.readData()).thenReturn(myFakeData);

        Camera camera = new Camera(sensor, mc, wcl);//Instancing an actual Camera, with our test constructor, to insert the mock into the object
        camera.powerOn();
        camera.pressShutter();
        verify(mc, times(1)).write(eq(myFakeData), any());



    }
}
