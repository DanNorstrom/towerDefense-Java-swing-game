import javax.swing.*;
import java.util.ArrayList;

public class Tower_Basic extends Tower {

    private int yC;
    private int xC;

    ImageIcon towerIcon = new ImageIcon("res/towers/basicTower.png");
    private Monster target;
    String name;
    String description;

    private int level;
    private int range;
    private int dmg =1;
    private int ats = 5;
    private int price = 0;
    private String type = "Single";

    public Tower_Basic(int lvl){
        this.level = lvl;
        //per level
        this.range += 1 + Math.round(0.5*lvl);
        this.dmg += 2*lvl;
        this.ats += Math.round(0.4*lvl);
        this.price += (lvl*15);

        this.name = "Basic Tower ("+type+"): Lv(" +lvl +")";
        this.description = "Cost efficient (dps/price)/space. \nDMG: "+dmg+"       AtkS: "+ats+"\nRange: " +range+"    Price: " +price;

    }


        @Override
        public Tower copy(){
        Tower newT = new Tower_Basic(this.getLVL());
        return newT;
        }



    @Override
    public int getLVL(){ return this.level; }

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
    public void setATS(double ats){this.ats += (int) Math.round(this.ats*ats);};

    @Override
    public void setDMG(double dmg){this.dmg += (int) Math.round(this.dmg*dmg);};

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
