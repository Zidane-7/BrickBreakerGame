import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.sql.Time;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class BrickBreaker extends JPanel implements ActionListener, KeyListener{
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final Timer timer;
    private boolean play = false;
    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballDirX = -3;
    private int ballDirY = -4;
    private int paddlePosX = 310;
    private final int PADDLE_WIDTH = 100;
    private final int PADDLE_HEIGHT = 15;
    private final int BALL_SIZE = 20;
    private int score = 0;
    private int level = 1;
    private final int ROWS = 4;
    private final int COLS = 8;
    private final BrickGame[][] bricks = new BrickGame[ROWS][COLS];
    private boolean gameOver = false;
    public BrickBreaker()
    {
addKeyListener(this);
setFocusable(true);
setFocusTraversalKeysEnabled(false);
timer = new Timer(10, this);
timer.start();

for (int row = 0; row < ROWS; row++) {
    for (int col = 0; col < COLS; col++) {
        Color[] BRICK_COLORS = {
            Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.cyan,Color.MAGENTA
        };
        Color brickColor = BRICK_COLORS[row % BRICK_COLORS.length];
        int BRICK_HEIGHT = 30;
        int BRICK_SPACE = 10;
        int BRICK_WIDTH = 80;
        int TOTAL_BRICK_WIDTH = COLS * (BRICK_WIDTH + BRICK_SPACE);
        int REMAINING_SPACE = WIDTH - TOTAL_BRICK_WIDTH;
        bricks[row][col] = new BrickGame(row, col, BRICK_WIDTH, BRICK_HEIGHT, REMAINING_SPACE/2, brickColor);

    }
}
    }
    public void paint(Graphics g)
    {
        g.setColor(Color.BLACK);//Background
        g.fillRect(0,0,WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.BOLD, 20));//Score
        g.drawString("Score "+score, 10, 25);

        g.setColor(Color.WHITE);
        g.fillRect(paddlePosX, HEIGHT-PADDLE_HEIGHT-30, PADDLE_WIDTH, PADDLE_HEIGHT);//paddle

        g.setColor(Color.yellow);
        g.fillOval(ballPosX, ballPosY, BALL_SIZE, BALL_SIZE);


        for(int row = 0; row<ROWS; row++)
        {
            for (int col = 0; col < COLS; col++) {
                bricks[row][col].draw(g);
                
            }
        }

        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, 3);
        g.fillRect(0, 0, 3, HEIGHT);
        g.fillRect(WIDTH-3, 0, 3, HEIGHT);

        if (gameOver)
        {
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Game Over", 300, 300);

            g.setFont(new Font("Serif", Font.BOLD, 20));
            g.drawString("Press Space to Restart", 265, 350);

        }
        g.dispose();

    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        timer.start();
        if(play)
        {
            if(new Rectangle(ballPosX, ballPosY, BALL_SIZE, BALL_SIZE).intersects(new Rectangle(paddlePosX, HEIGHT-PADDLE_HEIGHT-30, PADDLE_WIDTH, PADDLE_HEIGHT)))
            {
                ballDirY = -ballDirY;
            }
            if(ballPosY> HEIGHT)
            {
                gameOver=true;
                play=false;
            }
            outerLoop:
            for(int row = 0; row<ROWS; row++)
            {
                for (int col = 0; col < COLS; col++) {
                    BrickGame brick = bricks[row][col];
                    if(brick.isVisible() && brick.getRect().intersects(new Rectangle(ballPosX, ballPosY, BALL_SIZE, BALL_SIZE)))
                    {
                        brick.setVisible(false);
                        score+=10;
                        ballDirY = -ballDirY;
                        break outerLoop;
                    }
                    
                }   
            }
            if(allBricksDestroyed())
            {
                level++;
                for(int row = 0; row<ROWS; row++)
                {
                    for (int col = 0; col < COLS; col++) {
                        bricks[row][col].setVisible(true);
                    }
                }
                ballPosX=120;
                ballPosY=350;
                ballDirX=-3;
                ballDirY=-4;

            }
            ballPosX += ballDirX;
            ballPosY += ballDirY;
            if(ballPosX<0) ballDirX=-ballDirX;
            if(ballPosY<0) ballDirY=-ballDirY;
            if(ballPosX>WIDTH-BALL_SIZE) ballDirX=-ballDirX;
            repaint();
    }
  
}
    private boolean allBricksDestroyed() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (bricks[row][col].isVisible()) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            if(paddlePosX >= WIDTH - PADDLE_WIDTH)
            {
                paddlePosX = WIDTH - PADDLE_WIDTH;
            }
            else paddlePosX += 20;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            if(paddlePosX <= 0)
            {
                paddlePosX = 0;
            }
            else
            paddlePosX -= 20;
            
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            play=!play;
        }
        if(gameOver && e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            resetGame();
        }

    }
    private void resetGame() {
        
        ballPosX = 120;
        ballPosY = 350;
        ballDirX = -3;
        ballDirY = -4;
        paddlePosX = 310;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                bricks[row][col].setVisible(true);
            }
        }
        score = 0;
        level = 1;
        gameOver = false;
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

    
}
