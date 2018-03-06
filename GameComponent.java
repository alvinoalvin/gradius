import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Font; 
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

/** The game component for the game Gradius all components of the game are implemented here.
	@author Alvin Ng @ Langara
	@version 2017-05-07 
	@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736"</a>
*/

public class GameComponent extends JComponent {

   /** a method that shows things that need to be updated and/or redrawn after every tick called by a timer\n
   * also checks for game over and win conditions every tick
   */
	private void update() {
		this.requestFocusInWindow();
		ship1.move(level);
      moveAsteroids();
      checkCollisions();
      
      if (health == 0) {
         if (health < 0) {
            health = 0;   
         }
         gameOver();
      }
      if (level == 15) {
         gameWin();   
      }
		repaint();

   }

	private Timer[] newTimer = new Timer[4]; // array of all the timers
	private final Ship ship1; // your ship
   private static Set<Asteroid> asteroidSet; // all them asteroids on the screen
   private ShipKeyListener listen = new ShipKeyListener(); //listens to the keys events and does the events specified
   private boolean gameOverBool = false; //state boolean tells us if game is over
   private static int scoreTime=1;// the score 
   private int health; // health starts at 500 and goes down as you collide 
   private boolean paused = false;//state boolean tells if game is paused
   private boolean gameWon = false; // state boolean tells us if game is won
   int level=5; //the level starts at 5 but on screen starts at 0
   
   /** The game component for the game Gradius all components of the game are implemented here.
   */
	public GameComponent() {
		//sprites
		ship1 = new ShipImpl(10,GameFrame.HEIGHT/3);
		asteroidSet = new HashSet<Asteroid>();
      health = 500;
		//key listener
		this.addKeyListener(listen);
		//update constantly
		newTimer[0] = new Timer(1000/80, (a) -> {this.update();});
	   //update asteroids
      newTimer[1] = new Timer(1000/(level), (a) -> {this.makeAsteroid();});
      //update score
      newTimer[2] = new Timer(15/2, (a) -> {scoreTime++;});
      //update level
      newTimer[3] = new Timer(5000, (a) -> {level++;});
      
	}  
	
	public void paintComponent(Graphics g) {
         Graphics2D g2 = (Graphics2D) g;
         g2.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         paintComponent(g2);
	}
   
   /** paints the ship, asteroids, score, and level, calls paintGameOver(g) if gameOverBool is true \n
   *  if gameWon is true wil call paintWin(g)
   *   @return g an object of the graphics2D class allows us to modify graphic properties and draw graphics 
   */
	private void paintComponent(Graphics2D g) {
      if (gameOverBool == true) {
         paintGameOver(g);
      }
      else if (gameWon == true) {
         paintWin(g);
      }
      else{
         g.setColor(Color.black);
         g.fill(new Rectangle(0,0,getWidth(),getHeight()));
	 	   //ship 
         ship1.draw(g);
         //asteroids
         for (Asteroid a : asteroidSet) {
            a.draw(g);  
         }
         paintHP(g);
         g.setFont(new Font("Impact", Font.PLAIN, 30));
         g.setColor(Color.white);
         g.drawString("Score: " + Integer.toString(scoreTime), 725, 45);
         g.drawString("Level: " + Integer.toString(level-5), 725, 70);
      }
	}
   
   /** paints the Win screen
   *   @return g an object of the graphics2D class allows us to modify graphic properties and draw graphics 
   */
   private void paintWin(Graphics2D g){
      g.setColor(Color.white);
      g.fill(new Rectangle(0,0,getWidth(),getHeight()));
      g.setColor(Color.black);
      g.setFont(new Font("Impact", Font.PLAIN, 30));
      g.drawString("You Won!", (getWidth()/2)-80, getHeight()/2-100);
      g.drawString("Survival Time: " + Integer.toString(scoreTime) + "mills", 300 , 300);
      g.drawString("Level: "+ Integer.toString(level), 340, 350);  
      g.drawString("Play Again? Y/N ", 340, 400);  

   }
   
   /** paints the Game Over screen
   *   @return g an object of the graphics2D class allows us to modify graphic properties and draw graphics 
   */
   private void paintGameOver(Graphics2D g){
      g.setColor(Color.black);
      g.fill(new Rectangle(0,0,getWidth(),getHeight()));
      g.setColor(Color.white);
      g.setFont(new Font("Impact", Font.PLAIN, 30));
      g.drawString("GAME OVER", (getWidth()/2)-80, getHeight()/2-100);
      g.drawString("Survival Time: " + Integer.toString(scoreTime) + "mills", 300 , 300);
      g.drawString("Level: "+ Integer.toString(level), 390, 350);  
      g.drawString("Play Again? Y/N ", 340, 400);  
   }
   
