import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadingScreen extends JFrame {

    private JPanel loadingPanel;
    private Timer timer;
    private int angle = 0;

    public LoadingScreen() {
        super("Loading Screen");
        
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null); 
        
        loadingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                int radius = Math.min(width, height) / 3;
                int centerX = width / 2;
                int centerY = height / 2;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.BLACK);
                
                int x = (int) (centerX + radius * Math.cos(Math.toRadians(angle)));
                int y = (int) (centerY + radius * Math.sin(Math.toRadians(angle)));
                g2d.setStroke(new BasicStroke(5));
                g2d.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
                g2d.drawLine(centerX, centerY, x, y);
            }
        };
        
        JLabel waitLabel = new JLabel("Please wait...", SwingConstants.CENTER);
        waitLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        waitLabel.setForeground(Color.BLACK);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(waitLabel, BorderLayout.NORTH);
        mainPanel.add(loadingPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        
        timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                angle = (angle + 5) % 360;
                loadingPanel.repaint();
            }
        });
        timer.start();
    }


    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoadingScreen loadingScreen = new LoadingScreen();
            loadingScreen.setVisible(true);
        });
    }
}
