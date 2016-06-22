package Sandy;

import org.openkinect.freenect.*;
import java.nio.ByteBuffer;

public class KinectDepthHandler implements DepthHandler, KinectReference {


	public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
		/*out.println("null checks: "+"<-mode frame->"+frame+" ts->"+timestamp);
		out.println(mode.dataBitsPerPixel+" + "+mode.paddingBitsPerPixel);
		out.println(mode.bytes);
		out.println(mode.width);
		out.println(mode.height);
		out.println(mode.framerate);
		out.println(mode.valid);
		*/
		frame.rewind();
		short[][] mms = new short[640][480];
		for(int x = 0; x<640; x++) {
			for(int y = 0; y<480; y++) {
				mms[x][y]=frame.getShort();
			}
		}
		depths=mms;
		//System.out.println(mms[320][240]);
	}
	short[][] depths=null;

	public boolean readable() {
		return depths!=null;
	}

	public short[][] getDepths() {
		return depths;
	}
}