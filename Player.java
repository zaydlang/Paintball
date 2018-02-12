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

public class Player extends Element {
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
	private boolean connected = false;
	private ConnectedPlayer player;

    public Player(double x, double y, double width, double height) {
    	super(x, y, width, height, Color.GREEN);   	
    	setUpdate(true);
    }
    
    public void update() {
    	updatePos(xVel, yVel);
    }
    
    public boolean connect(String ip, int port) {
		try {
		    s         = new Socket(ip, port);
		  	instream  = s.getInputStream();
		  	outstream = s.getOutputStream();
		  	in        = new Scanner(instream);
		    out       = new PrintWriter(outstream); 
		    
		    connected = true;
		    
		    out.println("join 0");
		    out.flush();
			out.println("init " + getX() + " " + getY() + " " + getWidth() + " " + getHeight());
			out.flush();
		} catch (Exception e) { return false; }
        
        return true;
    }
    
    public Element[][] move(String action, Element[][] data) {
        dt = System.currentTimeMillis() - dt;
        oldXVel = xVel;
        oldYVel = yVel;
        oldX = getX();
        oldY = getY();

		if (action.equals("move left") || movingLeft) {
		    movingLeft = true;	
		    
            // Smooth Turning in mid-air
            if ((getY() != 0 && !enablePhysics) && xVel > 0) {
			    xVel -= Constants.PLAYER_ACC * dt;
		    }
		    
			xVel -= Constants.PLAYER_ACC * dt;
            if (-xVel > Constants.PLAYER_MOVE_SPEED) xVel = -Constants.PLAYER_MOVE_SPEED * dt; 
		}

        if (action.equals("move right") || movingRight) {	
            movingRight = true;
            
            // Smooth Turning in mid-air
            if (getY() != 0 && xVel < 0) {
			    xVel += Constants.PLAYER_ACC * dt;
		    }
		    
			xVel += Constants.PLAYER_ACC * dt;
            if (xVel > Constants.PLAYER_MOVE_SPEED) xVel = Constants.PLAYER_MOVE_SPEED * dt;
		}
		
        if (action.equals("jump") && (getY() == 0 || enableJump)) {
			//if (yVel < 0) yVel = 0;
			yVel += Constants.PLAYER_JUMP_SPEED * dt;
            setY(getY() + 1);
            enableJump = false;
		}
		
		if (action.equals("move left released")) movingLeft = false;
		if (action.equals("move right released")) movingRight = false;
		
		if (action.equals("grapple")) {
		   data[1][0] = new Grapple(getX(), getY(), Constants.GRAPPLE_SIZE, Constants.GRAPPLE_SIZE, Constants.GRAPPLE_SPEED);
		}

		if (enablePhysics) {
	    	if (xVel > 0) {
		    	xVel += Constants.PLAYER_MASS * Constants.GRAVITY * Constants.FRIC * dt;
		        if (xVel < 0) xVel = 0;
		    }

		    if (xVel < 0) {
		  	 	xVel -= Constants.PLAYER_MASS * Constants.GRAVITY * Constants.FRIC * dt;
		        if (xVel > 0) xVel = 0;
		    }
	    }

        if (getY() <= 0) {
			setY(0);
			yVel = 0;      
	    } else if (enableGravity) {
    		yVel += Constants.GRAVITY * dt;
    	}

		onGround = false;
		for (int i = 0; data[2][i] != null; i++) {
			boolean[] hitFlags = BoundingBox.getCollisions(this, data[2][i]);
			
			if (hitFlags[0]) {
				yVel = 0;
				setY(data[2][i].getY() + data[2][i].getHeight());
				enableJump = true;
				onGround = true;
			}
			
			if (hitFlags[1]) {
				setX(data[2][i].getX() - this.getWidth());
				xVel = 0;
				yVel += Constants.GRAVITY;
				enableJump = true;
			}
			
			if (hitFlags[2]) {
				setX(data[2][i].getX() + data[2][i].getWidth());
				xVel = 0;
				yVel += Constants.GRAVITY;
				enableJump = true;
			}
			
			if (hitFlags[3]) {
				yVel = Constants.GRAVITY * dt; 
				updatePos(0, yVel);
				enableJump = false;
			}
			
			if (hitFlags[0] || hitFlags[1] || hitFlags[2] || hitFlags[3]) i = -1;
	    }
		/*
			if (BoundingBox.intersects(this, data[2][i]) || BoundingBox.intersects(data[2][i], this)) {
				if (BoundingBox.isAbove(this, data[2][i], Constants.BUFFER)) {
					yVel = 0;
					setY(data[2][i].getY() + data[2][i].getHeight());
					enableJump = true;
					//enablePhysics = true;
					onGround = true;
					i = -1;
					continue;
				} 
				
				if (BoundingBox.hitRoof(this, data[2][i], Constants.BUFFER)) {
				System.out.println("OUCH!");
					yVel = Constants.GRAVITY * dt; 
					updatePos(0, yVel);
					enableJump = false;
					i = -1;
					continue;
				} 
				
				if (BoundingBox.hitLeft(this, data[2][i], Constants.BUFFER)) {
					setX(data[2][i].getX() - this.getWidth());
					System.out.println("BEEP");
					//yVel += Constants.GRAVITY;
					enableJump = true;
					i = -1;
					continue;
				}
				
				if (BoundingBox.hitRight(this, data[2][i], Constants.BUFFER)) {
					setX(data[2][i].getX() + data[2][i].getWidth());
					//yVel += Constants.GRAVITY;
					enableJump = true;
					i = -1;
					continue;
				}

				//enablePhysics = false;
			}
		}*/

		dt = System.currentTimeMillis();
		
    	if (connected) {
			try {
				String sendMessage = "loc " + getX() + " " + getY();
				out.println(sendMessage);
				out.flush();
				
				if (s.getInputStream().available() != 0) {
		      		String message = in.nextLine();
		            String[] args = message.split("\\s+");

		         	if (args[0].equals("init")) {
		         		System.out.println("Connected.");
						player = new ConnectedPlayer(Double.parseDouble(args[1]), 
													 Double.parseDouble(args[2]),
													 Double.parseDouble(args[3]), 
													 Double.parseDouble(args[4]));
					}

					if (args[0].equals("loc")) {
						if (player == null) player = new ConnectedPlayer(0, 0, getWidth(), getHeight()); // TODO: fix this pls
						player.setPos(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
					}
				}
			} catch (IOException e) {
				System.out.println(e.getStackTrace());
			}
    	}
    	
		return data;
	}
	
    public void updatePos(double xVel, double yVel) {
        //System.out.println(getX() + " " + getY() + " " + xVel + " " + yVel);
        setX(getX() + xVel);
        setY(getY() + yVel);
    }

    public void getHurt() {
	    System.out.println("YEEEEOWCH!!!!");
    }
    
    public void drawEnemy(Graphics g, double width, double height) {
    	if (!connected) return;
    	g.setColor(player.getColor());
    	g.fillRect((int)(player.getX()), (int)(height - player.getY() - player.getHeight()), (int)(player.getWidth()), (int)(player.getHeight()));
    }
    
    public boolean isConnected() {
    	return connected;
    }
}





