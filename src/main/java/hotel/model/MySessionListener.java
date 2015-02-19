/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.model;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author DB7
 */
public class MySessionListener implements HttpSessionListener{
    private static int totalSessions;
    
    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        totalSessions++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
        System.out.println("SESSION DESTROYED");
    }
    
    public int getTotalSessions(){
        return totalSessions;
    }
}
