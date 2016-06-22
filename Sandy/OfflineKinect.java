package Sandy;

public class OfflineKinect implements KinectReference {
	short[][] depths=null;

	short[][] onThread = new short[640][480];

	final static short startVal = 800;

	boolean grabbed = true;

	public OfflineKinect reset() {
		for(int r = 0; r<onThread.length/2; r++) {
			for(int c = 0; c<onThread[r].length; c++) {
				onThread[r][c]=startVal-50;
			}
		}
		for(int r = onThread.length/2; r<onThread.length; r++) {
			for(int c = 0; c<onThread[r].length; c++) {
				onThread[r][c]=startVal+50;
			}
		}

		run();
		return this;
		//@todo: make this a thread
	}

	short tenShort() {
		return (short)((Math.random()-0.5)*10);
	}

	void updateDepths() {
		short val, dif;
		for(int r = 0; r<onThread.length; r++) {
			for(int c = 0; c<onThread[r].length; c++) {
				val=onThread[r][c];
				if(val>1000) {
					val-=10;
				} else if(val<600) {
					val+=10;
				} else {
					val+=tenShort();
				}
				onThread[r][c]=val;
			}
		}
		for(int r = 1; r+1<onThread.length; r++) {
			for(int c = 1; c+1<onThread[r].length; c++) {
				val=onThread[r][c];
				dif=0;

				dif+=(onThread[r+1][c+1]-val)/2;
				dif+=(onThread[r+1][c+0]-val)/2;
				dif+=(onThread[r+1][c-1]-val)/2;
				dif+=(onThread[r+0][c+1]-val)/2;
				dif+=(onThread[r+0][c-1]-val)/2;
				dif+=(onThread[r-1][c+1]-val)/2;
				dif+=(onThread[r-1][c+0]-val)/2;
				dif+=(onThread[r-1][c-1]-val)/2;
				
				val+=dif/8;

				onThread[r][c]=val;
			}
		}
	}

	void copyDepths() {
		short[][] n = new short[640][480];
		for(int r = 0; r<onThread.length; r++) {
			for(int c = 0; c<onThread[r].length; c++) {
				n[r][c]=onThread[r][c];
			}
		}
		depths = n;
	}

	public void run() {
		if(grabbed) {
			updateDepths();
			copyDepths();
			grabbed = false;
		} else {
			try {
				Thread.sleep(30);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean readable() {
		return depths!=null;
	}

	public short[][] getDepths() {
		grabbed = true;
		run();
		return depths;
	}
}