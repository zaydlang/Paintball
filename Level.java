import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

import java.io.*;

import java.awt.geom.*;
import java.awt.Color;

import java.lang.Math.*;

import java.util.ArrayList;

public class Level {
    private int width;
    private int height;
    private Element[][] data;

    public Level(int width, int height, Element[][] data) {
    	this.width  = width;
    	this.height = height;
    	this.data   = data;
    }

    public void buildLevel(ArrayList<String> actionQueue) {
        updateLevel(actionQueue);
        
        for (int i = 0; i < data.length; i++) {
			for (int j = 0; data[i][j] != null; j++) {
                Element temp = data[i][j];
                temp.update();
			}
		}
    }
    
    public void drawLevel(Graphics g) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; data[i][j] != null; j++) {
                Element temp = data[i][j];
                g.setColor(temp.getColor());
				g.fillRect((int)(temp.getX()), (int)(height - temp.getY() - temp.getHeight()), (int)(temp.getWidth()), (int)(temp.getHeight()));
			}
		}
    }

    public void updateLevel(ArrayList<String> actionQueue) {
        for (int i = 0; actionQueue.size() != 0; actionQueue.remove(0)) {
            String action = actionQueue.get(0);
            
            data = data[0][0].move(action, data);
            //data = data[3][0].move("", data);
        }
        
        data = data[0][0].move("", data);
        //data = data[3][0].move("", data);
    }
}
