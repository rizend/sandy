# Sandy's Development Environment

## Login Credentials

Username: ```sandy```
Password: ```Sandy'sPassword!```

## Development

All files are located in ```~/Dev/```, all commands will be run from this directory unless otherwise noted.

Compiling: ```./compile.sh```

Running: ```./run.sh```

## File Structure (Relative to ~/Dev)

 * run.sh : shell script to run the project
 * compile.sh : shell script to compile the project
 * README.md : this file
 * updateDrive.sh : shell script that pulls the Sandy folder from google drive
 * libs/ : directory containing any necessary libraries or java library wrappers
   * freenect.jar : sym-link to the compiled libfreenect java wrapper with dependencies included
   * libfreenect/ : the libfreenect library
     * wrappers/java/
       * the java wrapper for libfreenect
 * Sandy/ : Sandy java source and compiled class files
   * KinectLoad.java : The main class that initializes the kinect if available, and the GUI
   * KinectApplet.java : The applet interface for a program that will run with the kinect data
   * KinectMenuApplet.java : The interface for applications that wish to be able to load other applets.
   * KinectReference.java : The interface allowing an applet to fetch the depth values.
   * KinectDepthHandler.java : Implements a call back from libfreenect that gives it the depth, it then stores that depth in a thread-safe manner to be used by KinectApplets through the KinectReference interface.
   * OfflineKinect.java : An implementation of KinectReference used in offline mode.
   * KinectFullWindow.java : Implements the actual Swing GUI on the last available display.
   * RoyMenu.java : A Menu Applet that doesn't act as a menu as of yet.

## TODO

 * Clean-up and comment the non-interface files.
 * Finish the resizing
 * Looking at RoyMenu.java, consider what really belongs in the KinectApplet, and what ought to be done elsewhere
   * I think the whole flipping the image, transforming it and writing it to the screen should be done for the applet.
     * This probably requires changes to the KinectApplet interface (return int[] of colors?)
 * The depth data needs to be massaged / transformed so it is mm from the bottom.
 * Provide a way to easily load applets.
 * Find a better way to get applets onto the computer.
 * Nicer, ideally processing-based interface.
