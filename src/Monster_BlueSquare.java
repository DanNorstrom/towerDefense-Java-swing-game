import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Monster_BlueSquare extends Monster {
    private int xC = 66;
    private int yC = 66;

    String name = "BlueSquare";
    String description = "The most cost efficient dps/price per space";
    ImageIcon monsterIcon = new ImageIcon("res/monsters/BlueSquareMonster.png");

    private int MAX_HP;
    private int HP;
    private int velocity;
    private int gold;

    private final int velocityC;


    public Monster_BlueSquare(Integer lvl){
        this.HP = 30 + (lvl *20);
        this.MAX_HP = 30 + (lvl *20);
        this.velocity = 2 + (int) Math.round((lvl/20));
        this.velocityC  = 2 + (int) Math.round((lvl/20));
        this.gold = 1 + lvl/10;
    }

    @Override
    public int getVC(){return this.velocityC;}

    @Override
    public int getMaxHP() {
        return MAX_HP;
    }

    @Override
    public ImageIcon getIcon() {
        return monsterIcon;
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
    public int getX() {
        return xC;
    }

    @Override
    public int getY() {
        return yC;
    }

    @Override
    public void setX(int xC) {
        this.xC = xC;
    }

    @Override
    public void setY(int yC) {
        this.yC = yC;
    }

    @Override
    public void setV(int velocity) { this.velocity = velocity; }

    @Override
    public int getV() { return velocity; }

    @Override public int getG() { return gold; }

    @Override
    public void setHP(int dmg) { this.HP -= dmg; }

    @Override
    public int getHP() { return HP; }
}