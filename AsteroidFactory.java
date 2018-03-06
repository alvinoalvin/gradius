import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Area;
import java.util.Random;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;

/** The AsteroidFactory contains all the implementation for the asteroids
	@author Alvin Ng @ Langara
	@version 2017-05-07 
	@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736"</a>
*/

public class AsteroidFactory {

	private final static AsteroidFactory instance = new AsteroidFactory();

	private static Rectangle startBounds;//where the asteroids are made on the frame

	private AsteroidFactory() {}

	/** returns an instance of the asteroid
      @return an instance of the asteroid
	*/
	public static AsteroidFactory getInstance() {
		return instance;
	}

   /** sets the starting point of all the asteroids
      @param x the x axis at which the asteroids are made
      @param maxY the highest point which the asteroids are made
      @param minY the lowest point at which the asteroids are made
   */
	public void setStartBounds(int x, int maxY, int minY) {
		startBounds = new Rectangle(x, maxY, x, minY);
	}

   /** makes a new asteroid with ba random size, and a random velocity of 1 to Level
      @param level the maxiumum  velocity at which an asteroid is allowed to travel at
   */
	public Asteroid makeAsteroid(int level) {
		return new AsteroidImpl(startBounds.width, random(startBounds.y,startBounds.height), random(15,60), random(1,level+5));
	}

   /** takes two integers and returns a random intger in between the two
      @param min the minimum number that can be produced from the random function
      @param max the maximum number that can be produced from the random function
      @return a random integer between the minimum and the maximum
   */
	private static int random(int min, int max) {
		Random rand = java.util.concurrent.ThreadLocalRandom.current();
		return min + (int) (rand.nextDouble()*(max-min));
	}

	private static class AsteroidImpl implements Asteroid {
		private static final Color COLOR = Color.green;
	   private final Polygon shape;
      private int velocity;

		private AsteroidImpl(int x, int y, int size, int aVelocity) {
         velocity = aVelocity;
         shape = new Polygon(
            new int[] {0, -size/3, 0, size/3, 2*size/3, size+size/4, size+size/2, size+size/3, 2*size/3, size/3}, //top left, bottom left, front middle
            new int[] {0, size/2, size, size+size/5, size+size/4, size, size/2, -size/5, -size/4}, 9);
         shape.translate(x, y);
		}
      
      /** moves the asteroid left, the frame.Using the velocity from it's constructor as the speed
      */
		public void move(int speed) {
         shape.translate(-velocity,0);
      }
      
   	/** checks if the shape is on the frame and thus telling us if it has been drawn
		   @return a boolean indicating if the asteroid is visible
	   */
		public boolean isVisible() {
         if (shape.getBounds().getX() < 0) {
            return false;
         }
         else {
			   return true;
         }
		}

   	/** draws the asteroid   
		   @param g an object of the graphics2D class allows us to modify graphic properties and draw graphics 
	   */
		public void draw(Graphics2D g) { 
         setColor(g);
         g.draw(shape);
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
         if (shape.intersects(other.getShape().getBounds())) {
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
      
      /** sets the color of the asteroid depending on the speed at which it's travelling
		   @return g an object of the graphics2D class allows us to modify graphic properties and draw graphics 
	   */
      public void setColor(Graphics2D g) {
         if (velocity >= 16) {
            g.setColor(Color.white);   
         }
         else if (velocity >= 10) {
            g.setColor(Color.pink);   
         }  
         else {
            g.setColor(Color.magenta);   
         }
      }
	}
}
