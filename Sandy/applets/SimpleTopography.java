package Sandy.applets;

import java.awt.Graphics2D;
import Sandy.*;

public class SimpleTopography extends KinectApplet {
	int baseHeight;
	int range = 150;

	public SimpleTopography(int baseHeght) {
		this.baseHeight = baseHeight;
	}

	public void draw(short[][] mms, Graphics2D g) {
		int avg = getAverageDepth();

		int[] rgbDrawArray = new int[640*480];//array of pixels (in RGB, red green blue, format) to draw to the screen, red, greeg, and blue values are 8 bit unsigned values packed into a 4 byte integer, int

		int val;
		int min = baseHeight;
		int max = min+range;
		min=1000;
		max=1150;

		int inRange = 0;

		int i = 0;
		for(short[] row : mms) {
			for(short s : row) {
				val = s&0xffff;
				//val = val>950&&val<1100 ? ((val-950)) : 0;
				val = val>min && val<max ? (val-min) : 0;
				//System.out.println(val+" "+(val>min)+" "+(val<max));
				if(val!=0)
					inRange++;
				//rgbDrawArray[i]=val==0 ? 0 : rgb(75,200, 225);//rgb(15,val==0 ? 0 : 255, val*255/150);
				rgbDrawArray[i]=rgb(val<50 ? val*255/50 : 0, val<100 && val>=50 ? (val-50)*255/50 : 0, val>=100 ? (val-100)*255/50 : 0);
				i++;
			}
		}

		//System.out.println(inRange);

		setImage(g, rgbDrawArray);
		g.drawString("         Avg: ("+avg+")", 100, 100);
	}
}