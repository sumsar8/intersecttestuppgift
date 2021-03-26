import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This is a class
 * Created 2021-03-19
 *
 * @author Magnus Silverdal
 */
public class intersect extends Canvas implements Runnable{
    private int width = 1920;
    private int height = 1080;

    private Thread thread;
    int fps = 30;
    private boolean isRunning;

    private BufferStrategy bs;

    private int carX, carY, carVX, carVY;


    public intersect() {
        JFrame frame = new JFrame("A simple painting");
        this.setSize(width,height);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(new KL());
        frame.setVisible(true);

        isRunning = false;



        carX = 300;
        carY = 150;
        carVX = 0;
        carVY = 0;
    }

    public void update() {

        carX += carVX;
        carY += carVY;
    }

    public void draw() {
        bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        update();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,width,height);
        drawcar(g, carX,carY);
        drawcar(g, carX,carY);
        g.dispose();
        bs.show();
    }


    private void drawcar(Graphics g, int x, int y) {
        g.setColor(new Color(000));
        g.fillRect(x, y-40, 50, 40);
        g.setColor(new Color(0x444444));
        int[] xcoords = {x-5, x + 25, x + 55};
        int[] ycoords = {y-40, y - 65, y-40};
    }

    public static void main(String[] args) {
        intersect painting = new intersect();
        painting.start();
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        double deltaT = 1000.0/fps;
        long lastTime = System.currentTimeMillis();

        while (isRunning) {
            long now = System.currentTimeMillis();
            if (now-lastTime > deltaT) {
                update();
                draw();
                lastTime = now;
            }

        }
        stop();
    }

    private class KL implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == 'a') {
                carVX = -5;
            }
            if (keyEvent.getKeyChar() == 'd') {
                carVX = 5;
            }
            if (keyEvent.getKeyChar() == 'w') {
                carVY = -5;
            }
            if (keyEvent.getKeyChar() == 's') {
                carVY = 5;
            }
        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == 'a') {
                carVX = 0;
            }
            if (keyEvent.getKeyChar() == 'd') {
                carVX = 0;
            }
            if (keyEvent.getKeyChar() == 'w') {
                carVY = 0;
            }
            if (keyEvent.getKeyChar() == 's') {
                carVY = 0;
            }
        }
    }

    private class ML implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent mouseEvent) {

        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }

    private class MML implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {

        }
    }
}
