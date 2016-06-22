package Sandy;

public interface KinectReference {
	/**
	* Method for getting the depth values for the sandbox.
	* @return a 2d, 640x480 array of the depths, from the bottom of the sandbox, approximated in millimeters
	*/
	public short[][] getDepths();
	public boolean readable();
}