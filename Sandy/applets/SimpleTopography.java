package Sandy.applets;

import java.awt.Graphics2D;
import Sandy.*;

public class SimpleTopography extends KinectApplet {
	public void draw(short[][] mms, Graphics2D g) {
		int avg = getAverageDepth();

		int[] rgbDrawArray = new int[640*480];//array of pixels (in RGB, red green blue, format) to draw to the screen, red, greeg, and blue values are 8 bit unsigned values packed into a 4 byte integer, int

		int val;

		int i = 0;
		for(short[] row : mms) {
			for(short s : row) {
				val = s&0xffff;
				val = val>950&&val<1100 ? ((val-950)) : 0;
				rgbDrawArray[i]=rgb(val<50 ? val*255/50 : 0, val<100 && val>=50 ? (val-50)*255/50 : 0, val>=100 ? (val-100)*255/50 : 0);
				i++;
			}
		}

		setImage(g, rgbDrawArray);
	}
}