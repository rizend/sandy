package Sandy.applets;

import java.awt.Graphics2D;
import Sandy.*;

public class JustShowAverage extends KinectApplet {
	
	public void draw(short[][] depths, Graphics2D g) {
		int avg = getAverageDepth();
		g.drawString("Avg: ("+avg+")", 100, 100);
	}
}