import javax.swing.*;
import javax.swing.ImageIcon;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class Game extends JLayeredPane {

    // GameState (Left side)
    JPanel grid_panel;                                                  // panel used to display grid panels
    JPanel[][] grid_array;                                              // array used to store panels in grid
    static ArrayList<Monster> monster_list = new ArrayList<>();         // Monsters that are spawned
    static ArrayList<Tower> tower_list = new ArrayList<>();             // Bought/placed towers
    static int wavecount = 0;                                           // current wave being spawned
    static Game CurrentGame;                                            // Game object
    static JPanel monster_panel;                                        // JPanel where all rendering is done

    // Shop (right side)
    JPanel menu_panel;                                                  // Main window for shop/info
    JPanel title_score;                                                 // holds gold, score and enemy count
    JPanel inventory_grid_panel;                                        // holds tower shop
    static JButton[][] inventory_grid_array;                            // holds tower shops items
    JPanel tower_info;                                                  // selected shop tower info
    JTextArea tower_status;                                             // tower description
    JPanel tower_pic_holder;                                            // tower picture
    static JLabel goldTxt = new JLabel("Gold:");                   // initialize gold
    static JLabel scoreTxt= new JLabel("Score:");                  // initialize score
    static JLabel monsterCountTxt= new JLabel("Enemies:");         // initialize Count of monsters
    ArrayList<Tower> tower_shop_list = new ArrayList<>();               // towers put into the shop

    // Data & Functionality
    static int gold;                                                    // ingame Gold
    static int score = 0;                                               // score per round
    static int monsterCount;                                            // count of monsters in monster_list

    static Tower selected_shop_tower = new Tower_Basic(1);          // shows tower info on bottom-right and selects for building
    static Tower previous_selected_shop_tower;                          // double click shop to buy/place towers

    static int selected_shop_x;                                         // Highlight shop
    static int selected_shop_y;                                         // Highlight shop

    static int selected_grid_x = 34;                                    // Highlight grid
    static int selected_grid_y = 34;                                    // Highlight grid
    static boolean selected_grid_place = false;                         // highlight type

    static boolean newGame;                                             // false stops threads
    static boolean endGame;                                             // used with threads

    static Thread RAT;                                                  // Attack Data shift Thread
    static Thread RRT;                                                  // Graphic paint() render Thread
    static Thread RWT;                                                  // Wave spawn Thread -> elaborates with MonsterSpawn class
    static JFrame GameHolder;                                           // Main Window

    // Image data
    ImageIcon iconCorner = new ImageIcon("res/activeSprites/tile492.png");
    ImageIcon iconGrass = new ImageIcon("res/activeSprites/tile289.png");
    ImageIcon iconPath = new ImageIcon("res/activeSprites/tile396.png");
    ImageIcon iconWell = new ImageIcon("res/activeSprites/tile095.png");
    ImageIcon iconDarkTile = new ImageIcon("res/towers/darkTile.png");
    ImageIcon iconSellTower = new ImageIcon("res/towers/sell.png");
    static ImageIcon iconPlaceOK = new ImageIcon("res/monsters/placeOK.png");
    static ImageIcon iconPlaceNO = new ImageIcon("res/monsters/placeNO.png");
    static ImageIcon iconBackGround = new ImageIcon("res/background/Screenshot_1.png");


    static ImageIcon icon123456M = new ImageIcon("res/menu/123456M2.png");
    static ImageIcon iconqweasdM = new ImageIcon("res/menu/QWEASDM.png");
    static ImageIcon icont0 = new ImageIcon("res/menu/t0.png");
    static ImageIcon icont1 = new ImageIcon("res/menu/t1.png");
    static ImageIcon icont2 = new ImageIcon("res/menu/t2.png");
    static ImageIcon icont3 = new ImageIcon("res/menu/t3.png");


    public void BackgroundPaint(int x, int y, boolean tower, ImageIcon icon) {
        grid_array[x][y] = new JPanel() {
    /*
            private int xC = x;
            private int yC = y;
            private boolean place = tower;

            public int getX(){ return xC;}
            public int getY(){ return yC;}
            public boolean getPlace(){ return place;}

     */

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(icon.getImage(), 0, 0, null);
            }
        };
        grid_array[x][y].setPreferredSize(new Dimension(32, 32));
    }


    void GameCanvas() {
        // if grid already drawn (reinitialise rather than initialise) remove grid from frame and delete it
        // this canvas is printscreened later as a background draw, while retaining click trough functionality.
        if (grid_panel != null) {
            this.remove(grid_panel);
            grid_panel = null;
        }

        //##Panel Structure (right side)
        // top panel
        menu_panel = new JPanel(new BorderLayout());

        title_score = new JPanel(new GridLayout(1, 2));
        title_score.add(goldTxt);
        title_score.add(scoreTxt);
        title_score.add(monsterCountTxt);
        title_score.setPreferredSize(new Dimension(300, 50));
        menu_panel.add(title_score, BorderLayout.NORTH);

        // Middle Panel
        inventory_grid_panel = new JPanel(new GridLayout(6, 6, 1, 1));
        inventory_grid_array = new JButton[6][6];    // loop further down adds this to menu_panel
        menu_panel.add(inventory_grid_panel, BorderLayout.CENTER);

        // initialize shop items.
        tower_shop_list.add(new Tower_Basic(1));
        tower_shop_list.add(new Tower_Basic(2));
        tower_shop_list.add(new Tower_Basic(3));
        tower_shop_list.add(new Tower_Basic(4));
        tower_shop_list.add(new Tower_Basic(5));
        tower_shop_list.add(new Tower_Basic(6));

        tower_shop_list.add(new Tower_RedTank(1));
        tower_shop_list.add(new Tower_RedTank(2));
        tower_shop_list.add(new Tower_RedTank(3));
        tower_shop_list.add(new Tower_RedTank(4));
        tower_shop_list.add(new Tower_RedTank(5));
        tower_shop_list.add(new Tower_RedTank(6));

        tower_shop_list.add(new Tower_MultiStar(1));
        tower_shop_list.add(new Tower_MultiStar(2));
        tower_shop_list.add(new Tower_MultiStar(3));
        tower_shop_list.add(new Tower_MultiStar(4));
        tower_shop_list.add(new Tower_MultiStar(5));
        tower_shop_list.add(new Tower_MultiStar(6));

        tower_shop_list.add(new Tower_Laser(1));
        tower_shop_list.add(new Tower_Laser(2));
        tower_shop_list.add(new Tower_Laser(3));
        tower_shop_list.add(new Tower_Laser(4));
        tower_shop_list.add(new Tower_Laser(5));
        tower_shop_list.add(new Tower_Laser(6));

        tower_shop_list.add(new Tower_SlowAura(1));
        tower_shop_list.add(new Tower_SlowAura(2));
        tower_shop_list.add(new Tower_SlowAura(3));

        tower_shop_list.add(new Tower_ATSAura(1));
        tower_shop_list.add(new Tower_ATSAura(2));
        tower_shop_list.add(new Tower_ATSAura(3));

        tower_shop_list.add(new Tower_DMGAura(1));
        tower_shop_list.add(new Tower_DMGAura(2));
        tower_shop_list.add(new Tower_DMGAura(3));

        // paints, adds listeners and buttons to the shop interface
        int towerShopIter = 0;
        for (int x = 0; x < inventory_grid_array.length; x++) {
            for (int y = 0; y < inventory_grid_array[x].length; y++) {

                if (tower_shop_list.size() > towerShopIter) {

                    Tower currentT = (tower_shop_list.get(towerShopIter));
                    ImageIcon iconIteration = currentT.getIcon();
                    towerShopIter += 1;


                    // add graphical IconImages to shopButton
                    inventory_grid_array[x][y] = new JButton() {

                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.drawImage(iconDarkTile.getImage(), 2, 2, 48, 48, null);
                            g.drawImage(iconIteration.getImage(), 2, 2, 48, 48, null);
                            g.setColor(Color.WHITE);
                            g.drawString("Lv:"+currentT.getLVL(),21,43);

                        }
                    };


                    // Selecting tower in shop, adds it to selected global variable.
                    inventory_grid_array[x][y].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            if (selected_shop_tower != null){
                            previous_selected_shop_tower = selected_shop_tower;}
                            selected_shop_tower = currentT;

                            // logic to place this tower
                            if (previous_selected_shop_tower.getName() == selected_shop_tower.getName()){
                                // Can we afford a tower?
                                if (gold >= selected_shop_tower.getPrice()){
                                    goldTxt.setForeground(Color.BLACK);

                                    boolean checkCoord = true;
                                    for (Tower t: tower_list){
                                        System.out.println("tower: "+t.getName());
                                        if ((t.getX()==selected_grid_x) && (t.getY() == selected_grid_y)){
                                            checkCoord = false;
                                        }
                                    }
                                    // is there a tower from before? can we place a tower here?
                                    if (checkCoord && selected_grid_place){
                                        gold -= currentT.getPrice();

                                        //initialize tower values
                                        try{
                                            Tower newT = currentT.copy();
                                            newT.setX(selected_grid_x);
                                            newT.setY(selected_grid_y);
                                            tower_list.add(newT);}
                                        catch(Exception eee){ System.out.println(eee.getMessage()); }
                                    }

                                }
                                else{
                                    // if not enough gold, flash money color red
                                        goldTxt.setForeground(Color.RED);
                                }
                            }

                            System.out.println("currently selected " + selected_shop_tower.getName());
                            tower_status.setText("Name: " + selected_shop_tower.getName() + "\nInfo: " + selected_shop_tower.getInfo());


                            tower_pic_holder = new JPanel() {
                                @Override
                                protected void paintComponent(Graphics g) {
                                    super.paintComponent(g);
                                    g.drawImage(iconDarkTile.getImage(), 0, 0, 75, 75, null);
                                    g.drawImage((selected_shop_tower.getIcon()).getImage(), 0, 0, 75, 75, null);

                                }
                            };
                            tower_pic_holder.setPreferredSize(new Dimension(75, 75));
                            tower_info.add(tower_pic_holder, BorderLayout.WEST);
                        }



                    });
                                       //
                    inventory_grid_array[x][y].setFocusable(false);
                    inventory_grid_panel.add(inventory_grid_array[x][y]);
                }

                // add special shop items
                else if ((x ==5) && (y ==5)){
                    // sell items

                    inventory_grid_array[x][y] = new JButton() {

                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.drawImage(iconSellTower.getImage(), 2, 2, 48, 48, null);

                        }
                    };


                    // Selecting tower in shop, adds it to selected global variable.
                    inventory_grid_array[x][y].addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            // is there a tower selected?
                            for (Tower T: tower_list){
                                if ((T.getX()==selected_grid_x) && (T.getY() == selected_grid_y)){
                                    // return half gold, sell tower(remove)
                                    gold += T.getPrice()/2;
                                    tower_list.remove(T);
                                    break;
                                }
                            }

                            // paint shop info
                            tower_pic_holder = new JPanel() {
                                @Override
                                protected void paintComponent(Graphics g) {
                                    super.paintComponent(g);
                                    g.drawImage(iconSellTower.getImage(), 0, 0, 75, 75, null);
                                }
                            };
                            tower_status.setText("you may sell towers for half the \ninitial value");
                            tower_pic_holder.setPreferredSize(new Dimension(75, 75));
                            tower_info.add(tower_pic_holder, BorderLayout.WEST);
                        }
                    });
                    inventory_grid_array[x][y].setFocusable(false);
                    inventory_grid_panel.add(inventory_grid_array[x][y]);
                    }



                // if we have empty buttons left
                else {
                    inventory_grid_array[x][y] = new JButton();
                    inventory_grid_array[x][y].setEnabled(false);
                    inventory_grid_array[x][y].setFocusable(false);
                    inventory_grid_panel.add(inventory_grid_array[x][y]);

                }
            }
        }

        // Bottom Panel
        tower_info = new JPanel(new BorderLayout());
        tower_status = new JTextArea("Click a tower to get information!");

        tower_pic_holder = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(iconDarkTile.getImage(), 0, 0, 75, 75, null);
            }
        };

        // this needs to get a picture from Listeners // set global var and cahnge
        tower_pic_holder.setPreferredSize(new Dimension(75, 75));
        tower_status.setPreferredSize(new Dimension(220, 75));
        tower_status.setEditable(false);
        tower_status.setDragEnabled(false);
        tower_status.setLineWrap(true);
        tower_info.add(tower_pic_holder, BorderLayout.WEST);
        tower_info.add(tower_status, BorderLayout.EAST);
        menu_panel.add(tower_info, BorderLayout.SOUTH);

        // Panel middle seperator
        JPanel seperate_panel = new JPanel();
        JPanel seperate_panel2 = new JPanel();

        // Panel structure (left side)
        grid_panel = new JPanel(new GridLayout(13, 15, 1, 1));
        grid_array = new JPanel[13][15];

        // for loop to create & paint grid for Gamespace
        for (int x = 0; x < grid_array.length; x++) {
            for (int y = 0; y < grid_array[x].length; y++) {

                // Paint TowerTiles
                BackgroundPaint(x, y,true, iconGrass);

                // Paint Border
                if (((x == 0) || ((y == 0))) || (y == grid_array[0].length - 1) || (x == grid_array.length - 1)) {
                    grid_array[x][y] = new JPanel();
                    grid_array[x][y].setBackground(Color.BLACK);
                }

                // paint monster path
                if (((x == 2) && ((y >= 2 && y < grid_array[0].length - 2))) || ((x == grid_array.length - 3) && ((y >= 2 && y < grid_array[0].length - 2))) || ((y == 2) && ((x >= 2 && x < grid_array.length - 2))) || ((y == grid_array[0].length - 3) && ((x >= 2 && x < grid_array.length - 2)))) {
                    BackgroundPaint(x, y,false, iconPath);
                }

                // paint monster path corners
                if (((x == 2) && (y == 2)) || ((x == grid_array.length - 3) && (y == 2)) || ((y == grid_array[0].length - 3) && (x == 2)) || ((y == grid_array[0].length - 3) && (x == grid_array.length - 3))) {
                    BackgroundPaint(x, y,false, iconCorner);
                }

                // paint option towers (Unique bc layering)
                if (x == 6 && y == 6) {
                    BackgroundPaint(x, y,false, iconCorner);
                }

                if (x == 6 && y == 7) {
                    grid_array[x][y] = new JPanel() {

                        /*
                        private int xC = 0; // removable here and down mayby?
                        private int yC = 0;

                        @Override
                        public int getX(){ return xC;}
                        @Override
                        public int getY(){ return yC;}

                         */

                        @Override
                        protected void paintComponent(Graphics g) {
                            //call to super resets current drawings
                            g.drawImage(iconGrass.getImage(), 0, 0, null);
                            g.drawImage(iconWell.getImage(), 0, 0, null);
                        }
                    };
                    grid_array[x][y].setPreferredSize(new Dimension(32, 32));
                }

                if (x == 6 && y == 8) {
                    BackgroundPaint(x, y,false, iconCorner);
                }


                // unique mouse listener per panel to determine which panel was clicked
                grid_array[x][y].addMouseListener(new MouseClickListener(this));
                grid_panel.add(grid_array[x][y]);
            }
        }


        monster_panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(iconBackGround.getImage(),0,0,496,430,null);

                // render monsters
                for (int i = 0; i < monster_list.size(); i++) {
                    g.drawImage(monster_list.get(i).getIcon().getImage(), monster_list.get(i).getX(), monster_list.get(i).getY(),32,32, null);

                    try {
                        // draw health bar
                        Monster M = monster_list.get(i);
                        double HPPer = (double) 40 / M.getMaxHP();        //hp per pixel (max 50 pixels)
                        double HPpart = M.getHP() * HPPer;              //

                        double currentHP = ((double)M.getHP() / (double)M.getMaxHP());

                        // healthbar Colors
                        if (currentHP < 0.33) {
                            g.setColor(Color.RED);
                            g.drawLine(M.getX() - 4, M.getY() - 5, M.getX() + (int) HPpart, M.getY() - 5);
                        }
                        else if (currentHP < 0.66) {
                            g.setColor(Color.YELLOW);
                            g.drawLine(M.getX() - 4, M.getY() - 5, M.getX() + (int) HPpart, M.getY() - 5);
                        }
                        else if (currentHP >= 0.66) {
                            g.setColor(Color.GREEN);
                            g.drawLine(M.getX() - 4, M.getY() - 5, M.getX() + (int) HPpart, M.getY() - 5);
                        }

                    } catch (ArithmeticException ee) {
                        System.out.println(ee.getMessage());
                    }
                }

                // render towers
                for (int i = 0; i < tower_list.size(); i++) {
                    g.drawImage(tower_list.get(i).getIcon().getImage(), tower_list.get(i).getX(), tower_list.get(i).getY(),32,32, null);

                    //render tower attacks
                    try {
                        Tower D = tower_list.get(i);
                        Monster M = D.getTarget();

                        int h = D.getY() - M.getY();
                        int w = D.getX() - M.getX();
                        int distance = (int) Math.sqrt((h*h)+(w*w));
                        int range = (D.getRange()*33);

                        // shot first monster
                        if ((distance > range) || (M.getHP() <= 0)) {
                            D.clearTarget();}
                        else{
                            g.setColor(Color.BLACK);
                            g.drawLine(D.getX() + 16, D.getY() + 16, M.getX() + 16, M.getY() + 16);
                        }
                    }
                    catch (NullPointerException ee){
                    }
                }

                // render grid highlight
                if (selected_grid_place){
                    g.drawImage(iconPlaceOK.getImage(),selected_grid_x, selected_grid_y,32,32, null);
                }

                else{
                    g.drawImage(iconPlaceNO.getImage(),selected_grid_x, selected_grid_y,32,32, null);
                }

                // render tower range (placed towers)
                boolean selectNoTower = true;
                for (Tower T: tower_list){
                    if ((T.getX() == selected_grid_x) && (T.getY() == selected_grid_y) && selected_grid_place){
                        int range = (int) (T.getRange()*33)*2;
                        int offset = range/2;
                        g.setColor(Color.BLACK);
                        g.drawOval(T.getX()-offset+16,T.getY()-offset+16,range,range);
                        selectNoTower = false;
                    }

                }

                // render tower range (other grids based on shop item selected)
                if(selectNoTower && selected_grid_place){
                    // render tower range (from shop)
                    int range = (int) (selected_shop_tower.getRange()*33)*2;
                    int offset = range/2;
                    g.setColor(Color.BLACK);
                    g.drawOval(selected_grid_x-offset+16,selected_grid_y-offset+16,range,range);
                }
            }
        };
        monster_panel.setBackground(new Color(0, 0, 0, 0));
        monster_panel.setBounds(0, 0, 496, 430);

        // key listener to respond to key events
        monster_panel.addKeyListener(new KeyboardListener(this));
        monster_panel.setFocusable(true);
        this.add(monster_panel,4);

        // add panel to frame
        menu_panel.setPreferredSize(new Dimension(300, 430));
        grid_panel.setPreferredSize(new Dimension(496, 430));

        seperate_panel.setPreferredSize(new Dimension(10, 430));
        seperate_panel2.setPreferredSize(new Dimension(10, 430));

        grid_panel.setBounds(0, 0, 496, 430);
        menu_panel.setBounds(504, 0, 300, 430);

        this.add(grid_panel, 5);
        this.add(menu_panel, 5);
        this.add(seperate_panel, 5);
        this.add(seperate_panel2, 5);
    }



    public Game() {
        // set up game environment
        GameCanvas();
        //end line
        setVisible(true);
    }


    private static class RunRender implements Runnable{
        public void run() {
            while (!endGame) {
                //add monster_panel layer to game window, renders all monsters sent to monster_list all the time

                int dead = 0;
                //update monster location for next iteration
                for (int i = 0; (i - dead) < (monster_list.size() - dead); i++) {

                    // move living monsters
                    // top left moving down
                    if ((monster_list.get(i).getY() < 336) && (monster_list.get(i).getX() <= 66)) {
                        monster_list.get(i).setY(monster_list.get(i).getY() + monster_list.get(i).getV());
                    }
                    // bottom left moving right
                    if ((monster_list.get(i).getY() >= 336) && (monster_list.get(i).getX() < 400)) {
                        monster_list.get(i).setX(monster_list.get(i).getX() + monster_list.get(i).getV());
                    }
                    // bottom right moving up
                    if ((monster_list.get(i).getY() > 66) && (monster_list.get(i).getX() >= 400)) {
                        monster_list.get(i).setY(monster_list.get(i).getY() - monster_list.get(i).getV());
                    }
                    // top right moving left
                    if ((monster_list.get(i).getY() <= 66) && (monster_list.get(i).getX() > 66)) {
                        monster_list.get(i).setX(monster_list.get(i).getX() - monster_list.get(i).getV());
                    }

                    // remove dead monsters and update score and gold
                    if (monster_list.get(i).getHP() <= 0) {
                        gold += monster_list.get(i).getG();
                        score += monster_list.get(i).getG();

                        monster_list.remove(i);
                        dead += 1;
                    }
                }
                monster_panel.repaint();

                //update gold, score, count
                goldTxt.setText("Gold: " + gold);
                scoreTxt.setText("Score: " + score);
                monsterCountTxt.setText("Wave: "+ wavecount);
                monsterCount = monster_list.size();
                monsterCountTxt.setText("Enemies: " + monsterCount + "/30");

                if (monsterCount > 30) {
                    endGame = true;
                    System.out.println("end game "+endGame);
                }

                //update Placement allowance
                // Paint Border
                int x = selected_grid_x;
                int y = selected_grid_y;
                if (((x == 1) || ((y == 1))) || (y == 397) || (x == 463)) {
                    // dont allow
                    selected_grid_place = false;
                }

                // paint monster path
                else if (((x == 67) && ((y >= 67 && y < 331))) || ((x == 397) && ((y >= 67 && y <= 331))) || ((y == 67) && ((x >= 67 && x < 397))) || ((y == 331) && ((x >= 67 && x < 397)))) {
                    // dont allow
                    selected_grid_place = false;
                }


                // paint option towers (Unique bc layering)
                else if (x == 199 && y == 199) {
                    // dont allow
                    selected_grid_place = false;
                } else if (x == 232 && y == 199) {
                    // dont allow
                    selected_grid_place = false;
                } else if (x == 265 && y == 199) {
                    // dont allow
                    selected_grid_place = false;
                } else {
                    // allow
                    selected_grid_place = true;
                }

                try { Thread.sleep(30);         // number decides game speed
                } catch (InterruptedException e) { }

            }
            return;
        }
    }

    //wave spawn
    private static class RunWave implements Runnable{
        public void run() {
            while (!endGame) {

                try { Thread.currentThread().sleep(10000); } // 10 sec
                catch (InterruptedException e) { }


                // stack list to add new monsters from the top of a stack
                wavecount += 1;
                System.out.println(">>>Spawning wave: " + wavecount);
                // wave generates amount and levels of enemies
                ArrayList<Integer> wave_spawn_ID = new ArrayList<>();
                ArrayList<Integer> wave_spawn_LVL = new ArrayList<>();

                // summon monster first wave to monster_list
                if (wavecount <= 10) {
                    for (int i = 0; i < (5 + (int) Math.round((wavecount / 5))); i++) {
                        int type = 1 + (int) (Math.random() * 2);             // only type 1 and 2
                        int lvl = 1 + (int) (Math.random() * 2);             // only lvl 1,2
                        wave_spawn_ID.add(type);
                        wave_spawn_LVL.add(lvl);
                    }
                }

                if (wavecount > 10 && wavecount < 20) {
                    for (int i = 0; i < (5 + (int) Math.round((wavecount / 4))); i++) {
                        int type = 1 + (int) (Math.random() * 3);             // type 1,2,3
                        int lvl = 2 + (int) (Math.random() * 5);              // lvl 2,3,4,5,6,7
                        wave_spawn_ID.add(type);
                        wave_spawn_LVL.add(lvl);
                    }
                }

                if (wavecount >= 20) {
                    for (int i = 0; i < ((int) Math.round((wavecount / 2))); i++) {
                        int type = 1 + (int) (Math.random() * 4);                                         // type 1,2,3,4
                        int lvl = (int) (wavecount / 2) + (int) (Math.random() * (int) (wavecount));       // lvl wave/4 +1
                        wave_spawn_ID.add(type);
                        wave_spawn_LVL.add(lvl);
                    }
                }

                if (wavecount % 10 == 0) {
                    // add boss to wave
                    wave_spawn_ID.add(10);
                    wave_spawn_LVL.add(wavecount);
                } else { // placeHolder
                    for (int i = 0; i < (5 + (int) Math.round((wavecount / 5))); i++) {
                        int type = 1 + (int) (Math.random() * 2);             // only type 1 and 2
                        int lvl = 2 + (int) (Math.random() * 10);             // only lvl1 mobs
                        wave_spawn_ID.add(type);
                        wave_spawn_LVL.add(lvl);
                    }
                }

                new MonsterSpawn(wave_spawn_ID, wave_spawn_LVL, CurrentGame);
                wave_spawn_ID.clear();             //reset map for next wave
                wave_spawn_LVL.clear();

            }
            return;
        }
    }

    private static class RunAttack implements Runnable{
        public void run() {
            while (!endGame) {

                for (int i = 1; i <= 10; i++) {

                    try {
                        for (Tower D : tower_list) {
                            // you may attack.1 attakcs one a second, 10, 10 times
                            if (D.getATS() >= i) {

                                if (D.getType() == "Single") {
                                    for (Monster M : monster_list) {
                                        //find monsters in towers range
                                        int h = D.getY() - M.getY();
                                        int w = D.getX() - M.getX();
                                        int distance = (int) Math.sqrt((h * h) + (w * w))-16;
                                        int range = (D.getRange() * 33);

                                        // shot first monster
                                        try {
                                            if (distance < range) {
                                                M.setHP(D.getDMG());
                                                D.setTarget(M);
                                                //System.out.println(D.getName()+" attacks "+M.getName());
                                                break;
                                            }
                                        } catch (NullPointerException eeee) {
                                        }

                                    }
                                }

                                if (D.getType() == "AOE") {
                                    for (Monster M : monster_list) {
                                        //find monsters in towers range
                                        int h = D.getY() - M.getY();
                                        int w = D.getX() - M.getX();
                                        int distance = (int) Math.sqrt((h * h) + (w * w))-16;
                                        int range = (D.getRange() * 33);

                                        // shot first monster
                                        try {
                                            if (distance < range) {
                                                D.setTarget(M);
                                                M.setHP(D.getDMG());    // full dmg to primary target

                                                // deals dmg to monsters close to it
                                                for (Monster MM : monster_list) {
                                                    if ((Math.abs(MM.getX() - M.getX()) < 50) && ((Math.abs(MM.getY() - M.getY()) < 50))) {
                                                        // deal dmg to nearby monsters
                                                        MM.setHP(D.getDMG() / 2); // half dmg for aoe targets
                                                    }
                                                }
                                                System.out.println(D.getName() + " attacks " + M.getName());
                                                break;
                                            }
                                        } catch (NullPointerException eeee) {
                                        }
                                    }
                                }

                                if (D.getType() == "Multi") {
                                    for (Monster M : monster_list) {
                                        //find monsters in towers range
                                        int h = D.getY() - M.getY();
                                        int w = D.getX() - M.getX();
                                        int distance = (int) Math.sqrt((h * h) + (w * w))-16;
                                        int range = (D.getRange() * 33);

                                        // shot first monster
                                        try {
                                            if (distance < range) {
                                                D.setTarget(M);         // this needs to be turned into a list to give proper graphics
                                                M.setHP(D.getDMG());    //deal dmg to all targets in range
                                                System.out.println(D.getName() + " attacks " + M.getName());
                                            }
                                        } catch (NullPointerException eeee) {
                                        }
                                    }
                                }

                                if (D.getType() == "AuraSlow") {
                                    for (Monster M : monster_list) {
                                        //find monsters in towers range
                                        int h = D.getY() - M.getY();
                                        int w = D.getX() - M.getX();
                                        int distance = (int) Math.sqrt((h * h) + (w * w))-16;
                                        int range = (D.getRange() * 33);

                                        // shot first monster
                                        try {
                                            if (distance < range) {
                                                M.setHP(D.getDMG());    //deal dmg to all targets in range

                                                M.setV(M.getVC() - D.getLVL());        // slows more for higher lvl towers
                                                if (M.getV() < 1) {
                                                    M.setV(1);
                                                }    // cant slow beneath one speed
                                                System.out.println(D.getName() + " Slows " + M.getName() + "to velocity: " + M.getV());
                                            } else {
                                                // return velocity if out of range
                                                M.setV(M.getVC());
                                            }
                                        } catch (NullPointerException eeee) {
                                        }
                                    }
                                }

                                if (D.getType() == "AuraDMG") {
                                    for (Tower T : tower_list) {
                                        //find monsters in towers range
                                        int h = D.getY() - T.getY();
                                        int w = D.getX() - T.getX();
                                        int distance = (int) Math.sqrt((h * h) + (w * w))-16;
                                        int range = (D.getRange() * 33);

                                        // shot first monster
                                        try {
                                            if (distance < range) {
                                                T.setDMG((double) D.getLVL() * 0.20); // 20% more dmg per lvl
                                            }
                                        } catch (NullPointerException eeee) {
                                        }
                                    }
                                }

                                if (D.getType() == "AuraATS") {
                                    for (Tower T : tower_list) {
                                        //find monsters in towers range
                                        int h = D.getY() - T.getY();
                                        int w = D.getX() - T.getX();
                                        int distance = (int) Math.sqrt((h * h) + (w * w)) -16;
                                        int range = (D.getRange() * 33);

                                        // shot first monster
                                        try {
                                            if (distance < range) {
                                                T.setATS((double) D.getLVL() * 0.20); // 20% more ats per lvl
                                            }
                                        } catch (NullPointerException eeee) {
                                        }
                                    }
                                }
                            }
                        }
                        //break iteration
                        try { Thread.currentThread().sleep(100);
                        } catch (InterruptedException e) { }

                    } catch (ConcurrentModificationException eee) {
                        System.out.println(eee.getMessage());
                    }
                }
            }
            return;
        }
    }


    static Thread threadMenu = new Thread() {
        public void run() {
            while (true) {

                // stop game if more then 30 monsters
                while (endGame || newGame) {
                    //stop thread activity
                    tower_list.clear();
                    monster_list.clear();
                    wavecount = 0;

                    if (endGame) {
                        JOptionPane.showMessageDialog(null, "Well done! The Game has ended! Your Score is: " + score);
                        endGame = false;
                        newGame = true;
                        if (score != 0){
                            // class for score reporting
                            ReportScore.addScore(score+"");
                        }
                    }


                    Object[] choices = {"Start Game", "GameGuide","Show Score", "Quit"};
                    Object defaultChoice = choices[0];
                    int option = JOptionPane.showOptionDialog(GameHolder,
                            "What would you like to do?",
                            "Title message",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            choices,
                            defaultChoice);

                    if (option == 0) {
                        JOptionPane.showMessageDialog(GameHolder, "Let's start the game for you!");

                        // Re Initialize game
                        tower_list.clear();
                        monster_list.clear();
                        gold = 80;
                        score = 0;

                        //get out of loop
                        newGame = false;
                        endGame = false;

                        //restart threads
                        try {
                            RunAttack RA = new RunAttack();
                            RAT = new Thread(RA);
                            RAT.start();

                            RunRender RR = new RunRender();
                            RRT = new Thread(RR);
                            RRT.start();

                            RunWave RW = new RunWave();
                            RWT = new Thread(RW);
                            RWT.start();

                        } catch (Exception ee){};
                    }


                     if (option == 1) {
                         JOptionPane.showMessageDialog(GameHolder, new JLabel(icont0));
                        JOptionPane.showMessageDialog(GameHolder, new JLabel(iconqweasdM));
                        JOptionPane.showMessageDialog(GameHolder, new JLabel(icon123456M));
                        JOptionPane.showMessageDialog(GameHolder, new JLabel(icont1));
                        JOptionPane.showMessageDialog(GameHolder, new JLabel(icont3));
                        JOptionPane.showMessageDialog(GameHolder, new JLabel(icont2));
                     }


                    if (option == 2) {
                        // call score class and show it
                        ArrayList<Integer> scoreList = ReportScore.ReturnScore();
                        String scoreDOC = "";
                        for (int i = 0; i < scoreList.size(); i++) {
                            scoreDOC += "" + (i + 1) + ": " + scoreList.get(i) + "\n";
                        }

                            JOptionPane.showMessageDialog(
                                    GameHolder,
                                    "Allright! Here is your Highscore!\n\n" + scoreDOC);
                        }

                    else if (option == 3) {
                        JOptionPane.showMessageDialog(GameHolder, "Thanks for playing. (Quitting the Game)");
                        System.exit(0);
                    }

                }

                try { Thread.sleep(30);}
                catch (InterruptedException e) { }
            }
        }
    };



    public static void main(String[] args) {


        // Initialize Game Window
        GameHolder = new JFrame();
        GameHolder.setResizable(false);
        GameHolder.setSize(new Dimension(830, 470));

        // Initialize Game
        CurrentGame = new Game();
        GameHolder.addKeyListener(new KeyboardListener(CurrentGame));
        GameHolder.add(CurrentGame);

        // Game Configuration
        GameHolder.setTitle("Dan Norstrom, DN18657, 1807572");
        GameHolder.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        GameHolder.setVisible(true);
        GameHolder.setFocusable(true);
        GameHolder.requestFocusInWindow();
        newGame = true;

        // Thread Handler.
        threadMenu.start();
}}
