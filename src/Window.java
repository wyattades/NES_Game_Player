import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Window {

    public BufferStrategy bufferStrategy;

    public Window(int w, int h) {

        JFrame frame = new JFrame("Test");
        frame.setSize(260,250);
        JPanel panel = (JPanel) frame.getContentPane();
       // panel.setLayout(null);
        panel.setLayout(null);
        panel.setSize(new Dimension(w, h));

        Canvas canvas = new Canvas();
        canvas.setBounds(0, 0, w, h);
        canvas.setIgnoreRepaint(true);
        panel.add(canvas);

        //frame.setLocation(x+2*w, y);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(false);

        frame.add(new JLabel("test", SwingConstants.CENTER), BorderLayout.CENTER);
        frame.validate();

        frame.dispose();
        frame.setVisible(true);

        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(null);
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        //canvas.requestFocus();
    }

}