
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

public abstract class Tower {

    private int xC;
    private int yC;
    private String type;
    public abstract String getType();
    public abstract void setTarget(Monster target);
    public abstract Monster getTarget();

    public abstract String getName();
    public abstract String getInfo();
    public abstract ImageIcon getIcon();

    public abstract int getX();
    public abstract int getY();
    public abstract void setX(int xC);
    public abstract void setY(int yC);

    public abstract int getPrice();

    public abstract int getATS();
    public abstract void setATS(double ats);
    public abstract void setDMG(double dmg);

    public abstract int getRange();
    public abstract int getDMG();
    public abstract void clearTarget();

    private Monster target;

    public abstract int getLVL();

    public abstract Tower copy();

}
