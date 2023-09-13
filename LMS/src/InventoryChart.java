import javax.swing.*;
import java.awt.*;


public class InventoryChart {
    int[] stats;
    String title;
    public InventoryChart(String title, int[] stats){
        if (stats == null || stats.length == 0) {
            throw new IllegalArgumentException("Stats array must have at least one element");
        }
        this.title = title;
        this.stats = stats;
    }


    public int[] getStats() {
        return stats;
    }
    public String getTitle(){
        return title;
    }


    public void setStats(int[] stats){
        this.stats = stats;
    }


    public void setTitle(String title){
        this.title = title;
    }
    public void displayGraph() {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();


                int maxY = 1000;
                int incY = 100;


                int spacing = (width - 100) / stats.length;
                int barWidth = 50;


                g2.setFont(new Font("Arial", Font.PLAIN, 12));


                for (int i = 0; i < stats.length; i++) {
                    int x = 50 + i * spacing;
                    int barHeight = (int) (height * ((double) stats[i] / maxY));
                    if(i ==0){
                        g2.setColor(Color.BLUE);
                    }
                    else{
                        g2.setColor(Color.GREEN);


                    }
                    g2.fillRect(x, height - barHeight - 20, barWidth, barHeight);
                    String book;
                    if(i == 1){
                        book = "Nonfiction";


                    }
                    else{
                        book = "Fiction";


                    }
                    g2.setColor(Color.BLACK);




                    g2.drawString(book, x + 10, height - 5);
                }


                g2.setColor(Color.BLACK);


                for (int y = 0; y <= maxY; y += incY) {
                    int yPos = height - (int) (height * ((double) y / maxY)) - 20;
                    g2.drawString(Integer.toString(y), 10, yPos + 5);
                    g2.drawLine(45, yPos, width - 5, yPos);
                }
            }


            public Dimension getPreferredSize() {
                return new Dimension(450, 300);
            }
        };


        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
