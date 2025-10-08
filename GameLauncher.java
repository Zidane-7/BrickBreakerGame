import javax.swing.*;

public class GameLauncher {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");
        BrickBreaker game = new BrickBreaker();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
