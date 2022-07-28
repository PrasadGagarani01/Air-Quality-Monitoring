package com.arduino;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.util.Enumeration;


import java.util.Properties;
import javax.sound.midi.MidiDevice.Info;
import connection.*;

public class ArduinoConnection implements SerialPortEventListener {
SerialPort serialPort;
    /** The port we're normally going to use. */
private static final String PORT_NAMES[] = {"/dev/tty.usbserial-A9007UX1", // Mac OS X
        "/dev/ttyUSB0", // Linux
        "COM8", // Windows
};

static String id;
static String temp;
static String hum;
static String mq2;
static String mq5;
static String mq135;
static String addedDate;
static String addedTime;
static String min;
static BufferedReader input;
private static OutputStream output;
private static final int TIME_OUT = 2000;
private static final int DATA_RATE = 9600;
Properties prop = new Properties();
public void initialize() {
	
    CommPortIdentifier portId = null;
    Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

    //First, Find an instance of serial port as set in PORT_NAMES.
    while (portEnum.hasMoreElements()) {
        CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
        for (String portName : PORT_NAMES) {
            if (currPortId.getName().equals(portName)) {
                portId = currPortId;
                break;
            }
        }
    }
    if (portId == null) {
        System.out.println("Could not find COM port...");
        return;
    }

    try {
        serialPort = (SerialPort) portId.open(this.getClass().getName(),
                TIME_OUT);
        serialPort.setSerialPortParams(DATA_RATE,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);

        // open the streams
        input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        output = serialPort.getOutputStream();
//        output=serialPort.getOutputBufferSize();

        
       
        
        serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
        
        
    } catch (Exception e) {
        System.err.println(e.toString());
    }
}


public synchronized void close() {
    if (serialPort != null) {
        serialPort.removeEventListener();
        serialPort.close();
    }
}

public synchronized void serialEvent(SerialPortEvent oEvent) {
	
    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
    	
    	 try {
         
           if(input.ready())
       
           id=input.readLine();
           System.out.println("id = "+id);
           Thread.sleep(1000);
           temp=input.readLine();
           System.out.println("temperature = "+temp);
           Thread.sleep(1000);
           hum=input.readLine();
           System.out.println("humidity = "+hum);
           Thread.sleep(1000);
           mq2=input.readLine();
           System.out.println("mq2 = "+mq2);
           Thread.sleep(1000);
           mq5=input.readLine();
           System.out.println("mq5 = "+mq5);
           Thread.sleep(1000);
           mq135=input.readLine();
           System.out.println("mq135 = "+mq135);
           Thread.sleep(1000);
        
       
        	Connection conn=DBconnect.getConnect();
	 	         
		      
        	PreparedStatement ps = conn.prepareStatement("UPDATE device SET temp=? , hum=? , mq2=? , mq5=? , mq135=? where id=?");
 	 	      
 	 	        ps.setString(1,temp);
 	 	        ps.setString(2,hum);
 	 	        ps.setString(3,mq2);
 	 	        ps.setString(4,mq5);
 	 	        ps.setString(5,mq135);
 	 	        ps.setString(6,id);
 	 	
 	 	        ps.executeUpdate();
 	 	        
 	 	        
 	 	        DateTimeFormatter dtf1=DateTimeFormatter.ofPattern("yyyy.MM.dd");
				LocalDateTime now1=LocalDateTime.now();
				DateTimeFormatter dtf2=DateTimeFormatter.ofPattern("HH-mm-ss");
				LocalDateTime now2=LocalDateTime.now();
				addedDate=dtf1.format(now1);
				addedTime=dtf2.format(now2);
				
				DateTimeFormatter dtf3=DateTimeFormatter.ofPattern("mm");
				LocalDateTime now3=LocalDateTime.now();
				min=dtf3.format(now3);
				System.out.println("Min : "+min);
				if(min.equals("00"))
				{
					PreparedStatement ps2 = conn.prepareStatement("insert into airquuality values(?,?,?,?,?,?,?,?,?)");
		 	        ps2.setString(1,"0");
		 	        ps2.setString(2,id);
		 	        ps2.setString(3,addedDate);
		 	        ps2.setString(4,addedTime);
		 	        ps2.setString(5,temp);
		 	        ps2.setString(6,hum);
		 	        ps2.setString(7,mq2);
		 	        ps2.setString(8,mq5);
		 	        ps2.setString(9,mq135);
		 	
		 	        ps2.executeUpdate();
		 	       System.out.println("Data added form prediction purpose..!!");
				}
        	 	
        }
    	 catch(Exception e)
    	 {
    		 System.err.println(e);
    		 e.printStackTrace();
    	 }
           
            
        }
   
    
    }
    

  
    // Ignore all the other eventTypes, but you should consider the other ones.


private Object open(String inputLine, String string) {
	
	// TODO Auto-generated method stub
	return null;
}
public static void main(String[] args) throws SQLException {
	 
	ArduinoConnection main = new ArduinoConnection();
 
    
   main.initialize();
  
        
    Thread t=new Thread() {
        public void run() {
            //the following line will keep this app alive for 1000    seconds,
            //waiting for events to occur and responding to them    (printing incoming messages to console).
            try {Thread.sleep(1000000);} catch (InterruptedException    ie) {}
        }
    };

    
    
   
 
          t.start();
    
    System.out.println("Server Started");
    
    
   
}
}

