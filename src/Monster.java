import javax.swing.*;
import java.awt.event.ActionListener;

public abstract class Monster {

    private int xC;
    private int yC;
    public abstract String getName();
    public abstract String getInfo();
    public abstract ImageIcon getIcon();

    public abstract int getX();
    public abstract int getY();
    public abstract int getV();
    public abstract void setX(int xC);
    public abstract void setY(int yC);
    public abstract void setV(int velocity);

    public abstract int getMaxHP();

    public abstract int getG();

    public abstract int getHP();
    public abstract void setHP(int xC);

    public abstract int getVC();

}
