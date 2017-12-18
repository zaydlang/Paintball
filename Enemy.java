import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

import java.io.*;

import java.awt.geom.*;

import java.lang.Math.*;

public class Enemy extends Element {
    private double xVel = Constants.ENEMY_MOVE_SPEED;
    private double yVel = 0;
    
    private boolean movingLeft  = false;
    private boolean movingRight = false;
    
    private boolean enablePhysics = true;
    private boolean enableGravity = true;

    public Enemy(double x, double y, double width, double height) {
    	super(x, y, width, height, Color.RED);   	
    	setUpdate(true);
    }
    
    public void update() {
        if (getY() <= 0) {
			setY(0);
			yVel = 0;
	    } else if (enableGravity) {
    		yVel += Constants.GRAVITY;
    	}
       
    	updatePos(xVel, yVel);
    }

    public Element[][] move(String action, Element[][] data) {
        double oldXVel = xVel;
        double oldYVel = yVel;
        double oldX = getX();
        double oldY = getY();

		updatePos(xVel, yVel);
          
        int j = 0;
		for (int i = 0; data[2][i] != null; i++) {
			if (BoundingBox.intersects(this, data[2][i]) || BoundingBox.intersects(data[2][i], this)) {
				if (BoundingBox.isAbove(this, data[2][i], Constants.BUFFER)) {
					yVel = 0;
					setY(data[2][i].getY() + data[2][i].getHeight());
					//enablePhysics = true;
				} 
				
				if (BoundingBox.hitRoof(this, data[2][i], Constants.BUFFER)) {
					yVel = Constants.GRAVITY;
					updatePos(0, yVel);
				} 
				
				if (BoundingBox.hitLeft(this, data[2][i], Constants.BUFFER)) {
					xVel = xVel > 0 ? -xVel : xVel;
					setX(data[2][i].getX() - this.getWidth());
					//yVel += Constants.GRAVITY;
				}
				
				if (BoundingBox.hitRight(this, data[2][i], Constants.BUFFER)) {
					xVel = xVel < 0 ? -xVel : xVel;
					setX(data[2][i].getX() + data[2][i].getWidth());
					//yVel += Constants.GRAVITY;
				}

				//enablePhysics = false;
				i = 0;
			}
		}
		return data;
	}
	
    public void updatePos(double xVel, double yVel) {
        setX(getX() + xVel);
        setY(getY() + yVel);
    }
}



