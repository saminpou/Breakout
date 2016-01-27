import javax.swing.JFrame;

/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Main {
    static private Model model;
    static private View view;
    //static private TitleView title;
    public static void main(String[] args) {

        model = new Model(Integer.parseInt(args[1]));
        view = new View(model);
        view.FPS = Integer.parseInt(args[0]);
        JFrame frame = new JFrame("Brick Breaker");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(view, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
        		view.updateSize(e.getComponent().getWidth(), e.getComponent().getHeight());
            }
        });

        
    }
}
