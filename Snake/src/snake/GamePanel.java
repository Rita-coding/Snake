package snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public final class GamePanel extends JPanel implements ActionListener {

    static final int WIDTH = 600;
    static final int HEIGHT = 600;
    static final int SIZE = 25;
    static final int UNITS = (WIDTH*HEIGHT)/(SIZE*SIZE);
    static final int DELAY = 150;
    final int x[] = new int[UNITS];
    final int y[] = new int[UNITS];
    static final int ROCKS = 5;
    int rockX[] = new int[ROCKS];
    int rockY[] = new int[ROCKS];
    int bodyParts = 2;
    int fruitsEaten;
    int fruitX;
    int fruitY;
    char direction;
    boolean running = false;
    boolean panel = false;
    Timer timer;
    Random random;
    Database database;
    
    static int miliSec = 0;
    static int seconds = 0;
    static int minutes = 0;
    

    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.database = new Database();
        startGame();
    }

    public void startGame(){
        newFruit();
        drawRock();
        startSnake();
        running = true;
        elapsedTime();
        timer = new Timer(DELAY,this);
        timer.start();
    }
    
    public void elapsedTime(){
        
        Thread t = new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    if (running) {
                        try {
                            sleep(1);

                            if (miliSec > 1000) {
                                miliSec = 0;
                                seconds++;
                            }
                            if (seconds > 60) {
                                miliSec = 0;
                                minutes = 0;
                                minutes++;
                            }
                            miliSec++;

                            //System.out.println(miliSec);
                            //System.out.println(seconds);
                            //System.out.println(minutes);
                        } catch (InterruptedException e) {
                            System.out.println(e);
                        }
                    } else {
                        break;
                    }
                }
            }
        };
        t.start();

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);

    }

    public void startSnake(){
        bodyParts = 2;
        fruitsEaten = 0;
        miliSec = 0;
        seconds = 0;
        minutes = 0;
        
        x[0] =  (int)(WIDTH/2);
        y[0] =  (int)(HEIGHT/2);

        char select[] = {'R','L', 'U', 'D'};

        direction = select[random.nextInt(4)];

    }
    public void draw(Graphics g){

        if(running) {

            g.setColor(Color.red);
            g.fillOval(fruitX, fruitY, SIZE, SIZE);

            for (int i = 0; i<ROCKS; i++){
                g.setColor(Color.orange);
                g.fillRect(rockX[i], rockY[i], SIZE, SIZE);
            }
            

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], SIZE, SIZE);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], SIZE, SIZE);
                }
            }

            g.setColor(Color.green);
            g.setFont(new Font("Ink Free", Font.BOLD,10));
            g.drawString("Score: "+ fruitsEaten, 100,10);

            g.drawString("Time: " + minutes + ":" + seconds + ":" + miliSec, 0,10);

        }else{
            gameOver(g);
        }
    }

    public void newFruit(){
        fruitX = random.nextInt(WIDTH/SIZE)*SIZE;
        fruitY = random.nextInt(HEIGHT/SIZE)*SIZE;
        //System.out.println(fruitX);
    }



    public void drawRock(){
        //TODO kicserélni for ciklusra
        
        for(int i=0; i<ROCKS; i++){
            rockX[i] = random.nextInt(WIDTH/SIZE)*SIZE;
            rockY[i] = random.nextInt(WIDTH/SIZE)*SIZE;
        }
    }

    public void move(){
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0] - SIZE;
                break;
            case 'D':
                y[0] = y[0] + SIZE;
                break;
            case 'L':
                x[0] = x[0] - SIZE;
                break;
            case 'R':
                x[0] = x[0] + SIZE;
                break;
        }
    }

    public void checkFruit(){
        if((x[0] == fruitX) && (y[0] == fruitY)){
            bodyParts++;
            fruitsEaten++;
            newFruit();
        }
    }

    public void checkCollision(){
        //with rocks

        for(int i=0; i<ROCKS; i++){
            if((x[0] == rockX[i] && y[0] == rockY[i])){
                running = false;
            }
        }

        //with body
        for(int i = bodyParts; i>0; i--){
            if((x[0] == x[i]) && (y[0] ==y[i])){
                running = false;
            }
        }
        //System.out.println(x[0]);
        //left wall
        if(x[0] < 0){
            running = false;
        }

        //right wall
        if(x[0] >= WIDTH){
            running = false;
        }

        //top wall
        if(y[0] < 0){
            running = false;
        }

        //bottom wall
        if(y[0] >= HEIGHT){
            running = false;
        }

        if(!running){
            timer.stop();
            panel = true;
        }

    }

    public void gameOver(Graphics g){

        g.setColor(Color.green);
        g.setFont(new Font("Ink Free", Font.BOLD,75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over!", (WIDTH - metrics2.stringWidth("Game Over'"))/2, HEIGHT/2);

        g.setFont(new Font("Ink Free", Font.BOLD,10));
        g.drawString("Score: "+ fruitsEaten, 100,10);

        g.drawString("Time: " + minutes + ":" + seconds + ":" + miliSec, 0,10);
    
    }
    
    public ArrayList<HighScore> getHighScores() {
        database.loadHighScores();
        return database.getHighScores();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkFruit();
            checkCollision();
        }
        repaint();
        
        if(!running){
           String name = (String)JOptionPane.showInputDialog(
               this,
               "Enter your name!", 
               "Name",            
               JOptionPane.PLAIN_MESSAGE,
               null,            
               null, 
               "Anonimus"
            );
           database.storeToDatabase(name, fruitsEaten);
           //database.loadHighScores();
        
       }
    }


    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void  keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_A:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
