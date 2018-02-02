import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.imageio.*;

import java.io.*;

import java.awt.geom.*;

import java.lang.Math.*;

import java.util.ArrayList;

public final class Constants {
    // Runtime
    public static final int    FRAMES_PER_SECOND   = 100;
    public static final int    UPDATES_PER_SECOND  = 100; // basically try as often as you can
    public static final int    REFRESH_TIMER       = 1000 / FRAMES_PER_SECOND;
    public static final int    UPDATE_TIMER        = 1000 / UPDATES_PER_SECOND;

    // Display  
    public static final int    WIDTH               = 1200;
    public static final int    HEIGHT              = 800;
    public static final int    IFW                 = JComponent.WHEN_IN_FOCUSED_WINDOW;

    // Physics
    public static final double GRAVITY             = -0.03;
    public static final double FRIC                = 2;

    // Player
    public static final String MOVE_LEFT           = "move left";
    public static final String MOVE_RIGHT          = "move right";
    public static final String MOVE_LEFT_RELEASED  = "move left released";
    public static final String MOVE_RIGHT_RELEASED = "move right released";
    public static final String JUMP                = "jump";
    public static final double PLAYER_MOVE_SPEED   = 0.3;
    public static final double PLAYER_ACC          = 0.1;
    public static final double PLAYER_JUMP_SPEED   = 1;
    public static final double PLAYER_MASS         = 1;

    // Enemy 1
    public static final double ENEMY_MOVE_SPEED   = 0.2;
    public static final double ENEMY_ACC          = 0.1;
    public static final double ENEMY_JUMP_SPEED   = 0.8;
    public static final double ENEMY_MASS         = 1;

    // Grapple
    public static final String GRAPPLE           = "grapple";
    public static final double GRAPPLE_SIZE      = 50;
    public static final double GRAPPLE_SPEED     = 2;

    // Ground
    public static final int    BUFFER            = 5;
}
