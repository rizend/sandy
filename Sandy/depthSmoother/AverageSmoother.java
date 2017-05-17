package Sandy.depthSmoother;

import Sandy.*;
import org.openkinect.freenect.*;
import java.nio.ByteBuffer;

public class AverageSmoother extends KinectDepthHandler {
	KinectDepthHandler dh;
	public AverageSmoother(KinectDepthHandler dh) {
		this.dh = dh;
	}

	public void onFrameReceived(FrameMode mode, ByteBuffer frame, int timestamp) {
		dh.onFrameReceived(mode,frame,timestamp);
	}

	public boolean readable() {
		return dh.readable();
	}

	short[][] avged = new short[640][480];
	short[][] lastSeen = null;

	void updateAvgs() {
		for(int i = 0; i<lastSeen.length; i++) {
			for(int j = 0; j<lastSeen[i].length; j++) {
				if(lastSeen[i][j]!=0) {
					if(avged[i][j]==0)
						avged[i][j]=lastSeen[i][j];
					else
						avged[i][j] = (short)((avged[i][j]>>1)+(lastSeen[i][j]>>1));
				}
			}
		}
	}

	public short[][] getDepths() {
		short[][] depths = dh.getDepths();
		if(depths!=lastSeen) {
			lastSeen = depths;
			updateAvgs();
		}
		return avged;
	}
}