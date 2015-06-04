package com.github.mouseRobot;

/**
 * Created by kozo on 6/1/15.
 */
import org.jnativehook.*;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MouseRobot implements NativeMouseInputListener ,NativeKeyListener {
    private boolean isRecording = false;
    private boolean isPreforming = false;
    private boolean isCTR;
    private boolean isALT;
    private boolean isShift;
    private static int PRESSED = 1;
    private static int RELEASED = 2;
    Robot mRobot;

    ArrayList<MouseEatery> record;

    public MouseRobot() throws NativeHookException, AWTException {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        Logger.getLogger("org.jnativehook").setLevel(Level.OFF);
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeKeyListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
        record = new ArrayList<MouseEatery>();
        mRobot = new Robot();
        while (true)
            System.out.println("isRecoding= "+isRecording+" isPreforming= "+isPreforming
            +"\n isCTR="+isCTR+" isAlt= "+isALT+"\n isShift= "+isShift);



    }


    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_CONTROL_R)
            isCTR = true;
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_SHIFT_L || nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_SHIFT_R)
            isShift = true;
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ALT_L || nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ALT_R)
            isALT = true;

        if(isALT&&isCTR&&!isRecording){
            record.clear();
            isRecording=true;
        }
        else if(isCTR&&isALT&&isRecording){
            isRecording=false;
        }
        if(isShift&&isCTR&&!isRecording){
            if(!isPreforming){
                isPreforming=true;
                new Performer().start();
            }
            else{
                isPreforming=false;
            }
        }
    }

    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_CONTROL_L || nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_CONTROL_R)
            isCTR = false;
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_SHIFT_L || nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_SHIFT_R)
            isShift = false;
        if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ALT_L || nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ALT_R)
            isALT = false;

    }

    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {

    }

    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        if (isRecording) {



            record.add(new MouseEatery(nativeMouseEvent.getX(), nativeMouseEvent.getY(), PRESSED, nativeMouseEvent.getButton()));

        }

    }

    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        if (isRecording) {

            record.add(new MouseEatery(nativeMouseEvent.getX(), nativeMouseEvent.getY(), RELEASED, nativeMouseEvent.getButton()));
        }

    }

    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        if (isRecording)
            record.add(new MouseEatery(nativeMouseEvent.getX(), nativeMouseEvent.getY(), -1, -1));
    }

    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {

    }

    class MouseEatery {
        public int x;
        public int y;
        public int action;
        public int button;

        public MouseEatery(int X, int Y, int Action, int Button) {
            action = Action;
            y = Y;
            x = X;
            button = Button;
        }

    }

    class Performer extends Thread {

        @Override
        public void run() {
            while (isPreforming) {
                for(MouseEatery e:record){
                    mRobot.mouseMove(e.x,e.y);
                    if(e.action==PRESSED) {
                        mRobot.mousePress((e.button == NativeMouseEvent.BUTTON1) ? InputEvent.BUTTON1_DOWN_MASK : InputEvent.BUTTON3_MASK);
                    }
                    if ((e.action==RELEASED)) {
                        mRobot.mouseRelease((e.button == NativeMouseEvent.BUTTON1) ? InputEvent.BUTTON1_DOWN_MASK : InputEvent.BUTTON3_MASK);
                    }

                    try {
                        sleep(20);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                }


            }
        }






    }
}
