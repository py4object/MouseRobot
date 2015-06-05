package com.github.mouseRobot;

import org.jnativehook.NativeHookException;

import java.awt.*;

/**
 * Created by Omar Zahed on 5/31/15.
 */
public class Main {
    public static void main(String []arvgs ) {
        new GUI();
        try {
            MouseRobot d = new MouseRobot();
        } catch (NativeHookException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
