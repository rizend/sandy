package Sandy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.DisplayMode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class KinectFullWindow extends JFrame {//KinectFullWindow

    KinectLoad kl;

    public KinectFullWindow(KinectLoad kl) {
        this.kl = kl;
        initUI();
    }

    KinectFullDrawer drawer;

    /**
    * Initializes and displays the the window on the second screen
    */
    public final void initUI() {
        drawer = new KinectFullDrawer();
        add(drawer);

        setTitle("Kinext Fullscreen Window");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        setSize(640, 480);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println(e);
                kl.click(e.getX(), e.getY());
            }
        });

        GraphicsDevice d = getSecondScreen();
        d.setFullScreenWindow(this);
        setVisible(true);
    }

    class KinectFullDrawer extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            kl.paintCB(g2d);
        }

        private void doDrawing(Graphics g) {
            long startT = System.currentTimeMillis();
            Graphics2D g2d = (Graphics2D) g;


            /*
            g2d.setColor(Color.blue);

            Dimension size = getSize();
            Insets insets = getInsets();

            int w = size.width - insets.left - insets.right;
            int h = size.height - insets.top - insets.bottom;

            for (int i = 0; i <= 10000; i++) {
                Random r = new Random();
                int x = Math.abs(r.nextInt()) % w;
                int y = Math.abs(r.nextInt()) % h;
                g2d.drawLine(x, y, x, y);
            }
            long endT = System.currentTimeMillis();
            System.out.println(endT-startT);
            */
        }

    }

    /**
    * Utility method to get the last screen; This is a hacky, but so far effective, method for getting a reference to the projector
    * @return last screen in the local GraphicsEnvironment
    */
    public static GraphicsDevice getSecondScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        return screens[screens.length-1];
    }
}