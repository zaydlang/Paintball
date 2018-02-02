public class BoundingBox {
    public static boolean intersects(Element left, Element right) {
    	return ( ( (left.getX() < right.getX() && left.getX() + left.getWidth() > right.getX()) || (left.getX() < right.getX() + right.getWidth() && left.getX() + left.getWidth() > right.getX() + right.getWidth()) ) && ( (left.getY() < right.getY() + right.getHeight() && left.getY() > right.getY()) || (left.getY() + left.getHeight() < right.getY() + right.getHeight() && left.getY() + left.getHeight() > right.getY()) ));
    }

    public static boolean isAbove(Element left, Element right, int buffer) {
		return right.getY() + right.getHeight() - left.getY() <= right.getWidth() / 2 && right.getY() + right.getHeight() - left.getY() >= 0;
    }

    public static boolean hitRoof(Element left, Element right, int buffer) {
		return left.getY() + left.getHeight() - right.getY() >= 0 && left.getY() + left.getHeight() - right.getY() <= right.getHeight() / 2;
    }

    public static boolean hitLeft(Element left, Element right, int buffer) {
		return left.getX() + left.getWidth() - right.getX() >= 0 && left.getX() + left.getWidth() - right.getX() <= right.getWidth() / 2;
    }

    public static boolean hitRight(Element left, Element right, int buffer) {
		return right.getX() + right.getWidth() - left.getX() <= buffer && right.getX() + right.getWidth() - left.getX() >= right.getWidth() / 2;
    }

    public static boolean[] getCollisions(Element left, Element right) {
        double leftX1  = left.getX();
        double leftX2  = left.getX() + left.getWidth();
        double leftY1  = left.getY();
        double leftY2  = left.getY() + left.getHeight();
        double rightX1 = right.getX();
        double rightX2 = right.getX() + right.getWidth();
        double rightY1 = right.getY();
        double rightY2 = right.getY() + right.getHeight();
        boolean upFlag, leftFlag, rightFlag, downFlag = false;
        
    	upFlag    = (rightY2 > leftY1 && rightY2 < leftY2 && leftX1 > rightX1 - left.getWidth() && leftX2 < rightX2 + left.getWidth());
    	leftFlag  = (rightX1 > leftX1 && rightX1 < leftX2 && leftY1 > rightY1 - left.getHeight() && leftY2 < rightY2 + left.getHeight());
    	rightFlag = (rightX2 > leftX1 && rightX2 < leftX2 && leftY1 > rightY1 - left.getHeight() && leftY2 < rightY2 + left.getHeight());
    	downFlag  = (rightY1 > leftY1 && rightY1 < leftY2 && leftX1 > rightX1 - left.getWidth() && leftX2 < rightX2 + left.getWidth());
    	
    	return new boolean[] {upFlag, leftFlag, rightFlag, downFlag};
    }
}
