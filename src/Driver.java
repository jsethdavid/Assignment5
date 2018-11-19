import javax.swing.JFrame;

public class Driver
{
    public Driver()
    {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FireFlies");
        frame.setDefaultCloseOperation(3);
        frame.getContentPane().add(new FireFlyPanel());
        frame.pack();
        frame.setVisible(true);
    }
}

