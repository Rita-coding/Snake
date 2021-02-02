package snake;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class Window extends JFrame {

    Window(){

        GamePanel panel = new GamePanel();
        this.add(panel);

        setSize(600,600);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("File");

        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("High Scores") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighScoreWindow(panel.getHighScores(), Window.this);
            }
        });
        
        JMenuItem menuNewGame = new JMenuItem(new AbstractAction("New Game") {
            @Override
            public void actionPerformed(ActionEvent e) {
        
               panel.startGame();
            }
        });

        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        
        menuGame.add(menuNewGame);
        menuGame.add(menuHighScores);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);

        this.setResizable(false);

        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
