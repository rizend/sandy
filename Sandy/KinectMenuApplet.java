package Sandy;

public abstract class KinectMenuApplet extends KinectApplet {
	protected KinectLoad kl;
	/**
	* Sets up the menu applet so that it is ready to begin drawing.
	* @param kl a KinectLoad instance which can be used to load a new applet
	*/
	public final void setupMenu(KinectLoad kl) {
		this.kl = kl;
	}

	protected final void loadApplet(KinectApplet ka) {
		kl.loadApplet(ka);
	}

	public void onHandRaise() {}
}