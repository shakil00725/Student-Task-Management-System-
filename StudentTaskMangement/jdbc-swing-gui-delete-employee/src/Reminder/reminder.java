package Reminder;

import Register.MainWindow;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * @author Shakil
 */
public class reminder extends TimerTask {

     public static int x,y,z,a,b,c1;
     public static String g;
     
    //Timer task
    @Override
    public void run()
    {

        JOptionPane.showMessageDialog(null,g," ", JOptionPane.INFORMATION_MESSAGE); //popong up the msg 

    }

    private static TrayIcon trayIcon;
   
    public void  reminder(int y1,int y2,int y3,int y4,int y5,int y6,String msg)
    {
        //initializing every value 
        x=y1;
        y=y2-1;
        z=y3;
        a=y4;
        b=y5;
        c1=y6;
        g=msg;
        
          
         Calendar c = Calendar.getInstance(); //calender object is called 
         Timer t = new Timer(false);

        c.set(Calendar.DATE, x); //getting the datae from the reminder frame 
        c.set(Calendar.MONTH,y); //month 
        c.set(Calendar.YEAR,z);//year 
        
        c.set(Calendar.HOUR, a); //hour 
        c.set(Calendar.MINUTE,b); //minute 
        c.set(Calendar.SECOND,c1);//sce 
        
        t.schedule(new reminder(), c.getTime(), 36000000);
        System.out.println("Notification is scheduled to run every hour starting " + c.getTime().toString() + ". ");
        
    }
   
    
    public static void main(String[] args) {
        
     
    }

}