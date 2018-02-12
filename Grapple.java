import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

import java.io.*;

import java.awt.geom.*;

import java.lang.Math.*;

public class Grapple extends Element {
    private double xVel = 0;
    private double yVel = 0;
    
    private boolean movingLeft  = false;
    private boolean movingRight = false;
    
    public Grapple(double x, double y, double width, double height, double yVel) {
    	super(x, y, width, height, Color.BLUE);   	
        this.yVel = yVel;
    	setUpdate(true);
    }
    
    public void update() {
    	updatePos(xVel, yVel);
    }
	
    public void updatePos(double xVel, double yVel) {
        //System.out.println(getX() + " " + getY() + " " + xVel + " " + yVel);
        setX(getX() + xVel);
        setY(getY() + yVel);
    }
}
