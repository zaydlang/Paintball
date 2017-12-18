import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

import java.io.*;

import java.awt.geom.*;

import java.lang.Math.*;

import java.util.ArrayList;

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

	private double dt = 0;

    public Player(double x, double y, double width, double height) {
    	super(x, y, width, height, Color.GREEN);   	
    	setUpdate(true);
    }
    
    public void update() {
    	updatePos(xVel, yVel);
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
			if (yVel < 0) yVel = 0;
			yVel += Constants.PLAYER_JUMP_SPEED * dt;
            setY(getY() + 1);
            enableJump = false;
		}
		
		if (action.equals("move left released")) movingLeft = false;
		if (action.equals("move right released")) movingRight = false;
		
		if (action.equals("grapple")) {
		   data[1][0] = new Grapple(getX(), getY(), Constants.GRAPPLE_SIZE, Constants.GRAPPLE_SIZE, Constants.GRAPPLE_SPEED);
		}

		updatePos(xVel, yVel);



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
			if (BoundingBox.intersects(this, data[2][i]) || BoundingBox.intersects(data[2][i], this)) {
System.out.println(i);
				if (BoundingBox.isAbove(this, data[2][i], Constants.BUFFER)) {
System.out.println("S");
					yVel = 0;
					setY(data[2][i].getY() + data[2][i].getHeight());
					enableJump = true;
					//enablePhysics = true;
					onGround = true;
					i = -1;
				} 
				
				if (BoundingBox.hitRoof(this, data[2][i], Constants.BUFFER)) {

System.out.println("H");
					yVel = Constants.GRAVITY * dt; 
					updatePos(0, yVel);
					enableJump = false;
					i = -1;
				} 
				
				if (BoundingBox.hitLeft(this, data[2][i], Constants.BUFFER)) {

System.out.println("I");
					setX(data[2][i].getX() - this.getWidth());
					//yVel += Constants.GRAVITY;
					enableJump = true;
					i = -1;
				}
				
				if (BoundingBox.hitRight(this, data[2][i], Constants.BUFFER)) {
System.out.println("T");
					setX(data[2][i].getX() + data[2][i].getWidth());
					//yVel += Constants.GRAVITY;
					enableJump = true;
					i = -1;
				}

				//enablePhysics = false;
			}
		}

		dt = System.currentTimeMillis();
		return data;
	}
	
    public void updatePos(double xVel, double yVel) {
        System.out.println(getX() + " " + getY() + " " + xVel + " " + yVel);
        setX(getX() + xVel);
        setY(getY() + yVel);
    }

    public void getHurt() {
	    System.out.println("YEEEEOWCH!!!!");
    }
}



