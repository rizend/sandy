package Sandy;

import java.awt.Graphics2D;

public interface KinectApplet {

	/**
	* Returns a name unique to the applet.
	*/
	public String getName();

	/**
	* Sets up the applet so that it is ready to begin drawing.
	* @param kr a KinectReference which can be used to fetch the depth values for the box.  Ought to be stored for use in draw.
	*/
	public void setup(KinectReference kr);

	/**
	* Draws the applet and updates internal state
	* @return false if the application is done running and we should exit; true if the program is still active and running.
	*/
	public boolean draw(Graphics2D g);

	public void click(int x, int y);

	/**
	* Forcibly stops the applet.
	* The applet can safely assume that it will not be asked to draw again unless its setup method is called again.
	* The applet should remove its window from the screen as quickly as possible and block until this is finished.
	*/
	public void remove();
}