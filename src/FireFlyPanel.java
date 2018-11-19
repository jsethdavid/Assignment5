import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;


public class FireFlyPanel extends JPanel
{
    private final int DELAY = 200;
    private Timer timer = new Timer(100, new FireFlyPanel.RiverBankListener());
    ArrayList<FireFly> flies;
    private JButton addAFireFly;
    private JButton sync;
    double couplingStrength = 0.01D;
    boolean visible;

    public  FireFlyPanel()
    {
        this.setPreferredSize(new Dimension(350, 250));
        this.setBackground(Color.white);
        this.flies = new ArrayList();

        for(int i = 0; i < 5; ++i) {
            FireFly f = new FireFly(0.2D, Math.random() * 2.0D * 3.141592653589793D, (int)(Math.random() * 200.0D), 20 + (int)(Math.random() * 180.0D));
            this.flies.add(f);
        }

        this.sync = new JButton("Synchronize");
        this.sync.addActionListener(new FireFlyPanel.RiverBankListener());
        this.addAFireFly = new JButton("Add a new Fly");
        this.addAFireFly.addActionListener(new FireFlyPanel.RiverBankListener());
        this.add(this.sync);
        this.add(this.addAFireFly);
        this.visible = false;
        this.timer.start();
    }

    public void addFireFly(FireFly ff) {
        this.flies.add(ff);
    }

    public void setVisibility(boolean detect) {
        this.visible = detect;
    }

    public void advanceAll(double time) {
        Iterator var = this.flies.iterator();

        while(var.hasNext()) {
            FireFly ff = (FireFly)var.next();
            ff.advance(time);
        }

        if (this.visible) {
            this.updateFrequencies();
        }

    }

    public void updateFrequencies() {
        FireFly current;
        for(int i = 0; i < this.flies.size(); ++i) {
            current = (FireFly)this.flies.get(i);
            if (!current.isFlashing()) {
                double freq = current.getNaturalFrequency();
                double freqAdjustment = 0.0D;

                for(int j = 0; j < this.flies.size(); ++j) {
                    if (i != j) {
                        FireFly other = (FireFly)this.flies.get(j);
                        if (other.isFlashing()) {
                            freqAdjustment += Math.sin(other.getPhase() - current.getPhase());
                        }
                    }
                }

                freqAdjustment *= this.couplingStrength;
                current.changeFrequency(freq + freqAdjustment);
            }
        }

        Iterator var2 = this.flies.iterator();

        while(var2.hasNext()) {
            current = (FireFly)var2.next();
            current.checkPhase();
        }

    }

    public void paintComponent(Graphics page) {
        super.paintComponent(page);
        Iterator var3 = this.flies.iterator();

        while(var3.hasNext()) {
            FireFly firefly = (FireFly)var3.next();
            firefly.draw(page);
        }

    }

    private class RiverBankListener implements ActionListener {
        private RiverBankListener() { }

        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == FireFlyPanel.this.sync)
            {
                if (FireFlyPanel.this.visible)
                {
                    FireFlyPanel.this.visible = false;
                    FireFlyPanel.this.sync.setText("Synchronize");
                } else
                {
                    FireFlyPanel.this.visible = true;
                    FireFlyPanel.this.sync.setText("UnSynchronize");
                }
            } else if (event.getSource() == FireFlyPanel.this.addAFireFly)
            {
                FireFly f = new FireFly(0.2D, Math.random() * 2.0D * 3.141592653589793D, (int)(Math.random() * 200.0D), 20 + (int)(Math.random() * 180.0D));
                FireFlyPanel.this.flies.add(f);
            } else
            {
                FireFlyPanel.this.advanceAll(1.0D);
            }

            FireFlyPanel.this.repaint();
        }
    }
}

