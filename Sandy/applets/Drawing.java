package Sandy.applets;

import java.awt.Graphics2D;
import Sandy.*;

public class Drawing extends KinectApplet {
	public void draw(short[][] mms, Graphics2D g) {
		int avg = getAverageDepth();

		int[] rgbDrawArray = new int[640*480];//array of pixels (in RGB, red green blue, format) to draw to the screen, red, greeg, and blue values are 8 bit unsigned values packed into a 4 byte integer, int

		int color1 = rgb(255, 0, 0) //Red
		int color2 = rgb(0, 0, 255) //Blue

		int val;

		int i = 0;
		for(short[] row : mms) {
			for(short s : row) {
				rgbDrawArray[i]= (s < avg) ? color1 : color2;
				i++;
			}
		}

		setImage(g, rgbDrawArray);
	}
}