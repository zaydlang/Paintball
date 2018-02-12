import java.awt.Color;

public class Element {
   private double x;
   private double y;
   private double width;
   private double height;
   
   private Color c;

   private boolean update = false;
   
   public Element(double x, double y, double width, double height, Color c) {
      this.x      = x;
      this.y      = y;
      this.width  = width;
      this.height = height;
      this.c      = c;
   }
   
   public void update() {};
   public Element[][] move(String action, Element[][] data) { return null; };

   public double getX()          { return x; }
   public double getY()          { return y; }
   public double getWidth()      { return width; }
   public double getHeight()     { return height; }
   public Color getColor()       { return c; }
   public boolean getUpdate()    { return update; }

   public void setX(double x)                  { this.x = x; }
   public void setY(double y)                  { this.y = y; }
   public void setWidth(double width)          { this.width = width; }
   public void setHeight(double height)        { this.height = height; }
   public void setUpdate(boolean newUpdate)    { update = newUpdate; }
}	
