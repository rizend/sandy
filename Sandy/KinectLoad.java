package Sandy;

import org.openkinect.freenect.*;
import java.awt.Graphics2D;
import static java.lang.System.out;

//@see: https://github.com/OpenKinect/libfreenect/blob/ec0b75dd0ee6d510294335cc4cdfee9375a4bde3/wrappers/java/src/main/java/org/openkinect/freenect/Device.java

public class KinectLoad implements LogHandler {
	private static Context ctx=null;/** A Freenect context initialized in main that allows us to interact with libfreekinect */
	private static KinectLoad inst=null;/** The instance of KinectLoad that actually handles running everything. */

	public static void main(String[] args) {
		ctx = Freenect.createContext();

		int numDevices = ctx.numDevices();
		System.out.println("There are "+numDevices+" devices!");
		if(numDevices>1) {
			System.out.println("We currently only support having one device, a multitude of devices is currently a fatal error.");
			System.exit(-1);
		} else if(numDevices==0) {
			System.out.println("Initializing in offline mode!");
		}
 
		//pass the device numbern
		inst = new KinectLoad(numDevices-1);

		//set the log handler as the KinectLoad instance
		ctx.setLogLevel(LogLevel.DEBUG);
		ctx.setLogHandler(inst);

		//run the program
		inst.run();
	}

	public void onMessage(Device dev, LogLevel level, String msg) {
		out.print(level+": "+msg);
	}

	int deviceNumber;
	Device device;
	boolean offlineMode = false;
	KinectReference dHandler;
	KinectMenuApplet menu = new RoyMenu();
	KinectApplet current = null;
	KinectApplet next = menu;

	private KinectLoad(int num) {
		this.deviceNumber = num;
		if(num==-1)
			offlineMode=true;
	}

	public void loadApplet(KinectApplet ka) {
		if(next!=null)
			next=ka;
	}

	private void runDrawCycle() {
		if(next!=null) {
			if(current!=null)
				current.remove();

			if(dHandler==null)
				System.out.println("??");

			if(next instanceof KinectMenuApplet)
				((KinectMenuApplet)next).setup(dHandler, this);
			else
				next.setup(dHandler);

			current = next;
			next = null;
		}

		if(current==null) {
			loadApplet(menu);
			return;
		}

		if(gui!=null) {
			gui.repaint();
		}
	}

	void paintCB(Graphics2D g2d) {
		if(current==null)
			return;

		try {
			if(!current.draw(g2d)) {
				loadApplet(menu);
			}
		} catch(Exception e) {
			e.printStackTrace();
			if(menu==current) {
				System.exit(-1);//we fail on error in menu
			} else {
				loadApplet(menu);//exit applet to menu if it failed
			}
		}
	}

	void click(int x, int y) {
		x-=150;
		y-=150;
		if(x>=0 && y>=0 && x<640 && y<480) {
			System.out.println("("+x+","+y+") => "+dHandler.getDepths()[x][y]);
		}
	}

	private void run() {
		initDevice();
		initGUI();
		while(true) {
			runDrawCycle();
			try {
				Thread.sleep(30);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void initDevice() {
		if(offlineMode) {
			dHandler = (new OfflineKinect()).reset();
			device = null;
			System.out.println("Offline Kinect depth handler registed.");

			return;
		}

		int ret=0;
		//use the context to open the given device:
		device = ctx.openDevice(deviceNumber);
		//construct a new Depth Handler
		dHandler = new KinectDepthHandler();
		//set the depth format to 16 bits per pixel in millimeters (the onFrameRecieved callback gets all null values if this is not set)
		device.setDepthFormat(DepthFormat.MM);
		//Make the kinect's led blink se we know that we are connected
		device.setLed(LedStatus.BLINK_RED_YELLOW);

		if( (ret=device.startDepth((DepthHandler)dHandler))!=0 ) {
			System.out.println("Attempting to start the depth handler had a non-zero return of "+ret+"; this may indicate a failuire of some sort.");
		}

		out.println("Depth Handler Registered");
	}

	KinectFullWindow gui;


	private void initGUI() {
		gui = new KinectFullWindow(this);
	}
}