   /** paints the health bar for the player
   *   @return g an object of the graphics2D class allows us to modify graphic properties and draw graphics 
   */
   private void paintHP(Graphics2D g){
      g.setColor(Color.green);
      g.setFont(new Font("Impact", Font.PLAIN, 25));
      if (health <= 200) {
         g.setColor(Color.red);         
      }
      else if (health <= 300) {
         g.setColor(Color.yellow);   
      }
      g.fill(new Rectangle(50, 600, health/2, 10));
      g.drawString(Integer.toString(health), 50, 590);
   }
   
   /** starts the game by starting all timers, setting the movement bounds to the ship using the frame, and setting the asteroid start bounds
   */
	public void start() {
      AsteroidFactory.getInstance().setStartBounds(getWidth(), getY(), getHeight());
   	ship1.setMovementBounds(new Rectangle(getX(), getY(), getWidth(), getHeight()));
      for (Timer time : newTimer) {
         time.start();
      }
	}
	
   /** calls make asteroid method in the asteroidFactory and puts the newly made asteroid into the  asteroidSet
   */
	public void makeAsteroid() {
      asteroidSet.add(AsteroidFactory.getInstance().makeAsteroid(level));
	}
   
   /** moves every asteroid left using the move method in the asteroidFactory 
   */
   public void moveAsteroids() {
      Iterator<Asteroid> iterator = asteroidSet.iterator();
      
      while (iterator.hasNext()) {
         Asteroid a = iterator.next();
         a.move(level);
         if (!a.isVisible()) {
            iterator.remove();
         }
      }
   }
   
   /** checks if the asteroid has collided with thye ship and if it has it'll lower your health by 50 
   */
   public void checkCollisions(){
      Iterator<Asteroid> iterator = asteroidSet.iterator();
      
      while (iterator.hasNext()) {
         Asteroid a = iterator.next();
         
         if (a.intersects(ship1) && ship1.intersects(a)) {
            health -= 50;
            
            if (health < 0) {
               health = 0;   
            }
            iterator.remove();
         }
      }
   }
   
   /** if game is lost sets the gameOverBool variable to true and stops all timers
   */
   public void gameOver(){
      for (Timer time : newTimer) {
         time.stop();
      }
      gameOverBool=true;

   }

   /** if game is won sets the gameWon variable to true and stops all timers
   */
   public void gameWin() {
      for (Timer time : newTimer) {
         time.stop();
      }
      gameWon=true;
   }

   /** Listens to keyevents in the gameFrame and does whatever the keyPressed event does
   */
   public class ShipKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			 switch (e.getKeyCode()) {
			 case KeyEvent.VK_KP_UP:
			 case KeyEvent.VK_UP:
			 case KeyEvent.VK_W:
				ship1.setDirectionY(Ship.DirectionY.UP);
				break;
			 case KeyEvent.VK_KP_DOWN:
			 case KeyEvent.VK_DOWN:
			 case KeyEvent.VK_S:
				ship1.setDirectionY(Ship.DirectionY.DOWN);
				break;
			 case KeyEvent.VK_KP_RIGHT:
			 case KeyEvent.VK_RIGHT:
			 case KeyEvent.VK_D:
				ship1.setDirectionX(Ship.DirectionX.RIGHT);
				break;
			 case KeyEvent.VK_KP_LEFT:
			 case KeyEvent.VK_LEFT:
			 case KeyEvent.VK_A:
				ship1.setDirectionX(Ship.DirectionX.LEFT);
				break;
          case KeyEvent.VK_ESCAPE:
            if(!paused) { 
               pause();
            }
            else{
               unPause();   
            }
				break;
          case KeyEvent.VK_Y:
            if (gameOverBool==true) {
               restart();
            }
				break;
          case KeyEvent.VK_N:
            if (gameOverBool==true) {
               removeKeyListener(listen);
               System.exit(0);
            }
			   break;
          default:
			 }
	  	}
      
      /** upon the release of the key the ship will stop moving in a direction
      *  @param e the keyevent which has occured may or may not be a keyRelease
      */
		public void keyReleased(KeyEvent e) {
				ship1.setDirectionY(Ship.DirectionY.NONE);
			 	ship1.setDirectionX(Ship.DirectionX.NONE);
		}

      /** if the timers are working it will stop the timers back up hence pausing the game
      */
      public void pause() {
         for (Timer time : newTimer) {
            time.stop();   
            paused = true;
         }   
      }
      
      /** if the timers are stopped it will start the timers back up hence unpausing the game
      */
      public void unPause() {
         for (Timer time : newTimer) {
            time.start();
            paused = false; 
         }   
      }
   
      /** restarts the game resets all the timers the booleans and the health of the ship however \n 
         will not move the ship back to initial position
      */
      public void restart() {
         Iterator<Asteroid> iterator = asteroidSet.iterator();
         health = 500;
         for (Timer time : newTimer) {
            time.restart();
         }  
         gameOverBool = false;
         scoreTime = 1;
         level = 5;
         while (iterator.hasNext()) {
            Asteroid a = iterator.next();
            iterator.remove();
         }
         repaint();
      }
   }
}
