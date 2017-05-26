package Sandy.applets;

import java.awt.Graphics2D;
import Sandy.*;

public class DynamicTopography extends KinectApplet {
	SimpleTopography simple = new SimpleTopography(950);

	protected void onSetup() {
		simple.setup(kr);
	}

	public void draw(short[][] mms, Graphics2D g) {
		simple._draw(g);
		simple.baseHeight = getAverageDepth()-50;
		g.drawString("Dynamic", 100, 100);
	}
}