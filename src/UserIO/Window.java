package UserIO;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window {

    public BufferStrategy bufferStrategy;

    public Window(int x, int y, int w, int h) {

        JFrame frame = new JFrame("Test");
        frame.setLocation(x, y);
        frame.setSize(w, h);


        Canvas canvas = new Canvas();
        canvas.setBounds(0, 0, w, h);
        canvas.setIgnoreRepaint(true);

        JPanel panel = (JPanel) frame.getContentPane();
        panel.setLayout(null);
        panel.setSize(new Dimension(w, h));
        panel.add(canvas);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(false);
        frame.setFocusableWindowState(false);
        frame.add(new JLabel("test", SwingConstants.CENTER), BorderLayout.CENTER);
        frame.validate();
        frame.dispose();
        frame.setVisible(true);

        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
    }

}