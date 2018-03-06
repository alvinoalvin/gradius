import java.awt.*;

/** The Sprite interface draw, getshape, move, intersects
	@author Alvin Ng @ Langara
	@version 2017-05-07 
	@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736"</a>
*/

public interface Sprite {
	public void draw(Graphics2D g);
	public void move(int speed);
	public Shape getShape(); // used by intersects
	public boolean intersects(Sprite other);
}
