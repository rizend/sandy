package Sandy;

import java.awt.Graphics2D;
import Sandy.applets.*;

public class RoyMenu extends KinectMenuApplet {
	public void draw(short[][] mms, Graphics2D g) {
		g.drawString("We are at the menu!", 100, 100);
	}

	public void click(int x, int y) {
		flashColor(rgb(255,100,100), 100);
	}

	int idx = 0;

	KinectApplet[] applets = new KinectApplet[] {
		this,
		new DynamicTopography(),
		new JustShowAverage(),
		new SimpleTopography(800),
	};

	static int loadColor = rgb(50, 150, 255);

	public void onHandRaise() {
		idx++;
		if(idx==applets.length)
			idx = 0;
		System.out.println(idx);
		loadApplet(applets[idx]);
		applets[idx].flashColor(loadColor, 300);
	}

	public String getName() {
		return "Roy's First Menu Attempt";
	}
}
