package Sandy;

import java.awt.image.*;
import java.awt.*;

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
				val = val>950&&val<1100 ? ((val-950)) : 0;
				rgbArray[i]=rgb(val<50 ? val*255/50 : 0, val<100 && val>=50 ? (val-50)*255/50 : 0, val>=100 ? (val-100)*255/50 : 0);
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

	public void click(int x, int y) {
	}

	int[] byteDraw = new int[640*480*2+640*480];

	static int RIGHT_CUT = 100;
	static int LEFT_CUT = 130;
	static int TOP_CUT = 40;
	static int BOTTOM_CUT = 100;

	public void setImage(Graphics2D g, int[] colors) {
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
		g.setColor(color(flashColor));
		g.fillRect(0,0,RIGHT_BAR_START, SCREEN_HEIGHT);
		g.fillRect(LEFT_BAR_START, 0, SCREEN_WIDTH-LEFT_BAR_START, SCREEN_HEIGHT);
	}
    BufferedImage image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
    WritableRaster raster = (WritableRaster) image.getRaster();
	public Image getImageFromArray(int[] pixels) {
            raster.setPixels(0,0,640,480,pixels);
            return image;
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
