package com.develogical.camera;

import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;

public class CameraTest {


    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        Sensor sensor = mock(Sensor.class);//This is actually a mock, implementing the interface

        Camera camera = new Camera(sensor, mock(MemoryCard.class));//Instancing an actual Camera, with our test constructor, to insert the mock into the object

        camera.powerOn();//Call my method
        verify(sensor).powerUp();//Expect the sensor to be powered up

    }

    @Test
    public void switchingOffTheCameraPowersDownTheSensor() {
        Sensor sensor = mock(Sensor.class);//This is actually a mock, implementing the interface

        Camera camera = new Camera(sensor, mock(MemoryCard.class));//Instancing an actual Camera, with our test constructor, to insert the mock into the object

        camera.powerOff();//Call my method
        verify(sensor).powerDown();//Expect the sensor to be powered off
    }

    @Test
    public void pressShutterCameraOff(){
        Sensor sensor = mock(Sensor.class);//This is actually a mock, implementing the interface

        Camera camera = new Camera(sensor, mock(MemoryCard.class));//Instancing an actual Camera, with our test constructor, to insert the mock into the object

        camera.pressShutter();
        verifyZeroInteractions(sensor);

    }

    @Test
    public void pressShutterCameraOn(){
        Sensor sensor = mock(Sensor.class);//This is actually a mock, implementing the interface
        MemoryCard mc = mock(MemoryCard.class);//This is actually a mock, implementing the interface
        byte[] myFakeData = new byte[1];
        when(sensor.readData()).thenReturn(myFakeData);

        Camera camera = new Camera(sensor, mc);//Instancing an actual Camera, with our test constructor, to insert the mock into the object
        camera.powerOn();
        camera.pressShutter();
        verify(mc, times(1)).write(eq(myFakeData), any());

    }

    @Test
    public void powerDownWhileWriting(){
        Sensor sensor = mock(Sensor.class);//This is actually a mock, implementing the interface
        MemoryCard mc = mock(MemoryCard.class);//This is actually a mock, implementing the interface

        Camera camera = new Camera(sensor, mc);//Instancing an actual Camera, with our test constructor, to insert the mock into the object


        camera.powerOn();
        camera.pressShutter();

        ArgumentCaptor<WriteCompleteListener> argumentCaptor = ArgumentCaptor.forClass(WriteCompleteListener.class);
        verify(mc).write(any(), argumentCaptor.capture());


        camera.powerOff();
        verify(sensor, times(0)).powerDown();

        argumentCaptor.getValue().writeComplete();

        camera.powerOff();
        verify(sensor, times(1)).powerDown();
    }
}


