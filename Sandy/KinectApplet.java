package Sandy;

import java.awt.image.*;
import java.awt.*;

public abstract class KinectApplet {
	protected KinectReference kr;
	/**
	* Returns a name unique to the applet.
	*/
	public String getName() {
		return this.getClass().getName();
	}
	
	/**
	* Draws the applet and updates internal state
	* @return false if the application is done running and we should exit; true if the program is still active and running.
	*/
	protected abstract void draw(short[][] depths, Graphics2D g);

	/**
	* Forcibly stops the applet.
	* The applet can safely assume that it will not be asked to draw again unless its setup method is called again.
	* The applet should remove its window from the screen as quickly as possible and block until this is finished.
	*/
	protected void onRemove() {}
	
	protected void exit() {
		if(running) {
			running = false;
			onRemove();
		}
	}

	/**
	* Sets up the applet so that it is ready to begin drawing.
	* @param kr a KinectReference which can be used to fetch the depth values for the box.  Ought to be stored for use in draw.
	*/
	public final void setup(KinectReference kr) {
		System.out.println("Setup "+getName());
		this.kr = kr;
	}

	boolean running = true;
	public boolean _draw(Graphics2D g) {
		if(!running)
			return false;

		int sum=0;
		short[][] mms = getDepths();

		if(mms==null)
			return true;

		int len = mms.length*mms[0].length;
		for(short[] row : mms) {
			for(short s : row) {
				sum+=s;
			}
		}

		avgDepth = sum/len;

		draw(mms, g);
		return running;
	}

	public void click(int x, int y) {}

	protected final short[][] getDepths() {
		return kr.getDepths();
	}
	int avgDepth = -1;
	protected final int getAverageDepth() {
		return avgDepth;
	}

	//========messy java AWT drawing stuff:

	public static final Color color(int rgb) {
		return new Color(rgb);
	}

	//Where to begin the area of black projected on either side
	//from perspective behind projector, goes from given value to edge
	final static int LEFT_BAR_START = 940;
	final static int RIGHT_BAR_START = 50;

	final static int SCREEN_WIDTH = 1024;
	final static int SCREEN_HEIGHT = 768;

	final static int DRAW_WIDTH = LEFT_BAR_START-RIGHT_BAR_START;

	int[] byteDraw = new int[640*480*2+640*480];

	static int RIGHT_CUT = 100;
	static int LEFT_CUT = 130;
	static int TOP_CUT = 40;
	static int BOTTOM_CUT = 100;

	public final void setImage(Graphics2D g, int[] colors) {
		//flip colors:
		int tmp;
		for(int i = 0; i<colors.length/2; i++) {
			tmp = colors[colors.length-1-i];
			colors[colors.length-1-i]=colors[i];
			colors[i]=tmp;
		}

		//int[] draw = new int[DRAW_WIDTH*SCREEN_HEIGHT];
		//System.out.println(DRAW_WIDTH+"*"+SCREEN_HEIGHT); 890*768

		int val = rgb(250,50,250);

		for(int i = 0; i<byteDraw.length; i++) {
			byteDraw[i]=0xff;
		}
		int n = 0;
		
		for(int i = 0; i<colors.length; i++) {
			byteDraw[n++]=(colors[i]&0xff0000)>>16;
			byteDraw[n++]=(colors[i]&0xff00)>>8;
			byteDraw[n++]=(colors[i]&0xff);
			//byteDraw[n++]=0xffffffff;//(colors[i]&0xff000000)>>24;
		}

		//draw the array
		/*BufferedImage b = new BufferedImage(DRAW_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		b.setRGB(0, 0, DRAW_WIDTH, SCREEN_HEIGHT, draw, 0, DRAW_WIDTH);//b*/

		g.drawImage(getImageFromArray(byteDraw), RIGHT_BAR_START, 0, LEFT_BAR_START, SCREEN_HEIGHT, LEFT_CUT, TOP_CUT,640-RIGHT_CUT,480-BOTTOM_CUT, null);
		//g.getRaster().setPixels(0,0,640,480,byteDraw);


		//set the no-draw bars on either side
		g.setColor(color(sidebarColor));
		g.fillRect(0,0,RIGHT_BAR_START, SCREEN_HEIGHT);
		g.fillRect(LEFT_BAR_START, 0, SCREEN_WIDTH-LEFT_BAR_START, SCREEN_HEIGHT);
	}

    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    WritableRaster raster = (WritableRaster) image.getRaster();
	public final Image getImageFromArray(int[] pixels) {
            raster.setPixels(0,0,640,480,pixels);
            return image;
    }

	int sidebarColor = rgb(0,0,0);
	protected final void setSidebarColor(int color) {
		sidebarColor = color;
	}
	protected final void setSidebarColor(int r, int g, int b) {
		setSidebarColor(rgb(r,g,b));
	}

	public final static int rgb(int r, int g, int b) {
		return (   0xff <<24) |
			   ((r&0xff)<<16) | 
			   ((g&0xff)<<8)  |
			   (b&0xff);
	}
}