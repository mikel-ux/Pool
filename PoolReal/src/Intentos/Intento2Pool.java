
package Intentos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Intento2Pool extends JPanel {

    private int ball1X, ball1Y, ball2X, ball2Y;
    private double ball1SpeedX, ball1SpeedY, ball2SpeedX, ball2SpeedY;
    private Timer timer;
    private static int ballSize = 20;
    private boolean isBall1Moving, isBall2Moving;

    public Intento2Pool() {
        ball1X = 100;
        ball1Y = 200;
        ball2X = 300;
        ball2Y = 200;
        ball1SpeedX = 5;
        ball1SpeedY = 2;
        ball2SpeedX = -5;
        ball2SpeedY = -2;
        isBall1Moving = false;
        isBall2Moving = false;
        setBackground(Color.green);
        timer = new Timer(20, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                moveBalls();
                checkCollision();
                repaint();
            }
        });
        timer.start();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isBall1Moving) {
                    double mouseX = e.getX();
                    double mouseY = e.getY();
                    double ballX = ball1X + ballSize / 2;
                    double ballY = ball1Y + ballSize / 2;
                    double direction = Math.atan2(mouseY - ballY, mouseX - ballX);
                    int initialSpeed = 5;
                    ball1SpeedX = initialSpeed * Math.cos(direction);
                    ball1SpeedY = initialSpeed * Math.sin(direction);
                    isBall1Moving = true;
                }
            }
        });
    }

    private void moveBalls() {
        ball1X += ball1SpeedX;
        ball1Y += ball1SpeedY;
        ball2X += ball2SpeedX;
        ball2Y += ball2SpeedY;
        ball1SpeedX *= 0.99; // Reducción de velocidad
        ball1SpeedY *= 0.99; // Reducción de velocidad
        ball2SpeedX *= 0.99; // Reducción de velocidad
        ball2SpeedY *= 0.99; // Reducción de velocidad
        
        if(ball1SpeedX<=0 && ball1SpeedY<=0){
            isBall1Moving = false;
        }
        if(ball2SpeedX<=0 && ball2SpeedY<=0){
            isBall2Moving = false;
        }
    }

    private void checkCollision() {
        double distance = Math.sqrt((ball1X - ball2X) * (ball1X - ball2X) + (ball1Y - ball2Y) * (ball1Y - ball2Y));

        if (distance < 20) {
            double dx = ball2X - ball1X;
            double dy = ball2Y - ball1Y;
            double collisionAngle = Math.atan2(dy, dx);

            double speed1 = Math.sqrt(ball1SpeedX * ball1SpeedX + ball1SpeedY * ball1SpeedY);
            double speed2 = Math.sqrt(ball2SpeedX * ball2SpeedX + ball2SpeedY * ball2SpeedY);

            double direction1 = Math.atan2(ball1SpeedY, ball1SpeedX);
            double direction2 = Math.atan2(ball2SpeedY, ball2SpeedX);

            double newSpeed1 = speed2;
            double newSpeed2 = speed1;

            ball1SpeedX = (int) (newSpeed1 * Math.cos(collisionAngle));
            ball1SpeedY = (int) (newSpeed1 * Math.sin(collisionAngle));
            ball2SpeedX = (int) (newSpeed2 * Math.cos(collisionAngle));
            ball2SpeedY = (int) (newSpeed2 * Math.sin(collisionAngle));                                                             
        }

        if (ball1X < 0 || ball1X > getWidth()) {
            ball1SpeedX *= -1;
        }
        if (ball1Y < 0 || ball1Y > getHeight()) {
            ball1SpeedY *= -1;
        }
        if (ball2X < 0 || ball2X > getWidth()) {
            ball2SpeedX *= -1;
        }
        if (ball2Y < 0 || ball2Y > getHeight()) {
            ball2SpeedY *= -1;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.RED);
        g2.fillOval(ball1X, ball1Y, ballSize, ballSize);

        g2.setColor(Color.BLUE);
        g2.fillOval(ball2X, ball2Y, ballSize, ballSize);

        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pool Game");
        Intento2Pool poolGame = new Intento2Pool();
        frame.add(poolGame);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
