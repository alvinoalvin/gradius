import java.awt.geom.Rectangle2D;

/** The ship interface for ship
	@author Alvin Ng @ Langara
	@version 2017-05-07 
	@see <a href="https://d2l.langara.bc.ca/d2l/lms/dropbox/user/folder_submit_files.d2l?db=51074&grpid=0&isprv=0&bp=0&ou=88736"</a>
*/

public interface Ship extends Sprite {

	public enum DirectionY {
		NONE(0), UP(-1), DOWN(1);
		public final int dy;
		DirectionY(int dy) {
			this.dy = dy;
		}
	};
   
	public enum DirectionX {
		NONE(0), LEFT(-1), RIGHT(1);
      public final int dx;
		DirectionX(int dx) {
		   this.dx = dx;
      }
	};
   
	public void setDirectionX(DirectionX dx);
   public void setDirectionY(DirectionY dy);
	public void setMovementBounds(Rectangle2D bounds);
}
