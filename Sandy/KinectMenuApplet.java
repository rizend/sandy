package Sandy;

public interface KinectMenuApplet extends KinectApplet {
	/**
	* Sets up the menu applet so that it is ready to begin drawing.
	* @param kr a KinectReference which can be used to fetch the depth values for the box.  Ought to be stored for use in draw.
	* @param kl a KinectLoad instance which can be used to load a new applet
	*/
	public void setup(KinectReference kr, KinectLoad kl);
}