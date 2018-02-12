import java.awt.Color;

public class Solid extends Element {
    public Solid(double x, double y, double width, double height) {
    	super(x, y, width, height, Color.GRAY);   	
    	setUpdate(false);
    }
}
