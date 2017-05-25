package Sandy;

import java.awt.Graphics2D;
import Sandy.applets.*;

public class RoyMenu extends KinectMenuApplet {
	public void draw(short[][] mms, Graphics2D g) {
		g.drawString("We are at the menu!", 100, 100);
	}

	int idx = 0;

	KinectApplet[] applets = new KinectApplet[] {
		this,
		new JustShowAverage(),
		new SimpleTopography(),
        new Drawing()
	};

	public void onHandRaise() {
		idx++;
		if(idx==applets.length)
			idx = 0;
		System.out.println(idx);
		loadApplet(applets[idx]);
	}

	public String getName() {
		return "Roy's First Menu Attempt";
	}
}
