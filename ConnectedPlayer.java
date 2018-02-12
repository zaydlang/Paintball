import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

import java.io.*;

import java.awt.geom.*;

import java.lang.Math.*;

import java.util.ArrayList;

import java.net.*;
import java.util.*;

public class ConnectedPlayer extends Element {
    private double xVel = 0;
    private double yVel = 0;
    private double oldXVel;
	private double oldYVel;
	private double oldX;
	private double oldY;

    private boolean movingLeft  = false;
    private boolean movingRight = false;
    
    private boolean enablePhysics = true;
    private boolean enableJump    = true;
    private boolean enableGravity = true;
	private boolean onGround      = false;

	private double dt = System.currentTimeMillis();
	
	private Socket s;
	private InputStream instream;
	private OutputStream outstream;
	private Scanner in;
	private PrintWriter out;
	
    public ConnectedPlayer(double x, double y, double width, double height) {
    	super(x, y, width, height, Color.RED);
    }
    /*
    public ConnectedPlayer(String ip, int port) throws Exception {
        this(0, 0, 0, 0); // dummy values cuz java is being stupid
        
        s         = new Socket(ip, port);
      	instream  = s.getInputStream();
      	outstream = s.getOutputStream();
      	in        = new Scanner(instream);
        out       = new PrintWriter(outstream); 
        
        if (s.getInputStream().available() != 0) {
             String initMessage = in.next();
             String[] initArgs = initMessage.split("\\s+");
             if (initArgs[0] != "init") throw new ConnectException();
             setX(Double.parseDouble(initArgs[1]));
             setY(Double.parseDouble(initArgs[2]));
             setWidth(Double.parseDouble(initArgs[3]));
             setHeight(Double.parseDouble(initArgs[4]));
             
        } else throw new ConnectException();
    }
    */
    public void update() {/*
        try {
		    if (s.getInputStream().available() != 0) {
		         String updateMessage = in.next();
		         String[] updateArgs = updateMessage.split("\\s+");
		         updatePos(Double.parseDouble(updateArgs[1]), 
		                   Double.parseDouble(updateArgs[2]));
		              
		    } else throw new ConnectException();
		} catch (Exception e) {}*/
    }
	
    public void updatePos(double xVel, double yVel) {
        setX(getX() + xVel);
        setY(getY() + yVel);
    }

	public void setPos(double x, double y) {
		setX(x);
		setY(y);
	}

    public void getHurt() {
	    System.out.println("YEEEEOWCH!!!!");
    }
}



