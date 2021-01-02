import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class Monster_WaveBoss extends Monster {
    private int xC = 66;
    private int yC = 66;

    String name = "RedDot";
    String description = "a red lill cutie";
    private ImageIcon monsterIcon;

    private int MAX_HP;
    private int HP;
    private int velocity;
    private int gold;

    private final int velocityC;


    public Monster_WaveBoss(Integer lvl, int type){
        this.HP = (lvl *50);
        this.MAX_HP = (lvl *50);
        this.velocity = 2 + (int)(Math.random() * 3);
        this.velocityC  = velocity;
        this.gold = lvl*10;

        switch (type) {
            case 1:
                //System.out.println("Adding spawn: BlueSquareMonster");
                this.monsterIcon = new ImageIcon("res/monsters/BossGreen.png");
            case 2:
                //System.out.println("Adding spawn: RedCircleMonster");
                this.monsterIcon = new ImageIcon("res/monsters/BossYellow.png");
            case 3:
                //System.out.println("Adding spawn: GreenTriangle");
                this.monsterIcon = new ImageIcon("res/monsters/BossBlue.png");
    }}

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

    @Override
    public int getG() {return gold; }


    @Override
    public void setHP(int dmg) { this.HP -= dmg; }

    @Override
    public int getHP() { return HP; }

}