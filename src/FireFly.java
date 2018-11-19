import java.awt.Color;
import java.awt.Graphics;

public class FireFly {
    public static double deltaTime = 1.0D;
    private final int SIZE = 25;
    private double phase;
    private double naturalFrequency;
    private double currentFrequency;
    private boolean flash;
    int x;
    int y;

    public FireFly(double naturalFrequency, double currentPhase, int xPos, int yPos) {
        this.naturalFrequency = naturalFrequency;
        this.currentFrequency = naturalFrequency;
        this.phase = currentPhase;
        this.x = xPos;
        this.y = yPos;
        this.flash = false;
    }

    public void advance(double time) {
        this.flash = false;
        double phaseDelta = this.currentFrequency * time * 2.0D * 3.141592653589793D;
        this.phase += phaseDelta;
        this.checkPhase();
    }

    public void checkPhase() {
        if (this.phase >= 6.283185307179586D) {
            this.flash = true;
            this.phase = 0.0D;
        }

    }

    public void changeFrequency(double newFreq) {
        this.currentFrequency = newFreq;
    }

    public double getNaturalFrequency() {
        return this.naturalFrequency;
    }

    public double getPhase() {
        return this.phase;
    }

    public boolean isFlashing() {
        return this.flash;
    }

    public void draw(Graphics page)
    {
        if (this.isFlashing())
        {
            page.setColor(Color.yellow);
            page.fillRect(this.x, this.y, 25, 75);
        } else
            {
                page.setColor(Color.blue);
             page.fillRect(this.x, this.y, 25, 25);
            }

    }
}
