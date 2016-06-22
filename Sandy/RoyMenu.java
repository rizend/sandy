package Sandy;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;

public class RoyMenu implements KinectMenuApplet {
	KinectReference kr;
	KinectLoad kl;
	boolean ready=false;

	public void setup(KinectReference kr) {
		throw new RuntimeException("This is a menu!");
	}

	public void setup(KinectReference kinectRef, KinectLoad kl) {
		this.kr = kinectRef;
		this.kl = kl;

		ready=true;

		System.out.println("setup!");
	}
	public boolean draw(Graphics2D g) {
		//System.out.println(kr+" -> "+ready);

		short[][] mms = kr.getDepths();

		if(mms==null)
			return true;

		int sum=0;
		int len = mms.length*mms[0].length;
		for(short[] row : mms) {
			for(short s : row) {
				sum+=s;
			}
		}
		int avg = sum/len;

		int[] rgbArray = new int[640*480];

		//int x = 0;
		//int y = 0;
		int val;

		int i = 0;
		for(short[] row : mms) {
			for(short s : row) {
				val = s&0xffff;
				rgbArray[i]=rgb(val>800&&val<1200 ? ((val-800)*255)/400 : 0, val<600 ? 0 : 255, val/10);
				i++;
			}
		}

		setImage(g, rgbArray);

		g.drawString("Avg: ("+avg+")", 100, 100);

		return true;
	}

	public static Color color(int rgb) {
		return new Color(rgb);
	}

	//Where to begin the area of black projected on either side
	//from perspective behind projector, goes from given value to edge
	final static int LEFT_BAR_START = 940;
	final static int RIGHT_BAR_START = 50;

	final static int SCREEN_WIDTH = 1024;
	final static int SCREEN_HEIGHT = 768;

	final static int DRAW_WIDTH = LEFT_BAR_START-RIGHT_BAR_START;

	public void setImage(Graphics2D g, int[] colors) {
		//flip colors:
		int tmp;
		for(int i = 0; i<colors.length/2; i++) {
			tmp = colors[colors.length-1-i];
			colors[colors.length-1-i]=colors[i];
			colors[i]=tmp;
		}

		int[] draw = new int[DRAW_WIDTH*SCREEN_HEIGHT];

		int val = rgb(255,50,255);

		for(int i = 0; i<draw.length; i++) {
			draw[i]=val;
		}

		//draw the array
		BufferedImage b = new BufferedImage(DRAW_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		b.setRGB(0, 0, DRAW_WIDTH, SCREEN_HEIGHT, draw, 0, DRAW_WIDTH);
		g.drawImage(b, RIGHT_BAR_START, 0, null);


		//set the no-draw bars on either side
		g.setColor(color(flashColor));
		g.fillRect(0,0,RIGHT_BAR_START, SCREEN_HEIGHT);
		g.fillRect(LEFT_BAR_START, 0, SCREEN_WIDTH-LEFT_BAR_START, SCREEN_HEIGHT);
	}

	int flashColor = rgb(0,0,0);

	public static int rgb(int r, int g, int b) {
		return (   0xff <<24) |
			   ((r&0xff)<<16) | 
			   ((g&0xff)<<8)  |
			   (b&0xff);
	}

	public void remove() {

	}

	public String getName() {
		return "Roy's First Menu Attempt";
	}
}