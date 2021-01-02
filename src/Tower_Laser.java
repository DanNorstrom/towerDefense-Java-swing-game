import javax.swing.*;
import java.util.ArrayList;

public class Tower_Laser extends Tower {

    private int yC;
    private int xC;

    ImageIcon towerIcon = new ImageIcon("res/towers/BrownTriangle.png");
    private Monster target;
    String name;
    String description;

    private final int atsC;
    private final int dmgC;

    private int level;
    private int range;
    private int dmg = 25;
    private int ats;
    private int price = 50;
    private String type = "Single";

    public Tower_Laser(int lvl){
        this.level = lvl;
        //per level
        this.range += 2 + Math.round(0.33*lvl);
        this.dmg +=  25*lvl;
        this.dmgC =  25*lvl;
        this.ats += 8 + (int) Math.round(0.33*lvl);
        this.atsC = 8 + (int) Math.round(0.33*lvl);
        this.price += (lvl*100);

        this.name = "Fast Single Target("+type+"): Lv(" +lvl +")";
        this.description = "great tower for bosses \nDMG: "+dmg+"       AtkS: "+ats+"\nRange: " +range+"    Price: " +price;

    }

    @Override
    public Tower copy(){
        Tower newT = new Tower_Laser(this.getLVL());
        return newT;
    }



    @Override
    public int getLVL(){return this.level; }

    @Override
    public void clearTarget(){this.target = null;}

    @Override
    public void setTarget(Monster target){this.target = target;}

    @Override
    public Monster getTarget(){return target;};

    @Override
    public String getType(){return type;};

    @Override
    public int getDMG(){return dmg;};

    @Override
    public int getRange(){return range;};

    @Override
    public int getATS(){return ats;};

    @Override
    public void setATS(double ats){this.ats = (int) Math.round(this.atsC*ats);};

    @Override
    public void setDMG(double dmg){this.dmg = (int) Math.round(this.dmgC*dmg);};


    @Override
    public ImageIcon getIcon() {
        return towerIcon;
    }

    @Override
    public String getInfo() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getX() { return xC; }

    @Override
    public int getY() { return yC; }

    @Override
    public void setX(int xC) {
        this.xC = xC;
    }

    @Override
    public void setY(int yC) {
        this.yC = yC;
    }

    @Override
    public int getPrice() { return price; }

    public void attack() {

    }
}