import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/** The game frame for the game Gradius along with the menu for said frame.
	@author Alvin Ng @ Langara
	@version 2017-05-07 
	@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736"</a>
*/

public class GameFrame extends JFrame {
	
	public final static int WIDTH = 900; // width of the Frame
	public final static int HEIGHT = 700; // height of the frame
	
	private final GameComponent comp;
	
   /** Create the game frame for the game Gradius along with the menu for said frame.
	*/
	public GameFrame() {
		setResizable(false);
		comp = new GameComponent();
		add(comp);
      
      JMenuBar menuBar = new JMenuBar();
      setJMenuBar(menuBar);
      menuBar.add(createFileMenu());
   }

   /** Create the menu above the frame contains exit
	*/
   public JMenu createFileMenu()
   {
      JMenu menu = new JMenu("File");
      menu.add(createFileExitItem());
      return menu;
   }

   /** Create the exit item for the menu
	*/
   public JMenuItem createFileExitItem() {
      JMenuItem item = new JMenuItem("Exit");
      
       class MenuItemListener implements ActionListener
       {
          public void actionPerformed(ActionEvent event)
          {
            System.exit(0);
   
          }
       }
       ActionListener listener = new MenuItemListener();
       item.addActionListener(listener);
       return item;
   }
	
	public static void main(String[] args) {
      GameFrame frame = new GameFrame();
      
		frame.setSize(WIDTH, HEIGHT); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setTitle("Gradius");
		frame.setVisible(true);
		frame.comp.start();
	}
}