import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/** The ShipImpl class contains all the implementation for the ship
	@author Alvin Ng @ Langara
	@version 2017-05-07 
	@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736"</a>
*/

public class ShipImpl implements Ship {

	private final static Color FILL = Color.GREEN;
	private final static Color BORDER = Color.BLACK;

	private final static int HIGHEST_I = 0; // the array position of the top
	private final static int LOWEST_I = 1;  // the array position of the bottom
	private final static int FRONT_I = 2; // the position of the front of the ship
	private final static int HEIGHT = 20; // the height of the ship
	private final static int WIDTH = HEIGHT; //width of the ship
	private final Polygon shape; // shape of the ship
	private DirectionX dirX; // the left right direction the ship is moving
   private DirectionY dirY; // the up down direction the ship is moving
	private Rectangle2D movementBounds;// the boundaries for the ship
                           
   /** Create the ship in the component ship is a triangle 
   @param starting x coordindate
   @param starting y coordindate
	*/
	public ShipImpl(int x, int y) {
		shape = new Polygon(
                     new int[] {0,0,WIDTH}, //top left, bottom left, front middle
                     new int[] {0,HEIGHT,HEIGHT/2}, 3);
		shape.translate(x, y);
		dirY = DirectionY.NONE;
      dirX = DirectionX.NONE;
	}

	/** Sets the direction of the ship left or right
		@param dx the direction which we want the ship to move in - for left + for right
	*/
   public void setDirectionX(DirectionX dx) {
      dirX = dx;
	}

	/** Sets the direction of the ship up and down
		@param dy the direction which we want the ship to move in - for up + for down
	*/
   public void setDirectionY(DirectionY dy) {
      dirY = dy;
	}
	public void setMovementBounds(Rectangle2D movementBounds) { 
      this.movementBounds = movementBounds;
	}
   
	/** moves the ship up, down, left, and right in it's boundary.\n
      If it passes the boundary it'll end up on the opposite side of the boundary
		@param speed The speed at which the ship moves, increasing with other 
	*/
	public void move(int speed) {
      Rectangle2D shipRect = shape.getBounds2D();
      
      if (movementBounds.outcode(shipRect.getX(), shipRect.getY()) == movementBounds.OUT_LEFT) {
         shape.translate(900,0);
      }
      else if (movementBounds.outcode(shipRect.getX(), shipRect.getY()) == movementBounds.OUT_RIGHT) {
         shape.translate(-900,0);
      }
      
      if (movementBounds.outcode(shipRect.getX(), shipRect.getY()) == movementBounds.OUT_TOP) {
         shape.translate(0,690);
      }
      else if (movementBounds.outcode(shipRect.getX(), shipRect.getY()) == movementBounds.OUT_BOTTOM) {
         shape.translate(0,-690);
      }
      
      shape.translate(speed*dirX.dx,speed*dirY.dy);
	}

	/** draws the ship   
      @return g an object of the graphics2D class allows us to modify graphic properties and draw graphics 
	*/
	public void draw(Graphics2D g) {
      g.setColor(FILL);
      g.fillPolygon(shape);
      g.setColor(BORDER);
      g.drawPolygon(shape);
	}
   
	/** returns the shape of the object
		@return shape , the shape of the object
	*/
	public Shape getShape() {
		return shape;
	}
   
	/** checks if the object has intersected the sprite in the parameter
		@param other the other object that we wish to check whether our sprite has crashed into
	*/
	public boolean intersects(Sprite other) {
      Area a = new Area(other.getShape());
      if (shape.intersects(other.getShape().getBounds2D())){
          return true;
      }
      else if (a.intersects(shape.getBounds2D())){
         return true;
      }
      else if (shape.getBounds().isEmpty() || other.getShape().getBounds().isEmpty()) {
         return false;          
      }
      else {
         return false;
      }
	}
}